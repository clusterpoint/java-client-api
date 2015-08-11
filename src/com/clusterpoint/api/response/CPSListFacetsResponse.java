package com.clusterpoint.api.response;

import java.util.List;

import com.clusterpoint.api.CPSConnection;
import com.clusterpoint.api.CPSResponse;
import com.clusterpoint.api.response.listfacets.CPSListFacetsContent;

/**
* The CPSListFacetsResponse class is a wrapper for the CPSResponse class for the list-facets command
* @see com.clusterpoint.api.request.CPSListFacetsRequest
*/
public class CPSListFacetsResponse extends CPSResponse {

	public CPSListFacetsResponse(CPSConnection conn, String respXML)
			throws Exception {
		super(conn, com.clusterpoint.api.response.listfacets.CPSListFacetsContent.class, respXML);
	}
	
	/**
	* Returns the list of facets facets from the response;
	* @return CPSListFacetsContent.Facet
	*/
	public List<CPSListFacetsContent.Facet> getFacets()
	{
		return ((CPSListFacetsContent)_resp_objects.firstElement()).getFacet();
	}

}
