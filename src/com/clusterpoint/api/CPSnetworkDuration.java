package com.clusterpoint.api;

public class CPSnetworkDuration {

	private long _value;

	public CPSnetworkDuration() {
		_value = 0;
	}

	public void setValue(long value) {
		_value = value;
	}

	public long getValue() {
		return _value;
	}

	public void addValue(long value) {
		_value += value;
	}

	public float getSeconds() {
		return (_value / (float) 1000);
	}

	public void addMilisecondsLong(long value) {
		addValue(value);
	}
}
