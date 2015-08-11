package com.clusterpoint.api.request;

import com.clusterpoint.api.CPSRequest;

/**
* The CPSSimilarDocumentRequest class is a wrapper for the CPSRequest class for the similar command for documents
* @see com.clusterpoint.api.response.CPSSearchResponse
*/
public class CPSSimilarDocumentRequest extends CPSRequest {
	/**
	* Constructs an instance of the CPSSimilarDocumentRequest class.
	* @param len number of keywords to extract from the source
	* @param quota minimum number of keywords matching in the destination
	* @param offset number of results to skip before returning the following ones
	* @param docs number of documents to retrieve
	*/
	public CPSSimilarDocumentRequest(String query, int len, int quota, int offset, int docs) throws Exception {
		super("similar");
		this.setParam("len", String.valueOf(len));
		this.setParam("quota", String.valueOf(quota));
		this.setParam("offset", String.valueOf(offset));
		this.setParam("docs", String.valueOf(docs));
	}
	
	/**
	* @param id ID of the source document - the one that You want to search similar documents to
	*/
	public void setId(String id) throws Exception
	{
		this.setParam("id", id);
	}
	
	/**
	* @param text A chunk of text that the found documents have to be similar to
	*/
	public void setText(String text) throws Exception
	{
		this.setParam("text", text);
	}
	
	/**
	* Defines query that all found documents have to match against
	* @param query query string
	*/
	public void setQuery(String query) throws Exception
	{
		this.setParam("query", query);
	}
}
