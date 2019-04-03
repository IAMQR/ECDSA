package com.demianko.ecdsa.logic;

import java.math.BigInteger;

public abstract class ECurve {
	private final String name;

	private final BigInteger p; // Field size
	private final BigInteger a; // Coefficient a
	private final BigInteger b; // Coefficient b
	private final ECPoint g; // Starting point
	private final BigInteger n; // Order of g

	protected ECurve(String name, BigInteger p, BigInteger a, BigInteger b, ECPoint g, BigInteger n) {
		this.name = name;
		this.p = p;
		this.a = a;
		this.b = b;
		this.g = g;
		this.n = n;
	}

	public BigInteger getP() {
		return p;
	}

	public BigInteger getA() {
		return a;
	}

	public BigInteger getB() {
		return b;
	}

	public ECPoint getG() {
		return g;
	}

	public BigInteger getN() {
		return n;
	}

	@Override
	public String toString() {
		return name;
	}
}
