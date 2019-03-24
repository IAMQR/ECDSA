package com.demianko.ecdsa.controllers;

import java.io.File;
import java.nio.file.FileAlreadyExistsException;

import com.demianko.ecdsa.logic.Stubs;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;

public class KeyGenUIController {

	private MainUIController mainUIController; // Parent controller

	void setMainUIController(MainUIController mainUIController) {
		this.mainUIController = mainUIController;
	}

	// ELEMENTS

	@FXML
	private ComboBox<?> curveSelectionComboBox;

	@FXML
	private TextField keyOutputDirectoryLocationField;
	@FXML
	private Button keyOutputDirectoryBrowseButton;

	@FXML
	private Button keyGenButton;

	// FILES TO WORK WITH

	private File privateKey;
	private File publicKey;

	// HANDLERS

	@FXML
	private void handleClickOnBrowseButton(MouseEvent mouseEvent) {
		DirectoryChooser directoryChooser = new DirectoryChooser();
		File directory = directoryChooser.showDialog(keyOutputDirectoryBrowseButton.getScene().getWindow());

		privateKey = new File(directory.getAbsolutePath(), "pkey");
		publicKey = new File(directory.getAbsolutePath(), "pubkey");

		try {
			if (privateKey.exists() || publicKey.exists()) {
				throw new FileAlreadyExistsException(
						"\"pkey\" or/and \"pubkey\" already exist! Choose a different directory");
			}
		} catch (Exception e) {
			mainUIController.setLabelTextFailure(e.getMessage());
		}

		keyOutputDirectoryLocationField.setText(directory.getAbsolutePath());

	}

	@FXML
	private void handleClickOnKeyGenButton(MouseEvent mouseEvent) {
		try {
			Stubs.generateKeys("", privateKey, publicKey); // Stub
			mainUIController.setLabelTextSuccess("Keys were succesfully generated!");
		} catch (Exception e) {
			mainUIController.setLabelTextFailure(e.getMessage());
		}
	}
}
