package com.vid.controller;

import com.vid.tagging.KeyWord;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

public class TagKeywordListRectCell<T> extends ListCell<T> {

	HBox hbox;
	Pane pane = new Pane();
	CheckBox checkBox;
	Label textLabel;

	int index;

	ListView<KeyWord> keywordListView;
	ObservableList<KeyWord> keywordObservableList;

	public TagKeywordListRectCell(ListView<KeyWord> keywordListView, ObservableList<KeyWord> keywordObservableList) {
		super();
		this.keywordObservableList = keywordObservableList;
		this.keywordListView = keywordListView;
	}

	@Override
	public void updateItem(T item, boolean empty) {
		super.updateItem(item, empty);
		if (empty) {
			setText(null);
			setGraphic(null);
		} else if (item instanceof KeyWord) {
			KeyWord comp = (KeyWord) item;
			hbox = new HBox();
			HBox.setHgrow(pane, Priority.ALWAYS);
			hbox.setMaxHeight(15);
			hbox.setSpacing(5);
			checkBox = new CheckBox();
			textLabel = new Label(comp.getWord());
			hbox.getChildren().addAll(checkBox, textLabel);
			this.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					FXMLLoader loader = new FXMLLoader(
							getClass().getClassLoader().getResource("fxml/tagging/Search_Keyword_popup.fxml"));
					// For showing the property window
					Parent root;
					try {
						root = (Parent) loader.load();
						Scene scene = new Scene(root);
						Stage stage = new Stage();
						stage.setScene(scene);
						stage.setTitle("Show Tags");

						ShowVideoTagController controller = (ShowVideoTagController) loader.getController();
						controller.setKeyWord(comp);
						controller.getKeywordtext().setText(comp.getWord());
						controller.initializeVideoTags();

						stage.show();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			setGraphic(hbox);
		} else {
			setText(item == null ? "null" : item.toString());
			setGraphic(null);
		}
	}

}
