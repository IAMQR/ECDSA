package com.demianko.ecdsa.keys;

import com.demianko.ecdsa.curves.ECurve;

public abstract class ECAbstractKey {
	private ECurve curve;
	private Object data;
	
	protected ECAbstractKey(ECurve curve, Object data) {
		this.curve = curve;
		this.data=data;
	}

	public ECurve getCurve() {
		return curve;
	}

	public Object getData() {
		return data;
	}
}
