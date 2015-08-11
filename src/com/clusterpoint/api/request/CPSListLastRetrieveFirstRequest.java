package com.clusterpoint.api.request;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.clusterpoint.api.CPSRequest;
import com.clusterpoint.api.CPSTerm;

/**
* The CPSListLastRetrieveFirstRequest class is a wrapper for the CPSRequest class for list-last, list-first, retrieve-last, and retrieve-first requests
* @see com.clusterpoint.api.response.CPSListLastRetrieveFirstResponse
*/
public class CPSListLastRetrieveFirstRequest extends CPSRequest {
	/**
	* Constructs an instance of the CPSListLastRetrieveFirstRequest class.
	* @param command command name
	* @param offset offset
	* @param docs max number of docs to return
	* @param list map where key is xpath and value contains listing options (yes, no, snippet or highlight)
	 * @throws Exception 
	*/
	
	public CPSListLastRetrieveFirstRequest(String command, int offset, int docs, Map<String, String> list) throws Exception {
		super(command);
		this.setParam("offset", String.valueOf(offset));
		this.setParam("docs", String.valueOf(docs));
		setList(list);
	}
	
	/**
	* Constructs an instance of the CPSListLastRetrieveFirstRequest class.
	* @param command command name
	* @param offset offset
	* @param docs max number of docs to return
	 * @throws Exception 
	*/
	
	public CPSListLastRetrieveFirstRequest(String command, int offset, int docs) throws Exception {
		super(command);
		this.setParam("offset", String.valueOf(offset));
		this.setParam("docs", String.valueOf(docs));
	}
	
	/**
	* Defines which tags of the search results should be listed in the response
	* @param list map where key is xpath and value contains listing options (yes, no, snippet or highlight)
	*/	
	public void setList(Map<String, String> list) throws Exception
	{
		String listStr = "";
		
			Iterator<Entry<String, String>> it = list.entrySet().iterator();
			while (it.hasNext())
			{
				listStr += CPSTerm.get(it.next().getValue(), it.next().getKey());
			}
		this.setParam("list", listStr);
	}
	
	/**
	 * Set limits for documents to be returned by command
	 * @param docs
	 * @throws Exception
	 */
	public void setDocs(int docs) throws Exception
	{
		this.setParam("docs", String.valueOf(docs));
	}
	
	/**
	 * Set offset from beginning of results from which documents will be returned
	 * @param offset
	 * @throws Exception
	 */
	public void setOffset(int offset) throws Exception 
	{
		this.setParam("offset", String.valueOf(offset));
	}

}
