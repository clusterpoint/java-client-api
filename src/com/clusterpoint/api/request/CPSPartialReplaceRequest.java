package com.clusterpoint.api.request;

import java.util.HashMap;
import java.util.Map;
import org.w3c.dom.Node;

/**
* The CPSPartialReplaceRequest class is a wrapper for the CPSModifyRequest class for the partial-replace command
* @see com.clusterpoint.api.response.CPSModifyResponse
*/
public class CPSPartialReplaceRequest extends CPSModifyRequest {
	/**
	* Constructs an instance of the CPSPartialReplaceRequest class.
	*/
	public CPSPartialReplaceRequest() {
		super("partial-replace");
	}
	
	/**
	* Constructs an instance of the CPSPartialReplaceRequest class.
	* @param id document id
	* @param document replaceable document contents
	*/
	public CPSPartialReplaceRequest(String id, String document) {
		super("partial-replace");
		Map<String, String> map = new HashMap<String, String>();
		map.put(id, document);
		this.setDocuments(map);
	}
	
	/**
	* Constructs an instance of the CPSPartialReplaceRequest class.
	* @param document replaceable document content with document id in it
	*/
	public CPSPartialReplaceRequest(String document) {
		super("partial-replace", document);
	}
	
	/**
	* Constructs an instance of the CPSPartialReplaceRequest class.
	* @param document Document as DOM Node
	*/
	public CPSPartialReplaceRequest(Node document) {
		super("partial-replace", document);
	}
}
