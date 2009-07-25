package com.gu.api.gwt.client;

import java.util.Collection;
import java.util.Date;

import com.gu.api.parser.client.Tag;

public class SearchParametersBuilder {
	
	private SearchParameters params = new SearchParameters();
	
	public SearchParametersBuilder q(String q) {
		params.setQ(q);
		return this;
	}
	
	public SearchParametersBuilder before(String yyyymmdd) {
		params.setBefore(toDate(yyyymmdd));
		return this;
	}
	
	public SearchParametersBuilder after(String yyyymmdd) {
		params.setAfter(toDate(yyyymmdd));
		return this;
	}
	
	public SearchParametersBuilder contentType(String contentType) {
		params.setContentType(contentType);
		return this;
	}
	
	public SearchParametersBuilder count(Integer count) {
		params.setCount(count);
		return this;
	}
	
	public SearchParametersBuilder startIndex(Integer startIndex) {
		params.setStartIndex(startIndex);
		return this;
	}
	
	public SearchParametersBuilder filters(String... filters) {
		for (String filter : filters) {
			params.getFilters().add(filter);
		}
		return this;
	}
	
	public SearchParametersBuilder filters(Tag... filters) {
		for (Tag tag : filters) {
			params.getFilters().add(tag.getFilter());
		}
		return this;
	}
	
	public SearchParametersBuilder filters(Collection<? extends Tag> filters) {
		for (Tag tag : filters) {
			params.getFilters().add(tag.getFilter());
		}
		return this;
	}

	@SuppressWarnings("deprecation")
	private Date toDate(String yyyymmdd) {
		
		//yes I know - but this needs to be converted to javascript and date formats do not appear to work both client side
		//and server side
		
		int year = Integer.parseInt(yyyymmdd.substring(0,4));
		int month = Integer.parseInt(yyyymmdd.substring(4,6)) - 1;
		int day = Integer.parseInt(yyyymmdd.substring(6,8));
		return new Date(year - 1900, month, day);
	}
	
	public SearchParametersBuilder orderByDate(boolean orderByDate) {
		params.setOrderByDate(orderByDate);
		return this;
	}
	
	public SearchParameters toSearchParameters() {
		return params;
	}

	public SearchParametersBuilder includeXml() {
		params.setIncludeXml(true);	
		return this;
	}
}

