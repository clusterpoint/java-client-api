package com.clusterpoint.api.response;

import com.clusterpoint.api.CPSConnection;
import com.clusterpoint.api.CPSResponse;
import com.clusterpoint.api.response.searchdelete.CPSSearchDeleteContent;

/**
* The CPSSearchDeleteResponse class is a wrapper for the CPSResponse class
* @see com.clusterpoint.api.request.CPSSearchDeleteRequest
*/
public class CPSSearchDeleteResponse extends CPSResponse {

	public CPSSearchDeleteResponse(CPSConnection conn, String respXML)
			throws Exception {
		super(conn, com.clusterpoint.api.response.searchdelete.CPSSearchDeleteContent.class, respXML);
	}
	
	/**
	* Returns the total number of hits - i.e. the number of documents erased
	* @return int
	*/
	public int getHits()
	{
		return ((CPSSearchDeleteContent)_resp_objects.firstElement()).getHits();
	}
}
