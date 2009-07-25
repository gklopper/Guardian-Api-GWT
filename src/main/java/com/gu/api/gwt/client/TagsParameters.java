package com.gu.api.gwt.client;

public class TagsParameters {
	
	private String q;
	private Integer count;
	private Integer startIndex;
	private boolean includeXml = false;
	
	public String getQ() {
		return q;
	}
	public void setQ(String q) {
		this.q = q;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public Integer getStartIndex() {
		return startIndex;
	}
	public void setStartIndex(Integer startIndex) {
		this.startIndex = startIndex;
	}
	public void setIncludeXml(boolean includeXml) {
		this.includeXml = includeXml;
	}
	public boolean isIncludeXml() {
		return includeXml;
	}
}
