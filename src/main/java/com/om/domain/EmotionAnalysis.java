package com.om.domain;

import java.util.Iterator;
import java.util.List;

public class EmotionAnalysis {

	public WriterFactor writerFactor;
	public Double authorActiveDegree;
	public Double getAuthorActiveDegree() {
		return authorActiveDegree;
	}

	public void setAuthorActiveDegree(Double authorActiveDegree) {
		this.authorActiveDegree = authorActiveDegree;
	}

	public List<Comment> getcList() {
		return cList;
	}

	public void setcList(List<Comment> cList) {
		this.cList = cList;
	}

	public Double emotionAvg;
	public List<Comment> cList;

	public EmotionAnalysis(WriterFactor writerFactor, List<Comment> cList) {
		super();
		this.writerFactor = writerFactor;
		this.cList = cList;
		Double sum = 0.0;
		for (Iterator<Comment> iterator = cList.iterator(); iterator.hasNext();) {
			Comment comment = (Comment) iterator.next();
			sum += comment.getEmotionValue();
		}
		this.emotionAvg = sum / cList.size();
		this.authorActiveDegree = writerFactor.getAuthorActiveDegree();
		System.out.println(this.writerFactor.getAuthorActiveDegree());
		System.out.println(this.emotionAvg);
	}

	public WriterFactor getWriterFactor() {
		return writerFactor;
	}

	public void setWriterFactor(WriterFactor writerFactor) {
		this.writerFactor = writerFactor;
	}

	public Double getEmotionAvg() {
		return emotionAvg;
	}

	public void setEmotionAvg(Double emotionAvg) {
		this.emotionAvg = emotionAvg;
	}
}
