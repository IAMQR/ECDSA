package com.demianko.ecdsa.operations;

import java.math.BigInteger;

import com.demianko.ecdsa.curves.ECPoint;
import com.demianko.ecdsa.curves.ECurve;

public class Operations {
	private Operations() {
	}

	// Addition
	public static ECPoint addPoints(ECurve curve, ECPoint p1, ECPoint p2) {
		ECPoint p3 = new ECPoint(BigInteger.ZERO, BigInteger.ZERO);
		BigInteger slope, x, y, temp;
		if (p1.getY().equals(BigInteger.ZERO) || p2.getY().equals(BigInteger.ZERO)
				|| (p1.isInfinity() && p2.isInfinity())
				|| (p1.getX().equals(p2.getX()) && p1.getY().equals(p2.getY().negate()))) {
			p3.setInfinity(true);
		} else {
			if ((p1.getX().equals(p2.getX())) && (p1.getY().equals(p2.getY()))) {
				temp = new BigInteger("" + 3);
				slope = ((temp.multiply(p1.getX().pow(2))).add(curve.getA()))
						.multiply((p1.getY().add(p1.getY())).modInverse(curve.getP()));
				x = (slope.multiply(slope)).subtract(p1.getX().add(p1.getX()));
				x = x.mod(curve.getP());
				y = (slope.multiply(p1.getX().subtract(x))).subtract(p1.getY());
			} else {
				if (p1.isInfinity()) {
					x = p2.getX();
					y = p2.getY();
				} else if (p2.isInfinity()) {
					x = p1.getX();
					y = p1.getY();
				} else {
					temp = (p2.getX().subtract(p1.getX())).modInverse(curve.getP());
					slope = (p2.getY().subtract(p1.getY())).multiply(temp);
					x = ((slope.multiply(slope)).subtract(p1.getX())).subtract(p2.getX());
					y = (slope.multiply(p1.getX().subtract(x))).subtract(p1.getY());
				}
				x = x.mod(curve.getP());
			}
			y = y.mod(curve.getP());
			p3 = new ECPoint(x, y);
		}
		return p3;
	}

	// Scalar Multiplication
	public static ECPoint multPoint(ECurve curve, BigInteger s, ECPoint p) {
		String binS = s.toString(2);
		ECPoint q = new ECPoint(BigInteger.ZERO, BigInteger.ZERO);
		q.setInfinity(true);
		if (binS.substring(0, 1).equals("1")) {
			q = p;
		}
		for (int i = 1; i < binS.length(); i++) {
			q = addPoints(curve, q, q);
			if (binS.substring(i, i + 1).equals("1")) {
				q = addPoints(curve, q, p);
			}
		}

		return (q);
	}
}
