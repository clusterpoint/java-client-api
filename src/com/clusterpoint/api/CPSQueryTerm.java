package com.clusterpoint.api;

public class CPSQueryTerm {
	/**
	* Escapes <, > and & characters, as well as @"{}()=$~+ (search query operators) in the given term for inclusion into the search query.
	* Also wraps the term in XML tags if xpath is specified.
	* @param term the term to be escaped (e.g. a search query term)
	* @param xpath an optional xpath, to be specified if the search term is to be searched under a specific xpath
	* @param allowed_symbols a string containing operator symbols that the user is allowed to use (e.g. ")
	* @see com.clusterpoint.api.CPSTerm
	*/
	public static String get(String term, String xpath, String allowed_symbols)
	{
		String newTerm = "";
		for (int x = 0; x < term.length(); ++x)
		{
			char c = term.charAt(x);
			switch (c) {
				case '@':
				case '$':
				case '"':
				case '=':
				case '>':
				case '<':
				case ')':
				case '(':
				case '{':
				case '}':
				case '~':
				case '+':
				{
					if (allowed_symbols.indexOf(c) == -1);
					{
						newTerm += "\\";
					}
				}
				default:
					newTerm += c;
			}
		}
		return CPSTerm.get(newTerm, xpath);
	}
}
