package com.vid.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.vid.commons.MatroskaAttachment;
import com.vid.comp.Jcomp.AbstractComp;
import com.vid.comp.Jcomp.BoundedRectangle;
import com.vid.comp.Jcomp.Title;

import javafx.beans.value.ChangeListener;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import uk.co.caprica.vlcj.player.direct.DirectMediaPlayer;

public class ButtonForController {

	private static final int SKIP_TIME_MS = 10 * 1000;

	public static void initializeButton(VideoEditorController controller, Button button) {

		final Delta ogPosition = new Delta(button.getLayoutX(), button.getLayoutY());

		button.setOnDragDetected(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				Dragboard db = button.startDragAndDrop(TransferMode.MOVE);
				ClipboardContent cc = new ClipboardContent();
				cc.putString(ogPosition.x + " " + ogPosition.y);
				db.setContent(cc);
				// Java 8 only:
				db.setDragView(button.snapshot(null, null));
			}
		});

		button.setOnDragOver(new EventHandler<DragEvent>() {
			@Override
			public void handle(DragEvent event) {
				event.acceptTransferModes(TransferMode.MOVE);
			}
		});
	}

	public static BoundedRectangle createDraggableRectangle(AbstractComp comp, double x, double y, double width,
			double height) {
		final double handleRadius = 4;

		BoundedRectangle rect = new BoundedRectangle(x, y, width, height);
		rect.setFill(Color.TRANSPARENT);
		rect.setStroke(Color.BLACK);

		// bottom right resize handle:
		Circle resizeHandleSE = new Circle(handleRadius, Color.GOLD);
		// bind to bottom right corner of Rectangle:
		resizeHandleSE.centerXProperty().bind(rect.xProperty().add(rect.widthProperty()));
		resizeHandleSE.centerYProperty().bind(rect.yProperty().add(rect.heightProperty()));

		// move handle:
		Circle moveHandle = new Circle(handleRadius, Color.GOLD);
		if (!(comp instanceof Title)) {
			// bind to bottom center of Rectangle:
			moveHandle.centerXProperty().bind(rect.xProperty().add(rect.widthProperty().divide(2)));
			moveHandle.centerYProperty().bind(rect.yProperty().add(rect.heightProperty()));
		}

		// force circles to live in same parent as rectangle:
		rect.parentProperty().addListener((obs, oldParent, newParent) -> {
			// for (Circle c : Arrays.asList(resizeHandleNW, resizeHandleSE,
			// moveHandle)) {
			for (Circle c : Arrays.asList(resizeHandleSE, moveHandle)) {
				Pane currentParent = (Pane) c.getParent();
				if (currentParent != null) {
					currentParent.getChildren().remove(c);
				}
				((Pane) newParent).getChildren().add(c);
			}
		});

		Wrapper<Point2D> mouseLocation = new Wrapper<>();

		// setUpDragging(resizeHandleNW, mouseLocation);
		setUpDragging(resizeHandleSE, mouseLocation);
		if (!(comp instanceof Title)) {
			setUpDragging(moveHandle, mouseLocation);
		}
		/*
		 * resizeHandleNW.setOnMouseDragged(event -> { if (mouseLocation.value
		 * != null) { double deltaX = event.getSceneX() -
		 * mouseLocation.value.getX(); double deltaY = event.getSceneY() -
		 * mouseLocation.value.getY(); double newX = rect.getX() + deltaX; if
		 * (newX >= handleRadius && newX <= rect.getX() + rect.getWidth() -
		 * handleRadius) { rect.setX(newX); rect.setWidth(rect.getWidth() -
		 * deltaX); } double newY = rect.getY() + deltaY; if (newY >=
		 * handleRadius && newY <= rect.getY() + rect.getHeight() -
		 * handleRadius) { rect.setY(newY); rect.setHeight(rect.getHeight() -
		 * deltaY); } mouseLocation.value = new Point2D(event.getSceneX(),
		 * event.getSceneY()); } });
		 */

		resizeHandleSE.setOnMouseDragged(event -> {
			if (mouseLocation.value != null) {
				double deltaX = event.getSceneX() - mouseLocation.value.getX();
				double deltaY = event.getSceneY() - mouseLocation.value.getY();
				double newMaxX = rect.getX() + rect.getWidth() + deltaX;
				System.out.println(newMaxX + " " + rect.getX() + " " + rect.getParent().getBoundsInLocal().getWidth()
						+ " " + handleRadius + " " + rect.getParent());
				if (newMaxX >= rect.getX()
						&& newMaxX <= rect.getParent().getBoundsInLocal().getWidth() - handleRadius) {
					rect.setWidth(rect.getWidth() + deltaX);
				} else if (newMaxX <= rect.getParent().getBoundsInLocal().getWidth() - handleRadius) {
					rect.setWidth(rect.getParent().getBoundsInLocal().getWidth());
				}
				double newMaxY = rect.getY() + rect.getHeight() + deltaY;
				if (newMaxY >= rect.getY()
						&& newMaxY <= rect.getParent().getBoundsInLocal().getHeight() - handleRadius) {
					rect.setHeight(rect.getHeight() + deltaY);
				}
				mouseLocation.value = new Point2D(event.getSceneX(), event.getSceneY());
			}
		});

		if (!(comp instanceof Title)) {
			moveHandle.setOnMouseDragged(event -> {
				if (mouseLocation.value != null) {
					double deltaX = event.getSceneX() - mouseLocation.value.getX();
					double deltaY = event.getSceneY() - mouseLocation.value.getY();
					double newX = rect.getX() + deltaX;
					double newMaxX = newX + rect.getWidth();
					if (newX >= handleRadius
							&& newMaxX <= rect.getParent().getBoundsInLocal().getWidth() - handleRadius) {
						rect.setX(newX);
					}
					double newY = rect.getY() + deltaY;
					double newMaxY = newY + rect.getHeight();
					if (newY >= handleRadius
							&& newMaxY <= rect.getParent().getBoundsInLocal().getHeight() - handleRadius) {
						rect.setY(newY);
					}
					mouseLocation.value = new Point2D(event.getSceneX(), event.getSceneY());
				}

			});
		}

		rect.visibleProperty().addListener((ChangeListener<? super Boolean>) (observable, oldValue, newValue) -> {
			Boolean isTrue = (Boolean) newValue;
			if (!isTrue) {
				resizeHandleSE.setVisible(false);
				moveHandle.setVisible(false);

				resizeHandleSE.centerXProperty().unbind();
				resizeHandleSE.centerYProperty().unbind();
				if (!(comp instanceof Title)) {
					moveHandle.centerXProperty().unbind();
					moveHandle.centerYProperty().unbind();
				}
			}
		});

		return rect;
	}

	private static void setUpDragging(Circle circle, Wrapper<Point2D> mouseLocation) {

		circle.setOnDragDetected(event -> {
			circle.getParent().setCursor(Cursor.CLOSED_HAND);
			mouseLocation.value = new Point2D(event.getSceneX(), event.getSceneY());
		});

		circle.setOnMouseReleased(event -> {
			circle.getParent().setCursor(Cursor.DEFAULT);
			mouseLocation.value = null;
		});
	}

	public static class Delta {
		double x, y;

		public Delta(double x, double y) {
			this.x = x;
			this.y = y;
		}

		public Delta() {
		}
	}

	static class Wrapper<T> {
		T value;
	}

	public static void initializePlayPauseButton(VideoEditorController videoEditorController, Button play_butt,
			DirectMediaPlayer mediaPlayer) {

		play_butt.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {

				if (mediaPlayer.isPlaying()) {
					// code for icon
					mediaPlayer.pause();
				} else {
					mediaPlayer.play();

				}
			}
		});
	}

	public static void initializeStopButton(VideoEditorController videoEditorController, Button stop_butt,
			DirectMediaPlayer mediaPlayer) {

		stop_butt.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				mediaPlayer.stop();
			}
		});
	}

	public static void initializeRewindButton(VideoEditorController videoEditorController, Button rewind_butt,
			DirectMediaPlayer mediaPlayer) {

		rewind_butt.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (mediaPlayer.getLength() > 0) {
					mediaPlayer.skip(SKIP_TIME_MS);
				}
			}
		});
	}

	public static void initializeForwardButton(VideoEditorController videoEditorController, Button fforward_butt,
			DirectMediaPlayer mediaPlayer) {
		fforward_butt.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (mediaPlayer.getLength() > 0) {
					mediaPlayer.skip(-SKIP_TIME_MS);
				}
			}
		});
	}

	private static final String outDir = "I:/workspace/VideoPlayerWorkSpace/SampleInOut/Out/";
	private static final String mkvToolPath = "C:/Program Files/MKVToolNix/mkvmerge.exe";

	public static void initializeConvertButton(VideoEditorController controller, Button convert_butt) {
		convert_butt.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {

				if (controller.getCompList().isEmpty()) {
					// show alert box
					Alert alert = new Alert(AlertType.ERROR);
					// alert.setTitle(titleTxt);
					alert.setHeaderText("Error");
					String s = "Please add some annotations";
					alert.setContentText(s);
					alert.show();
				} else {
					try {
						String[] split = VideoEditorController.getPathToVideo().replace("\\", "/").split("/");
						String filename = split[split.length - 1];
						// 1. convert the file into mkv using mkvmerge
						try {

							Runtime.getRuntime().exec(mkvToolPath + " -o " + outDir + filename + ".mkv" + " "
									+ VideoEditorController.getPathToVideo());
							String opFile = outDir + filename + ".mkv";
							System.out.println(
									mkvToolPath + " -o " + VideoEditorController.getPathToVideo() + " " + opFile);
						} catch (Exception e1) {
							e1.printStackTrace();
						}

						// 2. Generate XML Map (Along with it keep a map of to
						// be
						// attached files //

						List<MatroskaAttachment> attachments = new ArrayList<>();
						Map<Integer, AbstractComp> compList = controller.getCompList();
						Set<Integer> keySet = compList.keySet();

						String xmlText = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
						xmlText += "<Video_anno_data>";
						for (Integer i : keySet) {
							AbstractComp abstractComp = compList.get(i);
							xmlText += abstractComp.toXml();

							if (abstractComp.getBgfilepath() != null
									&& !abstractComp.getBgfilepath().equalsIgnoreCase("")) {
								MatroskaAttachment att = new MatroskaAttachment();
								att.setFile(abstractComp.getBgfilepath());
								att.setType("image/jpeg");
								att.setDescription(abstractComp.getId() + " " + abstractComp.getAnnName());
								System.out.println(abstractComp.getBgfilepath());
								attachments.add(att);
							}
						}
						xmlText += "</Video_anno_data>";

						// 3. Store in temp folder
						File overlayOp = new File(outDir + filename + ".xml");
						FileWriter writter = null;
						BufferedWriter bufferedWriter = null;
						try {
							writter = new FileWriter(overlayOp);
							bufferedWriter = new BufferedWriter(writter);
							bufferedWriter.write(xmlText);
							bufferedWriter.flush();
						} catch (IOException e) {
							e.printStackTrace();
						} finally {
							try {
								if (bufferedWriter != null)
									bufferedWriter.close();
								if (writter != null)
									writter.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}

						// 5. Add xml to the attachments
						MatroskaAttachment att = new MatroskaAttachment();
						att.setFile(outDir + filename + ".xml");
						att.setType("xml/txt");
						att.setDescription("Annotations list file");
						attachments.add(att);

						// 6. Store Attachments
						String cmd = mkvToolPath + " -o " + outDir + filename + ".mkv" + " "
								+ VideoEditorController.getPathToVideo().replace("\\", "/") + " -A ";
						for (MatroskaAttachment attachment : attachments) {
							if (attachment.getFile() != null && !attachment.getFile().equalsIgnoreCase("")) {
								cmd += " --attach-file " + attachment.getFile().replace("\\", "/");
								cmd += " --attachment-description \"" + attachment.getDescription() + "\"";
								cmd += " --attachment-mime-type " + attachment.getType() + "";
							}
						}

						System.out.println("Command: " + cmd);
						try {
							Runtime.getRuntime().exec(cmd);

							// TODO
							// recheck if the attachments added

							Alert alert = new Alert(AlertType.INFORMATION);
							// alert.setTitle(titleTxt);
							alert.setHeaderText("Success");
							String s = "Annotations created successfully";
							alert.setContentText(s);
							alert.show();
						} catch (IOException e) {
							e.printStackTrace();
						}
					} catch (Exception e) {
						Alert alert = new Alert(AlertType.ERROR);
						// alert.setTitle(titleTxt);
						alert.setHeaderText("Error Occurred");
						String s = "Error ocurred \n " + e.getMessage();
						alert.setContentText(s);
						alert.show();
					}
				}
			}
		});

	}

	public static void main(String[] args) {

		String cmd = "C:/Program Files/MKVToolNix/mkvmerge.exe -o I:/workspace/SpringWorkspace/VideoEditor/Sample/out/JaiMataDi_KingCircle.mp4.mkv C:/JaiMataDi_KingCircle.mp4 -A  "
				+ "--attachment-description \"1 SpotLight\" --attachment-mime-type image/jpeg --attach-file C:/Users/hp/Desktop/_MG_1801.JPG "
				+ "--attachment-description \"3 Marker\" --attachment-mime-type \"image/jpeg\" --attach-file C:/Users/hp/Desktop/_MG_1801.JPG "
				+ "--atjtachment-description \"Annotations list file\" --attachment-mime-type \"xml/txt\" --attach-file I:/workspace/SpringWorkspace/VideoEditor/Sample/out/JaiMataDi_KingCircle.mp4.xml";
		String cmd2 = "C:/Program Files/MKVToolNix/mkvmerge.exe -o I:/workspace/SpringWorkspace/VideoEditor/Sample/out/JaiMataDi_KingCircle.mp4.mkv C:/JaiMataDi_KingCircle.mp4 -A  "
				+ "--attachment-description \"2 Marker\" --attachment-mime-type image/jpeg --attach-file C:/Users/hp/Desktop/_MG_1801.JPG "
				+ "--attachment-description \"Annotations list file\" --attachment-mime-type xml/txt --attach-file I:/workspace/SpringWorkspace/VideoEditor/Sample/out/JaiMataDi_KingCircle.mp4.xml";
		String cmd3 = "C:/Program Files/MKVToolNix/mkvmerge.exe -o I:/workspace/VideoPlayerWorkSpace/SampleInOut/Out/JaiMataDi_KingCircle.mp4.mkv C:/JaiMataDi_KingCircle.mp4 -A  --attach-file C:/Users/hp/Desktop/1.jpg --attachment-description \"2 SpotLight\" --attachment-mime-type image/jpeg --attach-file I:/workspace/VideoPlayerWorkSpace/SampleInOut/Out/JaiMataDi_KingCircle.mp4.xml --attachment-description \"Annotations list file\" --attachment-mime-type xml/txt";
		try {
			String[] split = VideoEditorController.getPathToVideo().replace("\\", "/").split("/");
			String filename = split[split.length - 1];

			Runtime.getRuntime().exec(
					mkvToolPath + " -o " + outDir + filename + ".mkv" + " " + VideoEditorController.getPathToVideo());

			Runtime.getRuntime().exec(cmd3);
			System.out.println(
					mkvToolPath + " -o " + VideoEditorController.getPathToVideo() + " " + outDir + filename + ".mkv");
			System.out.println(cmd);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	public static void initializeNewButton(VideoEditorController videoEditorController, Button openvideo_butt,
			DirectMediaPlayer mediaPlayer) {

		// TODO Auto-generated method stub
		// Preload the information if mkv
		openvideo_butt.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {

				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Open Resource File");
				fileChooser.setInitialDirectory(new File("I:\\workspace\\VideoPlayerWorkSpace\\SampleInOut\\In"));
				// TODO Add supported video files
				fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Videos", "*.*"));
				try {
					File file = fileChooser.showOpenDialog(videoEditorController.getPrimaryStage());
					VideoEditorController.setPATH_TO_VIDEO(file.getAbsolutePath());
					videoEditorController.initializeMediaPlayer();
					mediaPlayer.start();
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});

	}

}
