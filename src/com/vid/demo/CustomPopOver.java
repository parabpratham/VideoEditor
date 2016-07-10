package com.vid.demo;

import java.io.IOException;

import org.controlsfx.control.PopOver;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;

public class CustomPopOver extends Application {

	private Circle circle;

	private Line line1;

	private Line line2;

	private PopOver popOver;

	private double targetX;

	private double targetY;

	@Override
	public void start(Stage primaryStage) throws IOException {
		try {
			new NativeDiscovery().discover();

			Group group = new Group();

			Image image = new Image("file:C:\\1.jpg");

			final ImageView iew = new ImageView(image);

			final Rectangle rect = new Rectangle();

			rect.setStroke(Color.BLACK);

			rect.setFill(Color.CORAL);

			rect.setWidth(220);
			iew.setFitWidth(220);

			rect.setHeight(220);
			iew.setFitHeight(220);

			group.getChildren().add(iew);

			circle = new Circle();

			circle.setStroke(Color.BLACK);

			circle.setFill(Color.WHITE);

			group.getChildren().add(circle);

			line1 = new Line();

			line1.setFill(Color.BLACK);

			group.getChildren().add(line1);

			line2 = new Line();

			line2.setFill(Color.BLACK);

			group.getChildren().add(line2);

			rect.setOnScroll(new EventHandler<ScrollEvent>() {

				@Override

				public void handle(ScrollEvent evt) {

					double delta = evt.getDeltaY();

					rect.setWidth(Math.max(100,

					Math.min(500, rect.getWidth() + delta)));

					rect.setHeight(Math.max(100,

					Math.min(500, rect.getHeight() + delta)));

				}

			});

			iew.setOnScroll(new EventHandler<ScrollEvent>() {

				@Override

				public void handle(ScrollEvent evt) {

					double delta = evt.getDeltaY();

					rect.setWidth(Math.max(100,

					Math.min(500, rect.getWidth() + delta)));

					rect.setHeight(Math.max(100,

					Math.min(500, rect.getHeight() + delta)));

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

							popOver.hide(Duration.ZERO);

						}

						targetX = evt.getScreenX();

						targetY = evt.getScreenY();

						popOver = createPopOver();

						double size = 3;

						line1.setStartX(evt.getX() - size);

						line1.setStartY(evt.getY() - size);

						line1.setEndX(evt.getX() + size);

						line1.setEndY(evt.getY() + size);

						line2.setStartX(evt.getX() + size);

						line2.setStartY(evt.getY() - size);

						line2.setEndX(evt.getX() - size);

						line2.setEndY(evt.getY() + size);

						circle.setCenterX(evt.getX());

						circle.setCenterY(evt.getY());

						circle.setRadius(size * 3);

						/*
						 * if (autoPosition.isSelected()) {
						 * 
						 * popOver.show(rect);
						 * 
						 * } else {
						 */

						popOver.show(iew);

						// }

					}

				}

			});

			rect.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override

				public void handle(MouseEvent evt) {

					if (popOver != null && !popOver.isDetached()) {

						popOver.hide();

					}

					if (evt.getClickCount() == 2) {

						if (popOver != null && popOver.isShowing()) {

							popOver.hide(Duration.ZERO);

						}

						targetX = evt.getScreenX();

						targetY = evt.getScreenY();

						popOver = createPopOver();

						double size = 3;

						line1.setStartX(evt.getX() - size);

						line1.setStartY(evt.getY() - size);

						line1.setEndX(evt.getX() + size);

						line1.setEndY(evt.getY() + size);

						line2.setStartX(evt.getX() + size);

						line2.setStartY(evt.getY() - size);

						line2.setEndX(evt.getX() - size);

						line2.setEndY(evt.getY() + size);

						circle.setCenterX(evt.getX());

						circle.setCenterY(evt.getY());

						circle.setRadius(size * 3);

						/*
						 * if (autoPosition.isSelected()) {
						 * 
						 * popOver.show(rect);
						 * 
						 * } else {
						 */

						popOver.show(iew, targetX, targetY);

						// }

					}

				}

			});

			StackPane stackPane = new StackPane();

			stackPane.getChildren().add(iew);

			BorderPane.setMargin(stackPane, new Insets(10));

			BorderPane borderPane = new BorderPane();

			borderPane.setCenter(stackPane);

			Scene scene = new Scene(borderPane);
			primaryStage.setScene(scene);

			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private PopOver createPopOver() {

		PopOver popOver = new PopOver();

		popOver.setDetachable(false);

		popOver.setDetached(false);

		/*
		 * popOver.arrowSizeProperty().bind(masterArrowSize);
		 * 
		 * popOver.arrowIndentProperty().bind(masterArrowIndent);
		 * 
		 * popOver.arrowLocationProperty().bind(masterArrowLocation);
		 * 
		 * popOver.cornerRadiusProperty().bind(masterCornerRadius);
		 */

		TextArea text = new TextArea();
		text.setMaxHeight(100);
		text.setMaxWidth(100);
		text.setWrapText(true);
		popOver.setContentNode(text);

		return popOver;

	}

	public PopOver getPopOver() {

		return popOver;

	}

	public static void main(String[] args) {
		launch(args);
	}
}
