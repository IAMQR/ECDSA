package com.demianko.ecdsa.curves;

import java.math.BigInteger;

public class ECPoint {
	
	private BigInteger x;
	private BigInteger y;
	private boolean infinity;

	public ECPoint(boolean infinity) {
		this(BigInteger.ZERO, BigInteger.ZERO);
		this.infinity = infinity;
	}

	public ECPoint(BigInteger x, BigInteger y) {
		this.x = x;
		this.y = y;
		infinity = false;
	}

	@Override
	public boolean equals(Object pt) {
		if (!(pt instanceof ECPoint)) {
			return false;
		}

		ECPoint point = (ECPoint) pt;

		return infinity == point.infinity && x.equals(point.getX()) && y.equals(point.getY());
	}

	public BigInteger getX() {
		return x;
	}

	public BigInteger getY() {
		return y;
	}

	public boolean isInfinity() {
		return infinity;
	}

	public void setInfinity(boolean infinity) {
		this.infinity = infinity;
	}
}
