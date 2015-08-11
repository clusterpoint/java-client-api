package com.clusterpoint.api;


public class CPSTerm {
	/**
	* Escapes <, > and & characters in the given term for inclusion into XML (like the search query). Also wraps the term in XML tags if xpath is specified.
	* Note that this function doesn't escape the @, $, " and other symbols that are meaningful in a search query. If You want to escape input that comes directly
	* from the user and that isn't supposed to contain any search operators at all, it's probably better to use {@link com.clusterpoint.api.CPSQueryTerm}
	* @param term the term to be escaped (e.g. a search query term)
	* @param xpath an optional xpath, to be specified if the search term is to be searched under a specific xpath
	* @see com.clusterpoint.api.CPSQueryTerm
	*/
	public static String get(String term, String xpath)
	{
		StringBuffer prefix = new StringBuffer(" ");
		StringBuffer suffix = new StringBuffer(" ");
		if (xpath.length() > 0)
		{
			String[] tags = xpath.split("/");
			for (int i = 0; i < tags.length; i++)
			{
				if (tags[i].length() > 0)
				{
					prefix.append("<" + tags[i] + ">");
					suffix.insert(0, "</" + tags[i] + ">");
				}
			}
		}
		return prefix.toString() + escape(term) + suffix.toString();
	}
	
	private static String escape(String s)
	{
		return s.replaceAll("&", "&amp;").replaceAll(">", "&gt;").replaceAll("<", "&lt;").replaceAll("\"", "&quot;").replaceAll("'", "&apos;");
	}
}
