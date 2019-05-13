package com.demianko.ecdsa.operations;

import java.math.BigInteger;

import com.demianko.ecdsa.curves.ECPoint;
import com.demianko.ecdsa.curves.ECurve;

public class CurveOperations {
	private CurveOperations() {
	}

	// Point addition
	public static ECPoint addPoints(ECurve curve, ECPoint p1, ECPoint p2) {
		if (p1.isInfinity()) {
			return p2;
		} else if (p2.isInfinity()) {
			return p1;
		}

		BigInteger x, y;

		if (p1.getX().equals(p2.getX()) && p1.getY().equals(p2.getY().negate())) {
			return new ECPoint(true);
		} else if (p1.equals(p2)) {
			BigInteger slope = p1.getX().pow(2).multiply(BigInteger.valueOf(3)).add(curve.getA())
					.multiply(p1.getY().multiply(BigInteger.valueOf(2)).modInverse(curve.getP()));
			x = slope.pow(2).subtract(p1.getX().multiply(BigInteger.valueOf(2)));
			y = slope.multiply(p1.getX().subtract(x)).subtract(p1.getY());
		} else {
			BigInteger slope = p2.getY().subtract(p1.getY())
					.multiply(p2.getX().subtract(p1.getX()).modInverse(curve.getP()));
			x = slope.pow(2).subtract(p1.getX()).subtract(p2.getX());
			y = slope.multiply(p1.getX().subtract(x)).subtract(p1.getY());
		}

		return new ECPoint(x.mod(curve.getP()), y.mod(curve.getP()));
	}

	// Point multiplication
	public static ECPoint multiplyPoint(ECurve curve, BigInteger s, ECPoint p) {
		String[] naf = computeNAF(s);

		ECPoint q = new ECPoint(true);
		for (int i = 0; i < naf.length; i++) {
			if (naf[i] == null) {
				continue;
			}
			q = addPoints(curve, q, q);
			if (naf[i].equals("1")) {
				q = addPoints(curve, q, p);
			} else if (naf[i].equals("-1")) {
				q = addPoints(curve, q, new ECPoint(p.getX(), p.getY().negate().mod(curve.getP())));
			}
		}
		return q;
	}
	
	private static String[] computeNAF(BigInteger k) {
		String[] result = new String[k.bitLength() + 1];

		int i = 0;

		while (k.compareTo(BigInteger.ZERO) > 0) {
			if (k.testBit(0)) {
				BigInteger t = BigInteger.TWO.subtract(k.mod(BigInteger.valueOf(4)));
				result[result.length - (i + 1)] = t.toString();
				k = k.subtract(t);
			} else {
				result[result.length - (i + 1)] = "0";
			}
			k = k.divide(BigInteger.TWO);
			i++;
		}

		return result;
	}
}
