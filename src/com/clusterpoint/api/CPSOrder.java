package com.clusterpoint.api;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringEscapeUtils;

/**
 * Defines all possible ordering rules that could be applied when doing search.
 *
 */
public class CPSOrder {
	/**
	* Returns an ordering string for sorting by distance from a latitude/longitude coordinate pair
	* @see com.clusterpoint.api.request.CPSSearchRequest#setOrdering(String)
	* @see com.clusterpoint.api.request.CPSSearchRequest#setOrdering(String[])
	* @param coords array of maps where key is xpath and value is centerpoint coordinates as values. Should contain exactly two elements - latitude first and longitude second.
	* @param ascending  parameter to specify ascending/descending order.
	*/
	public static String CPSLatLonDistanceOrdering(Map<String, String>[] coords, boolean ascending)
	{
		return CPSGenericDistanceOrdering("latlong", coords, ascending);
	}
	
	/**
	* Returns an ordering string for sorting by distance from specified coordinates on a geometric plane
	* @see com.clusterpoint.api.request.CPSSearchRequest#setOrdering(String)
	* @see com.clusterpoint.api.request.CPSSearchRequest#setOrdering(String[])
	* @param coords array of maps where key is xpath and value is centerpoint coordinates as values.
	* @param ascending  parameter to specify ascending/descending order.
	*/
	public static String CPSPlaneDistanceOrdering(Map<String, String>[] coords, boolean ascending)
	{
		return CPSGenericDistanceOrdering("plane", coords, ascending);
	}
	
	/**
	* Returns an ordering string for sorting by a numeric field
	* @see com.clusterpoint.api.request.CPSSearchRequest#setOrdering(String)
	* @see com.clusterpoint.api.request.CPSSearchRequest#setOrdering(String[])
	* @param tag the xpath of the tag by which You wish to perform sorting
	* @param ascending  parameter to specify ascending/descending order.
	*/
	public static String CPSNumericOrdering(String tag, boolean ascending)
	{
		return "<numeric>"+ CPSTerm.get(ascending ? "ascending" : "descending", tag) +"</numeric>";
	}
	
	/**
	* Returns an ordering string for sorting by relevance in descending order (more relevant first)
	* @see com.clusterpoint.api.request.CPSSearchRequest#setOrdering(String[])
	* @see com.clusterpoint.api.request.CPSSearchRequest#setOrdering(String)
	*/
	public static String CPSRelevanceOrdering()
	{
		return CPSRelevanceOrdering(false);
	}
	
	/**
	* Returns an ordering string for sorting by relevance
	* @see com.clusterpoint.api.request.CPSSearchRequest#setOrdering(String[])
	* @see com.clusterpoint.api.request.CPSSearchRequest#setOrdering(String)
	* @param ascending parameter to specify ascending/descending order.
	*/
	public static String CPSRelevanceOrdering(boolean ascending)
	{
		return "<relevance>"+ (ascending ? "ascending" : "descending") +"</numeric>";
	}
	
	private static String CPSGenericDistanceOrdering(String type, Map<String, String>[] coords, boolean ascending)
	{
		String res = "<distance type=\"" + StringEscapeUtils.escapeXml(type) + "\" order=\"" + (ascending ? "ascending" : "descending") + "\">";
		for (int i = 0; i < coords.length; i++)
		{
			Iterator<Entry<String, String>> it = coords[i].entrySet().iterator();
			while (it.hasNext())
			{
				res += CPSTerm.get(it.next().getValue(), it.next().getKey());
			}
		}
		res += "</distance>";
		return res;
	}
}
