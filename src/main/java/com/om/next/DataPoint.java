package com.om.next;

import java.util.Map;

public class DataPoint {
	private String dataPointName; // 样本点名
    private Map<String, Double> weight; // 样本点的维度
    private boolean isKey; //是否是核心对象
    public DataPoint() {
		// TODO Auto-generated constructor stub
	}
	public DataPoint(Map<String, Double> weight, String dataPointName, boolean isKey) {
		super();
		this.dataPointName = dataPointName;
		this.weight = weight;
		this.isKey = isKey;
	}
	public String getDataPointName() {
		return dataPointName;
	}
	public void setDataPointName(String dataPointName) {
		this.dataPointName = dataPointName;
	}
	public boolean isKey() {
		return isKey;
	}
	public void setKey(boolean isKey) {
		this.isKey = isKey;
	}
	public Map<String, Double> getWeight() {
		return weight;
	}
	public void setWeight(Map<String, Double> weight) {
		this.weight = weight;
	}
}
