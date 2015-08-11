package com.clusterpoint.api.request;

import com.clusterpoint.api.CPSRequest;

public class CPSCommitTransactionRequest extends CPSRequest {

	public CPSCommitTransactionRequest() {
		super("commit-transaction");
	}

	public CPSCommitTransactionRequest(String requestID) {
		super("commit-transaction", requestID);
	}

}
