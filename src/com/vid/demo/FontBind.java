package com.vid.demo;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class FontBind extends Application {

	private DoubleProperty fontSize = new SimpleDoubleProperty(10);
	private IntegerProperty blues = new SimpleIntegerProperty(50);

	@Override
	public void start(Stage primaryStage) {
		Button btn = new Button("click me, I change color");
		btn.setOnAction((evt) -> {
			blues.set(blues.get() + 20);
		});// max?
		Label lbl = new Label("I'm a label");
		TextArea ta = new TextArea("Lots of text can be typed\nand even number 1234567890");
		HBox hbox = new HBox(new Label("I never change"));
		VBox child = new VBox(btn, lbl, ta);
		VBox root = new VBox(child, hbox);
		Scene scene = new Scene(root, 300, 250);

		fontSize.bind(scene.widthProperty().add(scene.heightProperty()).divide(50));
		child.styleProperty().bind(Bindings.concat("-fx-font-size: ", fontSize.asString(), ";",
				"-fx-base: rgb(100,100,", blues.asString(), ");"));

		primaryStage.setTitle("Hello World!");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
