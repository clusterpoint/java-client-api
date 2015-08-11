package com.clusterpoint.api.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import com.clusterpoint.api.CPSnetworkDuration;

public class CPSHttpLib implements CPSTransport {
	private URL _url;
	HttpURLConnection _conn;

	public CPSHttpLib(String URL) throws IOException {
		_url = new URL(URL);
		_conn = (HttpURLConnection) _url.openConnection();
	}

	@Override
	public String exchange(String data, String storage,
			CPSnetworkDuration lastNetworkDuration) throws IOException {

		_conn.setDoOutput(true);
		if (storage.length() > 0)
			_conn.setRequestProperty("Recipient", storage);
		OutputStreamWriter wr = new OutputStreamWriter(_conn.getOutputStream());

		wr.write(data);
		wr.flush();

		BufferedReader rd = new BufferedReader(new InputStreamReader(
				_conn.getInputStream()));
		String line;
		StringBuffer buf = new StringBuffer();
		while ((line = rd.readLine()) != null) {
			buf.append(line);
		}
		wr.close();
		rd.close();

		return buf.toString();
	}

	@Override
	public String exchange(String data, String storage) throws IOException {
		CPSnetworkDuration lastNetworkDuration = new CPSnetworkDuration();
		return exchange(data, storage, lastNetworkDuration);
	}

	@Override
	public String exchange(String data) throws IOException {
		return exchange(data, "");
	}

	@Override
	public void close() {
		_conn.disconnect();
	}
}
