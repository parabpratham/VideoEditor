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

	public String toXml() {
		String tag = "<videotag id=\"" + getSegmentId() + "\">\n" + "<starttime>";
		tag += getStartTime() + "</starttime>\n";
		tag += "<endtime> " + getEndTime() + "</endtime>";
		tag += "<parameters set=\"" + 1 + "\"> \n";
		tag += "<TagDescription>" + getTagDescription() + "</TagDescription> \n";
		tag += "<TagKeyWords>" + getKeyWords() + "</TagKeyWords> \n";
		tag += "</parameters> \n";
		tag += "</videotag> \n";
		return tag;
	}

	private String getKeyWords() {
		String keywordString = "";
		for (KeyWord word : getKeyWordList()) {
			keywordString += word.getId() + ",";
		}
		if (keywordString.length() > 0)
			keywordString = keywordString.substring(0, keywordString.length() - 1);
		return keywordString;
	}
	
	public String getWords() {
		String keywordString = "";
		for (KeyWord word : getKeyWordList()) {
			keywordString += word.getWord() + ",";
		}
		if (keywordString.length() > 0)
			keywordString = keywordString.substring(0, keywordString.length() - 1);
		return keywordString;
	}

	@Override
	public String toString() {
		return "Tag" + getSegmentId();
	}

}
