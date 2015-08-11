package com.clusterpoint.api.response;

import java.util.Iterator;
import java.util.List;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.clusterpoint.api.CPSConnection;
import com.clusterpoint.api.CPSResponse;
import com.clusterpoint.api.response.modify.CPSModifyContent;

/**
* The CPSModifyResponse class is a wrapper for the CPSResponse class for insert, update, delete, replace and partial-replace commands
* @see com.clusterpoint.api.request.CPSInsertRequest
* @see com.clusterpoint.api.request.CPSUpdateRequest
* @see com.clusterpoint.api.request.CPSDeleteRequest
* @see com.clusterpoint.api.request.CPSReplaceRequest
* @see com.clusterpoint.api.request.CPSPartialReplaceRequest
*/
public class CPSModifyResponse extends CPSResponse {

	public CPSModifyResponse(CPSConnection conn, String respXML) throws Exception {
		super(conn, com.clusterpoint.api.response.modify.CPSModifyContent.class, respXML);
	}
	
	/**
	* Returns an array of IDs of documents that have been successfully modified
	* @return String[]
	*/
	public String[] getModifiedIds() throws XPathExpressionException
	{
		String[] idxpath_array = this.getConnection().getDocumentIdXpath();
		String idxpath = "/";
		int id_idx = 0;
		
		for (int i = 0; i < idxpath_array.length; i++)
			idxpath += "/" + idxpath_array[i];
		
		List<Element> els = this.getReply().getAny();
		
		Iterator<Element> it = els.iterator();
		String[] ret = new String[els.size()];
		XPathFactory factory = XPathFactory.newInstance();
		XPath xpath = factory.newXPath();
		XPathExpression expr = xpath.compile(idxpath + "/text()");
		
		while (it.hasNext())
		{
			NodeList result = (NodeList) expr.evaluate(it.next(), XPathConstants.NODESET);	
			for (int i = 0; i < result.getLength(); i++) 
			    ret[id_idx++] = result.item(i).getNodeValue();
		}
		
		return ret;
	}
	
	private CPSModifyContent getReply()
	{
		return (CPSModifyContent)_resp_objects.firstElement();
	}
}
