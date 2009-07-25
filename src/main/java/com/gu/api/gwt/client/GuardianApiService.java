package com.gu.api.gwt.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.gu.api.parser.client.Content;
import com.gu.api.parser.client.Search;
import com.gu.api.parser.client.Tags;



@RemoteServiceRelativePath("api")
public interface GuardianApiService extends RemoteService {
	Content getContent(Integer id) throws GuApiException;
	Search search(SearchParameters parameters) throws GuApiException;
	Tags tags(TagsParameters parameters) throws GuApiException;
}
