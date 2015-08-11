package com.clusterpoint.api.request;

import com.clusterpoint.api.CPSRequest;

public class CPSBeginTransactionRequest extends CPSRequest {

	public CPSBeginTransactionRequest() {
		super("begin-transaction");
	}

	public CPSBeginTransactionRequest(String requestID) {
		super("begin-transaction", requestID);
	}
}
