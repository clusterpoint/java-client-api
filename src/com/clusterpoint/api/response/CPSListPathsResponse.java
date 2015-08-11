package com.clusterpoint.api.response;

import java.util.List;

import com.clusterpoint.api.CPSConnection;
import com.clusterpoint.api.CPSResponse;
import com.clusterpoint.api.response.listpaths.CPSListPathsContent;

/**
* The CPSListPathsResponse class is a wrapper for the CPSResponse class for the list-paths command
* @see com.clusterpoint.api.request.CPSListPathsRequest
*/
public class CPSListPathsResponse extends CPSResponse {

	public CPSListPathsResponse(CPSConnection conn, String respXML)
			throws Exception {
		super(conn, com.clusterpoint.api.response.listpaths.CPSListPathsContent.class, respXML);
	}
	
	/**
	* Returns an array of paths
	* @return List<String>
	*/
	public List<String> getPaths()
	{
		return ((CPSListPathsContent)_resp_objects.firstElement()).getPaths().getPath();
	}

}
