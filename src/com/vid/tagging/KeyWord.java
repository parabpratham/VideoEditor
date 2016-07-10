package com.vid.tagging;

public class KeyWord implements Comparable<KeyWord> {

	private String word;

	private int id;

	public KeyWord() {
	}

	public KeyWord(String word) {
		this.word = word;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object obj) {

		if (obj == null)
			return false;

		KeyWord keyWord = (KeyWord) obj;
		return word.equalsIgnoreCase(keyWord.getWord());
	}

	@Override
	public int compareTo(KeyWord o) {
		return getWord().compareTo(o.getWord());
	}

	public String toXml() {
		String keyWords = "<keyword id=\"" + getId() + "\"> \n";
		keyWords += "<parameters set=\"" + 1 + "\"> \n";
		keyWords += "<Word>" + getWord() + "</Word> \n";
		keyWords += "</parameters> \n";
		keyWords += "</keyword> \n";
		return keyWords;
	}

}
