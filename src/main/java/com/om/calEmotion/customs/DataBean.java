package com.om.calEmotion.customs;

public class DataBean {
	private String firstWord;
	private int firstPositon;
	private String secondWord;
	private int secondPositon;
	private String relation;
	private double score;
	private boolean state;

	public boolean isState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}

	public DataBean(String firstWord, int firstPositon, String secondWord,
			int secondPostion, String relation, double score, boolean state) {
		this.firstWord = firstWord;
		this.firstPositon = firstPositon;
		this.secondPositon = secondPostion;
		this.secondWord = secondWord;
		this.relation = relation;
		this.score = score;
		this.state = state;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public String getFirstWord() {
		return firstWord;
	}

	public void setFirstWord(String firstWord) {
		this.firstWord = firstWord;
	}

	public int getFirstPositon() {
		return firstPositon;
	}

	public void setFirstPositon(int firstPositon) {
		this.firstPositon = firstPositon;
	}

	public String getSecondWord() {
		return secondWord;
	}

	public void setSecondWord(String secondWord) {
		this.secondWord = secondWord;
	}

	public int getSecondPositon() {
		return secondPositon;
	}

	public void setSecondPositon(int secondPositon) {
		this.secondPositon = secondPositon;
	}

	public String getRelation() {
		return relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}

	@Override
	public String toString() {

		return firstWord + "," + firstPositon + "," + secondWord + ","
				+ secondPositon + "," + state + "," + relation;
	}
}
