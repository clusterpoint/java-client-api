package com.clusterpoint.api.request;

import com.clusterpoint.api.CPSRequest;

/**
* The CPSListWordsRequest class is a wrapper for the CPSRequest class for the list-words command
* @see com.clusterpoint.api.response.CPSListWordsResponse
*/
public class CPSListWordsRequest extends CPSRequest {
	/**
	* Constructs an instance of CPSListWordsRequest
	*/
	public CPSListWordsRequest() throws Exception {
		super("list-words");
	}
	
	/**
	* Constructs an instance of CPSListWordsRequest
	*
	* @param query
	*/
	public CPSListWordsRequest(String query) throws Exception {
		super("list-words");
		this.setQuery(query);
	}
	
	/**
	 * Sets query for list-words command
	 * @param query
	 * @throws Exception
	 */
	public void setQuery(String query) throws Exception
	{
		this.setParam("query", query);
	}
}
