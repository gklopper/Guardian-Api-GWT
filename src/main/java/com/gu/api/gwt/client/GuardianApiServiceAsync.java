package com.gu.api.gwt.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gu.api.parser.client.Content;
import com.gu.api.parser.client.Search;
import com.gu.api.parser.client.Tags;



/**
 * The async counterpart of <code>GuardianApiService</code>.
 */
public interface GuardianApiServiceAsync {
	void getContent(Integer id, AsyncCallback<Content> callback);
	void search(SearchParameters parameters, AsyncCallback<Search> callback);
	void tags(TagsParameters parameters, AsyncCallback<Tags> callback);
}
