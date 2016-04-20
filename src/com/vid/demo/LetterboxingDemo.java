package com.vid.demo;

import javafx.application.Application;
import javafx.beans.value.*;
import javafx.scene.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Random;

public class LetterboxingDemo extends Application {

	private static final String IMAGE_URL = "/icons/8264285440_f5617efb71_b.jpg";
	private Image image;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void init() throws Exception {
		image = new Image(IMAGE_URL);
	}

	@Override
	public void start(final Stage stage) throws IOException {
		Pane root = createPane();

		Scene scene = new Scene(new Group(root));
		stage.setScene(scene);
		stage.show();

		letterbox(scene, root);
		stage.setFullScreen(true);
	}

	private StackPane createPane() {
		final int MAX_HEIGHT = 400;

		StackPane stack = new StackPane();

		Pane content = new Pane();

		ImageView imageView = new ImageView(image);
		imageView.setPreserveRatio(true);
		imageView.setFitHeight(MAX_HEIGHT);
		double width = imageView.getLayoutBounds().getWidth();
		content.getChildren().add(imageView);

		ShapeMachine machine = new ShapeMachine(width, MAX_HEIGHT, MAX_HEIGHT / 8, MAX_HEIGHT / 16);
		for (int i = 0; i < 200; i++) {
			content.getChildren().add(machine.randomShape());
		}
		content.setMaxSize(width, MAX_HEIGHT);
		content.setClip(new Rectangle(width, MAX_HEIGHT));

		stack.getChildren().add(content);
		stack.setStyle("-fx-background-color: midnightblue");

		return stack;
	}

	private void letterbox(final Scene scene, final Pane contentPane) {
		final double initWidth = scene.getWidth();
		final double initHeight = scene.getHeight();
		final double ratio = initWidth / initHeight;

		SceneSizeChangeListener sizeListener = new SceneSizeChangeListener(scene, ratio, initHeight, initWidth,
				contentPane);
		scene.widthProperty().addListener(sizeListener);
		scene.heightProperty().addListener(sizeListener);
	}

	private static class SceneSizeChangeListener implements ChangeListener<Number> {
		private final Scene scene;
		private final double ratio;
		private final double initHeight;
		private final double initWidth;
		private final Pane contentPane;

		public SceneSizeChangeListener(Scene scene, double ratio, double initHeight, double initWidth,
				Pane contentPane) {
			this.scene = scene;
			this.ratio = ratio;
			this.initHeight = initHeight;
			this.initWidth = initWidth;
			this.contentPane = contentPane;
		}

		@Override
		public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
			final double newWidth = scene.getWidth();
			final double newHeight = scene.getHeight();

			double scaleFactor = newWidth / newHeight > ratio ? newHeight / initHeight : newWidth / initWidth;

			if (scaleFactor >= 1) {
				Scale scale = new Scale(scaleFactor, scaleFactor);
				scale.setPivotX(0);
				scale.setPivotY(0);
				scene.getRoot().getTransforms().setAll(scale);

				contentPane.setPrefWidth(newWidth / scaleFactor);
				contentPane.setPrefHeight(newHeight / scaleFactor);
			} else {
				contentPane.setPrefWidth(Math.max(initWidth, newWidth));
				contentPane.setPrefHeight(Math.max(initHeight, newHeight));
			}
		}
	}
}

class ShapeMachine {

	private static final Random random = new Random();
	private final double canvasWidth, canvasHeight, maxShapeSize, minShapeSize;

	ShapeMachine(double canvasWidth, double canvasHeight, double maxShapeSize, double minShapeSize) {
		this.canvasWidth = canvasWidth;
		this.canvasHeight = canvasHeight;
		this.maxShapeSize = maxShapeSize;
		this.minShapeSize = minShapeSize;
	}

	private Color randomColor() {
		return Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256),
				0.1 + random.nextDouble() * 0.5);
	}

	enum Shapes {
		Circle, Rectangle, Line
	}

	public Shape randomShape() {
		Shape shape = null;

		switch (Shapes.values()[random.nextInt(Shapes.values().length)]) {
		case Circle:
			shape = randomCircle();
			break;
		case Rectangle:
			shape = randomRectangle();
			break;
		case Line:
			shape = randomLine();
			break;
		default:
			System.out.println("Unknown Shape");
			System.exit(1);
		}

		Color fill = randomColor();
		shape.setFill(fill);
		shape.setStroke(deriveStroke(fill));
		shape.setStrokeWidth(deriveStrokeWidth(shape));
		shape.setStrokeLineCap(StrokeLineCap.ROUND);
		shape.relocate(randomShapeX(), randomShapeY());

		return shape;
	}

	private double deriveStrokeWidth(Shape shape) {
		return Math.max(shape.getLayoutBounds().getWidth() / 10, shape.getLayoutBounds().getHeight() / 10);
	}

	private Color deriveStroke(Color fill) {
		return fill.desaturate();
	}

	private double randomShapeSize() {
		double range = maxShapeSize - minShapeSize;
		return random.nextDouble() * range + minShapeSize;
	}

	private double randomShapeX() {
		return random.nextDouble() * (canvasWidth + maxShapeSize) - maxShapeSize / 2;
	}

	private double randomShapeY() {
		return random.nextDouble() * (canvasHeight + maxShapeSize) - maxShapeSize / 2;
	}

	private Shape randomLine() {
		int xZero = random.nextBoolean() ? 1 : 0;
		int yZero = random.nextBoolean() || xZero == 0 ? 1 : 0;

		int xSign = random.nextBoolean() ? 1 : -1;
		int ySign = random.nextBoolean() ? 1 : -1;

		return new Line(0, 0, xZero * xSign * randomShapeSize(), yZero * ySign * randomShapeSize());
	}

	private Shape randomRectangle() {
		return new Rectangle(0, 0, randomShapeSize(), randomShapeSize());
	}

	private Shape randomCircle() {
		double radius = randomShapeSize() / 2;
		return new Circle(radius, radius, radius);
	}

}
