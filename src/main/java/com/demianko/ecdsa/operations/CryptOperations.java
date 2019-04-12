package com.demianko.ecdsa.operations;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import com.demianko.ecdsa.curves.ECPoint;
import com.demianko.ecdsa.keys.ECPrivateKey;
import com.demianko.ecdsa.keys.ECPublicKey;

public class CryptOperations {
	private CryptOperations() {
	}

	public static void signFile(File fileToSign, File privateKeyFile, File signatureOutputFile)
			throws IOException, NoSuchAlgorithmException {
		ECPrivateKey privateKey = FileOperations.readPrivateKey(privateKeyFile);

		SecureRandom sr = new SecureRandom();
		BigInteger k;
		do {
			k = new BigInteger(512, sr).mod(privateKey.getCurve().getN().subtract(BigInteger.ONE));
		} while ((k.compareTo(BigInteger.ZERO) == 0)
				|| (k.gcd(privateKey.getCurve().getN()).compareTo(BigInteger.ONE) != 0));

		ECPoint kG = CurveOperations.multiplyPoint(privateKey.getCurve(), k, privateKey.getCurve().getG());
		BigInteger r = kG.getX().mod(privateKey.getCurve().getN());
		if (r.equals(BigInteger.ZERO)) {
			signFile(fileToSign, privateKeyFile, signatureOutputFile); // Try again
		}

		BigInteger h = new BigInteger(calculateSha1(fileToSign));

		BigInteger s = k.modInverse(privateKey.getCurve().getN()).multiply(h.add(privateKey.getData().multiply(r)))
				.mod(privateKey.getCurve().getN());
		if (s.equals(BigInteger.ZERO)) {
			signFile(fileToSign, privateKeyFile, signatureOutputFile); // Try again
		}

		FileOperations.writeSignature(r, s, signatureOutputFile);
	}

	public static boolean verifyFile(File fileToVerify, File publicKeyFile, File signatureFile)
			throws IOException, NoSuchAlgorithmException {
		ECPublicKey publicKey = FileOperations.readPublicKey(publicKeyFile);

		BigInteger[] signature = FileOperations.readSignature(signatureFile);

		if (signature[0].compareTo(BigInteger.ONE) < 0
				|| signature[0].compareTo(publicKey.getCurve().getN().subtract(BigInteger.ONE)) > 0
				|| signature[1].compareTo(BigInteger.ONE) < 0
				|| signature[1].compareTo(publicKey.getCurve().getN().subtract(BigInteger.ONE)) > 0) {
			throw new IllegalArgumentException("Signature is not in range!"); // Signature not in range
		}

		BigInteger e = new BigInteger(calculateSha1(fileToVerify));
		BigInteger w = signature[1].modInverse(publicKey.getCurve().getN());

		BigInteger u1 = e.multiply(w).mod(publicKey.getCurve().getN());
		BigInteger u2 = signature[0].multiply(w).mod(publicKey.getCurve().getN());

		ECPoint x = CurveOperations.addPoints(publicKey.getCurve(),
				CurveOperations.multiplyPoint(publicKey.getCurve(), u1, publicKey.getCurve().getG()),
				CurveOperations.multiplyPoint(publicKey.getCurve(), u2, publicKey.getData()));
		if (x.isInfinity()) {
			return false;
		}

		BigInteger v = x.getX().mod(publicKey.getCurve().getN());

		return v.compareTo(signature[0]) == 0;
	}

	private static byte[] calculateSha1(File file) throws NoSuchAlgorithmException, IOException {
		try (DigestInputStream digestInputStream = new DigestInputStream(new FileInputStream(file),
				MessageDigest.getInstance("SHA-1"))) {
			digestInputStream.readAllBytes();
			return digestInputStream.getMessageDigest().digest();
		}
	}
}
