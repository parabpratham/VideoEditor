package com.vid.application;

import java.io.IOException;

import org.controlsfx.control.InfoOverlay;

import com.vid.commons.Helper;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class TextAreaTut extends Application {
	@Override
	public void start(Stage primaryStage) throws IOException {

		VBox vBox = new VBox();

		HBox box = new HBox();
		TextArea area = new TextArea();
		TextArea area1 = new TextArea();
		ToggleButton butt = new ToggleButton();
		box.getChildren().addAll(area, area1, butt);

		area1.textProperty().bind(area.textProperty());

		// css += "-fx-font-weight: bold;";
		butt.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				Font font = new Font("Arial", 24);
				area.setFont(font);
				Region region = (Region) area.lookup(".content");
				region.setStyle("-fx-background-color: " + toRgbStringa(Color.RED, 0.1) + ";");

				region = (Region) area1.lookup(".content");
				region.setStyle("-fx-background-color: transparent;");
				Text text = (Text) area.lookup(".text");
				text.setUnderline(butt.isSelected());
				text.setStrikethrough(butt.isSelected());
				text.setTextAlignment(TextAlignment.CENTER);
				text.setFill(Color.BROWN);
				area.setWrapText(true);
				System.out.println(text.getText());
			}

		});

		ImageView iew = new ImageView();
		InfoOverlay infoOverlay = new InfoOverlay(iew, "Enter text");
		iew.setFitWidth(150);
		iew.setFitHeight(50);
		infoOverlay.setPrefWidth(150);
		infoOverlay.setPrefHeight(50);
		ToggleButton butt2 = new ToggleButton();
		iew.setStyle("-fx-background-color: " + toRgbStringa(Color.RED, 0.5) + ";");
		HBox hbox2 = new HBox();

		WritableImage writableImage = Helper.createBorderImage(iew, Color.RED);

		iew.setImage(writableImage);

		Label contentText = new Label("getDisplayString()");
		contentText.setWrapText(true);
		contentText.setDisable(true);
		contentText.setMinWidth(120);
		contentText.setMinHeight(20);
//		BackgroundFill fill = new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY);
//		contentText.setBackground(new Background(fill));
		contentText.setStyle("-fx-background-color: " + toRgbStringa(Color.RED, 0.5) + ";");
		hbox2.getChildren().addAll(iew, contentText, butt2);

		vBox.getChildren().addAll(box, hbox2);

		AnchorPane root = new AnchorPane();
		root.getChildren().addAll(vBox);
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private WritableImage createBorderImage(ImageView iew) {
		WritableImage writableImage = new WritableImage(50, 50);
		PixelWriter writer = writableImage.getPixelWriter();

		for (int i = 0; i < iew.getFitHeight(); i++) {
			writer.setColor(0, i, Color.RED);
			writer.setColor(i, (int) iew.getFitWidth() - 1, Color.RED);
		}

		for (int j = 0; j < iew.getFitWidth(); j++) {
			writer.setColor(j, 0, Color.RED);
			writer.setColor((int) iew.getFitHeight() - 1, j, Color.RED);
		}
		return writableImage;
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

	public static void main(String[] args) {
		launch(args);
	}
}
