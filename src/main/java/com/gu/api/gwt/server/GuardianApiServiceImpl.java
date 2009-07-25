package com.gu.api.gwt.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.gu.api.gwt.client.GuApiException;
import com.gu.api.gwt.client.GuardianApiService;
import com.gu.api.gwt.client.SearchParameters;
import com.gu.api.gwt.client.TagsParameters;


import com.gu.api.parser.client.Content;
import com.gu.api.parser.client.Search;
import com.gu.api.parser.client.Tags;
import com.gu.api.parser.server.ApiParser;


/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GuardianApiServiceImpl extends RemoteServiceServlet implements GuardianApiService {
	
	private static final Logger LOGGER = Logger.getLogger(GuardianApiServiceImpl.class.getName());
	
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMdd");
	private String apiKey;
	
	@Override
	public void init() throws ServletException {
		super.init();
			apiKey = getServletConfig().getInitParameter("apiKey");
		
			if (apiKey == null) {
				throw new ServletException("Init Parameter 'apiKey' must be set for " + getClass().getSimpleName());
			}
	}
	
	public Content getContent(Integer contentId) throws GuApiException {
		Integer responseCode = null;
		
		StringRecordingInputStream stringRecorder = null; 
		
		try {
			String url = "http://api.guardianapis.com/content/item/" + contentId + "?api_key=" + apiKey;
			
			ApiConnection connection = new ApiConnection(url);
			InputStream xmlStream =connection.connect();
			stringRecorder = new StringRecordingInputStream(xmlStream);
			
			if (connection.getResponseCode() == 200) {
				return ApiParser.parseContent(stringRecorder);
			}
			
			//api currently has limited rate so retry
			//appengine will not let this happen indefinately
			if (connection.getResponseCode() == 403) {
				LOGGER.severe("Retrying content");
				return getContent(contentId);
			}
			
		} catch (IOException e) {
			propogate(e,stringRecorder);
		}	catch (SAXException e) {
			propogate(e,stringRecorder);
		} catch (ParserConfigurationException e) {
			propogate(e,stringRecorder);
		} catch (ParseException e) {
			propogate(e,stringRecorder);
		}
		throw new GuApiException("Unexpected response from api call: " + responseCode, responseCode);
	}

	public Search search(SearchParameters parameters) throws GuApiException {
		Integer responseCode = null;
		StringRecordingInputStream xmlStream = null;
		try {
			String url = "http://api.guardianapis.com/content/search" + searchParamString(parameters);
			LOGGER.info(url);
			
			ApiConnection connection = new ApiConnection(url);
			xmlStream = new StringRecordingInputStream(connection.connect());
			
			
			if (connection.getResponseCode() == 200) {
				Search result = ApiParser.parseSearch(xmlStream);
				
				if (parameters.isIncludeXml()) {
					String xml = xmlStream.toString();
					if (xml != null) {
						result.setXml(xml);
					}
				}		
				result.setApiUrl(url.replace(apiKey, "YOUR-API-KEY"));
				return result;
			}
			
			//api currently has limited rate so retry
			//appengine will not let this happen indefinately
			if (connection.getResponseCode() == 403) {
				LOGGER.warning("Retrying search");
				return search(parameters);
			}
			
		} catch (IOException e) {
			propogate(e,xmlStream);
		}	catch (SAXException e) {
			propogate(e,xmlStream);
		} catch (ParserConfigurationException e) {
			propogate(e,xmlStream);
		} catch (ParseException e) {
			propogate(e,xmlStream);
		}
		throw new GuApiException("Unexpected response from api call: " + responseCode, responseCode);
	}
	
	public Tags tags(TagsParameters parameters) throws GuApiException {
		Integer responseCode = null;
		
		StringRecordingInputStream stringRecorder = null;
		
		try {
			String url = "http://api.guardianapis.com/content/tags" + tagsParamString(parameters);
			LOGGER.info(url);
			
			ApiConnection connection = new ApiConnection(url);
			InputStream xmlStream =connection.connect();
			
			stringRecorder = new StringRecordingInputStream(xmlStream);
			if (connection.getResponseCode() == 200) {
				Tags result = ApiParser.parseTags(stringRecorder);
				
				if (parameters.isIncludeXml()) {
					String xml = stringRecorder.toString();
					if (xml != null) {
						result.setXml(xml);
					}
				}		
				return result;
			}
			
			//api currently has limited rate so retry
			//appengine will not let this happen indefinately
			if (connection.getResponseCode() == 403) {
				LOGGER.warning("Retrying search");
				return tags(parameters);
			}
			
		} catch (IOException e) {
			propogate(e,stringRecorder);
		}	catch (SAXException e) {
			propogate(e,stringRecorder);
		} catch (ParserConfigurationException e) {
			propogate(e,stringRecorder);
		} catch (ParseException e) {
			propogate(e,stringRecorder);
		}
		throw new GuApiException("Unexpected response from api call: " + responseCode, responseCode);
	}

	private void propogate(Exception e, StringRecordingInputStream stringRecorder) throws GuApiException  {
		GuApiException guApiException = new GuApiException("Error while parsing response from api call", e);
		
		if (stringRecorder != null) {
			String xml = stringRecorder.toString();
			guApiException.setParsedXml(xml);
			LOGGER.severe("XML from exception : " + xml);
		}
		
		throw guApiException;
	}
	
	private String searchParamString(SearchParameters params) throws UnsupportedEncodingException {
		StringBuilder sb = new StringBuilder();
		
		withSeparator("api_key", apiKey, sb);
		if (params.getQ() != null) {
			withSeparator("q", URLEncoder.encode(params.getQ(), "UTF-8"), sb);
		}
		withSeparator("before", params.getBefore(), sb);
		withSeparator("after", params.getAfter(), sb);
		withSeparator("content-type", params.getContentType(), sb);
		withSeparator("count", params.getCount(), sb);
		withSeparator("start-index", params.getStartIndex(), sb);
		
		if (params.isOrderByDate()) {
			withSeparator("order-by-date", sb);
		}
		
		for (String filter : params.getFilters()) {
			
			withSeparator("filter", filter, sb);
		}
		
		return sb.toString();
	}
	
	private String tagsParamString(TagsParameters params) throws UnsupportedEncodingException {
		StringBuilder sb = new StringBuilder();
		
		withSeparator("api_key", apiKey, sb);
		if (params.getQ() != null) {
			withSeparator("q", URLEncoder.encode(params.getQ(), "UTF-8"), sb);
		}
		withSeparator("count", params.getCount(), sb);
		withSeparator("start-index", params.getStartIndex(), sb);
		
		return sb.toString();
	}

	private void withSeparator(String key, Date date, StringBuilder sb) {
		
		if (date == null) {
			return;
		}
		
		withSeparator(key, DATE_FORMAT.format(date), sb);
	}
		
	private void withSeparator(String key, Integer value, StringBuilder sb) {
		if (value == null) {
			return;
		}
		withSeparator(key, String.valueOf(value), sb);
	}
	
	private void withSeparator(String key, String value, StringBuilder sb) {
		
		if (value == null || value.length() == 0) {
			return;
		}
		
		if (sb.length() == 0) {
			sb.append("?");
		} else {
			sb.append("&");
		}
		
		sb.append(key).append("=").append(value);
	}

	private void withSeparator(String key, StringBuilder sb) {
		
		if (sb.length() == 0) {
			sb.append("?");
		} else {
			sb.append("&");
		}
		
		sb.append(key);
	}
}
