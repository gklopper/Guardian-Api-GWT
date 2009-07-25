package com.gu.api.gwt.client;

import java.text.SimpleDateFormat;

import junit.framework.Assert;

import org.junit.Test;

public class SearchParametersBuilderTest {
	
	@Test
	public void shouldParseDateCorrectly() {
		SearchParameters searchParameters = new SearchParametersBuilder().before("20090621").after("20080130").toSearchParameters();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		Assert.assertEquals("20090621", format.format(searchParameters.getBefore()));
		Assert.assertEquals("20080130", format.format(searchParameters.getAfter()));
	}

}
