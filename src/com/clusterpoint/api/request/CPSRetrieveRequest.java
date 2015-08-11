package com.clusterpoint.api.request;

import java.util.HashMap;
import java.util.Map;

import com.clusterpoint.api.CPSRequest;

/**
* The CPSRetrieveRequest class is a wrapper for the CPSRequest class
* @see com.clusterpoint.api.response.CPSListLastRetrieveFirstResponse
*/
public class CPSRetrieveRequest extends CPSRequest {
	/**
	* Constructs an instance of the CPSRetrieveRequest class.
	*/
	public CPSRetrieveRequest() {
		super("retrieve");
	}
	
	/**
	* Constructs an instance of the CPSRetrieveRequest class.
	* @param id see {@link CPSRetrieveRequest#setDocs(String) setDocs}
	*/
	public CPSRetrieveRequest(String id) {
		super("retrieve");
		this.setDocs(id);
	}
	
	/**
	* Constructs an instance of the CPSRetrieveRequest class.
	* @param id see {@link CPSRetrieveRequest#setDocs(String[]) setDocs}
	*/
	public CPSRetrieveRequest(String[] id) {
		super("retrieve");
		this.setDocs(id);
	}
	
	/**
	 * Sets documents ids to be retrieved 
	 * @param id array of document ids
	 */
	public void setDocs(String[] id)
	{
		Map<String, String> map = new HashMap<String, String>();
		for (int i = 0; i < id.length; i++)
			map.put(id[i], "");
		this._documents = map;
	}
	/**
	 * Sets document id to be retrieved 
	 * @param id document id to be retrieved
	 */
	public void setDocs(String id)
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put(id, "");
		this._documents = map;
	}

}
