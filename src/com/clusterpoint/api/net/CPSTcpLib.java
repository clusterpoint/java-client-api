package com.clusterpoint.api.net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.net.ssl.SSLSocket;

import com.clusterpoint.api.CPSnetworkDuration;
import com.clusterpoint.api.net.proto.CPSRequestResponse;
import com.google.protobuf.ByteString;
import com.google.protobuf.CodedOutputStream;

public class CPSTcpLib implements CPSTransport {
	private Socket _sock;
	
	TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
		public java.security.cert.X509Certificate[] getAcceptedIssuers() {
			return new X509Certificate[0];
		}

		public void checkClientTrusted(
				java.security.cert.X509Certificate[] certs, String authType) {
			return;
		}

		public void checkServerTrusted(
				java.security.cert.X509Certificate[] certs, String authType) {
			return;
		}
	} };
	
	public CPSTcpLib(String host, int port)  throws IOException, KeyManagementException, NoSuchAlgorithmException {
		this(host, port, false);
	}
	
	public CPSTcpLib(String host, int port, boolean is_ssl) throws IOException, KeyManagementException, NoSuchAlgorithmException {
		if (!is_ssl) {
			_sock = new Socket(host, port);
		} else {
			SSLContext sc = SSLContext.getInstance("TLS");
			sc.init(null, trustAllCerts, new SecureRandom()); 
			_sock = (SSLSocket) sc.getSocketFactory().createSocket(host, port);			
		}
		_sock.setSoLinger(false, 0);
	}

	@Override
	public String exchange(String data, String storage,
			CPSnetworkDuration lastNetworkDuration) throws IOException {
		if (_sock.isClosed())
			throw new IOException("Attempt to send data on closed socket");
		send(data, storage, lastNetworkDuration);
		return receive(lastNetworkDuration);
	}

	@Override
	public String exchange(String data, String storage) throws IOException {
		if (_sock.isClosed())
			throw new IOException("Attempt to send data on closed socket");
		CPSnetworkDuration lastNetworkDuration = new CPSnetworkDuration();
		return exchange(data, storage, lastNetworkDuration);
	}

	@Override
	public String exchange(String data) throws IOException {
		return exchange(data, "special:detect-storage");
	}

	@Override
	public void close() throws IOException {
		if (!_sock.isClosed())
			_sock.close();
	}

	private void send(String data, String storage,
			CPSnetworkDuration lastNetworkDuration) throws IOException {
		long timeStart = System.currentTimeMillis();
		CodedOutputStream out = CodedOutputStream
				.newInstance(new DataOutputStream(_sock.getOutputStream()));
		CPSRequestResponse.CPSMessage msg = CPSRequestResponse.CPSMessage
				.newBuilder()
				.setData(ByteString.copyFrom(data.getBytes("UTF-8")))
				.setRecipient(storage).build();
		byte bheader[] = getHeader(msg.getSerializedSize());
		out.writeRawBytes(bheader);
		msg.writeTo(out);
		out.flush();
		lastNetworkDuration.addMilisecondsLong(System.currentTimeMillis()
				- timeStart);
	}

	private String receive(CPSnetworkDuration lastNetworkDuration)
			throws IOException {
		long timeStart = System.currentTimeMillis();
		DataInputStream in = new DataInputStream(_sock.getInputStream());
		byte header[] = new byte[8];
		in.readFully(header);
		int len = dataLength(header);
		byte data[] = new byte[len];
		in.readFully(data);
		lastNetworkDuration.addMilisecondsLong(System.currentTimeMillis()
				- timeStart);
		CPSRequestResponse.CPSMessage msg = CPSRequestResponse.CPSMessage
				.parseFrom(data);
		return msg.getData().toStringUtf8();
	}

	private static int dataLength(byte header[]) {
		if (header.length < 8)
			return 0;
		else
			return (((int) header[4]) & 0xff)
					| ((((int) header[5]) & 0xff) << 8)
					| ((((int) header[6]) & 0xff) << 16)
					| ((((int) header[7]) & 0xff) << 24);
	}

	private static byte[] getHeader(int length) {
		byte header[] = new byte[8];
		header[0] = 0x09;
		header[1] = 0x09;
		header[2] = 0x00;
		header[3] = 0x00;
		header[4] = (byte) (length & 0x000000FF);
		header[5] = (byte) ((length & 0x0000FF00) >> 8);
		header[6] = (byte) ((length & 0x00FF0000) >> 16);
		header[7] = (byte) ((length & 0xFF000000) >> 24);
		return header;
	}
}
