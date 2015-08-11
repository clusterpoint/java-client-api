package com.clusterpoint.api.request;

import java.util.Map;

/**
* The CPSRetrieveLastRequest class is a wrapper for the CPSListLastRetrieveFirstRequest class for retrieve-last requests
* @see com.clusterpoint.api.response.CPSListLastRetrieveFirstResponse
*/
public class CPSRetrieveLastRequest extends CPSListLastRetrieveFirstRequest {
	/**
	* Constructs an instance of the CPSRetrieveLastRequest class.
	* Default parameters: offset = 0, docs = 10
	* @throws Exception 
	* 
	*/
	public CPSRetrieveLastRequest() throws Exception {
		super("retrieve-last", 0, 10);
	}
	
	/**
	* Constructs an instance of the CPSRetrieveLastRequest class.
	* @param offset offset of result set
	* @param docs max number of docs to return 
	 * @throws Exception 
	* 
	*/
	public CPSRetrieveLastRequest(int offset, int docs) throws Exception {
		super("retrieve-last", offset, docs);
	}
	
	/**
	* Constructs an instance of the CPSRetrieveLastRequest class.
	* @param offset offset of result set
	* @param docs max number of docs to return
	* @param list map where key is xpath and value contains listing options (yes, no, snippet or highlight) 
	 * @throws Exception 
	* 
	*/
	public CPSRetrieveLastRequest(int offset, int docs,
			Map<String, String> list) throws Exception {
		super("retrieve-last", offset, docs, list);
	}
}
