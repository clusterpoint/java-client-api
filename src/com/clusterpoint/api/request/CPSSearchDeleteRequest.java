package com.clusterpoint.api.request;

import com.clusterpoint.api.CPSRequest;

/**
* The CPSSearchDeleteRequest class is a wrapper for the CPSRequest class for the search-delete command
* @see com.clusterpoint.api.response.CPSSearchDeleteResponse
*/
public class CPSSearchDeleteRequest extends CPSRequest {
	/**
	* Constructs an instance of the CPSSearchDeleteRequest class.
	*/
	public CPSSearchDeleteRequest()  {
		super("search-delete");
	}
	
	/**
	* Constructs an instance of the CPSSearchDeleteRequest class.
	* @param query The query string. see {@link #setQuery(String) setQuery } for more info.
	*/
	public CPSSearchDeleteRequest(String query) throws Exception {
		super("search-delete");
		this.setQuery(query);
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
	public void setQuery(String query) throws Exception	{
		this.setParam("query", query);
	}
	
	/**
	* Sets the stemming language
	* @param lang 2-letter language ID
	*/
	public void setStemLang(String lang) throws Exception {
		this.setParam("stem-lang", lang);
	}
	
	/**
	* Sets the exact match option
	* @param value Exact match option : text, binary or all
	*/
	public void setExactMatch(String value) throws Exception {
		this.setParam("exact-match", value);
	}

}
