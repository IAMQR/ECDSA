package com.demianko.ecdsa.keys;

import java.math.BigInteger;

import com.demianko.ecdsa.curves.ECurve;

public class ECPrivateKey extends ECAbstractKey {
	public ECPrivateKey(ECurve curve, BigInteger data) {
		super(curve, data);
	}

	@Override
	public BigInteger getData() {
		return (BigInteger) super.getData();
	}
}
