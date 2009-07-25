package com.gu.api.gwt.client;


public class TagsParametersBuilder {
	
	private TagsParameters tagParameters = new TagsParameters();
	
	public TagsParametersBuilder q(String query) {
		tagParameters.setQ(query);
		return this;
	}
	
	public TagsParametersBuilder count(Integer count) {
		tagParameters.setCount(count);
		return this;
	}
	
	public TagsParametersBuilder startIndex(Integer startIndex) {
		tagParameters.setStartIndex(startIndex);
		return this;
	}
	
	public TagsParametersBuilder includeXml(boolean includeXml) {
		tagParameters.setIncludeXml(includeXml);
		return this;
	}
	
	public TagsParameters toTagsParameters() {
		return tagParameters;
	}

}
