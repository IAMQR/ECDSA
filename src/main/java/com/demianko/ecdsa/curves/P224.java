package com.demianko.ecdsa.curves;

import java.math.BigInteger;

public final class P224 extends ECurve {
	public P224() {
		super("NIST P-224", new BigInteger("26959946667150639794667015087019630673557916260026308143510066298881"),
				BigInteger.valueOf(-3), new BigInteger("b4050a850c04b3abf54132565044b0b7d7bfd8ba270b39432355ffb4", 16),
				new ECPoint(new BigInteger("b70e0cbd6bb4bf7f321390b94a03c1d356c21122343280d6115c1d21", 16),
						new BigInteger("bd376388b5f723fb4c22dfe6cd4375a05a07476444d5819985007e34", 16)),
				new BigInteger("26959946667150639794667015087019625940457807714424391721682722368061"));
	}
}
