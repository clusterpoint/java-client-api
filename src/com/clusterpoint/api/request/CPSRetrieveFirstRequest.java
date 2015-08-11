package com.clusterpoint.api.request;

import java.util.Map;

/**
* The CPSRetrieveFirstRequest class is a wrapper for the CPSListLastRetrieveFirstRequest class for retrieve-first requests
* @see com.clusterpoint.api.response.CPSListLastRetrieveFirstResponse
*/
public class CPSRetrieveFirstRequest extends CPSListLastRetrieveFirstRequest {
	/**
	* Constructs an instance of the CPSRetrieveFirstRequest class.
	* Default parameters: offset = 0, docs = 10
	* @throws Exception 
	* 
	*/
	public CPSRetrieveFirstRequest() throws Exception {
		super("retrieve-first", 0, 10);
	}
	
	/**
	* Constructs an instance of the CPSRetrieveFirstRequest class.
	* @param offset offset
	* @param docs max number of docs to return
	* @throws Exception 
	*/
	public CPSRetrieveFirstRequest(int offset, int docs) throws Exception {
		super("retrieve-first", offset, docs);
	}
	
	/**
	* Constructs an instance of the CPSRetrieveFirstRequest class.
	* @param offset offset
	* @param docs max number of docs to return
	* @param list map where key is xpath and value contains listing options (yes, no, snippet or highlight)
	* @throws Exception 
	*/
	public CPSRetrieveFirstRequest(int offset, int docs,
			Map<String, String> list) throws Exception {
		super("retrieve-first", offset, docs, list);
	}
}
