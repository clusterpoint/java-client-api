package com.clusterpoint.api.request;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Node;

/**
* The CPSUpdateRequest class is a wrapper for the CPSModifyRequest class for the update command
* @see com.clusterpoint.api.response.CPSModifyResponse
*/
public class CPSUpdateRequest extends CPSModifyRequest {
	/**
	* Constructs an instance of the CPSUpdateRequest class.
	* Documents can be set using add setDocuments or setStringDocuments methods
	* inherited from CPSModifyRequest class
	*/
	public CPSUpdateRequest() {
		super("update");
	}
	
	/**
	* Constructs an instance of the CPSUpdateRequest class.
	* @param id document id
	* @param document document content to be updated
	*/
	public CPSUpdateRequest(String id, String document) {
		super("update");
		Map<String, String> map = new HashMap<String, String>();
		map.put(id, document);
		this.setDocuments(map);
	}
	
	/**
	* Constructs an instance of the CPSUpdateRequest class.
	* @param document document content to be updated with document id in it
	*/
	public CPSUpdateRequest(String document) {
		super("update", document);
	}	
	
	/**
	* Constructs an instance of the CPSUpdateRequest class.
	* @param document Document as DOM Node
	*/
	public CPSUpdateRequest(Node document) {
		super("insert", document);
	}
}
