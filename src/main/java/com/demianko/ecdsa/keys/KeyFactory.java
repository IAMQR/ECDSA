package com.demianko.ecdsa.keys;

import java.math.BigInteger;
import java.security.SecureRandom;

import com.demianko.ecdsa.curves.ECurve;
import com.demianko.ecdsa.operations.CurveOperations;

public class KeyFactory {
	
	private SecureRandom secureRandom;
	
	private ECurve curve;

	public KeyFactory(ECurve curve, byte[] seed) {
		if (seed != null) {
			secureRandom = new SecureRandom(seed);
		} else {
			secureRandom = new SecureRandom();
		}
		this.curve = curve;
	}

	public KeyFactory(ECurve curve) {
		this(curve, null);
	}

	public ECPrivateKey generatePrivateKey() {
		return new ECPrivateKey(curve, new BigInteger(512, secureRandom).mod(curve.getN().subtract(BigInteger.ONE)));
	}

	public ECPublicKey generatePublicKey(ECPrivateKey privateKey) {
		return new ECPublicKey(curve, CurveOperations.multiplyPoint(curve, privateKey.getData(), curve.getG()));
	}

	public ECurve getCurve() {
		return curve;
	}
}
