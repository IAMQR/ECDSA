package com.demianko.ecdsa.controllers.util;

import java.io.File;
import java.io.FileNotFoundException;

public class Utility {
	private Utility() {
	}

	public static File loadIfExistsAndIsFile(String fileLocation, String description) throws FileNotFoundException {
		File file = new File(fileLocation);
		if (!file.exists()) {
			throw new FileNotFoundException(description + " doesn't exist: \"" + fileLocation + '\"');
		}

		if (!file.isFile()) {
			throw new FileNotFoundException(description + " is not a file: \"" + fileLocation + '\"');
		}

		return file;
	}
}
