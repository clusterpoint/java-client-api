package com.clusterpoint.api.request;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Node;

/**
* The CPSInsertRequest class is a wrapper for the CPSModifyRequest class for the insert command
* @see com.clusterpoint.api.response.CPSModifyResponse
*/
public class CPSInsertRequest extends CPSModifyRequest {
	/**
	* Constructs an instance of the CPSInsertRequest class.
	* Documents can be set using add setDocuments or setStringDocuments methods
	* inherited from CPSModifyRequest class
	*/
	public CPSInsertRequest() {
		super("insert");
	}
	
	/**
	* Constructs an instance of the CPSInsertRequest class.
	* @param id document id
	* @param document document content to be inserted
	*/
	public CPSInsertRequest(String id, String document) {
		super("update");
		Map<String, String> map = new HashMap<String, String>();
		map.put(id, document);
		this.setDocuments(map);
	}
	
	/**
	* Constructs an instance of the CPSInsertRequest class.
	* @param document Document as DOM Node
	*/
	public CPSInsertRequest(Node document) {
		super("insert", document);
	}

	/**
	* Constructs an instance of the CPSInsertRequest class.
	* @param document Document as String
	*/
	public CPSInsertRequest(String document) {
		super("insert", document);
	}
	
}
