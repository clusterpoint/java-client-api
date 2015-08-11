package com.clusterpoint.api.request;

import java.util.Map;

/**
* The CPS_ListFirstRequest class is a wrapper for the CPSListLastRetrieveFirstRequest class for list-first requests
* @see com.clusterpoint.api.response.CPSListLastRetrieveFirstResponse
*/
public class CPSListFirstRequest extends CPSListLastRetrieveFirstRequest {
	/**
	* Constructs an instance of the CPSListFirstRequest class with default parameters
	* Default parameters: offset = 0, docs = 10
	* @throws Exception 
	* 
	*/
	public CPSListFirstRequest() throws Exception {
		super("list-first", 0, 10);
	}
	
	/**
	* Constructs an instance of the CPSListFirstRequest class.
	* @param offset offset of result set
	* @param docs max number of docs to return 
	* @throws Exception 
	* 
	*/
	public CPSListFirstRequest(int offset, int docs) throws Exception {
		super("list-first", offset, docs);
	}
	
	/**
	* Constructs an instance of the CPSListFirstRequest class.
	* @param offset offset of result set
	* @param docs max number of docs to return
	* @param list map where key is xpath and value contains listing options (yes, no, snippet or highlight) 
	* @throws Exception 
	* 
	*/
	public CPSListFirstRequest(int offset, int docs, Map<String, String> list) throws Exception {
		super("list-first", offset, docs, list);
	}

}
