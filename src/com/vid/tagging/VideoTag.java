package com.vid.tagging;

import java.util.LinkedList;
import java.util.List;

public class VideoTag {

	// SceneId
	// StartTime
	// EndTime
	// TagInfo
	// List of keywords

	private int segmentId;

	private double startTime;

	private double endTime;

	private String tagDescription;

	private List<KeyWord> keyWordList; // List of keyword ids

	public VideoTag() {
		keyWordList = new LinkedList<>();
	}

	public int getSegmentId() {
		return segmentId;
	}

	public void setSegmentId(int segmentId) {
		this.segmentId = segmentId;
	}

	public double getStartTime() {
		return startTime;
	}

	public void setStartTime(double startTime) {
		this.startTime = startTime;
	}

	public double getEndTime() {
		return endTime;
	}

	public void setEndTime(double endTime) {
		this.endTime = endTime;
	}

	public String getTagDescription() {
		return tagDescription;
	}

	public void setTagDescription(String tagDescription) {
		this.tagDescription = tagDescription;
	}

	public List<KeyWord> getKeyWordList() {
		return keyWordList;
	}

	public void setKeyWordList(List<KeyWord> keyWordList) {
		this.keyWordList = keyWordList;
	}

	public void addKeyWord(KeyWord keyWord) {
		keyWordList.add(keyWord);
	}

}
