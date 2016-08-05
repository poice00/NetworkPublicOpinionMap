package com.om.next;

import java.util.ArrayList;
import java.util.List;

import com.om.domain.DataSource;


public class Cluster {
	private String clusterName;
	private List<DataPoint> document = new ArrayList<DataPoint>(); // 类簇中的样本点
	public String getClusterName() {
		return clusterName;
	}
	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}
	public List<DataPoint> getDocument() {
		return document;
	}
	public void setDocument(List<DataPoint> document) {
		this.document = document;
	}
}
