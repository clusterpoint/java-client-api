package com.clusterpoint.api.request;

import com.clusterpoint.api.CPSRequest;

/**
* The CPSStatusRequest class is a wrapper for the CPSRequest class for the status command
* @see com.clusterpoint.api.response.CPSStatusResponse
*/
public class CPSStatusRequest extends CPSRequest {
	/**
	* Constructs an instance of CPSStatusRequest
	*/
	public CPSStatusRequest() {
		super("status");
	}

}
