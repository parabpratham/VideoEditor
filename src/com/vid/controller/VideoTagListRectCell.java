package com.vid.controller;

import com.vid.commons.Helper;
import com.vid.tagging.VideoTag;

import javafx.collections.ObservableList;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

public class VideoTagListRectCell<T> extends ListCell<T> {

	HBox hbox;
	Pane pane = new Pane();
	CheckBox checkBox;
	Label textLabel;

	int index;

	ListView<VideoTag> videoTagListView;
	ObservableList<VideoTag> videoTagObservableList;

	public VideoTagListRectCell(ListView<VideoTag> videoTagListView, ObservableList<VideoTag> videoTagObservableList) {
		super();
		this.videoTagObservableList = videoTagObservableList;
		this.videoTagListView = videoTagListView;
	}

	@Override
	public void updateItem(T item, boolean empty) {
		super.updateItem(item, empty);
		if (empty) {
			setText(null);
			setGraphic(null);
		} else if (item instanceof VideoTag) {
			VideoTag comp = (VideoTag) item;
			hbox = new HBox();
			HBox.setHgrow(pane, Priority.ALWAYS);
			hbox.setMaxHeight(15);
			hbox.setSpacing(5);
			checkBox = new CheckBox();
			textLabel = new Label("Tag" + comp.getSegmentId() + "--" + Helper.setTotalTime((long) comp.getStartTime()));
			hbox.getChildren().addAll(checkBox, textLabel);
			setGraphic(hbox);
		} else {
			setText(item == null ? "null" : item.toString());
			setGraphic(null);
		}
	}

}
