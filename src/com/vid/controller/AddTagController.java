package com.vid.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.vid.tagging.KeyWord;
import com.vid.tagging.VideoTag;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

public class AddTagController implements Initializable {

	public static final String FXMLPath = "fxml/AddVideoTag.fxml";

	@FXML
	private TextArea description_text;
	@FXML
	private TextArea keyword_text;
	@FXML
	private TextArea start_time_text;
	@FXML
	private TextArea end_time_text;
	@FXML
	private Button save_butt;
	@FXML
	private Button cancel_butt;

	private VideoTag tag;

	public AddTagController() {
		tag = new VideoTag();
	}

	public void setTagController(ListView<KeyWord> keywordListView, ListView<VideoTag> videoTagListView,
			ObservableList<VideoTag> videoTagObservableList, ObservableList<KeyWord> keywordObservableList) {
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	public TextArea getDescription_text() {
		return description_text;
	}

	public void setDescription_text(TextArea description_text) {
		this.description_text = description_text;
	}

	public TextArea getKeyword_text() {
		return keyword_text;
	}

	public void setKeyword_text(TextArea keyword_text) {
		this.keyword_text = keyword_text;
	}

	public TextArea getStart_time_text() {
		return start_time_text;
	}

	public void setStart_time_text(TextArea start_time_text) {
		this.start_time_text = start_time_text;
	}

	public TextArea getEnd_time_text() {
		return end_time_text;
	}

	public void setEnd_time_text(TextArea end_time_text) {
		this.end_time_text = end_time_text;
	}

	public Button getSave_butt() {
		return save_butt;
	}

	public void setSave_butt(Button save_butt) {
		this.save_butt = save_butt;
	}

	public Button getCancel_butt() {
		return cancel_butt;
	}

	public void setCancel_butt(Button cancel_butt) {
		this.cancel_butt = cancel_butt;
	}

	public VideoTag getTag() {
		return tag;
	}

	public void setTag(VideoTag tag) {
		this.tag = tag;
	}

	public static String getFXMLPath() {
		return FXMLPath;
	}

}
