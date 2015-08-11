package com.clusterpoint.api.request;

import java.util.Map;

/**
* The CPSListLastRequest class is a wrapper for the CPSListLastRetrieveFirstRequest class for list-last requests
* @see com.clusterpoint.api.response.CPSListLastRetrieveFirstResponse
*/
public class CPSListLastRequest extends CPSListLastRetrieveFirstRequest {
	/**
	* Constructs an instance of the CPSListLastRequest class.
	* Default parameters: offset = 0, docs = 10
	* @throws Exception 
	* 
	*/
	public CPSListLastRequest() throws Exception {
		super("list-last", 0, 10);
	}
	
	/**
	* Constructs an instance of the CPSListLastRequest class.
	* @param offset offset of result set
	* @param docs max number of docs to return 
	* @throws Exception 
	* 
	*/
	
	public CPSListLastRequest(int offset, int docs) throws Exception {
		super("list-last", offset, docs);
	}
	
	/**
	* Constructs an instance of the CPSListLastRequest class.
	* @param offset offset of result set
	* @param docs max number of docs to return
	* @param list map where key is xpath and value contains listing options (yes, no, snippet or highlight) 
	* @throws Exception 
	* 
	*/
	public CPSListLastRequest(int offset, int docs, Map<String, String> list) throws Exception {
		super("list-last", offset, docs, list);
	}
}
