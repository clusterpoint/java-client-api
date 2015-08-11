package com.clusterpoint.api.request;

import java.util.HashMap;
import java.util.Map;

/**
* The CPSDeleteRequest class is a wrapper for the CPSModifyRequest class for the delete command
* @see com.clusterpoint.api.response.CPSModifyResponse
*/
public class CPSDeleteRequest extends CPSModifyRequest {
	/**
	* Constructs an instance of the CPSDeleteRequest class.
	*/
	public CPSDeleteRequest() {
		super("delete");
	}
	
	/**
	* Constructs an instance of the CPSDeleteRequest class.
	* @param id document id
	*/
	public CPSDeleteRequest(String id) {
		super("delete");
		this.setID(id);
	}
	
	/**
	* Constructs an instance of the CPSDeleteRequest class.
	* @param id array of document ids
	*/	
	public CPSDeleteRequest(String[] id) {
		super("delete");
		this.setID(id);
	}
	
	/**
	* Sets ID of document to delete
	* @param id document id to delete
	*/
	public void setID(String id)
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put(id, "");
		this.setDocuments(map);
	}
	
	/**
	* Sets IDs of documents to delete
	* @param id array of document ids to delete
	*/
	public void setID(String[] id)
	{
		Map<String, String> map = new HashMap<String, String>();
		for (int i = 0; i < id.length; i++)
			map.put(id[i], "");
		this.setDocuments(map);
	}
}