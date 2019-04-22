package com.demianko.ecdsa.controllers;

import java.io.File;
import java.net.URL;
import java.nio.file.FileAlreadyExistsException;
import java.util.ResourceBundle;

import com.demianko.ecdsa.Main;
import com.demianko.ecdsa.operations.FileOperations;
import com.demianko.ecdsa.curves.ECurve;
import com.demianko.ecdsa.keys.ECPrivateKey;
import com.demianko.ecdsa.keys.ECPublicKey;
import com.demianko.ecdsa.keys.KeyFactory;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;

public class KeyGenUIController implements Initializable {

	private MainUIController mainUIController; // Parent controller

	public void setMainUIController(MainUIController mainUIController) {
		this.mainUIController = mainUIController;
	}

	// ELEMENTS

	@FXML
	private ComboBox<ECurve> curveSelectionComboBox;

	@FXML
	private TextField keyOutputDirectoryLocationField;
	@FXML
	private Button keyOutputDirectoryBrowseButton;

	@FXML
	private Button keyGenButton;

	// HANDLERS

	@FXML
	private void handleClickOnBrowseButton(MouseEvent mouseEvent) {
		try {
			File selectedDirectory = new DirectoryChooser()
					.showDialog(keyOutputDirectoryBrowseButton.getScene().getWindow());
			keyOutputDirectoryLocationField.setText(selectedDirectory.getAbsolutePath());
		} catch (NullPointerException e) { // Do nothing if user doesn't choose a file
		}
	}

	@FXML
	private void handleClickOnKeyGenButton(MouseEvent mouseEvent) {
		try {
			// Throw an exception if the path is empty
			if (keyOutputDirectoryLocationField.getText().equals("")) {
				throw new IllegalArgumentException("Path to the output directory can't be empty!");
			}

			// Load files with keys
			File privateKeyFile = new File(keyOutputDirectoryLocationField.getText(), "pkey");
			File publicKeyFile = new File(keyOutputDirectoryLocationField.getText(), "pubkey");

			if (privateKeyFile.exists() || publicKeyFile.exists()) {
				throw new FileAlreadyExistsException(
						"\"pkey\" or/and \"pubkey\" already exist! Choose a different directory!");
			}

			mainUIController.setLabelTextInProgress();

			ECurve curve = curveSelectionComboBox.getValue();
			KeyFactory keyFactory = new KeyFactory(curve);

			// Generate keys
			ECPrivateKey privateKey = keyFactory.generatePrivateKey();
			ECPublicKey publicKey = keyFactory.generatePublicKey(privateKey);

			// Write keys
			FileOperations.writePrivateKey(privateKey, privateKeyFile);
			FileOperations.writePublicKey(publicKey, publicKeyFile);
			mainUIController.setLabelTextSuccess("Keys were succesfully generated!");
		} catch (Exception e) {
			mainUIController.setLabelTextFailure(e.getMessage());
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Add curves from the map in Main
		curveSelectionComboBox.getItems().addAll(Main.getCurves().values());
	}
}
