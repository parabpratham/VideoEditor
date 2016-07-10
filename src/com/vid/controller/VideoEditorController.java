package com.vid.controller;

import java.awt.Dimension;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import org.controlsfx.control.InfoOverlay;
import org.controlsfx.control.PopOver;
import org.controlsfx.control.PopOver.ArrowLocation;

import com.sun.jna.Memory;
import com.vid.commons.Helper;
import com.vid.comp.Jcomp.AbstractComp;
import com.vid.comp.Jcomp.AbstractComp.MarkerPopUp;
import com.vid.comp.Jcomp.AnnotationMarkers;
import com.vid.comp.Jcomp.BoundedRectangle;
import com.vid.comp.Jcomp.Note;
import com.vid.comp.Jcomp.SpotLight;
import com.vid.comp.Jcomp.Title;
import com.vid.comp.Scomp.CircleComp;
import com.vid.comp.Scomp.LineComp;
import com.vid.comp.Scomp.RectangleComp;
import com.vid.comp.Scomp.TextComp;
import com.vid.tagging.KeyWord;
import com.vid.tagging.VideoTag;

import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.image.WritablePixelFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import uk.co.caprica.vlcj.component.DirectMediaPlayerComponent;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.player.direct.BufferFormat;
import uk.co.caprica.vlcj.player.direct.BufferFormatCallback;
import uk.co.caprica.vlcj.player.direct.DirectMediaPlayer;
import uk.co.caprica.vlcj.player.direct.format.RV32BufferFormat;

public class VideoEditorController implements Initializable {

	public VideoEditorController() {
		super();
		compList = new HashMap<>();
		compObservableList = FXCollections.observableArrayList();
		tagSegmentObservableList = FXCollections.observableArrayList();
		keywordObservableList = FXCollections.observableArrayList();
	}

	public static final int baseRectWidth = 110;
	public static final int baseRectHeight = 60;

	private Stage primaryStage;

	// Contains list of new and old annotations
	@FXML
	private ListView<AbstractComp> compListView;
	private Map<Integer, AbstractComp> compList;
	ObservableList<AbstractComp> compObservableList;
	private int index = 0;

	@FXML
	private Pane playerHolder;

	// The tab panels names
	@FXML
	private Tab taginfo_tab;
	@FXML
	private Tab addann_tab;

	// Add annotations panel
	@FXML
	private Button comment_butt;

	@FXML
	private Button spot_butt;

	@FXML
	private Button note_butt;

	@FXML
	private Button speech_butt;

	@FXML
	private Button face_butt;

	@FXML
	private Button video_butt;

	@FXML
	private Button marker_butt;

	@FXML
	private Button poly_butt;

	@FXML
	private Button oval_butt;

	@FXML
	private Button line_butt;

	@FXML
	private Button rect_butt;

	@FXML
	private Button cir_butt;

	@FXML
	private Button simple_text_butt;

	// Playback buttons

	@FXML
	private Button openvideo_butt;

	@FXML
	private Button fforward_butt;

	@FXML
	private Button play_butt;

	@FXML
	private Button rewind_butt;

	@FXML
	private Button stop_butt;

	@FXML
	private Button convert_butt;

	// Slider Control
	@FXML
	private AnchorPane timelineholder;

	@FXML
	private TextField timeLabel;

	public TextField getTimeLabel() {
		return timeLabel;
	}

	public void setTimeLabel(TextField timeLabel) {
		this.timeLabel = timeLabel;
	}

	@FXML
	private Slider timeSlider;

	@FXML
	private Slider endtimeSlider;

	@FXML
	private TextField endtimeLabel;

	// Tagging window information
	@FXML
	private Button serachtag_butt;

	@FXML
	private Button addtag_butt;

	@FXML
	private ListView<VideoTag> tagSegmentListView;
	private ObservableList<VideoTag> tagSegmentObservableList;

	@FXML
	private ListView<KeyWord> keywordListView;
	private ObservableList<KeyWord> keywordObservableList;

	private static Map<String, List<VideoTag>> keyTagMap;

	// Media Play variables

	private static String PATH_TO_VIDEO = "C:\\JaiMataDi_KingCircle.mp4";

	public static String getPATH_TO_VIDEO() {
		return PATH_TO_VIDEO;
	}

	public static void setPATH_TO_VIDEO(String pATH_TO_VIDEO) {
		PATH_TO_VIDEO = pATH_TO_VIDEO;
	}

	private ImageView imageView;

	private DirectMediaPlayerComponent mediaPlayerComponent;

	private WritableImage writableImage;

	private WritablePixelFormat<ByteBuffer> pixelFormat;

	private FloatProperty videoSourceRatioProperty;

	private Dimension videoDimension;

	private double wtFactor, htFactor;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		mediaPlayerComponent = new CanvasPlayerComponent();
		videoSourceRatioProperty = new SimpleFloatProperty(0.4f);
		pixelFormat = PixelFormat.getByteBgraPreInstance();
		playerHolder.setStyle("-fx-background-color: black;");

		initializeImageView();
		initializeButtons();
		initializeSliders();
		initializeMediaPlayer();

		// initialize anotationlistview
		// ListViewController.initializeListView(listView, listItems);
		compListView.setItems(compObservableList);
		compListView.setCellFactory((ListView<AbstractComp> l) -> new AnnotationListRectCell<AbstractComp>(compListView,
				compObservableList, compList));

		intializeTaggingPane();
	}

	private void intializeTaggingPane() {

		keyTagMap = new HashMap<>();

		keywordListView.setItems(keywordObservableList);
		keywordListView.setCellFactory(
				(ListView<KeyWord> l) -> new TagKeywordListRectCell<KeyWord>(keywordListView, keywordObservableList));

		tagSegmentListView.setItems(tagSegmentObservableList);
		tagSegmentListView
				.setCellFactory((ListView<VideoTag> l) -> new VideoTagListRectCell<VideoTag>(tagSegmentListView,
						tagSegmentObservableList));

	}

	public void initializeMediaPlayer() {
		mediaPlayerComponent.getMediaPlayer().prepareMedia(PATH_TO_VIDEO);
		// mediaPlayerComponent.getMediaPlayer().mute();
	}

	// timeline related changes
	private void initializeSliders() {
		timeSlider.setMinWidth(50);
		timeSlider.setMaxWidth(Double.MAX_VALUE);
		timeSlider.setMajorTickUnit(10.0);
		timeSlider.setShowTickMarks(true);
		timeSlider.valueProperty().addListener(new InvalidationListener() {
			public void invalidated(Observable ov) {
				try {
					if (timeSlider.isValueChanging()) {
						Duration duration = new Duration(mediaPlayerComponent.getMediaPlayer().getLength());
						// multiply duration by percentage calculated by slider
						// position
						int newTime = (int) duration.multiply(timeSlider.getValue() / 100.0).toMillis();
						System.out.println("timeSlider.valueProperty " + newTime);
						mediaPlayerComponent.getMediaPlayer().setTime(newTime);
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		timeSlider.visibleProperty().addListener((ChangeListener<? super Boolean>) (observable, oldValue, newValue) -> {
			timeLabel.setVisible((Boolean) newValue);
		});
		mediaPlayerComponent.getMediaPlayer().addMediaPlayerEventListener(new MediaPlayerEventAdapter() {
			@Override
			public void timeChanged(MediaPlayer mediaPlayer, long newTime) {
				try {
					timeSlider.setValue(Math.round(mediaPlayerComponent.getMediaPlayer().getPosition() * 100));
					String s = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(newTime),
							TimeUnit.MILLISECONDS.toMinutes(newTime)
									- TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(newTime)),
							TimeUnit.MILLISECONDS.toSeconds(newTime)
									- TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(newTime)));
					timeLabel.setText(s);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static Map<String, List<VideoTag>> getKeyTagMap() {
		return keyTagMap;
	}

	public void setKeyTagMap(Map<String, List<VideoTag>> keyTagMap) {
		VideoEditorController.keyTagMap = keyTagMap;
	}

	private void initializeButtons() {

		// Add components buttons
		ButtonForController.initializeButton(this, comment_butt);
		ButtonForController.initializeButton(this, spot_butt);
		ButtonForController.initializeButton(this, note_butt);
		ButtonForController.initializeButton(this, speech_butt);
		ButtonForController.initializeButton(this, face_butt);
		ButtonForController.initializeButton(this, video_butt);
		ButtonForController.initializeButton(this, marker_butt);

		// add shape buttons
		ButtonForController.initializeButton(this, poly_butt);
		ButtonForController.initializeButton(this, oval_butt);
		ButtonForController.initializeButton(this, line_butt);
		ButtonForController.initializeButton(this, rect_butt);
		ButtonForController.initializeButton(this, cir_butt);
		ButtonForController.initializeButton(this, simple_text_butt);
		ButtonForController.initializeButton(this, speech_butt);

		// play,pause buttons
		ButtonForController.initializePlayPauseButton(this, play_butt, mediaPlayerComponent.getMediaPlayer());
		ButtonForController.initializeStopButton(this, stop_butt, mediaPlayerComponent.getMediaPlayer());
		ButtonForController.initializeRewindButton(this, rewind_butt, mediaPlayerComponent.getMediaPlayer());
		ButtonForController.initializeForwardButton(this, fforward_butt, mediaPlayerComponent.getMediaPlayer());

		ButtonForController.initializeNewButton(this, openvideo_butt, mediaPlayerComponent.getMediaPlayer());

		ButtonForController.initializeConvertButton(this, convert_butt);

		ButtonForController.initializeAddTagButton(this, addtag_butt);

	}

	private void initializeImageView() {

		Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
		writableImage = new WritableImage((int) visualBounds.getWidth(), (int) visualBounds.getHeight());

		setImageView(new ImageView(writableImage));

		playerHolder.getChildren().add(getImageView());

		fitImageViewSizeForMe((float) playerHolder.getMaxWidth(), (float) playerHolder.getMaxHeight());

		/*
		 * playerHolder.widthProperty().addListener((observable, oldValue,
		 * newValue) -> { fitImageViewSize(newValue.floatValue(), (float)
		 * playerHolder.getHeight()); });
		 * 
		 * playerHolder.heightProperty().addListener((observable, oldValue,
		 * newValue) -> { fitImageViewSize((float) playerHolder.getWidth(),
		 * newValue.floatValue()); });
		 */

		/*
		 * videoSourceRatioProperty.addListener((observable, oldValue, newValue)
		 * -> { fitImageViewSize((float) playerHolder.getWidth(), (float)
		 * playerHolder.getHeight()); });
		 */

		getImageView().setOnDragOver(new EventHandler<DragEvent>() {
			@Override
			public void handle(DragEvent event) {
				event.acceptTransferModes(TransferMode.MOVE);
			}
		});

		VideoEditorController vController = this;
		getImageView().setOnDragDropped(new EventHandler<DragEvent>() {

			public void handle(DragEvent event) {
				addNewAnnotation(event);
			}

			private void addNewAnnotation(DragEvent event) {
				if (event.getGestureSource() instanceof Button) {
					Button source = (Button) event.getGestureSource();
					AbstractComp abstractComp = getAbstractCompFromName(source.getText());
					BoundedRectangle baseRectWithScroll = null;

					if (abstractComp != null && abstractComp instanceof Note) {

						baseRectWithScroll = ButtonForController.createDraggableRectangle(abstractComp,
								event.getSceneX() - 37, event.getSceneY() - 45, baseRectWidth, baseRectHeight);
						abstractComp.addComponent(baseRectWithScroll);
						TextArea ta = addtextArea(baseRectWithScroll);
						playerHolder.getChildren().addAll(baseRectWithScroll, ta);
						abstractComp.setTextbox(ta);
					} else if (abstractComp != null && abstractComp instanceof Title) {
						baseRectWithScroll = ButtonForController.createDraggableRectangle(abstractComp, 0, 0,
								baseRectWidth, baseRectHeight);
						abstractComp.addComponent(baseRectWithScroll);
						TextArea ta = addtextArea(baseRectWithScroll);
						playerHolder.getChildren().addAll(baseRectWithScroll, ta);
						abstractComp.setTextbox(ta);
					} else if (abstractComp != null && abstractComp instanceof SpotLight) {
						baseRectWithScroll = ButtonForController.createDraggableRectangle(abstractComp,
								event.getSceneX() - 37, event.getSceneY() - 45, baseRectWidth, baseRectHeight);
						InfoOverlay info = addInfoOverlay(baseRectWithScroll);
						playerHolder.getChildren().addAll(baseRectWithScroll, info);
						abstractComp.setInfoOverlay(info);

					} else if (abstractComp != null && abstractComp instanceof AnnotationMarkers) {
						baseRectWithScroll = ButtonForController.createDraggableRectangle(abstractComp,
								event.getSceneX() - 37, event.getSceneY() - 45, baseRectWidth, baseRectHeight);
						MarkerPopUp info = addPopUpPanel(baseRectWithScroll, abstractComp);
						playerHolder.getChildren().addAll(baseRectWithScroll, info.getView());
						abstractComp.setMarkerPopup(info);
					} else if (abstractComp != null && abstractComp instanceof CircleComp) {
						baseRectWithScroll = ButtonForController.createDraggableRectangle(abstractComp,
								event.getSceneX() - 37, event.getSceneY() - 45, baseRectWidth, baseRectHeight);
						Shape add2dShape = add2dShapeCircle(baseRectWithScroll);
						abstractComp.setShape(add2dShape);
						playerHolder.getChildren().addAll(baseRectWithScroll, add2dShape);

					} else if (abstractComp != null && abstractComp instanceof RectangleComp) {
						baseRectWithScroll = ButtonForController.createDraggableRectangle(abstractComp,
								event.getSceneX() - 37, event.getSceneY() - 45, baseRectWidth, baseRectHeight);
						Shape add2dShape = baseRectWithScroll;
						abstractComp.setShape(add2dShape);
						playerHolder.getChildren().addAll(baseRectWithScroll);

					} else if (abstractComp != null && abstractComp instanceof LineComp) {
						baseRectWithScroll = ButtonForController.createDraggableRectangle(abstractComp,
								event.getSceneX() - 37, event.getSceneY() - 45, 100, 10);
						Shape add2dShape = add2dShapeLine(baseRectWithScroll);
						abstractComp.setShape(add2dShape);
						playerHolder.getChildren().addAll(baseRectWithScroll, add2dShape);

					} else if (abstractComp != null && abstractComp instanceof TextComp) {
						baseRectWithScroll = ButtonForController.createDraggableRectangle(abstractComp,
								event.getSceneX() - 37, event.getSceneY() - 45, baseRectWidth, baseRectHeight);
						Shape add2dShape = add2dShapeText(baseRectWithScroll);
						abstractComp.setShape(add2dShape);
						playerHolder.getChildren().addAll(baseRectWithScroll, add2dShape);

					} else {

						playerHolder.getChildren().add(baseRectWithScroll);
					}

					if (mediaPlayerComponent.getMediaPlayer().isPlaying())
						mediaPlayerComponent.getMediaPlayer().pause();

					// Show slider of end frame
					if (!(abstractComp instanceof Title))
						showEndSlider();
					else {
						timeSlider.setVisible(false);
						timeLabel.setVisible(false);
					}

					// Kept here becuase of the getClass().getClassLoader()
					// method
					// Change if work arround found
					FXMLLoader loader = new FXMLLoader(
							getClass().getClassLoader().getResource(abstractComp.getFXMLPath()));
					AnnotationPropertyWindow.setupPopupWindow(vController, loader, abstractComp, baseRectWithScroll);

					// setupPopupWindow(abstractComp, baseRectWithScroll);

				}
				event.consume();
			}

			private AbstractComp getAbstractCompFromName(String text) {
				AbstractComp comp = null;
				try {
					if (text.equalsIgnoreCase("Note")) {
						comp = new Note();
					} else if (text.equalsIgnoreCase("Title")) {
						comp = new Title();
					} else if (text.equalsIgnoreCase("SpotLight")) {
						comp = new SpotLight();
					} else if (text.equalsIgnoreCase("Marker")) {
						comp = new AnnotationMarkers();
					} else if (text != null && !text.equalsIgnoreCase("")) {
						if (text.equalsIgnoreCase("Circle")) {
							comp = new CircleComp();
						} else if (text.equalsIgnoreCase("Rectangle")) {
							comp = new RectangleComp();
						} else if (text.equalsIgnoreCase("Line")) {
							comp = new LineComp();
						} else if (text.equalsIgnoreCase("Text")) {
							comp = new TextComp();
						}
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
				return comp;
			}

			private InfoOverlay addInfoOverlay(BoundedRectangle rect) {

				ImageView iew = new ImageView();
				InfoOverlay infoOverlay = new InfoOverlay(iew, "Enter text");

				iew.setFitWidth(baseRectWidth - 10);
				iew.setFitHeight(baseRectHeight - 10);

				iew.setImage(null);
				WritableImage createFilledImage = Helper.createBorderImage(iew, javafx.scene.paint.Color.BLACK);
				iew.setImage(createFilledImage);

				infoOverlay.setPrefWidth(baseRectWidth - 10);
				infoOverlay.setPrefHeight(baseRectHeight - 10);
				infoOverlay.setShowOnHover(true);

				rect.widthProperty().addListener((ChangeListener<? super Number>) (observable, oldValue, newValue) -> {
					Double width = (Double) newValue;
					iew.setFitWidth(width - 10);
					infoOverlay.setMinWidth(width - 10);
				});

				rect.heightProperty().addListener((ChangeListener<? super Number>) (observable, oldValue, newValue) -> {
					Double height = (Double) newValue;
					iew.setFitHeight(height - 10);
					infoOverlay.setMinHeight(height - 10);
				});

				rect.xProperty().addListener((ChangeListener<? super Number>) (observable, oldValue, newValue) -> {
					infoOverlay.relocate(rect.getX() + 5, rect.getY() + 5);
				});

				rect.yProperty().addListener((ChangeListener<? super Number>) (observable, oldValue, newValue) -> {
					infoOverlay.relocate(rect.getX() + 5, rect.getY() + 5);
				});

				infoOverlay.relocate(rect.getX() + 5, rect.getY() + 5);

				return infoOverlay;
			}

			private MarkerPopUp addPopUpPanel(BoundedRectangle rect, AbstractComp comp) {

				URL resource = getClass().getClassLoader().getResource("icons/marker_default.png");
				Image image = new Image("file:" + resource.getPath());
				ImageView iew = new ImageView(image);
				PopOver popOver = createPopOver();

				MarkerPopUp markerPopUp = comp.new MarkerPopUp(iew, popOver);

				iew.setFitWidth(baseRectWidth - 10);
				iew.setFitHeight(baseRectHeight - 10);

				rect.widthProperty().addListener((ChangeListener<? super Number>) (observable, oldValue, newValue) -> {
					Double width = (Double) newValue;
					iew.setFitWidth(width - 10);
				});

				rect.heightProperty().addListener((ChangeListener<? super Number>) (observable, oldValue, newValue) -> {
					Double height = (Double) newValue;
					iew.setFitHeight(height - 10);
				});

				rect.xProperty().addListener((ChangeListener<? super Number>) (observable, oldValue, newValue) -> {
					iew.relocate(rect.getX() + 5, rect.getY() + 5);
				});

				rect.yProperty().addListener((ChangeListener<? super Number>) (observable, oldValue, newValue) -> {
					iew.relocate(rect.getX() + 5, rect.getY() + 5);
				});

				iew.setOnScroll(new EventHandler<ScrollEvent>() {
					@Override
					public void handle(ScrollEvent evt) {
						double delta = evt.getDeltaY();
						rect.setWidth(Math.max(100, Math.min(500, rect.getWidth() + delta)));
						rect.setHeight(Math.max(100, Math.min(500, rect.getHeight() + delta)));
					}
				});

				iew.setOnMouseClicked(new EventHandler<MouseEvent>() {

					@Override
					public void handle(MouseEvent evt) {
						if (popOver != null && !popOver.isDetached()) {
							popOver.hide();
						}
						if (evt.getClickCount() == 2) {
							if (popOver != null && popOver.isShowing()) {
								try {
									popOver.hide(Duration.ZERO);
								} catch (Exception e) {
									// e.printStackTrace();
								}
							}
							// TODO
							// targetX = evt.getScreenX();
							// targetY = evt.getScreenY();
							popOver.setContentNode(markerPopUp.getContentText());
							popOver.show(iew);
						}
					}

				});

				iew.relocate(rect.getX() + 5, rect.getY() + 5);

				return markerPopUp;
			}

			private PopOver createPopOver() {
				PopOver popOver = new PopOver();
				popOver.setDetachable(false);
				popOver.setDetached(false);
				popOver.setArrowSize(12);
				popOver.setArrowIndent(12);
				popOver.setArrowLocation(ArrowLocation.TOP_CENTER);
				popOver.setCornerRadius(10);
				return popOver;

			}

			private Shape add2dShapeCircle(Rectangle rect) {
				double diameter = Math.min(rect.getHeight(), rect.getWidth());
				Circle circle = new Circle(diameter / 2);
				circle.setCenterX(rect.getX() + rect.getWidth() / 2);
				circle.setCenterY(rect.getY() + rect.getHeight() / 2);
				rect.widthProperty().addListener((ChangeListener<? super Number>) (observable, oldValue, newValue) -> {
					Double width = (Double) newValue;
					if (rect.getHeight() != width)
						rect.setHeight(width);
					double diameter1 = Math.min(rect.getHeight(), rect.getWidth());
					circle.setRadius(diameter1 / 2);
					circle.setCenterX(rect.getX() + rect.getWidth() / 2);
					circle.setCenterY(rect.getY() + rect.getHeight() / 2);
				});

				rect.heightProperty().addListener((ChangeListener<? super Number>) (observable, oldValue, newValue) -> {
					Double height = (Double) newValue;
					if (rect.getHeight() != height)
						rect.setHeight(height);
					double diameter2 = Math.min(rect.getHeight(), rect.getWidth());
					circle.setRadius(diameter2 / 2);
					circle.setCenterX(rect.getX() + rect.getWidth() / 2);
					circle.setCenterY(rect.getY() + rect.getHeight() / 2);
				});

				rect.xProperty().addListener((ChangeListener<? super Number>) (observable, oldValue, newValue) -> {
					circle.setCenterX(rect.getX() + rect.getWidth() / 2);
					circle.setCenterY(rect.getY() + rect.getHeight() / 2);
				});

				rect.yProperty().addListener((ChangeListener<? super Number>) (observable, oldValue, newValue) -> {
					circle.setCenterX(rect.getX() + rect.getWidth() / 2);
					circle.setCenterY(rect.getY() + rect.getHeight() / 2);
				});

				rect.visibleProperty()
						.addListener((ChangeListener<? super Boolean>) (observable, oldValue, newValue) -> {
					Boolean isTrue = (Boolean) newValue;
					if (!isTrue) {
						AnchorPane parent = (AnchorPane) circle.getParent();
						parent.getChildren().remove(circle);
					}
				});

				return circle;
			}

			private Shape add2dShapeLine(BoundedRectangle rect) {

				Line line = new Line();
				line.setStartX(rect.getX());
				line.setStartY(rect.getY() - 2);
				line.setEndX(rect.getX() + rect.getWidth());
				line.setEndY(rect.getY() - 2);
				line.setStrokeWidth(rect.getHeight() - 4);
				line.setStroke(
						new Color(Color.WHITE.getRed(), Color.WHITE.getGreen(), Color.WHITE.getBlue(), 50 * 0.01));

				rect.xProperty().addListener((ChangeListener<? super Number>) (observable, oldValue, newValue) -> {
					line.setStartX(rect.getX());
					line.setStartY(rect.getY() - 2);
					line.setEndX(rect.getX() + rect.getWidth());
					line.setEndY(rect.getY() - 2);
				});

				rect.yProperty().addListener((ChangeListener<? super Number>) (observable, oldValue, newValue) -> {
					line.setStartX(rect.getX());
					line.setStartY(rect.getY() - 2);
					line.setEndX(rect.getX() + rect.getWidth());
					line.setEndY(rect.getY() - 2);
				});

				rect.widthProperty().addListener((ChangeListener<? super Number>) (observable, oldValue, newValue) -> {
					Double width = (Double) newValue;
					line.setEndX(rect.getX() + width);
					line.setEndY(rect.getY() - 2);
				});

				rect.heightProperty().addListener((ChangeListener<? super Number>) (observable, oldValue, newValue) -> {
					Double height = (Double) newValue;
					line.setStrokeWidth(height - 4);
				});

				rect.visibleProperty()
						.addListener((ChangeListener<? super Boolean>) (observable, oldValue, newValue) -> {
					Boolean isTrue = (Boolean) newValue;
					if (!isTrue) {
						AnchorPane parent = (AnchorPane) line.getParent();
						parent.getChildren().remove(line);
					}
				});

				return line;
			}

			private Shape add2dShapeText(BoundedRectangle rect) {
				Text text = new Text();
				text.setX(rect.getX());
				text.setY(rect.getY());
				text.setFont(new Font("Arial", 10));

				rect.xProperty().addListener((ChangeListener<? super Number>) (observable, oldValue, newValue) -> {
					text.setX(rect.getX());
					text.setY(rect.getY());
				});

				rect.yProperty().addListener((ChangeListener<? super Number>) (observable, oldValue, newValue) -> {
					text.setX(rect.getX());
					text.setY(rect.getY());
				});

				rect.visibleProperty()
						.addListener((ChangeListener<? super Boolean>) (observable, oldValue, newValue) -> {
					Boolean isTrue = (Boolean) newValue;
					if (!isTrue) {
						AnchorPane parent = (AnchorPane) text.getParent();
						parent.getChildren().remove(text);
					}
				});
				return text;
			}

			private TextArea addtextArea(Rectangle rect) {

				TextArea ta = new TextArea("");
				ta.setPromptText("Enter text here");
				ta.setWrapText(true);
				ta.setPrefSize(baseRectWidth - 10, baseRectHeight - 10);
				rect.widthProperty().addListener((ChangeListener<? super Number>) (observable, oldValue, newValue) -> {
					Double width = (Double) newValue;
					ta.setPrefWidth(width - 10);
				});

				rect.heightProperty().addListener((ChangeListener<? super Number>) (observable, oldValue, newValue) -> {
					Double height = (Double) newValue;
					ta.setPrefHeight(height - 10);
				});

				rect.xProperty().addListener((ChangeListener<? super Number>) (observable, oldValue, newValue) -> {
					ta.relocate(rect.getX() + 5, rect.getY() + 5);
				});

				rect.yProperty().addListener((ChangeListener<? super Number>) (observable, oldValue, newValue) -> {
					ta.relocate(rect.getX() + 5, rect.getY() + 5);
				});

				ta.relocate(rect.getX() + 5, rect.getY() + 5);

				rect.visibleProperty()
						.addListener((ChangeListener<? super Boolean>) (observable, oldValue, newValue) -> {
					Boolean isTrue = (Boolean) newValue;
					if (!isTrue) {
						AnchorPane parent = (AnchorPane) ta.getParent();
						parent.getChildren().remove(ta);
					}
				});

				return ta;
			}

		});

	}

	public void showEndSlider() {
		getEndtimeLabel().setVisible(true);
		getEndtimeSlider().setVisible(true);

		getEndtimeSlider().setValue(timeSlider.getValue());
		getEndtimeLabel().setText(timeLabel.getText());

		Duration duration = new Duration(mediaPlayerComponent.getMediaPlayer().getLength());
		getEndtimeSlider().valueProperty().addListener(new InvalidationListener() {
			public void invalidated(Observable ov) {
				try {
					if (getEndtimeSlider().isValueChanging()) {
						int newTime = (int) duration.multiply(getEndtimeSlider().getValue() / 100.0).toMillis();
						mediaPlayerComponent.getMediaPlayer().setTime(newTime);
						String s = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(newTime),
								TimeUnit.MILLISECONDS.toMinutes(newTime)
										- TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(newTime)),
								TimeUnit.MILLISECONDS.toSeconds(newTime)
										- TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(newTime)));
						endtimeLabel.setText(s);
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		getEndtimeSlider().visibleProperty()
				.addListener((ChangeListener<? super Boolean>) (observable, oldValue, newValue) -> {
					Boolean isTrue = (Boolean) newValue;
					if (!isTrue) {
						int newTime = (int) duration.multiply(timeSlider.getValue() / 100.0).toMillis();
						mediaPlayerComponent.getMediaPlayer().setTime(newTime);
					} else {
						timeLabel.setVisible(true);
					}
				});
	}

	private void fitImageViewSizeForMe(float width, float height) {
		Platform.runLater(() -> {
			getImageView().setFitHeight(height - 10);
			getImageView().setFitWidth(width - 10);
			getImageView().setX(5);
			getImageView().setY(5);
		});
	}

	// was required if size of the playerholder was changing but it is not in
	// this case
	private void fitImageViewSize(float width, float height) {
		Platform.runLater(() -> {
			float fitHeight = videoSourceRatioProperty.get() * width;
			if (fitHeight > height) {
				getImageView().setFitHeight(height);
				double fitWidth = height / videoSourceRatioProperty.get();
				getImageView().setFitWidth(fitWidth);
				getImageView().setX((width - fitWidth) / 2);
				getImageView().setY(0);
			} else {
				getImageView().setFitWidth(width);
				getImageView().setFitHeight(fitHeight);
				getImageView().setY((height - fitHeight) / 2);
				getImageView().setX(0);
			}
		});
	}

	private class CanvasPlayerComponent extends DirectMediaPlayerComponent {

		public CanvasPlayerComponent() {
			super(new CanvasBufferFormatCallback());
		}

		PixelWriter pixelWriter = null;

		private PixelWriter getPW() {
			if (pixelWriter == null) {
				pixelWriter = writableImage.getPixelWriter();
			}
			return pixelWriter;
		}

		@Override
		public void display(DirectMediaPlayer mediaPlayer, Memory[] nativeBuffers, BufferFormat bufferFormat) {
			if (writableImage == null) {
				return;
			}
			Platform.runLater(() -> {
				try {
					Memory nativeBuffer = mediaPlayer.lock()[0];
					ByteBuffer byteBuffer = nativeBuffer.getByteBuffer(0, nativeBuffer.size());
					getPW().setPixels(0, 0, bufferFormat.getWidth(), bufferFormat.getHeight(), pixelFormat, byteBuffer,
							bufferFormat.getPitches()[0]);
					videoDimension = mediaPlayer.getVideoDimension();
					wtFactor = videoDimension.getWidth() / (playerHolder.getMaxWidth() - 10) * 1.0;
					htFactor = videoDimension.getHeight() / (playerHolder.getMaxHeight() - 10) * 1.0;
					// System.out.println(videoDimension + " " + wtFactor + " "
					// + htFactor);
				} catch (Exception e) {

				} finally {
					mediaPlayer.unlock();
				}
			});
		}
	}

	private class CanvasBufferFormatCallback implements BufferFormatCallback {
		@Override
		public BufferFormat getBufferFormat(int sourceWidth, int sourceHeight) {
			Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
			Platform.runLater(() -> videoSourceRatioProperty.set((float) sourceHeight / (float) sourceWidth));
			return new RV32BufferFormat((int) visualBounds.getWidth(), (int) visualBounds.getHeight());
		}
	}

	public Pane getPlayerHolder() {
		return playerHolder;
	}

	public void setPlayerHolder(Pane playerHolder) {
		this.playerHolder = playerHolder;
	}

	public DirectMediaPlayerComponent getMediaPlayerComponent() {
		return mediaPlayerComponent;
	}

	public void setMediaPlayerComponent(DirectMediaPlayerComponent mediaPlayerComponent) {
		this.mediaPlayerComponent = mediaPlayerComponent;
	}

	public ImageView getImageView() {
		return imageView;
	}

	public void setImageView(ImageView imageView) {
		this.imageView = imageView;
	}

	public Map<Integer, AbstractComp> getCompList() {
		return compList;
	}

	public void setCompList(HashMap<Integer, AbstractComp> compList) {
		this.compList = compList;
	}

	public ListView<AbstractComp> getListView() {
		return compListView;
	}

	public void setListView(ListView<AbstractComp> listView) {
		this.compListView = listView;
	}

	public Slider getEndtimeSlider() {
		return endtimeSlider;
	}

	public void setEndtimeSlider(Slider endtimeSlider) {
		this.endtimeSlider = endtimeSlider;
	}

	public TextField getEndtimeLabel() {
		return endtimeLabel;
	}

	public void setEndtimeLabel(TextField endtimeLabel) {
		this.endtimeLabel = endtimeLabel;
	}

	public ObservableList<AbstractComp> getListItems() {
		return compObservableList;
	}

	public void setListItems(ObservableList<AbstractComp> listItems) {
		this.compObservableList = listItems;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public Slider getTimeSlider() {
		return timeSlider;
	}

	public void setTimeSlider(Slider timeSlider) {
		this.timeSlider = timeSlider;
	}

	public Dimension getVideoDimension() {
		return videoDimension;
	}

	public void setVideoDimension(Dimension videoDimension) {
		this.videoDimension = videoDimension;
	}

	public double getWtFactor() {
		return wtFactor;
	}

	public void setWtFactor(double wtFactor) {
		this.wtFactor = wtFactor;
	}

	public double getHtFactor() {
		return htFactor;
	}

	public void setHtFactor(double htFactor) {
		this.htFactor = htFactor;
	}

	public static String getPathToVideo() {
		return PATH_TO_VIDEO;
	}

	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	public ListView<AbstractComp> getCompListView() {
		return compListView;
	}

	public void setCompListView(ListView<AbstractComp> compListView) {
		this.compListView = compListView;
	}

	public ObservableList<AbstractComp> getCompObservableList() {
		return compObservableList;
	}

	public void setCompObservableList(ObservableList<AbstractComp> compObservableList) {
		this.compObservableList = compObservableList;
	}

	public ListView<VideoTag> getTagSegmentListView() {
		return tagSegmentListView;
	}

	public void setTagSegmentListView(ListView<VideoTag> tagSegmentListView) {
		this.tagSegmentListView = tagSegmentListView;
	}

	public ObservableList<VideoTag> getTagSegmentObservableList() {
		return tagSegmentObservableList;
	}

	public void setTagSegmentObservableList(ObservableList<VideoTag> tagSegmentObservableList) {
		this.tagSegmentObservableList = tagSegmentObservableList;
	}

	public ListView<KeyWord> getKeywordListView() {
		return keywordListView;
	}

	public void setKeywordListView(ListView<KeyWord> keywordListView) {
		this.keywordListView = keywordListView;
	}

	public ObservableList<KeyWord> getKeywordObservableList() {
		return keywordObservableList;
	}

	public void setKeywordObservableList(ObservableList<KeyWord> keywordObservableList) {
		this.keywordObservableList = keywordObservableList;
	}

	public void setCompList(Map<Integer, AbstractComp> compList) {
		this.compList = compList;
	}

	public Button getRect_butt() {
		return rect_butt;
	}

	public void setRect_butt(Button rect_butt) {
		this.rect_butt = rect_butt;
	}

	public Button getSimple_text_butt() {
		return simple_text_butt;
	}

	public void setSimple_text_butt(Button simple_text_butt) {
		this.simple_text_butt = simple_text_butt;
	}

}
