package com.clusterpoint.api;

import java.io.IOException;
import java.net.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import com.clusterpoint.api.CPSRequest;
import com.clusterpoint.api.net.CPSHttpLib;
import com.clusterpoint.api.net.CPSTcpLib;
import com.clusterpoint.api.net.CPSTransport;
import com.clusterpoint.api.response.CPSAlternativesResponse;
import com.clusterpoint.api.response.CPSBeginTransactionResponse;
import com.clusterpoint.api.response.CPSListFacetsResponse;
import com.clusterpoint.api.response.CPSListLastRetrieveFirstResponse;
import com.clusterpoint.api.response.CPSListPathsResponse;
import com.clusterpoint.api.response.CPSListWordsResponse;
import com.clusterpoint.api.response.CPSLookupResponse;
import com.clusterpoint.api.response.CPSModifyResponse;
import com.clusterpoint.api.response.CPSSearchDeleteResponse;
import com.clusterpoint.api.response.CPSSearchResponse;
import com.clusterpoint.api.response.CPSStatusResponse;

import javax.xml.transform.TransformerFactoryConfigurationError;

/**
 * The connection class - represents a connection to Clusterpoint Storage
 */
public class CPSConnection {
	private ConnectionObj _connectionString;
	private String _storageName;
	private String _username;
	private String _password;
	private String _accountID;
	private String _documentRootXpath;
	private String[] _documentIdXpath;
	private String _applicationId;
	private boolean _debug;
	private CPSTransport _transport;
	private long _transactionId;
	private CPSnetworkDuration _lastNetworkDuration;
	private float _lastRequestDuration;

	final int NO_TRANSACTION = -1;

	/**
	 * Constructs an instance of the Connection class. Note that it doesn't
	 * necessarily make a connection to CPS when the constructor is called. To
	 * specify document root tag and document id path please use other
	 * constructor
	 * {@link #CPSConnection(String, String, String, String, String, String)}.
	 * Default tag name for document root is "document" and document ID path
	 * "//document/id".
	 * 
	 * @param cns
	 *            Specifies the connection string, such as tcp://127.0.0.1:5550
	 * @param storage
	 *            The name of the storage you want to connect to
	 * @param username
	 *            Username for authenticating with the storage
	 * @param password
	 *            Password for this user
	 */
	public CPSConnection(String cns, String storage, String username,
			String password) throws Exception {
		this(cns, storage, username, password, "document", "//document/id");
	}

	/**
	 * Constructs an instance of the Connection class. Note that it doesn't
	 * necessarily make a connection to CPS when the constructor is called. To
	 * specify document root tag and document id path please use other
	 * constructor
	 * {@link #CPSConnection(String, String, String, String, String, String)}.
	 * Default tag name for document root is "document" and document ID path
	 * "//document/id".
	 * 
	 * @param cns
	 *            Specifies the connection string, such as tcp://127.0.0.1:5550
	 * @param storage
	 *            The name of the storage you want to connect to
	 * @param username
	 *            Username for authenticating with the storage
	 * @param password
	 *            Password for this user
	 * @param account
	 *            Cloud Account ID
	 */
	public CPSConnection(String cns, String storage, String username,
			String password, String account) throws Exception {
		this(cns, storage, username, password, account, "document",
				"//document/id");
	}

	/**
	 * Constructs an instance of the Connection class. Note that it doesn't
	 * necessarily make a connection to CPS when the constructor is called.
	 * 
	 * @param cns
	 *            Specifies the connection string, such as tcp://127.0.0.1:5550
	 * @param storage
	 *            The name of the storage you want to connect to
	 * @param username
	 *            Username for authenticating with the storage
	 * @param password
	 *            Password for this user
	 * @param account
	 *            Cloud Account ID
	 * @param documentRootXpath
	 *            Document root tag name.
	 * @param documentIdXpath
	 *            Document ID xpath.
	 */
	public CPSConnection(String cns, String storage, String username,
			String password, String account, String documentRootXpath,
			String documentIdXpath) throws Exception {
		_connectionString = _parseConnectionString(cns, storage);
		_storageName = storage;
		_username = username;
		_password = password;
		_documentRootXpath = documentRootXpath;
		_transactionId = NO_TRANSACTION;
		_accountID = account;

		if (_connectionString.get_type() == ConnectionObj.CONN_TYPE_SOCKET)
			_transport = new CPSTcpLib(_connectionString.get_host(), _connectionString.get_port(), _connectionString.is_ssl());			
		else
			_transport = new CPSHttpLib(_connectionString._url);

		// split xpath into parts
		Vector<String> idxpathArr = new Vector<String>(
				Arrays.asList(documentIdXpath.split("[/]+")));
		Iterator<String> it = idxpathArr.iterator();
		// get rid of empty elements
		while (it.hasNext()) {
			if (it.next().length() == 0)
				it.remove();
		}
		_documentIdXpath = new String[idxpathArr.size()];
		int i = 0;
		it = idxpathArr.iterator();
		while (it.hasNext()) {
			_documentIdXpath[i++] = it.next();
		}

		_applicationId = "CPS_JAVA_API";
		_debug = false;
		_lastNetworkDuration = new CPSnetworkDuration();
	}

	/**
	 * Constructs an instance of the Connection class. Note that it doesn't
	 * necessarily make a connection to CPS when the constructor is called.
	 * 
	 * @param cns
	 *            Specifies the connection string, such as tcp://127.0.0.1:5550
	 * @param storage
	 *            The name of the storage you want to connect to
	 * @param username
	 *            Username for authenticating with the storage
	 * @param password
	 *            Password for this user
	 * @param documentRootXpath
	 *            Document root tag name.
	 * @param documentIdXpath
	 *            Document ID xpath.
	 */

	public CPSConnection(String cns, String storage, String username,
			String password, String documentRootXpath, String documentIdXpath)
			throws Exception {
		this(cns, storage, username, password, "", documentRootXpath,
				documentIdXpath);
	}

	/**
	 * Sends the request to CPS
	 * 
	 * @param request
	 *            An object of the class CPSRequest
	 */
	public CPSResponse sendRequest(CPSRequest request)
			throws TransformerFactoryConfigurationError, Exception {
		_lastRequestDuration = 0;
		long timeStart = System.currentTimeMillis();
		String reqString = _renderRequest(request);
		String respString, command;
		respString = "";
		if (_debug)
			System.out.println("Sending: " + reqString);
		_lastNetworkDuration.setValue(0);
		respString = _transport.exchange(reqString, _storageName,
				_lastNetworkDuration);
		if (_debug)
			System.out.println("Received: " + respString);
		command = request.getCommand();
		_lastRequestDuration = (System.currentTimeMillis() - timeStart)
				/ (float) 1000;
		switch (command) {
		case "status":
			return new CPSStatusResponse(this, respString);
		case "alternatives":
			return new CPSAlternativesResponse(this, respString);
		case "search":
		case "similar":
			return new CPSSearchResponse(this, respString);
		case "update":
		case "insert":
		case "delete":
		case "replace":
		case "partial-replace":
			return new CPSModifyResponse(this, respString);
		case "list-facets":
			return new CPSListFacetsResponse(this, respString);
		case "list-last":
		case "list-first":
		case "retrieve-first":
		case "retrieve-last":
		case "retrieve":
			return new CPSListLastRetrieveFirstResponse(this, respString);
		case "lookup":
			return new CPSLookupResponse(this, respString);
		case "list-paths":
			return new CPSListPathsResponse(this, respString);
		case "list-words":
			return new CPSListWordsResponse(this, respString);
		case "search-delete":
			return new CPSSearchDeleteResponse(this, respString);
		case "begin-transaction": {
			CPSBeginTransactionResponse resp = new CPSBeginTransactionResponse(
					this, respString);
			if (resp.getTransactionId() > 0)
				_transactionId = resp.getTransactionId();
			return (CPSResponse) resp;
		}
		default: {
			if (command == "commit-transaction"
					|| command == "begin-transaction")
				_transactionId = NO_TRANSACTION;
			return new CPSResponse(this, null, respString);
		}
		}
	}

	/**
	 * Sets the application ID for the request
	 * 
	 * This method can be used to set a custom application ID for your requests.
	 * This can be useful to identify requests sent by a particular application
	 * in a log file
	 * 
	 * @param app_id
	 *            Application id
	 */
	public void setApplicationId(String app_id) {
		this._applicationId = app_id;
	}

	/**
	 * Sets the debugging mode
	 * 
	 * @param debug
	 */
	public void setDebug(boolean debug) {
		this._debug = debug;
	}

	/**
	 * Returns the document ID xpath
	 * 
	 * @return String[]
	 */
	public String[] getDocumentIdXpath() {
		return _documentIdXpath;
	}

	/**
	 * Returns the document root xpath
	 * 
	 * @return String
	 */
	public String getDocumentRootXpath() {
		return _documentRootXpath;
	}

	/**
	 * Closes underlying transport layer
	 * 
	 * @throws IOException
	 */
	public void close() throws IOException {
		_transport.close();
	}

	/**
	 * Clears transaction id in connection object
	 * 
	 * @throws IOException
	 */
	public void clearTransactionId() {
		_transactionId = NO_TRANSACTION;
	}

	/**
	 * Returns current transaction ID
	 * 
	 * @return Long
	 */
	public Long getTransactionId() {
		return _transactionId;
	}

	/**
	 * returns the duration (in seconds) of the last request from the moment the
	 * first packet is sent to the network until the moment the last packet is
	 * received back
	 * 
	 * @return float
	 */
	public float getLastNetworkDuration() {
		return _lastNetworkDuration.getSeconds();
	}

	/**
	 * returns the duration (in seconds) of the last request, as measured on the
	 * client side
	 * 
	 * @return float
	 */
	public float getLastRequestDuration() {
		return _lastRequestDuration;
	}

	/**
	 * Returns an object with parsed connection string data
	 * 
	 * @return ConnectionObj
	 * @throws URISyntaxException
	 */
	private ConnectionObj _parseConnectionString(String cns, String storage)
			throws URISyntaxException {
		ConnectionObj conn = new ConnectionObj();
		boolean ssl = false;
		if (cns == "" || cns == "unix://") {
			// default connection
			conn.set_type(ConnectionObj.CONN_TYPE_SOCKET);
			conn.set_host("unix:///usr/local/" + storage + "/storage.sock");
		} else if (cns.startsWith("http://") || cns.startsWith("https://")) {
			// HTTP connection
			conn.set_type(ConnectionObj.CONN_TYPE_HTTP);
			conn.set_url(cns);
		} else if (cns.startsWith("unix://")) {
			// Unix socket
			conn.set_type(ConnectionObj.CONN_TYPE_SOCKET);
			conn.set_host(cns);
			conn.set_port(0);
		} else if (cns.startsWith("tcp://") || (ssl = cns.startsWith("tcps://"))) {
			// TCP socket
			URI url = new URI(cns);
			conn.set_type(ConnectionObj.CONN_TYPE_SOCKET);
			conn.set_host(url.getHost());
			if (url.getPort() < 0)
				conn.set_port(5550);
			else
				conn.set_port(url.getPort());
			conn.set_ssl(ssl);		
		}
		return conn;
	}

	/**
	 * Renders the request as XML
	 * 
	 * @param request
	 *            Instance of CPSRequest class
	 * @return string XML request
	 */
	private String _renderRequest(CPSRequest request)
			throws TransformerFactoryConfigurationError, Exception {
		Map<String, String[]> envelopeFields = new HashMap<String, String[]>();
		envelopeFields.put("cps:storage", new String[] { _storageName });
		envelopeFields.put("cps:user", new String[] { _username });
		envelopeFields.put("cps:password", new String[] { _password });
		if (_accountID.length() > 0)
			envelopeFields.put("cps:account", new String[] { _accountID });
		envelopeFields
				.put("cps:command", new String[] { request.getCommand() });
		if (request.getRequestID().length() > 0)
			envelopeFields
					.put("cps:requestid", new String[] { _applicationId });
		if (request.getRequestType() != CPSRequest.CPSREQUEST_TYPE_AUTO)
			envelopeFields.put("cps:type", new String[] { CPSRequest
					.get_requestTypeString(request.getRequestType()) });
		if (_transactionId > 0)
			request.setParam("transaction_id", Long.toString(_transactionId));
		return request.getRequestXml(_documentRootXpath, _documentIdXpath,
				envelopeFields);
	}
	
	private class ConnectionObj {
		public int _type;
		public String _host;
		public String _url;
		public int _port;
		private boolean _ssl;

		public final static int CONN_TYPE_UNKNOWN = 0;
		public final static int CONN_TYPE_SOCKET = 1;
		public final static int CONN_TYPE_HTTP = 2;

		public ConnectionObj() {
			this(CONN_TYPE_UNKNOWN, "", "", 0, false);
		} 
		
		@SuppressWarnings("unused")
		public ConnectionObj(int type, String host, String url, int port)
		{
			this(type, host, url, port, false);
		}
		
		public ConnectionObj(int type, String host, String url, int port, boolean is_ssl)
		{
			_type 	= type;
			_host 	= host;
			_url 	= url;
			_port 	= port;
			_ssl	= is_ssl;
		}
		
		public int get_type() {
			return _type;
		}
		
		public boolean is_ssl()
		{
			return _ssl;
		}
		
		public void set_ssl(boolean ssl)
		{
			_ssl = ssl;
		}

		public void set_type(int _type) {
			this._type = _type;
		}

		public String get_host() {
			return _host;
		}

		public void set_host(String _host) {
			this._host = _host;
		}

		@SuppressWarnings("unused")
		public String get_url() {
			return _url;
		}

		public void set_url(String _url) {
			this._url = _url;
		}

		public int get_port() {
			return _port;
		}

		public void set_port(int _port) {
			this._port = _port;
		}
	}
}
