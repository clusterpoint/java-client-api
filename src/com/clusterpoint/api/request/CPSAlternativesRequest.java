package com.clusterpoint.api.request;

import com.clusterpoint.api.CPSRequest;


/**
* The CPSAlternativesRequest class is a wrapper for the CPSRequest class for the alternatives command
* @see com.clusterpoint.api.response.CPSAlternativesResponse
*/

public class CPSAlternativesRequest extends CPSRequest {
	/**
	* Constructs an instance of CPSAlternativesRequest
	*/
	public CPSAlternativesRequest() throws Exception {
		super("alternatives");
	}
	
	/**
	* Constructs an instance of CPSAlternativesRequest
	*
	* @param query see {@link #setQuery(String) setQuery}
	* */
	public CPSAlternativesRequest(String query) throws Exception {
		super("alternatives");
		this.setQuery(query);
	}
	
	/**
	* Constructs an instance of CPSAlternativesRequest
	*
	* @param query see {@link #setQuery(String) setQuery}
	* @param cr see {@link #setCr(double) setCr}
	* @param idif see {@link #setIdif(double) setIdif}
	* @param h see {@link #setH(double) setH}
	*/
	public CPSAlternativesRequest(String query, double cr, double idif, double h) throws Exception {
		super("alternatives");
		this.setQuery(query);
	}
	
	/**
	* Sets the search query.
	*
	* Example usage:
	* <code>r.setQuery(CPSTerm.getString("predefined_term", "/generated_fields/type/"));</code>
	* @param value The query string.
	* All <, > and & characters that aren't supposed to be XML tags, should be escaped (e.g. with {@link com.clusterpoint.api.CPSTerm CPSTerm});
	* @see com.clusterpoint.api.CPSTerm
	*/
	public void setQuery(String value) throws Exception
	{
		this.setParam("query", value);
	}
	
	/**
	* Minimum ratio between the occurrence of the alternative and the occurrence of the search term.
	* If this parameter is increased, less results are returned while performance is improved.
	* @param value cr value
	*/
	public void setCr(double value) throws Exception
	{
		this.setParam("cr", String.valueOf(value));
	}
	
	/**
	* A number that limits how much the alternative may differ from the search term,
    * the greater the idif value, the greater the allowed difference.
    * If this parameter is increased, more results are returned while performance is decreased.
	* @param value idif value
	*/
	public void setIdif(double value) throws Exception
	{
		this.setParam("idif", String.valueOf(value));
	}
	
	/**
	* A number that limits the overall estimate of the quality of the alternative,
    * the greater the cr value and the smaller the idif value, the greater the h value.
    * If this parameter is increased, less results are returned while performance is improved.
	* @param value h value
	*/
	public void setH(double value) throws Exception
	{
		this.setParam("h", String.valueOf(value));
	}

}
