package com.clusterpoint.api.request;

import java.util.Iterator;
import java.util.Map;

import com.clusterpoint.api.CPSRequest;
import com.clusterpoint.api.CPSTerm;

/**
* The CPSSearchRequest class is a wrapper for the CPSRequest class
* @see com.clusterpoint.api.response.CPSSearchResponse
*/
public class CPSSearchRequest extends CPSRequest {
	/**
	* Constructs an instance of the CPSSearchRequest class.
	*/
	public CPSSearchRequest() throws Exception {
		super("search");
	}
	
	/**
	* Constructs an instance of the CPSSearchRequest class.
	* @param query The query string. see {@link #setQuery(String) setQuery} for more info.
	* @param offset Defines the number of documents to skip before including them in the results
	* @param docs Maximum document count to retrieve
	*/
	public CPSSearchRequest(String query, int offset, int docs) throws Exception {
		super("search");
		
		if (query.length() > 0)
			this.setQuery(query);
		if (offset >= 0)
			this.setOffset(offset);
		if (docs >= 0)
			this.setDocs(docs);
	}
	
	/**
	* Constructs an instance of the CPSSearchRequest class.
	* @param query The query string. see {@link #setQuery(String) setQuery} for more info.
	* @param offset Defines the number of documents to skip before including them in the results
	* @param docs Maximum document count to retrieve
	* @param list map where key is xpath and value contains listing options (yes, no, snippet or highlight)
	*/
	public CPSSearchRequest(String query, int offset, int docs, Map<String, String> list) throws Exception {
		super("search");
		
		if (query.length() > 0)
			this.setQuery(query);
		if (offset >= 0)
			this.setOffset(offset);
		if (docs >= 0)
			this.setDocs(docs);
		if (list != null && list.size() > 0)
			this.setList(list);
	}
	
	/**
	* Sets the search query.
	*
	* Example usage:
	* <code>r.setQuery(CPSTerm.getString("predefined_term", "/generated_fields/type/"));</code>
	* @param query The query string.
	* All <, > and & characters that aren't supposed to be XML tags, should be escaped (e.g. with {@link com.clusterpoint.api.CPSTerm#CPSTerm() CPSTerm});
	* @see com.clusterpoint.api.CPSTerm
	*/
	public void setQuery(String query) throws Exception
	{
		this.setParam("query", query);
	}
	
	/**
	* Sets the maximum number of documents to be returned
	* @param value maximum number of documents
	*/
	public void setDocs(int value) throws Exception
	{
		this.setParam("docs", String.valueOf(value));
	}
	
	/**
	* Sets the number of documents to skip in the results
	* @param value number of results to skip
	*/
	public void setOffset(int value) throws Exception
	{
		this.setParam("offset", String.valueOf(value));
	}
	
	/**
	* Sets the paths for facets
	* @param value path of facet
	*/
	public void setFacet(String value) throws Exception
	{
		this.setParam("facet", value);
	}
	
	/**
	* Sets the paths for facets
	* @param value array of paths of facets
	*/
	public void setFacet(String[] value) throws Exception
	{
		this.setParam("facet", value);
	}
	
	/**
	* Sets the stemming language
	* @param value 2-letter language ID
	*/
	public void setStemLang(String value) throws Exception
	{
		this.setParam("stem-lang", value);
	}
	
	/**
	* Sets the exact match option
	* @param value Exact match option : text, binary or all
	*/
	public void setExactMatch(String value) throws Exception
	{
		this.setParam("exact-match", value);
	}
	
	/**
	* Sets grouping options
	* @param tag name of the grouping tag
	* @param group_size maximum number of documents to return from each group
	*/
	public void setGroup(String tag, int group_size) throws Exception
	{
		this.setParam("group", tag);
		this.setParam("group_size", String.valueOf(group_size));
	}
	
	/**
	* Defines which tags of the search results should be listed in the response
	* @param list map where key is xpath and value contains listing options (yes, no, snippet or highlight)
	*/
	public void setList(Map<String, String> list) throws Exception
	{
		Iterator<Map.Entry<String, String>> it = list.entrySet().iterator();
		StringBuffer buff = new StringBuffer();
		while (it.hasNext())
		{
			Map.Entry<String, String> entry = it.next();
			buff.append(CPSTerm.get(entry.getValue(), entry.getKey()));
		}
		this.setParam("list", buff.toString());
	}
	
	/**
	* Defines the order in which results should be returned.
	* @param order sorting string. Could be conveniently generated with ordering macros ??????
	*/
	public void setOrdering(String order) throws Exception
	{
		this.setParam("ordering", order);	
	}
	/**
	* Defines the order in which results should be returned.
	* @param order array of sorting strings. Could be conveniently generated with ordering macros ??????
	*/
	public void setOrdering(String[] order) throws Exception
	{
		StringBuffer buff = new StringBuffer();
		for (int i = 0; i < order.length; i++)
			buff.append(order[i]);
		this.setParam("ordering", buff.toString());
	}
}
