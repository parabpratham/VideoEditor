package com.vid.demo;

import org.controlsfx.control.InfoOverlay;
import org.controlsfx.control.PopOver;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;

public class ListViewSample extends Application {

	ListView<String> list = new ListView<String>();
	ObservableList<String> data = FXCollections.observableArrayList("chocolate", "salmon", "gold", "coral",
			"darkorchid", "darkgoldenrod", "lightsalmon", "black", "rosybrown", "blue", "blueviolet", "brown");
	final Label label = new Label();

	@Override
	public void start(Stage stage) {
		VBox box = new VBox();
		VBox.setVgrow(list, Priority.ALWAYS);
		Scene scene = new Scene(box, 500, 500);
		stage.setScene(scene);
		stage.setTitle("ListViewSample");
		Image image = new Image("file:C:\\1.jpg");
		ImageView iew = new ImageView(image);
		InfoOverlay infoOverlay = new InfoOverlay(iew,
				"infoOverlay.setContent();infoOverlay.setContent();infoOverlay.setContent();infoOverlay.setContent();infoOverlay.setContent();infoOverlay.setContent();infoOverlay.setContent();");
		iew.setFitHeight(250);
		iew.setFitWidth(200);
		infoOverlay.setStyle("-fx-background-color: rgba(179,102,128,0.6203703703703703);");

		// infoOverlay.setBackground(
		// new Background(new BackgroundFill(Color.CORNFLOWERBLUE,
		// CornerRadii.EMPTY, Insets.EMPTY)));
		infoOverlay.setShowOnHover(false);

		PopOver popOver = createPopOver();
		iew.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent evt) {
				popOver.show(iew);
				if (popOver != null && !popOver.isDetached()) {
					if (popOver != null && !popOver.isDetached()) {
						popOver.hide();
					}
					if (evt.getClickCount() == 2) {
						if (popOver != null && popOver.isShowing()) {
							popOver.hide(Duration.ZERO);
						}
						popOver.show(iew);
					}

				}
			}
		});

		box.getChildren().addAll(iew);
		label.setLayoutX(10);
		label.setLayoutY(115);
		label.setFont(Font.font("Verdana", 20));

		list.setItems(data);

		list.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
			@Override
			public ListCell<String> call(ListView<String> list) {
				return new ColorRectCell();
			}
		});

		list.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> ov, String old_val, String new_val) {
				label.setText(new_val);
				label.setTextFill(Color.web(new_val));
			}
		});
		stage.show();
	}

	private PopOver createPopOver() {

		PopOver popOver = new PopOver();
		popOver.setDetachable(true);
		popOver.setDetached(true);

		return popOver;

	}

	private TitledPane createTitledPane(String title) {
		VBox box = new VBox(5);
		box.getChildren().add(new Button("Test"));
		box.getChildren().add(new Slider());

		ListView<String> view = new ListView<>();
		view.setPrefHeight(100);
		box.getChildren().add(view);
		final TitledPane pane = new TitledPane(title, box);
		pane.setTextAlignment(TextAlignment.LEFT);

		Pane connectivityArrow = (Pane) pane.lookup(".arrow");
		if (connectivityArrow != null) {
			connectivityArrow.translateXProperty()
					.bind(pane.widthProperty().subtract(connectivityArrow.widthProperty().multiply(2)));
		}

		return pane;
	}

	static class ColorRectCell extends ListCell<String> {
		@Override
		public void updateItem(String item, boolean empty) {
			super.updateItem(item, empty);
			Rectangle rect = new Rectangle(100, 20);
			if (item != null) {
				rect.setFill(Color.web(item));
				setGraphic(rect);
			}
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}