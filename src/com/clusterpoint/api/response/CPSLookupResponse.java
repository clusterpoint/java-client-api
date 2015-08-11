package com.clusterpoint.api.response;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.w3c.dom.Element;

import com.clusterpoint.api.CPSConnection;
import com.clusterpoint.api.CPSResponse;
import com.clusterpoint.api.response.lookup.CPSLookupContent;


/**
* The CPSLookupResponse class is a wrapper for the CPSResponse class for the lookup command
* @see com.clusterpoint.api.request.CPSLookupRequest
*/
public class CPSLookupResponse extends CPSResponse {
	private int _found;
	ArrayList<Element> _elements;
	public CPSLookupResponse(CPSConnection conn, String respXML)
			throws Exception {
		super(conn, com.clusterpoint.api.response.lookup.CPSLookupContent.class, respXML);
		List<Element> lel = ((CPSLookupContent) _resp_objects.firstElement()).getAny();
		_found = 0;
		_elements = new ArrayList<Element>();
		if (lel.size() > 0)
		{
			Iterator<Element> it = lel.iterator();
			while (it.hasNext())
			{
				Element el = it.next();
				if (el.getNodeName() == "found")
					_found = Integer.valueOf(el.getTextContent());
				else
					_elements.add(el);
					
			}
		}
	}
	
	/**
	* Returns the number of documents found by lookup command
	* @return int
	*/
	public int getFound() {
		return _found;
	}
	
	/**
	* Returns the documents from the response as List of documents represented as DOM Element objects
	* @return List<Element>
	*/
	public List<Element> getDocuments()
	{
		return _elements;
	}
	
}
