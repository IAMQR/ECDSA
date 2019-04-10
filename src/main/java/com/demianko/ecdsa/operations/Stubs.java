package com.demianko.ecdsa.operations;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.SecureRandom;

import com.demianko.ecdsa.Main;
import com.demianko.ecdsa.curves.ECPoint;
import com.demianko.ecdsa.curves.ECurve;

// Temporary class

public class Stubs {
	private Stubs() {
	}

	// TODO: return status as a boolean
	
	// Write everything to files provided in method arguments

	// Write to signatureOutput
	public static void sign(File fileToSign, File privateKey, File signatureOutput) throws Exception {
		// TODO: rewrite
		ECurve curve = null;
		BigInteger pKey = null;
		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(privateKey))) {
			curve = Main.CURVES.get(bufferedReader.readLine());
			pKey = new BigInteger(bufferedReader.readLine(), 16);
		} catch (Exception e) {
			// TODO: handle exception
		}
		SecureRandom sr = new SecureRandom();
		BigInteger k;
		k = new BigInteger(512, sr);
		k = k.mod(curve.getN().subtract(BigInteger.ONE));
		while ((k.compareTo(java.math.BigInteger.ZERO) == 0) || (k.gcd(curve.getN()).compareTo(BigInteger.ONE) != 0)) {
			k = new BigInteger(512, sr);
			k = k.mod(curve.getN().subtract(BigInteger.ONE));
		}
		ECPoint p = Operations.multPoint(curve, k, curve.getG());
		if (p.getX().equals(BigInteger.ZERO)) {
			sign(fileToSign, privateKey, signatureOutput);
		}

		MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
		InputStream inputStream = new FileInputStream(fileToSign);
		DigestInputStream digestInputStream = new DigestInputStream(inputStream, messageDigest);
		digestInputStream.readAllBytes();
		digestInputStream.close();
		byte[] bytes = messageDigest.digest();
		BigInteger h = new BigInteger(bytes);

		BigInteger r = p.getX();
		BigInteger kinv = k.modInverse(curve.getN());
		BigInteger s = (kinv.multiply((h.add((pKey.multiply(r)))))).mod(curve.getN());
		if (s.compareTo(java.math.BigInteger.ZERO) == 0) {
			sign(fileToSign, privateKey, signatureOutput);
		}

		try (FileWriter fileWriter = new FileWriter(signatureOutput)) {
			fileWriter.append(r.toString(16) + '\n');
			fileWriter.append(s.toString(16) + '\n');
		}
	}

	public static void verify(File fileToVerify, File publicKey, File signature) throws Exception {
		ECurve curve = null;
		BigInteger x, y;
		x = y = null;
		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(publicKey))) {
			curve = Main.CURVES.get(bufferedReader.readLine());
			x = new BigInteger(bufferedReader.readLine(), 16);
			y = new BigInteger(bufferedReader.readLine(), 16);
		} catch (Exception e) {
			// TODO: handle exception
		}
		BigInteger w, u1, u2, v;
		BigInteger rs[] = new BigInteger[2];
		ECPoint q = new ECPoint(x, y);
		ECPoint pt;
		BufferedReader buffer = new BufferedReader(new FileReader(signature));
		rs[0] = new BigInteger(buffer.readLine(), 16);
		rs[1] = new BigInteger(buffer.readLine(), 16);
		System.out.println("rs[0]: " + rs[0]);
		System.out.println("rs[1]: " + rs[1]);
		buffer.close();
		System.out.println(q.getX());
		System.out.println(q.getY());
		if (rs[0].compareTo(curve.getN()) == 1 || rs[0].compareTo(BigInteger.ONE) < 0
				|| rs[1].compareTo(curve.getN()) == 1 || rs[1].compareTo(BigInteger.ONE) < 0) {
			throw new Exception(); // Signature not in range
		}
		w = rs[1].modInverse(curve.getN());
		System.out.println("w: " + w);
		MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
		InputStream inputStream = new FileInputStream(fileToVerify);
		DigestInputStream digestInputStream = new DigestInputStream(inputStream, messageDigest);
		digestInputStream.readAllBytes();
		digestInputStream.close();
		byte[] bytes = messageDigest.digest();
		BigInteger h = new BigInteger(bytes);
		u1 = h.multiply(w).mod(curve.getN());
		u2 = rs[0].multiply(w).mod(curve.getN());
		ECPoint u1P = Operations.multPoint(curve, u1, curve.getG());
		ECPoint u2Q = Operations.multPoint(curve, u2, q);
		pt = Operations.addPoints(curve, Operations.multPoint(curve, u1, curve.getG()),
				Operations.multPoint(curve, u2, q));
		v = pt.getX();
		v = v.mod(curve.getN());
		if (v.compareTo(rs[0]) != 0) {
			throw new Exception(); // Invalid Signature
		}
	}

	// Write keys to arguments
	public static void generateKeys(ECurve curve, File privateKey, File publicKey) throws Exception {
		generatePublicKey(curve, generatePrivateKey(curve, privateKey), publicKey);
	}

	private static BigInteger generatePrivateKey(ECurve curve, File privateKeyFile) {
		SecureRandom secureRandom = new SecureRandom();
		BigInteger privateKey = new BigInteger(512, secureRandom);
		privateKey = privateKey.mod(curve.getN().subtract(BigInteger.ONE));
		try (FileWriter fileWriter = new FileWriter(privateKeyFile)) {
			fileWriter.append(curve.toString() + '\n');
			fileWriter.append(privateKey.toString(16));
		} catch (Exception e) {
			// TODO: handle exception
		}
		return privateKey;
	}

	private static void generatePublicKey(ECurve curve, BigInteger privateKey, File publicKeyFile) {
		ECPoint publicKey = Operations.multPoint(curve, privateKey, curve.getG());
		BigInteger x = publicKey.getX();
		BigInteger y = publicKey.getY();

		try (FileWriter fileWriter = new FileWriter(publicKeyFile)) {
			fileWriter.append(curve.toString() + '\n');
			fileWriter.append(x.toString(16) + '\n');
			fileWriter.append(y.toString(16) + '\n');
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
