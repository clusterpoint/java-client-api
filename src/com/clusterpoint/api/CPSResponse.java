package com.clusterpoint.api;

import java.io.IOException;
import java.io.StringReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Vector;

import javax.xml.bind.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;

import org.w3c.dom.Node;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.NamespaceSupport;
import org.xml.sax.helpers.XMLFilterImpl;

import com.clusterpoint.api.CPSException;
/**
* The Response class contains a response received from CPS storage
*/
public class CPSResponse extends XMLFilterImpl {
	protected String _storageName;
	protected String _command;
	protected float _seconds;
	protected Node _errors;
	protected Vector<Object> _resp_objects;
	
	private int _depth;
	private Locator _locator;
    private org.xml.sax.ContentHandler _handler;
    private NamespaceSupport _namespaces = new NamespaceSupport();
    private JAXBContext _jaxbCtx;
    private StringBuffer _str_buffer;
    private DOMResult domresult;
    private CPSConnection _conn;
    private boolean _is_error;
	private Integer _err_code;
	private String _err_text;
	private String _err_msg;
	private String _err_level;
	private String _err_src;
	private String _err_docid;
	private CPSException _exception;
	
	private static final ThreadLocal<SAXParserFactory> _tlSAXParserFactory = new ThreadLocal<SAXParserFactory>() {
		@Override protected SAXParserFactory initialValue() {
			return SAXParserFactory.newInstance();
	    }	
	};
	
	private static final ThreadLocal<TransformerFactory> _tlTransformerFactory = new ThreadLocal<TransformerFactory>() {
		@Override protected TransformerFactory initialValue() {
			return TransformerFactory.newInstance();
	    }	
	};
	
	
	private static final ThreadLocal<HashMap<Class<?> ,JAXBContext>> _tlJAXBContext = new ThreadLocal<HashMap<Class<?> ,JAXBContext>>() {
		@Override protected HashMap<Class<?> ,JAXBContext> initialValue() {
			try {
				HashMap<Class<?> ,JAXBContext> jaxbMap = new HashMap<Class<?> ,JAXBContext>();
				jaxbMap.put(com.clusterpoint.api.response.alternatives.CPSAlternativesAlternatives.class, 
						JAXBContext.newInstance(com.clusterpoint.api.response.alternatives.CPSAlternativesAlternatives.class));
				jaxbMap.put(com.clusterpoint.api.response.begintransaction.CPSBeginTransactionContent.class, 
						JAXBContext.newInstance(com.clusterpoint.api.response.begintransaction.CPSBeginTransactionContent.class));
				jaxbMap.put(com.clusterpoint.api.response.listfacets.CPSListFacetsContent.class, 
						JAXBContext.newInstance(com.clusterpoint.api.response.listfacets.CPSListFacetsContent.class));
				jaxbMap.put(com.clusterpoint.api.response.listlastretrievefirst.CPSListLastRetrieveFirstContent.class, 
						JAXBContext.newInstance(com.clusterpoint.api.response.listlastretrievefirst.CPSListLastRetrieveFirstContent.class));
				jaxbMap.put(com.clusterpoint.api.response.listpaths.CPSListPathsContent.class, 
						JAXBContext.newInstance(com.clusterpoint.api.response.listpaths.CPSListPathsContent.class));				
				jaxbMap.put(com.clusterpoint.api.response.listwords.CPSListWordsContent.class, 
						JAXBContext.newInstance(com.clusterpoint.api.response.listwords.CPSListWordsContent.class));
				jaxbMap.put(com.clusterpoint.api.response.lookup.CPSLookupContent.class, 
						JAXBContext.newInstance(com.clusterpoint.api.response.lookup.CPSLookupContent.class));
				jaxbMap.put(com.clusterpoint.api.response.lookup.CPSLookupContent.class, 
						JAXBContext.newInstance(com.clusterpoint.api.response.lookup.CPSLookupContent.class));	
				jaxbMap.put(com.clusterpoint.api.response.modify.CPSModifyContent.class, 
						JAXBContext.newInstance(com.clusterpoint.api.response.modify.CPSModifyContent.class));
				jaxbMap.put(com.clusterpoint.api.response.searchdelete.CPSSearchDeleteContent.class, 
						JAXBContext.newInstance(com.clusterpoint.api.response.searchdelete.CPSSearchDeleteContent.class));				
				jaxbMap.put(com.clusterpoint.api.response.status.CPSStatusContent.class, 
						JAXBContext.newInstance(com.clusterpoint.api.response.status.CPSStatusContent.class));							
				jaxbMap.put(com.clusterpoint.api.response.search.CPSSearchContent.class, 
						JAXBContext.newInstance(com.clusterpoint.api.response.search.CPSSearchContent.class));				
				return jaxbMap;								
				/*return JAXBContext.newInstance(com.clusterpoint.api.response.alternatives.CPSAlternativesAlternatives.class,
						com.clusterpoint.api.response.begintransaction.CPSBeginTransactionContent.class,
						com.clusterpoint.api.response.listfacets.CPSListFacetsContent.class,
						com.clusterpoint.api.response.listlastretrievefirst.CPSListLastRetrieveFirstContent.class,
						com.clusterpoint.api.response.listpaths.CPSListPathsContent.class,
						com.clusterpoint.api.response.listwords.CPSListWordsContent.class,
						com.clusterpoint.api.response.lookup.CPSLookupContent.class,
						com.clusterpoint.api.response.modify.CPSModifyContent.class,
						com.clusterpoint.api.response.search.CPSSearchContent.class,
						com.clusterpoint.api.response.searchdelete.CPSSearchDeleteContent.class,
						com.clusterpoint.api.response.status.CPSStatusContent.class
						);*/
						
			} catch (JAXBException e) {
				e.printStackTrace();
				return null;
			}					
	    }
	};

	
	
	
	
	
    /**
	* Constructs an instance of the Response class.
	* @param connection CPS connection object
	* @param unmarshall_class Class for unmarshalling content of response XML
	* @param respXML The raw XML response
     * @throws JAXBException 
     * @throws ParserConfigurationException 
     * @throws SAXException 
     * @throws IOException 
     * @throws CPSException 
	*/
	public CPSResponse(CPSConnection connection, Class<?> unmarshall_class, String respXML) throws JAXBException, SAXException, ParserConfigurationException, IOException, CPSException
	{
		_resp_objects = new Vector<Object>();
		_str_buffer = new StringBuffer();
		_depth = 0;
		_conn = connection;
		_jaxbCtx = null;
		_is_error = false;
		_err_code = 0;
		_err_text = _err_msg = _err_level = _err_src = _err_docid = "";
		_exception = new CPSException();
		_jaxbCtx = _tlJAXBContext.get().get(unmarshall_class);
		//_jaxbCtx = JAXBContext.newInstance(unmarshall_class);
        SAXParserFactory factory = _tlSAXParserFactory.get();
        factory.setNamespaceAware(true);
        XMLReader reader = factory.newSAXParser().getXMLReader();
        reader.setContentHandler(this);
        reader.parse(new InputSource(new StringReader(respXML)));
        if (_exception.errorCount() > 0)
        	throw _exception;
	}
	
	/**
	* Returns the time that it took to process the request in the CPS engine
	* @return float
	*/
	public float getSeconds()
	{
		return _seconds;
	}
	
	/**
	* Returns executed command name
	* @return string
	*/
	public String getCommand()
	{
		return _command;
	}
	
	/**
	* Returns storage name for what request was executed
	* @return string
	*/
	public String getStorage()
	{
		return _storageName;
	}
	
	/**
	* Returns CPSConnection object which was used to execute request
	* @return CPSConnection
	*/
	public CPSConnection getConnection() {
		return _conn;
	}
	
	/**
	 * Overrides XMLFilterImpl event startElement
	 * @see org.xml.sax.helpers.XMLFilterImpl#startElement(String, String, String, Attributes)
	 */
    public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException 
    {
    	if (namespaceURI.equals("www.clusterpoint.com"))
    	{
	    	if ((localName.equals("storage") || localName.equals("command") || localName.equals("seconds")) && namespaceURI.equals("www.clusterpoint.com"))
	    		_str_buffer.setLength(0);
	    	if (localName.equals("error"))
	    	{
	    		_is_error = true;
	        	_str_buffer.setLength(0);
	    	}
    	}
    	
    	if (_depth != 0) {
			// we are in the middle of forwarding events.
			// continue to do so.
			_depth++;
			super.startElement(namespaceURI, localName, qName, atts);
			return;
		}

    	//trigger content parsing
		if (localName.equals("content") && namespaceURI.equals("www.clusterpoint.com"))
		{
			if (_jaxbCtx == null)
			{
				TransformerFactory factory = _tlTransformerFactory.get();
				if(factory.getFeature(SAXTransformerFactory.FEATURE))
				{
				   SAXTransformerFactory saxFactory = (SAXTransformerFactory)factory;
				   TransformerHandler transformerHandler = null;
					try {
						transformerHandler = saxFactory.newTransformerHandler();
					} catch (TransformerConfigurationException e1) {
						e1.printStackTrace();
					}
				   domresult = new DOMResult();
				   transformerHandler.setResult(domresult);
				   _handler = transformerHandler;
				}
				else
				   throw new UnsupportedOperationException("compatibility error");
			}
			else
			{
				// start a new unmarshaller
				Unmarshaller unmarshaller;
				try {
					unmarshaller = _jaxbCtx.createUnmarshaller();
				} catch (JAXBException je) {
					// there's no way to recover from this error.
					// we will abort the processing.
					throw new SAXException(je);
				}	
				_handler = unmarshaller.getUnmarshallerHandler();
			}
	
			// set it as the content handler so that it will receive
			// SAX events from now on.
			setContentHandler(_handler);			

			// fire SAX events to emulate the start of a new document.
			_handler.startDocument();
			_handler.setDocumentLocator(_locator);

			// keep in track with namespace prefix declarations
			Enumeration<?> e = _namespaces.getPrefixes();
			while (e.hasMoreElements()) {
				String prefix = (String)e.nextElement();
				String uri = _namespaces.getURI(prefix);
				_handler.startPrefixMapping(prefix,uri);
			}

			String defaultURI = _namespaces.getURI("");
			if (defaultURI != null)
				_handler.startPrefixMapping("", defaultURI);
		
			super.startElement(namespaceURI, localName, qName, atts);
		
			// count the depth of elements and we will know when to stop.
			_depth = 1;   	
		}
    }
	
    /**
	 * Overrides XMLFilterImpl event endElement
	 * @see org.xml.sax.helpers.XMLFilterImpl#endElement(String, String, String)
	 */
    public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
    	// forward this event
		super.endElement(namespaceURI, localName, qName);
		
		if (_is_error)
    	{
	    	if (localName.equals("code"))
	    		_err_code = Integer.valueOf(_str_buffer.toString().trim());
	    	if (localName.equals("text"))
	    		_err_text = _str_buffer.toString().trim();
	    	if (localName.equals("message"))
	    		_err_msg = _str_buffer.toString().trim();
	    	if (localName.equals("level"))
	    		_err_level = _str_buffer.toString().trim();
	    	if (localName.equals("source"))
	    		_err_src = _str_buffer.toString().trim();
	    	if (localName.equals("document_id"))
	    		_err_docid = _str_buffer.toString().trim();
	    	_str_buffer.setLength(0);
    	}
		
		if (namespaceURI.equals("www.clusterpoint.com"))
		{
	    	if (localName.equals("command"))
	    		_command = _str_buffer.toString().trim();
	    	else if (localName.equals("storage"))
	    		_storageName = _str_buffer.toString().trim();
	    	else if (localName.equals("seconds"))
	    		_seconds = Float.valueOf(_str_buffer.toString().trim());
	    	
	    	if (localName.equals("error"))
	    	{
	    		_is_error = false;
	    		if (_err_code > 0)
	    			_exception.addError(_err_code, _err_text, _err_msg, _err_level, _err_src, _err_docid);
	    		_err_code = 0;
	    		_err_text = _err_msg = _err_level = _err_src = _err_docid = "";
	    	}
		}
    	
		if (_depth != 0) {
			_depth--;

			if (_depth == 0) {
				// just finished sending one chunk.

				// emulate the end of a document.
				Enumeration<?> e = _namespaces.getPrefixes();
				while (e.hasMoreElements()) {
					String prefix = (String)e.nextElement();
					_handler.endPrefixMapping(prefix);
				}

				String defaultURI = _namespaces.getURI("");
				if (defaultURI != null)
					_handler.endPrefixMapping("");
				_handler.endDocument();

				// stop forwarding events by setting a dummy handler.
				// XMLFilter doesn't accept null, so we have to give it something,
				// hence a DefaultHandler, which does nothing.
				setContentHandler(new DefaultHandler());

				// then retrieve the fully unmarshalled object
				try {
					Object result = null;
					if (_handler instanceof UnmarshallerHandler)
						result = ((UnmarshallerHandler) _handler).getResult();
					else if (_handler instanceof TransformerHandler)
						result = domresult.getNode();

					// add result to response object
					_resp_objects.add(result);
				} catch (JAXBException je) {
					// error was found during the unmarshalling.
					// you can either abort the processing by throwing a SAXException,
					// or you can continue processing by returning from this method.
					System.err.println("unable to process the <content> at line " +
							_locator.getLineNumber());
					return;
				}

				_handler = null;
			}
		}
	}
    /**
	 * Overrides XMLFilterImpl event characters
	 * @see org.xml.sax.helpers.XMLFilterImpl#characters(char[], int, int)
	 */
    public void characters(char ch[], int start, int length)
    {
    	try {
			super.characters(ch, start, length);
		} catch (SAXException e) {
			e.printStackTrace();
		}
    	_str_buffer.append(ch, start, length );
    }
    
    public void setDocumentLocator(Locator locator) {
		super.setDocumentLocator(locator);
		this._locator = locator;
	}

	public void startPrefixMapping(String prefix, String uri) throws SAXException {
		_namespaces.pushContext();
		_namespaces.declarePrefix(prefix,uri);

		super.startPrefixMapping(prefix, uri);
	}

	public void endPrefixMapping(String prefix) throws SAXException {
		_namespaces.popContext();

		super.endPrefixMapping(prefix);
	}
}
