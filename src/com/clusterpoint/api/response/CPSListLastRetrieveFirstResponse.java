package com.clusterpoint.api.response;

import java.util.List;

import org.w3c.dom.Element;

import com.clusterpoint.api.CPSConnection;
import com.clusterpoint.api.CPSResponse;
import com.clusterpoint.api.response.listlastretrievefirst.CPSListLastRetrieveFirstContent;

/**
* The CPSListLastRetrieveFirstResponse class is a wrapper for the CPSResponse class for replies to retrieve, list-last, list-first, retrieve-last, and retrieve-first commands
* @see com.clusterpoint.api.request.CPSRetrieveRequest
* @see com.clusterpoint.api.request.CPSListLastRequest
* @see com.clusterpoint.api.request.CPSListFirstRequest
* @see com.clusterpoint.api.request.CPSRetrieveLastRequest
* @see com.clusterpoint.api.request.CPSRetrieveFirstRequest
*/
public class CPSListLastRetrieveFirstResponse extends CPSResponse {

	public CPSListLastRetrieveFirstResponse(CPSConnection conn, String respXML) throws Exception {
		super(conn, com.clusterpoint.api.response.listlastretrievefirst.CPSListLastRetrieveFirstContent.class, respXML);
	}
	
	/**
	* Returns the documents from the response as List of documents represented as DOM Element objects
	* @return List<Element>
	*/
	public List<Element> getDocuments()
	{
		return ((CPSListLastRetrieveFirstContent) _resp_objects.firstElement()).getResults().getAny();
	}
	
	/**
	* Returns the number of documents returned
	* @return int
	*/
	public int getFrom() {
		return ((CPSListLastRetrieveFirstContent) _resp_objects.firstElement()).getFrom();
	}
	
	public int getTo() {
		return ((CPSListLastRetrieveFirstContent) _resp_objects.firstElement()).getTo();
	}
	
}
