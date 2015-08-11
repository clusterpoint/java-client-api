package com.clusterpoint.api.request;

import com.clusterpoint.api.CPSRequest;

/**
* The CPSListPathsRequest class is a wrapper for the CPSRequest class for the list-paths command
* @see com.clusterpoint.api.response.CPSListPathsResponse
*/
public class CPSListPathsRequest extends CPSRequest {
	/**
	* Constructs an instance of CPSListPathsRequest
	*/
	public CPSListPathsRequest() {
		super("list-paths");
	}
}
