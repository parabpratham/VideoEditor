package com.vid.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.controlsfx.control.InfoOverlay;
import org.controlsfx.control.PopOver;
import org.controlsfx.control.PopOver.ArrowLocation;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

import com.vid.commons.ConfirmBox;
import com.vid.commons.Helper;
import com.vid.comp.Jcomp.AbstractComp;
import com.vid.comp.Jcomp.AbstractComp.MarkerPopUp;
import com.vid.comp.Jcomp.AnnotationMarkers;
import com.vid.comp.Jcomp.FaceMarker;
import com.vid.comp.Jcomp.Note;
import com.vid.comp.Jcomp.SpotLight;
import com.vid.comp.Jcomp.StaticComponent;
import com.vid.comp.Jcomp.Title;
import com.vid.comp.Scomp.CircleComp;
import com.vid.comp.Scomp.LineComp;
import com.vid.comp.Scomp.RectangleComp;
import com.vid.comp.Scomp.TextComp;
import com.vid.overlay.comp.master.SHAPE_TYPE;
import com.vid.tagging.KeyWord;
import com.vid.tagging.VideoTag;

import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import uk.co.caprica.vlcj.player.direct.DirectMediaPlayer;

public class AnnotationPropertyWindow {

	public static void setupPopupWindow(VideoEditorController vController, FXMLLoader loader, AbstractComp abstractComp,
			Rectangle rect) {
		// For showing the property window
		Parent root;
		try {
			root = (Parent) loader.load();
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.setTitle("Add Annotations");

			AbstractAddController controller = (AbstractAddController) loader.getController();
			controller.setComp(abstractComp);
			controller.getAnntype().setText(abstractComp.getAnnName());

			if (abstractComp instanceof Title || abstractComp instanceof Note) {
				setupTextPropertiesForTextBox(vController, abstractComp, controller);
			} else if (abstractComp instanceof SpotLight) {
				setupTextPropertiesInfoOverlay(vController, abstractComp, controller);
			} else if (abstractComp instanceof AnnotationMarkers) {
				setupTextPropertiesForMarker(vController, abstractComp, controller);
			} else if (abstractComp instanceof FaceMarker) {
				// set propertirs
				// setupFaceMarkerProperties(vController, abstractComp,
				// controller);
			} else if (abstractComp instanceof StaticComponent) {
				setupShapeProperties(vController, abstractComp, controller);
			}

			// The annotation property panel
			if (controller.getBgfilepath() != null) {
				// controller.getBgfilepath().setDisable(true);
				controller.getFilechooser().setOnMouseClicked(new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent event) {
						FileChooser fileChooser = new FileChooser();
						fileChooser.setTitle("Open Resource File");
						fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Images", "*.*"),
								new FileChooser.ExtensionFilter("JPG", "*.jpg"),
								new FileChooser.ExtensionFilter("PNG", "*.png"));
						try {
							File file = fileChooser.showOpenDialog(stage);
							controller.getBgfilepath().setText(file.getAbsolutePath());

							if (abstractComp instanceof SpotLight) {
								ImageView iew = new ImageView(new Image("file:" + file.getAbsolutePath()));
								iew.setFitHeight(abstractComp.getInfoOverlay().getHeight());
								iew.setFitWidth(abstractComp.getInfoOverlay().getWidth());
								abstractComp.getInfoOverlay().setContent(iew);
							}

							if (abstractComp instanceof AnnotationMarkers) {
								MarkerPopUp markerPopup = abstractComp.getMarkerPopup();
								markerPopup.getView().setImage(new Image("file:" + file.getAbsolutePath()));

							}

						} catch (Exception e) {
								e.printStackTrace();
						}
					}
				});
			}
			/*
			 * 
			 * Tried to make it both way but are doing maramari
			 * 
			 * controller.getWidth().textProperty()
			 * .addListener((ChangeListener<? super String>) (observable,
			 * oldValue, newValue) -> { String Width = "" + newValue;
			 * ta.setPrefWidth(Double.parseDouble(Width));
			 * rect.setWidth(Double.parseDouble(Width) + 5); });
			 * 
			 * controller.getHeight().textProperty()
			 * .addListener((ChangeListener<? super String>) (observable,
			 * oldValue, newValue) -> { String height = "" + newValue;
			 * ta.setPrefHeight(Double.parseDouble(height));
			 * rect.setHeight(Double.parseDouble(height) + 5); });
			 */

			if (abstractComp instanceof Title) {
				controller.getPopupinterval().textProperty().addListener(new ChangeListener<String>() {

					@Override
					public void changed(ObservableValue<? extends String> observable, String oldValue,
							String newValue) {
						if (!newValue.matches("\\d+")) {
							Alert alert = new Alert(AlertType.ERROR);
							// alert.setTitle(titleTxt);
							alert.setHeaderText("Error");
							String s = "Please enter valid number";
							alert.setContentText(s);
							alert.show();
							controller.getPopupinterval().setText(oldValue);
							controller.getPopupinterval().positionCaret(controller.getPopupinterval().getLength());
						}
					}
				});
			}

			if (abstractComp instanceof SpotLight) {

				changeOpacity(controller, abstractComp, 50);

				controller.getBgcolor().valueProperty()
						.addListener((observable, oldValue, newValue) -> changeSpotlightBG(abstractComp, newValue,
								controller.getOpacity().getValue() * 0.01, controller.getFillbg().isSelected()));

				controller.getFillbg().selectedProperty()
						.addListener((observable, oldValue, newValue) -> changeSpotlightBG(abstractComp,
								controller.getBgcolor().getValue(), controller.getOpacity().getValue() * 0.01,
								newValue));

				controller.getOpacity().valueProperty().addListener(
						(observable, oldColor, newalpha) -> changeOpacity(controller, abstractComp, newalpha));
			}

			if (abstractComp instanceof AnnotationMarkers) {

				DoubleProperty masterArrowSize = new SimpleDoubleProperty(12);
				DoubleProperty masterArrowIndent = new SimpleDoubleProperty(12);
				DoubleProperty masterCornerRadius = new SimpleDoubleProperty(6);
				ObjectProperty<ArrowLocation> masterArrowLocation = new SimpleObjectProperty<>(ArrowLocation.RIGHT_TOP);
				BooleanProperty detachableProperty = new SimpleBooleanProperty(false);
				BooleanProperty detachedProperty = new SimpleBooleanProperty(false);

				PopOver popOver = abstractComp.getMarkerPopup().getPopOver();
				controller.getWidth().setText("" + abstractComp.getMarkerPopup().getView().getFitWidth());
				controller.getHeight().setText("" + abstractComp.getMarkerPopup().getView().getFitHeight());
				controller.getDetachable().setSelected(false);
				controller.getInitially_detached().setSelected(false);
				popOver.detachableProperty().bind(detachableProperty);
				popOver.detachedProperty().bind(detachedProperty);
				popOver.arrowSizeProperty().bind(masterArrowSize);
				popOver.arrowIndentProperty().bind(masterArrowIndent);
				popOver.arrowLocationProperty().bind(masterArrowLocation);
				popOver.cornerRadiusProperty().bind(masterCornerRadius);

				detachableProperty.bind(controller.getInitially_detached().selectedProperty());
				detachedProperty.bind(controller.getDetachable().selectedProperty());
				masterArrowSize.bind(controller.getArrow_size().valueProperty());
				masterArrowIndent.bind(controller.getArrow_indent().valueProperty());
				masterCornerRadius.bind(controller.getCorner_radius().valueProperty());
				Bindings.bindBidirectional(masterArrowLocation, controller.getArrow_loaction().valueProperty());

			}

			// End annotation panel

			// save,cancel,close button

			/*
			 * Save button: --> validate, store in the list, hide the
			 * annotation, add to timeline
			 */

			// null check due to static comp added
			if (controller.getTextbox() != null) {
				ValidationSupport validationSupport = new ValidationSupport();
				validationSupport.registerValidator(controller.getTextbox(),
						Validator.createEmptyValidator("Text is required"));
			}

			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

				@Override
				public void handle(WindowEvent event) {
					boolean display = ConfirmBox.display("title", "Are u sure:");
					if (display) {
						try {
							hideComponets(vController, rect, controller);
						} catch (Exception e) {
						}
						stage.close();
					}

				}

				public void hideComponets(VideoEditorController vController, Rectangle rect,
						AbstractAddController controller) {
					rect.setVisible(false);
					if (controller.getComp().isInfopanel())
						vController.getPlayerHolder().getChildren().remove(controller.getComp().getInfoOverlay());
					if (controller.getComp() instanceof AnnotationMarkers) {

						if (controller.getComp().getMarkerPopup().getPopOver().isShowing())
							controller.getComp().getMarkerPopup().getPopOver().hide();

						vController.getPlayerHolder().getChildren()
								.remove(controller.getComp().getMarkerPopup().getView());
					}
					vController.getEndtimeLabel().setVisible(false);
					vController.getEndtimeSlider().setVisible(false);

					vController.getTimeSlider().setVisible(true);
					vController.getTimeLabel().setVisible(true);
				}
			});

			// On cancel button clicked
			controller.getCancel().setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {

					// show dialog confirm
					boolean display = ConfirmBox.display("title", "Are u sure:");
					if (display) {
						try {
							hideComponets(vController, rect, controller);
						} catch (Exception e) {
						}
						stage.close();
					}

				}

				public void hideComponets(VideoEditorController vController, Rectangle rect,
						AbstractAddController controller) {
					rect.setVisible(false);
					if (controller.getComp().isInfopanel())
						vController.getPlayerHolder().getChildren().remove(controller.getComp().getInfoOverlay());
					if (controller.getComp() instanceof AnnotationMarkers) {
						if (controller.getComp().getMarkerPopup().getPopOver().isShowing())
							controller.getComp().getMarkerPopup().getPopOver().hide();
						vController.getPlayerHolder().getChildren()
								.remove(controller.getComp().getMarkerPopup().getView());
					}
					vController.getEndtimeLabel().setVisible(false);
					vController.getEndtimeSlider().setVisible(false);

					vController.getTimeSlider().setVisible(true);
					vController.getTimeLabel().setVisible(true);

				}

			});

			controller.getSave().setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					boolean isOK = true;
					try {
						if (controller.getComp().isTextIncluded()
								|| controller.getComp() instanceof AnnotationMarkers) {
							TextArea ta = controller.getTextbox();
							if (ta.getText() == null || ta.getText().equalsIgnoreCase("")) {
								Alert alert = new Alert(AlertType.ERROR);
								// alert.setTitle(titleTxt);
								alert.setHeaderText("Information Alert");
								String s = "Text is required ";
								alert.setContentText(s);
								alert.show();
								isOK = false;
							} else if (controller.getComp() instanceof AnnotationMarkers) {
								if (controller.getBgfilepath() == null
										|| controller.getBgfilepath().getText().equalsIgnoreCase("")) {
									Alert alert = new Alert(AlertType.ERROR);
									// alert.setTitle(titleTxt);
									alert.setHeaderText("Information Alert");
									String s = "Please select an image";
									alert.setContentText(s);
									alert.show();
									isOK = false;
								}
							}

						}

						if (!(controller.getComp() instanceof Title) && vController.getTimeSlider()
								.getValue() == vController.getEndtimeSlider().getValue()) {
							Alert alert = new Alert(AlertType.ERROR);
							// alert.setTitle(titleTxt);
							alert.setHeaderText("Information Alert");
							String s = "Please select end time";
							alert.setContentText(s);
							alert.show();
							isOK = false;
						}
						if (isOK) {
							setComponent(controller, rect);
							hideComponets(vController, rect, controller);
							stage.close();
						}

					} catch (Exception e) {
						e.printStackTrace();
					}

				}

				public void hideComponets(VideoEditorController vController, Rectangle rect,
						AbstractAddController controller) {
					rect.setVisible(false);
					if (controller.getComp().isInfopanel())
						vController.getPlayerHolder().getChildren().remove(controller.getComp().getInfoOverlay());
					if (controller.getComp() instanceof AnnotationMarkers) {
						if (controller.getComp().getMarkerPopup().getPopOver().isShowing())
							controller.getComp().getMarkerPopup().getPopOver().hide();
						vController.getPlayerHolder().getChildren()
								.remove(controller.getComp().getMarkerPopup().getView());
					}

					if (controller.getComp() instanceof Title) {
						vController.getTimeSlider().setVisible(true);
					}

					vController.getEndtimeLabel().setVisible(false);
					vController.getEndtimeSlider().setVisible(false);

					vController.getTimeSlider().setVisible(true);
					vController.getTimeLabel().setVisible(true);

				}

				private void setComponent(AbstractAddController controller, Rectangle rect) {

					// set start time and end time
					Duration duration = new Duration(
							vController.getMediaPlayerComponent().getMediaPlayer().getLength());
					int startTime = (int) duration.multiply(vController.getTimeSlider().getValue() / 100.0).toMillis();
					int endTime = (int) duration.multiply(vController.getEndtimeSlider().getValue() / 100.0).toMillis();
					String id = "" + vController.getIndex();
					controller.getComp().setId(id);
					controller.getComp().setStartTime(startTime);
					controller.getComp().setEndTime(endTime);
					if (controller.getComp() != null && controller.getComp() instanceof Note) {
						Note label = (Note) controller.getComp();
						label.setBounds(Double.parseDouble("" + rect.getX() + 5),
								Double.parseDouble("" + rect.getY() + 5), Double.parseDouble("" + rect.getWidth()) - 10,
								Double.parseDouble("" + rect.getHeight()) - 10);
						label.setTextOptions(controller.getTextbox().getText(),
								toRgbString(controller.getFont_colour().getValue()), controller.getFont().getFamily(),
								"" + controller.getFont_size().getValue(), controller.getFont_bold().isSelected(),
								controller.getFont_I().isSelected(), controller.getFont_strikeout().isSelected(),
								controller.getFont_U().isSelected());
						label.setBackgroundOptions(
								toRgbStringa(controller.getBgcolor().getValue(), controller.getOpacity().getValue()));
					} else if (controller.getComp() != null && controller.getComp() instanceof Title) {
						Title title = (Title) controller.getComp();
						title.setBounds(0, 0, Double.parseDouble("" + rect.getWidth()) - 10,
								Double.parseDouble("" + rect.getHeight()) - 10);
						title.setTextOptions(controller.getTextbox().getText(),
								toRgbString(controller.getFont_colour().getValue()), controller.getFont().getFamily(),
								"" + controller.getFont_size().getValue(), controller.getFont_bold().isSelected(),
								controller.getFont_I().isSelected(), controller.getFont_strikeout().isSelected(),
								controller.getFont_U().isSelected());
						title.setBackgroundOptions(
								toRgbStringa(controller.getBgcolor().getValue(), controller.getOpacity().getValue()));
						title.setPopupinterval(Integer.parseInt(controller.getPopupinterval().getText()));
					} else if (controller.getComp() != null && controller.getComp() instanceof SpotLight) {
						SpotLight spotLight = (SpotLight) controller.getComp();
						spotLight.setBounds(Double.parseDouble("" + rect.getX() + 5),
								Double.parseDouble("" + rect.getY() + 5), Double.parseDouble("" + rect.getWidth()) - 10,
								Double.parseDouble("" + rect.getHeight()) - 10);
						spotLight.setTextOptions(controller.getTextbox().getText(),
								toRgbString(controller.getFont_colour().getValue()), controller.getFont().getFamily(),
								"" + controller.getFont_size().getValue(), controller.getFont_bold().isSelected(),
								controller.getFont_I().isSelected(), controller.getFont_strikeout().isSelected(),
								controller.getFont_U().isSelected());
						spotLight.setBgColor(
								toRgbStringa(controller.getBgcolor().getValue(), controller.getOpacity().getValue()));
						spotLight.setBackgroundOptions(
								toRgbStringa(controller.getBgcolor().getValue(), controller.getOpacity().getValue()));
						spotLight.setBgfilepath(controller.getBgfilepath().getText());
						// System.out.println(controller.getShowonhover().isSelected());
						spotLight.setShowonhover(controller.getShowonhover().isSelected());
						spotLight.setFillbg(controller.getFillbg().isSelected());
					} else if (controller.getComp() != null && controller.getComp() instanceof AnnotationMarkers) {
						AnnotationMarkers marker = (AnnotationMarkers) controller.getComp();
						marker.setBounds(Double.parseDouble("" + rect.getX() + 5),
								Double.parseDouble("" + rect.getY() + 5), Double.parseDouble("" + rect.getWidth()) - 10,
								Double.parseDouble("" + rect.getHeight()) - 10);
						marker.setTextOptions(controller.getTextbox().getText(),
								toRgbString(controller.getFont_colour().getValue()), controller.getFont().getFamily(),
								"" + controller.getFont_size().getValue(), controller.getFont_bold().isSelected(),
								controller.getFont_I().isSelected(), controller.getFont_strikeout().isSelected(),
								controller.getFont_U().isSelected());
						// System.out.println(" -- " +
						// controller.getBgfilepath().getText());
						marker.setBgfilepath(controller.getBgfilepath().getText());

						// TODO decide for autoposition
						marker.setMarkerProperties(controller.getDetachable().isSelected(),
								controller.getInitially_detached().isSelected(), false,
								controller.getArrow_loaction().getSelectionModel().getSelectedItem(),
								controller.getArrow_size().getValue(), controller.getArrow_indent().getValue(),
								controller.getCorner_radius().getValue());

					} else if (controller.getComp() != null && controller.getComp() instanceof CircleComp) {
						CircleComp circleComp = (CircleComp) controller.getComp();
						// For circle height and width are same
						Double radius = Double.parseDouble("" + rect.getHeight()) / 2;
						circleComp.setCenterX(Double.parseDouble("" + rect.getX()) + radius);
						circleComp.setCenterY(Double.parseDouble("" + rect.getY()) + radius);
						circleComp.setRadius(radius);
						circleComp.setFillShape(controller.getFillShape().isSelected());
						circleComp.setBgColor(
								toRgbStringa(controller.getBgcolor().getValue(), controller.getOpacity().getValue()));
					} else if (controller.getComp() != null && controller.getComp() instanceof RectangleComp) {
						RectangleComp rectComp = (RectangleComp) controller.getComp();
						rectComp.setStartX(rect.getX());
						rectComp.setStartY(rect.getY());
						rectComp.setWidth(rect.getWidth());
						rectComp.setHeight(rect.getHeight());
						rectComp.setFillShape(controller.getFillShape().isSelected());
						rectComp.setBgColor(
								toRgbStringa(controller.getBgcolor().getValue(), controller.getOpacity().getValue()));
					} else if (controller.getComp() != null && controller.getComp() instanceof LineComp) {
						LineComp lineComp = (LineComp) controller.getComp();
						lineComp.setStartX(rect.getX());
						lineComp.setStartY(rect.getY() - 2);
						lineComp.setLength(rect.getWidth());
						lineComp.setHeight(rect.getHeight() - 4);
						lineComp.setFillShape(controller.getFillShape().isSelected());
						lineComp.setBgColor(
								toRgbStringa(controller.getBgcolor().getValue(), controller.getOpacity().getValue()));
					} else if (controller.getComp() != null && controller.getComp() instanceof TextComp) {
						TextComp text = (TextComp) controller.getComp();
						text.setStartX(rect.getX());
						text.setStartY(rect.getY());
						text.setTextOptions(controller.getTextbox().getText(),
								toRgbStringa(controller.getFont_colour().getValue(),
										controller.getOpacity().getValue()),
								controller.getFont().getFamily(), "" + controller.getFont_size().getValue(),
								controller.getFont_bold().isSelected(), controller.getFont_I().isSelected(),
								controller.getFont_strikeout().isSelected(), controller.getFont_U().isSelected());
					}

					vController.getCompList().put(vController.getIndex() + 1, controller.getComp());
					vController.setIndex(vController.getIndex() + 1);
					vController.getListItems().add(controller.getComp());
				}
			});

			stage.show();
		} catch (

		Exception e)

		{
			e.printStackTrace();

		}

	}

	private static void setupShapeProperties(VideoEditorController vController, AbstractComp abstractComp,
			AbstractAddController controller) {

		abstractComp.getShape()
				.setFill(new Color(Color.WHITE.getRed(), Color.WHITE.getGreen(), Color.WHITE.getBlue(), 50 * 0.01));

		if (abstractComp.getShapeType() == SHAPE_TYPE.CIRCLE) {

			if (controller.getOpacity() != null) {
				controller.getOpacity().valueProperty().addListener((observable, oldColor, newalpha) -> {
					Color color = controller.getBgcolor().getValue();
					abstractComp.getShape().setFill(
							new Color(color.getRed(), color.getGreen(), color.getBlue(), (double) newalpha * 0.01));
				});
			}

			if (controller.getBgcolor() != null) {
				controller.getBgcolor().valueProperty()
						.addListener((observable, oldValue, newValue) -> abstractComp.getShape()
								.setFill(new Color(newValue.getRed(), newValue.getGreen(), newValue.getBlue(),
										controller.getOpacity().getValue() * 0.01)));
			}

			Circle circle = (Circle) abstractComp.getShape();
			circle.radiusProperty().addListener((observable, oldValue, newValue) -> {
				// System.out.println("controller.getWidth().setText");
				controller.getWidth().setText("" + newValue);
				controller.getHeight().setText("" + newValue);
			});

		} else if (abstractComp.getShapeType() == SHAPE_TYPE.RECTANGLE) {

			if (controller.getOpacity() != null) {
				controller.getOpacity().valueProperty().addListener((observable, oldColor, newalpha) -> {
					Color color = controller.getBgcolor().getValue();
					abstractComp.getShape().setFill(
							new Color(color.getRed(), color.getGreen(), color.getBlue(), (double) newalpha * 0.01));
				});
			}

			if (controller.getBgcolor() != null) {
				controller.getBgcolor().valueProperty()
						.addListener((observable, oldValue, newValue) -> abstractComp.getShape()
								.setFill(new Color(newValue.getRed(), newValue.getGreen(), newValue.getBlue(),
										controller.getOpacity().getValue() * 0.01)));
			}

			Rectangle rectangle = (Rectangle) abstractComp.getShape();
			rectangle.widthProperty().addListener((observable, oldValue, newValue) -> {
				// System.out.println("controller.getWidth().setText");
				controller.getWidth().setText("" + newValue);
			});
			rectangle.heightProperty().addListener((observable, oldValue, newValue) -> {
				// System.out.println("controller.getWidth().setText");
				controller.getHeight().setText("" + newValue);
			});

		} else if (abstractComp.getShapeType() == SHAPE_TYPE.LINE) {

			Line line = (Line) abstractComp.getShape();
			controller.getWidth().setText("" + (line.getEndY() - line.getStartX()));
			controller.getHeight().setText("" + line.getStrokeWidth());

			if (controller.getOpacity() != null) {
				controller.getOpacity().valueProperty().addListener((observable, oldColor, newalpha) -> {
					Color color = controller.getBgcolor().getValue();
					line.setStroke(
							new Color(color.getRed(), color.getGreen(), color.getBlue(), (double) newalpha * 0.01));
				});
			}

			if (controller.getBgcolor() != null) {
				controller.getBgcolor().valueProperty()
						.addListener((observable, oldValue, newValue) -> line.setStroke(new Color(newValue.getRed(),
								newValue.getGreen(), newValue.getBlue(), controller.getOpacity().getValue() * 0.01)));
			}

			line.endXProperty().addListener((observable, oldValue, newValue) -> {
				// System.out.println("controller.getWidth().setText");
				controller.getWidth().setText("" + ((double) newValue - line.getStartX()));
			});

			line.startXProperty().addListener((observable, oldValue, newValue) -> {
				// System.out.println("controller.getWidth().setText");
				controller.getWidth().setText("" + (line.getEndX() - (double) newValue));
			});

			line.strokeWidthProperty().addListener((observable, oldValue, newValue) -> {
				// System.out.println("controller.getWidth().setText");
				controller.getHeight().setText("" + newValue);
			});
		} else if (abstractComp.getShapeType() == SHAPE_TYPE.TEXT) {
			Text text = (Text) abstractComp.getShape();
			controller.getFont_size().setValue(10);
			controller.getFonts().getSelectionModel().select(0);

			if (text != null && controller.getFont_strikeout() != null)
				text.setStrikethrough(controller.getFont_strikeout().isSelected());
			if (text != null && controller.getFont_U() != null)
				text.setUnderline(controller.getFont_U().isSelected());

			controller.getTextbox().textProperty().addListener((observable, oldText, newText) -> {
				text.setText(newText);
			});

			controller.getFonts().valueProperty()
					.addListener((ChangeListener<? super String>) (observable, oldValue, newValue) -> {
						setTextProperties(controller, text);
					});

			controller.getFont_colour().valueProperty()
					.addListener((observable, oldColor, newColor) -> text.setFill(new Color(newColor.getRed(),
							newColor.getGreen(), newColor.getBlue(), controller.getOpacity().getValue() * .01)));

			controller.getOpacity().valueProperty().addListener((observable, oldVal, newVal) -> {
				Color newColor = controller.getFont_colour().getValue();
				text.setFill(
						new Color(newColor.getRed(), newColor.getGreen(), newColor.getBlue(), (double) newVal * .01));
			}); // End

			controller.getFont_size().valueProperty()
					.addListener((ChangeListener<? super Integer>) (observable, oldValue, newValue) -> {
						setTextProperties(controller, text);
					});

			// Font styling controls
			controller.getFont_bold().selectedProperty()
					.addListener((ChangeListener<? super Boolean>) (observable, oldValue, newValue) -> {
						setTextProperties(controller, text);
					});

			controller.getFont_I().selectedProperty()
					.addListener((ChangeListener<? super Boolean>) (observable, oldValue, newValue) -> {
						setTextProperties(controller, text);
					});

			controller.getFont_U().selectedProperty()
					.addListener((ChangeListener<? super Boolean>) (observable, oldValue, newValue) -> {
						setTextProperties(controller, text);
					});

			controller.getFont_strikeout().selectedProperty()
					.addListener((ChangeListener<? super Boolean>) (observable, oldValue, newValue) -> {
						setTextProperties(controller, text);
					});

		}

	}

	private static void setTextProperties(AbstractAddController controller, Text text) {

		String val = text.getText();
		FontWeight weight = FontWeight.NORMAL;
		if (controller.getFont_bold().isSelected())
			weight = FontWeight.BOLD;
		FontPosture posture = FontPosture.REGULAR;
		if (controller.getFont_I().isSelected())
			posture = FontPosture.ITALIC;
		Font font = Font.font(controller.getFonts().getSelectionModel().getSelectedItem(), weight, posture,
				controller.getFont_size().getSelectionModel().getSelectedItem());

		text.setFont(font);
		//System.out.println(font);
		text.setUnderline(controller.getFont_U().isSelected());
		text.setStrikethrough(controller.getFont_strikeout().isSelected());
		text.setText(val);
	}

	private static void changeOpacity(AbstractAddController controller, AbstractComp abstractComp, Number newalpha) {
		if (controller.getBgfilepath().getText().equalsIgnoreCase(""))
			changeSpotlightBG(abstractComp, controller.getBgcolor().getValue(), newalpha.doubleValue() * 0.01,
					controller.getFillbg().isSelected());
		else
			changeImagOpacity(abstractComp);
	}

	private static void changeImagOpacity(AbstractComp abstractComp) {
		// TODO image opaque code
	}

	private static void setupTextPropertiesForTextBox(VideoEditorController vController, AbstractComp abstractComp,
			AbstractAddController controller) {
		TextArea ta = abstractComp.getTextbox();
		setTextAreaProperties(controller, ta);

		// Display size in the width height box
		// Changed to add accuracy
		ta.widthProperty().addListener((ChangeListener<? super Number>) (observable, oldValue, newValue) -> {
			Double width = ((Double) newValue) / vController.getWtFactor();
			controller.getWidth().setText("" + width.intValue());
		});
		ta.heightProperty().addListener((ChangeListener<? super Number>) (observable, oldValue, newValue) -> {
			Double height = ((Double) newValue) / vController.getHtFactor();
			controller.getHeight().setText("" + height.intValue());
		});

		/*
		 * ObjectProperty<Background> background = ta.backgroundProperty();
		 * background.bind(Bindings.createObjectBinding(() -> { Color value =
		 * controller.getBgcolor().valueProperty().getValue(); BackgroundFill
		 * fill = new BackgroundFill(new Color(value.getRed(), value.getGreen(),
		 * value.getBlue(), controller.getOpacity().getValue() * .01),
		 * CornerRadii.EMPTY, Insets.EMPTY); return new Background(fill); } ,
		 * controller.getBgcolor().valueProperty(),
		 * controller.getOpacity().valueProperty()));
		 */
	}

	private static void setTextAreaProperties(AbstractAddController controller, TextArea ta) {
		ta.setFont(new Font("Arial", 10));

		controller.getFont_size().setValue(10);
		controller.getFonts().getSelectionModel().select(0);

		setCSSProperties(ta, controller);

		// Text panel
		// Bind the text in the text area with user defined
		ta.textProperty().bind(controller.getTextbox().textProperty());

		// fonts
		controller.getFont_size().getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				Font font = controller.getFont(controller.getFont().getFamily(), newValue.intValue());
				ta.setFont(font);
				setCSSProperties(ta, controller);
			}
		});

		controller.getFonts().getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				Font font = controller.getFont(newValue.intValue());
				ta.setFont(font);
				setCSSProperties(ta, controller);

			}
		});

		controller.getFont_colour().valueProperty()
				.addListener((observable, oldColor, newColor) -> setCSSProperties(ta, controller));

		if (controller.getOpacity() != null)
			controller.getOpacity().valueProperty()
					.addListener((observable, oldColor, newalpha) -> setCSSProperties(ta, controller));
				// End

		// Font styling controls
		controller.getFont_bold().selectedProperty()
				.addListener((ChangeListener<? super Boolean>) (observable, oldValue, newValue) -> {
					ta.setFont(controller.getFont(true));
				});

		controller.getFont_I().selectedProperty()
				.addListener((ChangeListener<? super Boolean>) (observable, oldValue, newValue) -> {
					ta.setFont(controller.getFont(true));
				});

		controller.getFont_U().selectedProperty()
				.addListener((ChangeListener<? super Boolean>) (observable, oldValue, newValue) -> {
					ta.setFont(controller.getFont(true));
				});

		controller.getFont_strikeout().selectedProperty()
				.addListener((ChangeListener<? super Boolean>) (observable, oldValue, newValue) -> {
					setCSSProperties(ta, controller);
				});
				// end
				// end text panel

		// align boxes
		/*
		 * controller.getLeftalign().setOnMouseClicked(new
		 * EventHandler<MouseEvent>() {
		 * 
		 * @Override public void handle(MouseEvent event) { setCSSProperties(ta,
		 * controller, TextAlignment.LEFT); }
		 * 
		 * }); controller.getRightalign().setOnMouseClicked(new
		 * EventHandler<MouseEvent>() {
		 * 
		 * @Override public void handle(MouseEvent event) { setCSSProperties(ta,
		 * controller, TextAlignment.RIGHT); }
		 * 
		 * }); controller.getJestifyalign().setOnMouseClicked(new
		 * EventHandler<MouseEvent>() {
		 * 
		 * @Override public void handle(MouseEvent event) { setCSSProperties(ta,
		 * controller, TextAlignment.JUSTIFY); }
		 * 
		 * }); controller.getCenteralign().setOnMouseClicked(new
		 * EventHandler<MouseEvent>() {
		 * 
		 * @Override public void handle(MouseEvent event) { setCSSProperties(ta,
		 * controller, TextAlignment.CENTER); }
		 * 
		 * });
		 */ }

	private static void setupTextPropertiesForMarker(VideoEditorController vController, AbstractComp abstractComp,
			AbstractAddController controller) {
		TextArea ta = abstractComp.getMarkerPopup().getContentText();

		setTextAreaProperties(controller, ta);
		// oldMetod(controller, ta);

		// Display size in the width height box
		// Changed to add accuracy
		abstractComp.getMarkerPopup().getView().fitWidthProperty()
				.addListener((ChangeListener<? super Number>) (observable, oldValue, newValue) -> {
					Double width = ((Double) newValue) / vController.getWtFactor();
					controller.getWidth().setText("" + width.intValue());
				});
		abstractComp.getMarkerPopup().getView().fitHeightProperty()
				.addListener((ChangeListener<? super Number>) (observable, oldValue, newValue) -> {
					Double height = ((Double) newValue) / vController.getHtFactor();
					controller.getHeight().setText("" + height.intValue());
				});

	}

	private static void oldMetod(AbstractAddController controller, TextArea ta) {
		// Text panel
		// Bind the text in the text area with user defined
		// fonts
		controller.getFont_size().getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				Font font = controller.getFont(controller.getFont().getFamily(), newValue.intValue());
				ta.setFont(font);
			}
		});

		controller.getFonts().getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				Font font = controller.getFont(newValue.intValue());
				ta.setFont(font);
			}
		});

		ta.textProperty().bind(controller.getTextbox().textProperty());

		controller.getFont_colour().valueProperty().addListener(
				(observable, oldColor, newColor) -> ta.setStyle("-fx-text-fill: " + toRgbString(newColor) + ";"));

		// Font styling controls
		controller.getFont_bold().selectedProperty()
				.addListener((ChangeListener<? super Boolean>) (observable, oldValue, newValue) -> {
					Boolean isTrue = (Boolean) newValue;
					if (isTrue) {
						ta.setStyle("-fx-font-weight: bold;");
					} else {
						ta.setStyle("-fx-font-weight: regular;");
					}
				});

		controller.getFont_I().selectedProperty()
				.addListener((ChangeListener<? super Boolean>) (observable, oldValue, newValue) -> {
					Boolean isTrue = (Boolean) newValue;
					if (isTrue) {
						ta.setStyle("-fx-font-style: italic;");
					} else {
						ta.setStyle("-fx-font-style: normal;");
					}
				});

		controller.getFont_U().selectedProperty()
				.addListener((ChangeListener<? super Boolean>) (observable, oldValue, newValue) -> {
					Boolean isTrue = (Boolean) newValue;
					if (isTrue) {
						ta.setStyle(".text -fx-underline: true;");
					} else {
						ta.setStyle("-fx-underline: false;");
					}
				});

		controller.getFont_strikeout().selectedProperty()
				.addListener((ChangeListener<? super Boolean>) (observable, oldValue, newValue) -> {
					Boolean isTrue = (Boolean) newValue;
					if (isTrue) {
						ta.setStyle("-fx-strikethrough: true;");
					} else {
						ta.setStyle("-fx-strikethrough: false;");
					}
				});
		// end
		// end text panel
	}

	private static void setupTextPropertiesInfoOverlay(VideoEditorController vController, AbstractComp abstractComp,
			AbstractAddController controller) {
		InfoOverlay infoOverlay = abstractComp.getInfoOverlay();

		infoOverlay.textProperty().bind(controller.getTextbox().textProperty());
		infoOverlay.setStyle("-fx-text-fill: " + toRgbString(Color.BLACK) + ";");

		/*
		 * ObjectProperty<Background> background =
		 * infoOverlay.backgroundProperty();
		 * background.bind(Bindings.createObjectBinding(() -> { Color value =
		 * controller.getBgcolor().valueProperty().getValue(); BackgroundFill
		 * fill = new BackgroundFill(new Color(value.getRed(), value.getGreen(),
		 * value.getBlue(), controller.getOpacity().getValue() / 100),
		 * CornerRadii.EMPTY, Insets.EMPTY); return new Background(fill); } ,
		 * controller.getBgcolor().valueProperty(),
		 * controller.getOpacity().valueProperty()));
		 */

		controller.getFont_colour().valueProperty().addListener((observable, oldColor, newColor) -> infoOverlay
				.setStyle("-fx-text-fill: " + toRgbString(newColor) + ";"));

		// Font styling controls
		controller.getFont_bold().selectedProperty()
				.addListener((ChangeListener<? super Boolean>) (observable, oldValue, newValue) -> {
					Boolean isTrue = (Boolean) newValue;
					if (isTrue) {
						infoOverlay.setStyle("-fx-font-weight: bold;");
					} else {
						infoOverlay.setStyle("-fx-font-weight: regular;");
					}
				});

		controller.getFont_I().selectedProperty()
				.addListener((ChangeListener<? super Boolean>) (observable, oldValue, newValue) -> {
					Boolean isTrue = (Boolean) newValue;
					if (isTrue) {
						infoOverlay.setStyle("-fx-font-style: italic;");
					} else {
						infoOverlay.setStyle("-fx-font-style: normal;");
					}
				});

		controller.getFont_U().selectedProperty()
				.addListener((ChangeListener<? super Boolean>) (observable, oldValue, newValue) -> {
					Boolean isTrue = (Boolean) newValue;
					if (isTrue) {
						infoOverlay.setStyle(".text -fx-underline: true;");
					} else {
						infoOverlay.setStyle("-fx-underline: false;");
					}
				});

		controller.getFont_strikeout().selectedProperty()
				.addListener((ChangeListener<? super Boolean>) (observable, oldValue, newValue) -> {
					Boolean isTrue = (Boolean) newValue;
					if (isTrue) {
						infoOverlay.setStyle("-fx-text-strikethrough: true;");
					} else {
						infoOverlay.setStyle("-fx-text-strikethrough: false;");
					}
				});
				// end
				// end text panel

		// Display size in the width height box
		// Changed to add accuracy
		infoOverlay.widthProperty().addListener((ChangeListener<? super Number>) (observable, oldValue, newValue) -> {
			Double width = ((Double) newValue) / vController.getWtFactor();
			controller.getWidth().setText("" + width.intValue());
		});

		infoOverlay.heightProperty().addListener((ChangeListener<? super Number>) (observable, oldValue, newValue) -> {
			Double height = ((Double) newValue) / vController.getHtFactor();
			controller.getHeight().setText("" + height.intValue());
		});

		controller.getShowonhover().selectedProperty()
				.addListener((ChangeListener<? super Boolean>) (observable, oldValue, newValue) -> {
					Boolean isTrue = (Boolean) newValue;
					infoOverlay.setShowOnHover(isTrue);
				});
	}

	private static void setCSSProperties(TextArea ta, AbstractAddController controller, TextAlignment align) {
		Text text = setCSSProperties(ta, controller);
		setTextAlignment(text, align);
	}

	private static void setTextAlignment(Text ta, TextAlignment align) {
		if (ta != null)
			ta.setTextAlignment(align);
	}

	private static Text setCSSProperties(TextArea ta, AbstractAddController controller) {
		Text text = null;
		try {

			// SetBackground
			Region region = (Region) ta.lookup(".content");
			if (region != null)
				region.setStyle("-fx-background-color: " + toRgbStringa(controller.getBgcolor().getValue(),
						(double) controller.getOpacity().getValue() / 100) + ";");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			// SetFontProperties
			ta.setStyle("-fx-text-fill: " + toRgbString(controller.getFont_colour().getValue()) + ";");

			text = (Text) ta.lookup(".text");
			if (text != null && controller.getFont_U() != null)
				text.setUnderline(controller.getFont_U().isSelected());
			if (text != null && controller.getFont_strikeout() != null)
				text.setStrikethrough(controller.getFont_strikeout().isSelected());
			Text text2 = (Text) controller.getTextbox().lookup(".text");
			if (text2 != null && controller.getFont_U() != null)
				text2.setUnderline(controller.getFont_U().isSelected());
			if (text2 != null && controller.getFont_strikeout() != null)
				text2.setStrikethrough(controller.getFont_strikeout().isSelected());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return text;
	}

	private static String toRgbStringa(Color c, double alpha) {
		String t = "rgba(" + to255Int(c.getRed()) + "," + to255Int(c.getGreen()) + "," + to255Int(c.getBlue()) + ","
				+ alpha + ")";
		return t;
	}

	private static String toRgbString(Color c) {
		String t = "rgb(" + to255Int(c.getRed()) + "," + to255Int(c.getGreen()) + "," + to255Int(c.getBlue()) + ")";
		return t;
	}

	private static int to255Int(double d) {
		return (int) (d * 255);
	}

	private static void changeSpotlightBG(AbstractComp abstractComp, Color color, double opacity, Boolean isSelected) {
		if (isSelected) {
			ImageView iew = (ImageView) abstractComp.getInfoOverlay().getContent();
			WritableImage createFilledImage = Helper.createFilledImage(iew, Helper.getColorWithOpacity(color, opacity));
			iew.setImage(null);
			iew.setImage(createFilledImage);
		} else {
			ImageView iew = (ImageView) abstractComp.getInfoOverlay().getContent();
			WritableImage createFilledImage = Helper.createBorderImage(iew, Helper.getColorWithOpacity(color, opacity));
			iew.setImage(null);
			iew.setImage(createFilledImage);
		}
	}

	public static void setupAddTagWindow(VideoEditorController vController, FXMLLoader loader,
			DirectMediaPlayer mediaPlayer) {
		Parent root;
		try {
			root = (Parent) loader.load();
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.setTitle("Add Tags");
			AddTagController controller = (AddTagController) loader.getController();

			// start end timer
			vController.showEndSlider();
			controller.getStart_time_text().textProperty().bind(vController.getTimeLabel().textProperty());
			controller.getEnd_time_text().textProperty().bind(vController.getEndtimeLabel().textProperty());

			// Save button
			controller.getSave_butt().setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					try {
						// validation description not blank and keyword not
						// blank

						ValidationSupport validationSupport = new ValidationSupport();
						validationSupport.registerValidator(controller.getDescription_text(),
								Validator.createEmptyValidator("Description is required"));

						validationSupport = new ValidationSupport();
						validationSupport.registerValidator(controller.getKeyword_text(),
								Validator.createEmptyValidator("Keywords required"));

						if (controller.getDescription_text().getText() == null
								|| controller.getDescription_text().getText().equalsIgnoreCase("")) {
							Alert alert = new Alert(AlertType.ERROR);
							// alert.setTitle(titleTxt);
							alert.setHeaderText("Information Alert");
							String s = "Description Required";
							alert.setContentText(s);
							alert.show();

							return;
						}

						if (controller.getDescription_text().getText() == null
								|| controller.getDescription_text().getText().length() > 200) {
							Alert alert = new Alert(AlertType.ERROR);
							// alert.setTitle(titleTxt);
							alert.setHeaderText("Information Alert");
							String s = "Description length exceeds max value(200)";
							alert.setContentText(s);
							alert.show();

							return;
						}

						if (controller.getKeyword_text().getText() == null
								|| controller.getKeyword_text().getText().equalsIgnoreCase("")) {
							Alert alert = new Alert(AlertType.ERROR);
							// alert.setTitle(titleTxt);
							alert.setHeaderText("Information Alert");
							String s = "Keywords Required";
							alert.setContentText(s);
							alert.show();

							return;
						}

						if (vController.getTimeSlider().getValue() == vController.getEndtimeSlider().getValue()) {
							Alert alert = new Alert(AlertType.ERROR);
							// alert.setTitle(titleTxt);
							alert.setHeaderText("Information Alert");
							String s = "Please select end time";
							alert.setContentText(s);
							alert.show();

							return;
						}

						// Identify all the keywords --> create tag --> refresh
						// UI and add to key-tag map for search option
						ObservableList<KeyWord> keywordList = vController.getKeywordObservableList();
						int index = keywordList.size();

						String keywordString = controller.getKeyword_text().getText();
						String[] keyWords = keywordString.split(",");
						List<KeyWord> listWords = new ArrayList<>(vController.getKeywordObservableList());
						for (String keyword : keyWords) {
							int id;
							KeyWord newWord = null;
							for (KeyWord word : keywordList) {
								if (word.getWord().equalsIgnoreCase(keyword)) {
									newWord = word;
									break;
								}
							}
							if (newWord == null) {
								id = index++;
								newWord = new KeyWord(keyword);
								newWord.setId(id);
								listWords.add(newWord);
							}
							controller.getTag().addKeyWord(newWord);
							System.out.println(newWord.toXml());

							List<VideoTag> tagsList = VideoEditorController.getKeyTagMap().get(keyword);
							if (tagsList == null) {
								tagsList = new ArrayList<>();
							}
							tagsList.add(controller.getTag());
							VideoEditorController.getKeyTagMap().put(keyword, tagsList);
						}

						controller.getTag().setTagDescription(controller.getDescription_text().getText());
						controller.getTag().setSegmentId(vController.getTagSegmentObservableList().size() + 1);
						Duration duration = new Duration(
								vController.getMediaPlayerComponent().getMediaPlayer().getLength());
						int newTime = (int) duration.multiply(vController.getTimeSlider().getValue() / 100.0)
								.toMillis();
						controller.getTag().setStartTime(newTime);
						newTime = (int) duration.multiply(vController.getEndtimeSlider().getValue() / 100.0).toMillis();
						controller.getTag().setEndTime(newTime);

						System.out.println(controller.getTag().toXml());
						vController.getTagSegmentObservableList().add(controller.getTag());
						vController.getTagSegmentListView().refresh();
						vController.getTagSegmentListView().setItems(null);
						vController.getTagSegmentListView().setItems(vController.getTagSegmentObservableList());

						vController.getKeywordListView().refresh();
						vController.getKeywordListView().setItems(null);
						Collections.sort(listWords);
						vController.getKeywordObservableList().clear();
						vController.getKeywordObservableList().addAll(listWords);
						vController.getKeywordListView().setItems(vController.getKeywordObservableList());

						vController.getEndtimeLabel().setVisible(false);
						vController.getEndtimeSlider().setVisible(false);

						stage.close();
						mediaPlayer.play();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});

			// cancel button
			controller.getCancel_butt().setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {

					boolean display = ConfirmBox.display("title", "Are u sure:");
					if (display) {
						try {
							vController.getEndtimeLabel().setVisible(false);
							vController.getEndtimeSlider().setVisible(false);
						} catch (Exception e) {
							e.printStackTrace();
						}

						stage.close();
						mediaPlayer.play();
					}
				}
			});

			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

				@Override
				public void handle(WindowEvent event) {
					boolean display = ConfirmBox.display("title", "Are u sure:");
					if (display) {
						try {
							vController.getEndtimeLabel().setVisible(false);
							vController.getEndtimeSlider().setVisible(false);
						} catch (Exception e) {
							e.printStackTrace();
						}

						mediaPlayer.play();
						stage.close();
					}
				}

			});
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();

		}

	}
}
