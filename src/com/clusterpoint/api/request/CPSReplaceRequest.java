package com.clusterpoint.api.request;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Node;

/**
* The CPSReplaceRequest class is a wrapper for the CPSModifyRequest class for the replace command
* @see com.clusterpoint.api.response.CPSModifyResponse
*/
public class CPSReplaceRequest extends CPSModifyRequest {
	/**
	* Constructs an instance of the CPSReplaceRequest class.
	* Documents can be set using add setDocuments or setStringDocuments methods
	* inherited from CPSModifyRequest class
	*/
	public CPSReplaceRequest() {
		super("partial-replace");
	}
	
	/**
	* Constructs an instance of the CPSReplaceRequest class.
	* @param id document id
	* @param document replaceable document contents
	*/
	public CPSReplaceRequest(String id, String document) {
		super("partial-replace");
		Map<String, String> map = new HashMap<String, String>();
		map.put(id, document);
		this.setDocuments(map);
	}
	
	/**
	* Constructs an instance of the CPSReplaceRequest class.
	* @param document replaceable document contents with document id in it
	*/
	public CPSReplaceRequest(String document) {
		super("partial-replace", document);
	}
	
	/**
	* Constructs an instance of the CPSReplaceRequest class.
	* @param document Document as DOM Node
	*/
	public CPSReplaceRequest(Node document) {
		super("insert", document);
	}
}
