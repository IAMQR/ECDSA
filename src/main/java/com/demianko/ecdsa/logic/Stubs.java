package com.demianko.ecdsa.logic;

import java.io.File;

// Temporary class

public class Stubs {
	private Stubs() {
	}

	// Write everything in files provided in method arguments

	// Write to signatureOutput
	public static void sign(File fileToSign, File privateKey, File signatureOutput) throws Exception {
	}

	// Return nothing if success
	public static void verify(File fileToVerify, File publicKey, File signature) throws Exception {
	}

	// Write keys to arguments
	public static void generateKeys(String curve, File privateKey, File publicKey) throws Exception {
	}
}
