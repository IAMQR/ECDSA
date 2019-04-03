package com.demianko.ecdsa.logic;

import java.math.BigInteger;

public class ECPoint {

	private BigInteger x;
	private BigInteger y;
	private boolean infinity;

	public ECPoint(BigInteger xPos, BigInteger yPos) {
		x = xPos;
		y = yPos;
		infinity = false;
	}

	@Override
	public boolean equals(Object pt) {
		if (!(pt instanceof ECPoint)) {
			return false;
		} else if (this.x.equals(((ECPoint) pt).getX()) && this.y.equals(((ECPoint) pt).getY())) {
			return true;
		} else {
			return false;
		}
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
