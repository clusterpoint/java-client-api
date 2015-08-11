package com.clusterpoint.api.request;

import com.clusterpoint.api.CPSRequest;

/**
* The CPSListFacetsRequest class is a wrapper for the CPSRequest class for the list-facets command
* @see com.clusterpoint.api.response.CPSListFacetsResponse
*/
public class CPSListFacetsRequest extends CPSRequest {
	/**
	* Constructs an instance of the CPSListFacetsRequest class.
	*/
	public CPSListFacetsRequest() throws Exception {
		super("list-facets");
	}
	
	/**
	* Constructs an instance of the CPSListFacetsRequest class.
	* @param paths an array of paths to list the facet terms from
	*/
	public CPSListFacetsRequest(String[] paths) throws Exception {
		super("list-facets");
		this.setPaths(paths);
	}
	
	/**
	* Constructs an instance of the CPSListFacetsRequest class.
	* @param path facet path to list the facet terms from
	*/
	public CPSListFacetsRequest(String path) throws Exception {
		super("list-facets");
		this.setPaths(path);
	}
	
	/**
	 * Set path for facet for which values will be returned
	 * @param path facet path to list the facet terms from
	 * @throws Exception
	 */
	public void setPaths(String path) throws Exception
	{
		this.setParam("path", path);
	}
	
	/**
	 * Set array of paths for facets for which values will be returned
	 * @param path facet path to list the facet terms from
	 * @throws Exception
	 */
	public void setPaths(String[] path) throws Exception
	{
		this.setParam("path", path);
	}
}
