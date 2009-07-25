package com.gu.api.gwt.server;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiConnection {
	
	private final String url;
	private Integer responseCode;

	public ApiConnection(String url) {
		this.url = url;
	}
	
	public InputStream connect() throws IOException {
		
		if (responseCode != null) {
			throw new IllegalStateException("This connection has already been used");
		}
		
		URL apiCall = new URL(url);
		HttpURLConnection connection = (HttpURLConnection) apiCall.openConnection();
		
		InputStream xmlStream = connection.getInputStream();
		
		responseCode = connection.getResponseCode();
		
		return xmlStream;
	}
	
	public Integer getResponseCode() {
		return responseCode;
	}

}
