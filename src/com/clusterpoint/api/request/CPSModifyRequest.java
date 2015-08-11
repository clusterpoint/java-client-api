package com.clusterpoint.api.request;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import org.w3c.dom.Node;

import com.clusterpoint.api.CPSRequest;

/**
* The CPSModifyRequest class is a wrapper for the CPSRequest class
* @see com.clusterpoint.api.response.CPSModifyResponse
*/
public class CPSModifyRequest extends CPSRequest {
	/**
	* Constructs an instance of the CPSModifyRequest class.
	* @param command name of the command
	* @param document document to be sent as String
	*/
	public CPSModifyRequest(String command, String document) {
		super(command);
		Collection<String> v = new ArrayList<String>();
		v.add(document);
		this._documents = v;
	}
	/**
	* Constructs an instance of the CPSModifyRequest class.
	* @param command name of the command
	* @param document document to be sent as DOM Node
	*/
	public CPSModifyRequest(String command, Node document) {
		super(command);
		Collection<Node> v = new ArrayList<Node>();
		v.add(document);
		this._documents = v;
	}
	
	/**
	* Constructs an instance of the CPSModifyRequest class.
	* @param command name of the command
	*/
	public CPSModifyRequest(String command) {
		super(command);
	}
	
	/**
	* Sets documents to bes sent as Vector of Strings.
	* @param documents Vector of documents
	*/
	public void setStringDocuments(Collection<String> documents)
	{
		this._documents = documents;
	}
	
	/**
	* Sets documents to bes sent as Vector of DOM Nodes.
	* @param documents Vector of documents
	*/
	public void setDocuments(Collection<Node> documents)
	{
		this._documents = documents;
	}
	
	/**
	* Sets documents to be sent as Map, where key is document id and value XML document as String
	* @param documents Vector of documents
	*/
	public void setDocuments(Map<String, String> documents)
	{
		this._documents = documents;
	}
}
