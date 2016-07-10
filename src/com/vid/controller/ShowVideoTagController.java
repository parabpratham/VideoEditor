package com.vid.controller;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import com.vid.commons.Helper;
import com.vid.tagging.KeyWord;
import com.vid.tagging.VideoTag;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class ShowVideoTagController implements Initializable {

	@FXML
	TextField keywordtext;
	@FXML
	ComboBox<VideoTag> videotags;
	@FXML
	TextField tag_description;
	@FXML
	TextField tag_starttime;
	@FXML
	TextField tag_endtime;
	@FXML
	TextField tag_keywords;

	KeyWord keyWord;

	private Map<String, List<VideoTag>> keyTagMap;
	private Map<String, VideoTag> videoTagMap;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setKeyTagMap(VideoEditorController.getKeyTagMap());
		videoTagMap = new HashMap<>();
	}

	public void initializeVideoTags() {
		List<VideoTag> list = getKeyTagMap().get(getKeyWord().getWord());
		System.out.println(list);
		ObservableList<VideoTag> videoTags = FXCollections.observableList(list);
		videotags.setItems(videoTags);
		videotags.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
				setVideoTagDetails(videotags.getSelectionModel().getSelectedItem());
			}
		});
		setVideoTagDetails(list.get(0));

	}

	private void setVideoTagDetails(VideoTag vt) {
		getTag_description().setText(vt.getTagDescription());
		getTag_starttime().setText(Helper.setTotalTime((long) vt.getStartTime()));
		getTag_endtime().setText(Helper.setTotalTime((long) vt.getEndTime()));
		getTag_keywords().setText(vt.getWords());
	}

	public TextField getTag_description() {
		return tag_description;
	}

	public void setTag_description(TextField tag_description) {
		this.tag_description = tag_description;
	}

	public TextField getTag_starttime() {
		return tag_starttime;
	}

	public void setTag_starttime(TextField tag_starttime) {
		this.tag_starttime = tag_starttime;
	}

	public TextField getTag_endtime() {
		return tag_endtime;
	}

	public void setTag_endtime(TextField tag_endtime) {
		this.tag_endtime = tag_endtime;
	}

	public TextField getTag_keywords() {
		return tag_keywords;
	}

	public void setTag_keywords(TextField tag_keywords) {
		this.tag_keywords = tag_keywords;
	}

	public Map<String, List<VideoTag>> getKeyTagMap() {
		return keyTagMap;
	}

	public void setKeyTagMap(Map<String, List<VideoTag>> keyTagMap) {
		this.keyTagMap = keyTagMap;
	}

	public void setVideotags(ComboBox<VideoTag> videotags) {
		this.videotags = videotags;
	}

	public KeyWord getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(KeyWord keyWord) {
		this.keyWord = keyWord;
	}

	public ComboBox<VideoTag> getVideotags() {
		return videotags;
	}

	public TextField getKeywordtext() {
		return keywordtext;
	}

	public void setKeywordtext(TextField keywordtext) {
		this.keywordtext = keywordtext;
	}

	public Map<String, VideoTag> getVideoTagMap() {
		return videoTagMap;
	}

	public void setVideoTagMap(Map<String, VideoTag> videoTagMap) {
		this.videoTagMap = videoTagMap;
	}

}
