package com.clusterpoint.api;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import java.util.Map.Entry;

import javax.xml.parsers.*;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.*;
import org.xml.sax.*;

/**
 * General request class for CPS API
 */
public class CPSRequest {
	private String 						_command;
	private String 						_requestID;
	private int 						_requestType;
	private Map<String, String[]> 		_textParams;
	private Map<String, String[]>		_rawParams;
	protected Object					_documents;
	private Document 		 			_requestDom;
	
	private static final ThreadLocal<DocumentBuilderFactory> _tlDocumentBuilderFactory = new ThreadLocal<DocumentBuilderFactory>() {
		@Override protected DocumentBuilderFactory initialValue() {
			return DocumentBuilderFactory.newInstance();
	    }	
	};

	public final static int CPSREQUEST_TYPE_AUTO 		= 0;
	public final static int CPSREQUEST_TYPE_SINGLE 		= 1;
	public final static int CPSREQUEST_TYPE_CLUSTER 	= 2;
	
	/**
	* Constructs an instance of the CPSRequest class.
	* @param command Specifies the command field for the request
	*/
	public CPSRequest (String command)
	{
		this(command, "");
	}
	
	/**
	* Constructs an instance of the CPSRequest class.
	* @param command Specifies the command field for the request
	* @param requestID The request ID. Can be useful for identifying a particular request in a log file when debugging
	*/
	public CPSRequest (String command, String requestID)
	{
		_command 		= command;
		_requestID 		= requestID;
		_requestType 	= CPSREQUEST_TYPE_AUTO;
		_textParams		= new HashMap<String, String[]>();
		_rawParams		= new HashMap<String, String[]>();
		_documents		= new Object();
		
		Arrays.sort(_textParamNames);
		Arrays.sort(_rawParamNames);
	}
	/**
	 * Return string representation of request type (single/cluster/auto)
	 * @param type request type (CPSREQUEST_TYPE_SINGLE/CPSREQUEST_TYPE_CLUSTER)
	 * @return String
	 */
	
	public static String get_requestTypeString(int type)
	{
		if (type == CPSREQUEST_TYPE_SINGLE)
			return "single";
		else if (type == CPSREQUEST_TYPE_CLUSTER)
			return "cluster";
		else
			return "auto";
	}
	
	/**
	* Returns the contents of the request as an XML string
	* @param docRootXpath document root xpath
	* @param docIDXpath document ID xpath
	* @param envelopeParams map of CPS envelope parameters
	* @return string
	*/
	public String getRequestXml(String docRootXpath, String[] docIDXpath, Map<String, String[]> envelopeParams) throws TransformerFactoryConfigurationError, Exception
	{
//		long timeStart = System.currentTimeMillis();
		DocumentBuilderFactory factory = _tlDocumentBuilderFactory.get();
		DocumentBuilder builder = factory.newDocumentBuilder();
		
//		System.out.println("+1+: " + ((System.currentTimeMillis() - timeStart) / (float) 1000));
		
		_requestDom = builder.newDocument();
		Element root = _requestDom.createElementNS("www.clusterpoint.com", "cps:request");
		_requestDom.appendChild(root);
		
		// envelope parameters first
		if (envelopeParams.size() > 0)
		{
			Iterator<Map.Entry<String, String[]>> it = envelopeParams.entrySet().iterator();
			while (it.hasNext())
			{
				Map.Entry<String, String[]> entry = it.next();
				for (int i = 0; i < entry.getValue().length; i++)
				{
					Element el = _requestDom.createElement(entry.getKey());
					Text elText = _requestDom.createTextNode(entry.getValue()[i]);
					el.appendChild(elText);
					root.appendChild(el);
				}
			}
		}
		
		Element content = _requestDom.createElement("cps:content");
		
		// content tag text parameters
		if (_textParams.size() > 0)
		{
			Iterator<Map.Entry<String, String[]>> it = _textParams.entrySet().iterator();
			while (it.hasNext())
			{
				Map.Entry<String, String[]> entry = it.next();
				for (int i = 0; i < entry.getValue().length; i++)
				{
					Element el = _requestDom.createElement(entry.getKey());
					Text elText = _requestDom.createTextNode(entry.getValue()[i]);
					el.appendChild(elText);
					content.appendChild(el);
				}
			}
		}
		
		// special fields: query, list, ordering
		if (_rawParams.size() > 0)
		{
			Iterator<Map.Entry<String, String[]>> it = _rawParams.entrySet().iterator();
			while (it.hasNext())
			{
				Map.Entry<String, String[]> entry = it.next();
				for (int i = 0; i < entry.getValue().length; i++)
				{
					Element el = _requestDom.createElement(entry.getKey());
					DocumentFragment elFrag = parseXml(_requestDom, entry.getValue()[i]);
					el.appendChild(elFrag);
					content.appendChild(el);
				}
			}
		}
		
		// documents, document IDs
		if (_documents instanceof Collection<?>)
		{
			@SuppressWarnings("unchecked")
			Collection<Object> doc = (Collection<Object>) _documents;
			Iterator<Object> it = doc.iterator();
			while (it.hasNext())
			{
				Object item = it.next();
				String itemXML = "";
				if (item instanceof Node)
				{
					itemXML = dumpDOMNode((Node) item);
				}
				else if (item instanceof String)
				{
					itemXML = (String)item;
				}
				else
					throw new Exception("Unknow document in Vector");
				if (itemXML.length() > 0)
				{
					DocumentFragment elFrag = parseXml(_requestDom, itemXML);
					content.appendChild(elFrag);
				}
			}
		}
		else if (_documents instanceof Map<?,?>)
		{
			@SuppressWarnings("unchecked")
			Map<String, String> docs = (Map<String, String>)_documents;

			Iterator<Entry<String, String>> it = docs.entrySet().iterator();
			while (it.hasNext())
			{
				DocumentFragment elFrag = null;
				Map.Entry<String, String> e = (Map.Entry<String, String>) it.next();
				if (e.getValue().length() > 0)
					elFrag = parseXml(_requestDom, e.getValue());
				else
					elFrag = parseXml(_requestDom, "<"+docRootXpath+"/>");
				_setDocId(_requestDom, elFrag.getFirstChild(), e.getKey(), docIDXpath, 0);		
				content.appendChild(elFrag);
			}
		}
		
		root.appendChild(content);
		
//		System.out.println("+2+: " + ((System.currentTimeMillis() - timeStart)
//				/ (float) 1000));
		
		Transformer transformer = TransformerFactory.newInstance().newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		StreamResult result = new StreamResult(new StringWriter());
		DOMSource source = new DOMSource(_requestDom);
		transformer.transform(source, result);
		
//		System.out.println("+3+: " + ((System.currentTimeMillis() - timeStart)
//				/ (float) 1000));
		
		return result.getWriter().toString();
	}
	
	/**
	* sets a request parameter
	* @param name name of the parameter
	* @param value value to set
	*/
	public void setParam(String name, String[] value) throws Exception
	{
		if (Arrays.binarySearch(_textParamNames, name) >= 0)
			_textParams.put(name, value);
		else if (Arrays.binarySearch(_rawParamNames, name) >= 0)
			_rawParams.put(name, value);
		else
			throw new Exception("Wrong param");
	}
	
	/**
	* sets a request parameter
	* @param name name of the parameter
	* @param value value to set
	*/
	public void setParam(String name, String value) throws Exception
	{
		String[] v = new String[1];		
		v[0] = value;
		setParam(name, v);
	}
	
	/**
	* gets the request id
	* @return string
	*/
	public String getRequestID() {
		return _requestID;
	}
	
	/**
	* sets the request id
	* @param requestID
	*/
	public void setRequestID(String requestID) {
		this._requestID = requestID;
	}
	
	/**
	* gets the request type
	* @return string
	*/
	public int getRequestType() {
		return _requestType;
	}
	
	/**
	* sets the request type
	* @param requestType
	*/
	public void setRequestType(int requestType) {
		this._requestType = requestType;
	}
	
	/**
	* returns the command name
	* @return string
	*/
	public String getCommand() {
		return _command;
	}
	
	/**
	 * Dumps DOM node into string
	 * @param node Node to dump
	 * @return String
	 */
	private String dumpDOMNode(Node node) throws TransformerException
	{
		TransformerFactory transFactory = TransformerFactory.newInstance();
		Transformer transformer = transFactory.newTransformer();
		StringWriter buffer = new StringWriter();
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
		transformer.transform(new DOMSource(node),
		      new StreamResult(buffer));
		return buffer.toString();
	}
	 
	/**
	 *  Parses a string containing XML and returns a DocumentFragment
	 *  containing the nodes of the parsed XML.
	 * @param doc
	 * @param fragment
	 * @return DocumentFragment
	 */
    private static DocumentFragment parseXml(Document doc,String fragment) {
        // Wrap the fragment in an arbitrary element
        fragment = "<fragment>" + fragment + "</fragment>";
        try {
            // Create a DOM builder and parse the fragment
            DocumentBuilderFactory factory = _tlDocumentBuilderFactory.get();
            Document d = factory.newDocumentBuilder().parse(
                new InputSource(new StringReader(fragment)));
    
            // Import the nodes of the new document into doc so that they
            // will be compatible with doc
            Node node = doc.importNode(d.getDocumentElement(),true);
    
            // Create the document fragment node to hold the new nodes
            DocumentFragment docfrag = doc.createDocumentFragment();
    
            // Move the nodes into the fragment
            while (node.hasChildNodes()) {
                docfrag.appendChild(node.removeChild(node.getFirstChild()));
            }
    
            // Return the fragment
            return docfrag;
        } catch (SAXException e) {
            // A parsing error occurred; the xml input is not valid
        } catch (ParserConfigurationException e) {
        } catch (IOException e) {
        }
        return null;
    }

    /**
	 * sets the docid inside the document
	 * @param subdoc document where the id should be loaded into
     * @param parentNode
     * @param id
     * @param docIdXpath
     * @param curLevel
     * @throws Exception
     */
    private static void _setDocId(Document subdoc, Node parentNode, String id, String[] docIdXpath, int curLevel) throws Exception 
    {
    	Node curChild = null;
		if (parentNode.getNodeName().equals(docIdXpath[curLevel]) || (docIdXpath[curLevel].contains("@") && parentNode.getNodeName().equals(docIdXpath[curLevel].split("@")[0]))) 
		{
			if (curLevel == docIdXpath.length - 1) 
			{
				if (!docIdXpath[curLevel].contains("@"))
				{
					Node nextChild = null;
					// remove all sub-nodes
					curChild = parentNode.getFirstChild();
					while (curChild != null) {
						nextChild = curChild.getNextSibling();
						parentNode.removeChild(curChild);
						curChild = nextChild;
					}
					// set the ID
					Text textNode = subdoc.createTextNode(id);
					parentNode.appendChild(textNode);
				}
			} else {
				// traverse children
				boolean found = false;
				curChild = parentNode.getFirstChild();
				while (curChild != null) {
					if (curChild.getNodeName().equals(docIdXpath[curLevel + 1]) || (docIdXpath[curLevel+1].contains("@") && parentNode.getNodeName().equals(docIdXpath[curLevel+1].split("@")[0]))) {
						_setDocId(subdoc, curChild, id, docIdXpath, curLevel + 1);
						found = true;
						break;
					}
					curChild = curChild.getNextSibling();
				}
				if (!found) {
					Element newNode;
					String curel = docIdXpath[curLevel + 1];
					if (curel.contains("@"))
					{
						String pair[] = curel.split("[@]+");
						if (pair.length < 2)
						{
							newNode = subdoc.createElement(curel);
							parentNode.appendChild(newNode);
						}
						else
						{
							newNode = subdoc.createElement(pair[0]);	
							newNode.setAttribute(pair[1], id);				
							parentNode.appendChild(newNode);
						}
					}
					else
					{
						newNode = subdoc.createElement(docIdXpath[curLevel + 1]);
						parentNode.appendChild(newNode);
					}
					_setDocId(subdoc, newNode, id, docIdXpath, curLevel + 1);					
				}
			}		
		} else {
			throw new Exception("Document root xpath not matching document ID xpath");
		}
	}
    
    private String[] _textParamNames = {		
        "added_external_id",
		"added_id",
		"aggregate",
		"case_sensitive",
		"cr",
		"deleted_external_id",
		"deleted_id",
		"description",
		"docs",
		"exact-match",
		"facet",
		"fail_if_exists",
		"file",
		"finalize",
		"for",
		"force",
		"from",
		"full",
		"group",
		"group_size",
		"h",
		"id",
		"idif",
		"iterator_id",
		"len",
		"message",
		"offset",
		"path",
		"persistent",
		"position",
		"quota",
		"rate2_ordering",
		"rate_from",
		"rate_to",
		"relevance",
		"return_internal",
		"sequence_check",
		"stem-lang",
		"step_size",
		"text",
		"transaction_id",
		"type"
    };
	private String[] _rawParamNames = {
		"query",
		"list",
		"ordering",
		"shapes"
	};
}
