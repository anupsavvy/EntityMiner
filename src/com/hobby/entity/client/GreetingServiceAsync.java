package com.hobby.entity.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface GreetingServiceAsync {
	void getLinkedArticle(String article, AsyncCallback<List<String>> callback)
	throws IllegalArgumentException;
	
}
