package com.vid.demo;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class JavaFXShapes extends Application {

	@Override
	public void start(Stage stage) {
		VBox box = new VBox();
		final Scene scene = new Scene(box, 300, 250);
		scene.setFill(null);
		Line line = new Line(156,86,452,86);
		line.setStrokeWidth(5);
		line.setStroke(Color.RED);
		line.setStrokeLineCap(StrokeLineCap.SQUARE);
		line.setStrokeDashOffset(10);
		Text text = new Text(110, 110, "Hi");

		box.getChildren().addAll(line,text);
		

		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}