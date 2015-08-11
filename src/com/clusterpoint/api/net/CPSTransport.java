package com.clusterpoint.api.net;

import java.io.IOException;
import com.clusterpoint.api.CPSnetworkDuration;

public interface CPSTransport {
	String exchange(String data, String storage, CPSnetworkDuration lastNetworkDuration) throws IOException;
	String exchange(String data, String storage) throws IOException;
	String exchange(String data) throws IOException;
	void close() throws IOException;
}
