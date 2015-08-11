package com.clusterpoint.api;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
* Exception class for CPS API
*/
public class CPSException extends Exception {
	private static final long serialVersionUID = 5267913562278115121L;
	private List<CPSError> errors;
	public CPSException()
	{
		super();
		errors = new ArrayList<CPSException.CPSError>();
	}
	
	public CPSException(String text)
	{
		super(text);
		errors = new ArrayList<CPSException.CPSError>();
	}
	
	@Override
	public String getMessage() {
		String errmsg = "";
		Iterator<CPSError> it = errors.iterator();
		while (it.hasNext())
		{
			CPSError e = it.next();
			errmsg += "[" + String.valueOf(e.getCode()) + "]:" + e.getText() + ";";
		}
		return errmsg;
	}
	
	public int errorCount()
	{
		return errors.size();
	}
	
	public void addError(int code, String text, String level, String source)
	{
		errors.add(new CPSError(code, text, "", level, source, ""));
	}
	
	public void addError(int code, String text, String msg, String level, String source)
	{
		errors.add(new CPSError(code, text, msg, level, source, ""));
	}
	
	public void addError(int code, String text, String msg, String level, String source, String doc_id)
	{
		errors.add(new CPSError(code, text, msg, level, source, doc_id));
	}
	
	private class CPSError {
		private int _code;
		private String _text, _msg, _level, _source, _doc_id;
		public CPSError(int code, String text, String msg, String level, String source, String doc_id)
		{
			this._code 		= code;
			this._text 		= text;
			this._msg 		= msg;
			this._level 	= level;
			this._source 	= source;
			this._doc_id 	= doc_id;
		}
		public int getCode() {
			return _code;
		}
		public String getText() {
			return _text;
		}
		@SuppressWarnings("unused")
		public String getMsg() {
			return _msg;
		}
		@SuppressWarnings("unused")
		public String getLevel() {
			return _level;
		}
		@SuppressWarnings("unused")
		public String getSource() {
			return _source;
		}
		@SuppressWarnings("unused")
		public String getDocId() {
			return _doc_id;
		}
	}

}
