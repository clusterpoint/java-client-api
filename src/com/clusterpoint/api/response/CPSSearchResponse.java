package com.clusterpoint.api.response;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.w3c.dom.Element;

import com.clusterpoint.api.CPSConnection;
import com.clusterpoint.api.CPSResponse;
import com.clusterpoint.api.response.search.CPSSearchContent;
import com.clusterpoint.api.response.search.CPSSearchContent.Aggregate;
import com.clusterpoint.api.response.search.CPSSearchContent.Aggregate.Data;
import com.clusterpoint.api.response.search.CPSSearchFacet;

/**
* The CPSSearchResponse class is a wrapper for the CPSResponse class
* @see com.clusterpoint.api.request.CPSSearchRequest
*/
public class CPSSearchResponse extends CPSResponse {

	public CPSSearchResponse(CPSConnection conn, String respXML) throws Exception {
		super(conn, com.clusterpoint.api.response.search.CPSSearchContent.class, respXML);
	}
	
	/**
	* Returns the documents from the response as List of documents represented as DOM Element objecs
	* @return List<Element>
	*/
	public List<Element> getDocuments() {
		return ((CPSSearchContent)_resp_objects.firstElement()).getResults().getAny();
	}
	
	public Map<String, List<HashMap<String, String>>> getAggregates() {
		Map<String, List<HashMap<String, String>>> result = new HashMap<String, List<HashMap<String, String>>>();
		Iterator<Aggregate> ait = ((CPSSearchContent)_resp_objects.firstElement()).getAggregate().iterator();
		while (ait.hasNext()) {
			Aggregate a = ait.next();
			Iterator<Data> dit = a.getData().iterator();
			List<HashMap<String, String>> a_data = new Vector<HashMap<String, String>>();			
			while (dit.hasNext()) {			
				HashMap<String, String> a_data_item = new HashMap<String, String>();
				Data data = dit.next();
				Iterator<Element> item = data.getAny().iterator();				
				while (item.hasNext())
				{
					Element el = item.next();
					a_data_item.put(el.getTagName(), el.getTextContent());	
				}	
				a_data.add(a_data_item);
			}
			result.put(a.getQuery(), a_data);
		}
		return result;
	}
	
	/**
	* Returns the facets from the response in a form of List where each element is of class CPSSearchFacet
	* @return List<CPSSearchFacet>
	*/
	public List<CPSSearchFacet> getFacets()	{
		return ((CPSSearchContent)_resp_objects.firstElement()).getFacet();
	}
	
	/**
	* Returns the number of documents returned
	* @return int
	*/
	public int getFound() {
		return ((CPSSearchContent)_resp_objects.firstElement()).getFound();
	}
	
	/**
	* Returns the total number of hits - i.e. the number of documents in a storage that match the request
	* @return int
	*/
	public int getHits() {
		return ((CPSSearchContent)_resp_objects.firstElement()).getHits();
	}
	
	/**
	* Returns the position of the first document that was returned
	* @see com.clusterpoint.api.request.CPSSearchRequest#setOffset(int)
	* @see com.clusterpoint.api.request.CPSSearchRequest#setDocs(int)
	* @return int
	*/
	public int getFrom() {
		return ((CPSSearchContent)_resp_objects.firstElement()).getFrom();		
	}
	
	/**
	* Returns the position of the first document that was returned
	* @see com.clusterpoint.api.request.CPSSearchRequest#setOffset(int)
	* @see com.clusterpoint.api.request.CPSSearchRequest#setDocs(int)
	* @return int
	*/
	public String getMore() {
		return ((CPSSearchContent)_resp_objects.firstElement()).getMore();
	}
	
	/**
	* Returns the position of the last document that was returned
	* @see com.clusterpoint.api.request.CPSSearchRequest#setOffset(int)
	* @see com.clusterpoint.api.request.CPSSearchRequest#setDocs(int)
	* @return int
	*/
	public int getTo() {
		return ((CPSSearchContent)_resp_objects.firstElement()).getTo();
	}

}
