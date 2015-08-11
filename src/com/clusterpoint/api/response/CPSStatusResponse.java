package com.clusterpoint.api.response;

import com.clusterpoint.api.CPSConnection;
import com.clusterpoint.api.CPSResponse;
import com.clusterpoint.api.response.status.CPSStatusContent;

/**
* The CPSStatusResponse class is a wrapper for the CPSResponse class for the status command
* @see com.clusterpoint.api.request.CPSStatusRequest
*/
public class CPSStatusResponse extends CPSResponse  {
	
	public CPSStatusResponse(CPSConnection conn, String respXML) throws Exception {
		super(conn, com.clusterpoint.api.response.status.CPSStatusContent.class, respXML);
	}

	/**
	* Returns an CPSStatusContent object, which contains the status information
	* @return CPSStatusContent
	*/
	public CPSStatusContent getStatus()
	{
		return ((CPSStatusContent)_resp_objects.firstElement());
	}
}
