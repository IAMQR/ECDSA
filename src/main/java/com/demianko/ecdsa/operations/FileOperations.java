package com.demianko.ecdsa.operations;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;

import com.demianko.ecdsa.Main;
import com.demianko.ecdsa.curves.ECPoint;
import com.demianko.ecdsa.curves.ECurve;
import com.demianko.ecdsa.keys.ECPrivateKey;
import com.demianko.ecdsa.keys.ECPublicKey;

public class FileOperations {
	private FileOperations() {
	}

	public static void writeSignature(BigInteger r, BigInteger s, File file) throws IOException {
		try (FileWriter fileWriter = new FileWriter(file)) {
			fileWriter.append(r.toString(16) + '\n');
			fileWriter.append(s.toString(16) + '\n');
		} catch (IOException e) {
			throw new IOException("Signature write error: " + e.getMessage());
		}
	}

	public static BigInteger[] readSignature(File file) throws IOException {
		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
			return new BigInteger[] { new BigInteger(bufferedReader.readLine(), 16),
					new BigInteger(bufferedReader.readLine(), 16) };
		} catch (IOException e) {
			throw new IOException("Signature read error: " + e.getMessage());
		}
	}

	public static void writePrivateKey(ECPrivateKey privateKey, File file) throws IOException {
		try (FileWriter fileWriter = new FileWriter(file)) {
			fileWriter.append(privateKey.getCurve().toString() + '\n');
			fileWriter.append(privateKey.getData().toString(16) + '\n');
		} catch (IOException e) {
			throw new IOException("Private key write error: " + e.getMessage());
		}
	}

	public static void writePublicKey(ECPublicKey publicKey, File file) throws IOException {
		try (FileWriter fileWriter = new FileWriter(file)) {
			fileWriter.append(publicKey.getCurve().toString() + '\n');
			fileWriter.append(publicKey.getData().getX().toString(16) + '\n');
			fileWriter.append(publicKey.getData().getY().toString(16) + '\n');
		} catch (IOException e) {
			throw new IOException("Public key write error: " + e.getMessage());
		}
	}

	public static ECPrivateKey readPrivateKey(File file) throws IOException {
		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
			return new ECPrivateKey(getCurve(bufferedReader.readLine()), new BigInteger(bufferedReader.readLine(), 16));
		} catch (IOException e) {
			throw new IOException("Private key read error: " + e.getMessage());
		}
	}

	public static ECPublicKey readPublicKey(File file) throws IOException {
		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
			return new ECPublicKey(getCurve(bufferedReader.readLine()), new ECPoint(
					new BigInteger(bufferedReader.readLine(), 16), new BigInteger(bufferedReader.readLine(), 16)));
		} catch (IOException e) {
			throw new IOException("Public key read error: " + e.getMessage());
		}
	}

	private static ECurve getCurve(String curveName) {
		ECurve curve = Main.getCurves().get(curveName);

		if (curve == null) {
			throw new IllegalArgumentException(String.format("Unknown curve : \"%s\"", curveName));
		}
		return curve;
	}
}
