package com.clusterpoint.api.request;

import com.clusterpoint.api.CPSRequest;

public class CPSRollbackTransactionRequest extends CPSRequest {

	public CPSRollbackTransactionRequest() {
		super("rollback-transaction");
	}

	public CPSRollbackTransactionRequest(String requestID) {
		super("rollback-transaction", requestID);
	}

}
