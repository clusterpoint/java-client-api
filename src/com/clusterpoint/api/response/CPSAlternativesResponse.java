package com.clusterpoint.api.response;

import java.util.List;

import com.clusterpoint.api.CPSConnection;
import com.clusterpoint.api.CPSResponse;
import com.clusterpoint.api.response.alternatives.CPSAlternativesAlternatives;
import com.clusterpoint.api.response.alternatives.CPSAlternativesContent;

/**
* The CPSAlternativesResponse class is a wrapper for the CPSResponse class for the alternatives command
* @see com.clusterpoint.api.request.CPSAlternativesRequest
*/
public class CPSAlternativesResponse extends CPSResponse {
	private CPSAlternativesContent _content;

	public CPSAlternativesResponse(CPSConnection conn, String respXML)
			throws Exception {
		super(conn, com.clusterpoint.api.response.alternatives.CPSAlternativesContent.class, respXML);
		_content = (CPSAlternativesContent) _resp_objects.firstElement();
	}
	
	/**
	* Gets the spelling alternatives to the specified query terms
	*
	* Returns an List of CPSAlternativesAlternatives objects for each query term
	* @return List<CPSAlternativesAlternatives>
	* @see CPSAlternativesAlternatives
	*/
	public List<CPSAlternativesAlternatives> getAlternatives()
	{
		return _content.getAlternativesList().getAlternatives();
	}

}
