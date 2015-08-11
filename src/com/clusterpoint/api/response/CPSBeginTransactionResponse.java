package com.clusterpoint.api.response;

import com.clusterpoint.api.CPSConnection;
import com.clusterpoint.api.CPSResponse;
import com.clusterpoint.api.response.begintransaction.CPSBeginTransactionContent;

/**
* The CPSListPathsResponse class is a wrapper for the CPSResponse class for the list-paths command
* @see com.clusterpoint.api.request.CPSListPathsRequest
*/
public class CPSBeginTransactionResponse extends CPSResponse {

	public CPSBeginTransactionResponse(CPSConnection conn, String respXML)
			throws Exception {
		super(conn, com.clusterpoint.api.response.begintransaction.CPSBeginTransactionContent.class, respXML);
	}
	
	/**
	* Returns an array of paths
	* @return List<String>
	*/
	public Long getTransactionId()
	{
		return ((CPSBeginTransactionContent)_resp_objects.firstElement()).getTransactionId();
	}

}

