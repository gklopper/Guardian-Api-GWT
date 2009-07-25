package com.gu.api.gwt.client;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("serial")
public class SearchParameters implements Serializable {
	
	private String q;
	private Date after;
	private Date before;
	private String contentType;
	private Integer count;
	private Integer startIndex;
	private Set<String> filters = new HashSet<String>();
	private boolean orderByDate = true;
	
	private boolean includeXml = false;
	
	public String getQ() {
		return q;
	}
	public void setQ(String q) {
		this.q = q;
	}
	public Date getAfter() {
		return after;
	}
	public void setAfter(Date after) {
		this.after = after;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
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
	public Set<String> getFilters() {
		return filters;
	}
	public void setFilters(Set<String> filters) {
		this.filters = filters;
	}
	public Date getBefore() {
		return before;
	}
	public void setBefore(Date before) {
		this.before = before;
	}
	public void setOrderByDate(boolean orderByDate) {
		this.orderByDate = orderByDate;
	}
	public boolean isOrderByDate() {
		return orderByDate;
	}
	
	/**
	 * Use sparingly as this will double your network overheads if set to true
	 * @param includeXml
	 */
	public void setIncludeXml(boolean includeXml) {
		this.includeXml = includeXml;
	}
	
	public boolean isIncludeXml() {
		return includeXml;
	}
}