package com.demianko.ecdsa.curves;

import java.math.BigInteger;

public abstract class ECurve {
	
	private String name;

	private BigInteger p; // Field size
	private BigInteger a; // Coefficient a
	private BigInteger b; // Coefficient b
	private ECPoint g; // Starting point
	private BigInteger n; // Order of g

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
