package com.gu.api.gwt.client;


@SuppressWarnings("serial")
public class GuApiException extends Exception {
	
	private Integer responseCode;
	private String parsedXml;
	
	public GuApiException() {
		
	}
	
	public GuApiException(String message, Throwable cause) {
		super(message, cause);
	}

	public GuApiException(String message, Integer responseCode) {
		super(message);
		this.responseCode = responseCode;
	}

	public Integer getResponseCode() {
		return responseCode;
	}

	public void setParsedXml(String parsedXml) {
		this.parsedXml = parsedXml;
	}

	public String getParsedXml() {
		return parsedXml;
	}

}
