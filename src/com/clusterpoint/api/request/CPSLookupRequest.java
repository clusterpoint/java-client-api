package com.clusterpoint.api.request;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.clusterpoint.api.CPSRequest;
import com.clusterpoint.api.CPSTerm;
/**
* The CPSLookupRequest class is a wrapper for the CPSRequest class
* @see com.clusterpoint.api.response.CPSLookupResponse
*/
public class CPSLookupRequest extends CPSRequest {
	/**
	* Constructs an instance of the CPSLookupRequest class.
	*/
	public CPSLookupRequest() throws Exception {
		super("lookup");
	}
	
	/**
	* Constructs an instance of the CPSLookupRequest class.
	* @param id document id to be looked up
	*/
	public CPSLookupRequest(String id) throws Exception {
		super("lookup");
		this.setID(id);
	}
	
	/**
	* Constructs an instance of the CPSLookupRequest class.
	* @param id array of document ids to be looked up
	*/
	public CPSLookupRequest(String[] id) throws Exception {
		super("lookup");
		this.setID(id);
	}
	
	/**
	* Constructs an instance of the CPSLookupRequest class.
	* @param id document id to be looked up
	* @param list map where key is xpath and value contains listing options (yes, no, snippet or highlight)
	*/
	public CPSLookupRequest(String id, Map<String, String> list) throws Exception {
		super("lookup");
		this.setID(id);
		this.setList(list);
	}
	/**
	* Constructs an instance of the CPSLookupRequest class.
	* @param id array of document ids to be looked up
	* @param list map where key is xpath and value contains listing options (yes, no, snippet or highlight)
	*/
	public CPSLookupRequest(String[] id, Map<String, String> list) throws Exception {
		super("lookup");
		this.setID(id);
		this.setList(list);
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
			Entry<String, String> entry = it.next();
			listStr += CPSTerm.get(entry.getValue(), entry.getKey());
		}
		this.setParam("list", listStr);
	}
	
	/**
	 * Set document ids to be looked up
	 * @param id array of document ids
	 */
	public void setID(String[] id)
	{
		HashMap<String, String> map = new HashMap<String, String>();
		for (int i = 0; i < id.length; i++)
			map.put(id[i], "");
		this._documents = map;
	}
	
	/**
	 * Set document id to be looked up
	 * @param id document id
	 */
	public void setID(String id)
	{
		HashMap<String, String> map = new HashMap<String, String>();
		map.put(id, "");
		this._documents = map;
	}
}
