package com.demianko.ecdsa.controllers;

import java.io.File;
import java.net.URL;
import java.nio.file.FileAlreadyExistsException;
import java.util.ResourceBundle;

import com.demianko.ecdsa.Main;
import com.demianko.ecdsa.operations.Stubs;
import com.demianko.ecdsa.curves.ECurve;

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
			Stubs.generateKeys(curveSelectionComboBox.getValue(), privateKey, publicKey); // Stub
			mainUIController.setLabelTextSuccess("Keys were succesfully generated!");
		} catch (Exception e) {
			mainUIController.setLabelTextFailure(e.getMessage());
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Add curves
		curveSelectionComboBox.getItems().addAll(Main.CURVES.values());
	}
}
