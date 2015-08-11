package com.clusterpoint.api.response;

import java.util.List;

import com.clusterpoint.api.CPSConnection;
import com.clusterpoint.api.CPSResponse;
import com.clusterpoint.api.response.listwords.CPSListWordsContent;

/**
* The CPSListWordsResponse class is a wrapper for the CPSResponse class for the list-words command
* @see com.clusterpoint.api.request.CPSListWordsRequest
*/
public class CPSListWordsResponse extends CPSResponse {

	public CPSListWordsResponse(CPSConnection conn, String respXML)
			throws Exception {
		super(conn, com.clusterpoint.api.response.listwords.CPSListWordsContent.class, respXML);
	}
	
	/**
	* Returns words matching the given wildcard
	* @return List<CPSListWordsContent.List>
	*/
	public List<CPSListWordsContent.List> getWords()
	{
		return ((CPSListWordsContent)_resp_objects.firstElement()).getList();
	}

}
