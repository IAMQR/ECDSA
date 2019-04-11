package com.demianko.ecdsa.keys;

import com.demianko.ecdsa.curves.ECPoint;
import com.demianko.ecdsa.curves.ECurve;

public class ECPublicKey extends ECAbstractKey {
	public ECPublicKey(ECurve curve, ECPoint data) {
		super(curve, data);
	}

	@Override
	public ECPoint getData() {
		return (ECPoint) super.getData();
	}
}
