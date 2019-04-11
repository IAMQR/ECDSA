package com.demianko.ecdsa.controllers;

import java.io.File;
import java.net.URL;
import java.nio.file.FileAlreadyExistsException;
import java.util.ResourceBundle;

import com.demianko.ecdsa.operations.CryptOperations;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

public class MainUIController implements Initializable {

	private Stage keyGenStage; // Stage of the keyGen window

	public void setKeyGenStage(Stage keyGenStage) {
		this.keyGenStage = keyGenStage;
	}

	private Window thisWindow; // Window used to open file browser dialogs and close the application

	public void setThisWindow(Window thisWindow) {
		this.thisWindow = thisWindow;
	}

	// ELEMENTS

	// Menu Bar

	@FXML
	private MenuBar menuBar;

	@FXML
	private MenuItem genKeysMenuItem;

	@FXML
	private MenuItem closeMenuItem;

	// Signing section

	@FXML
	private TextField fileToSignLocationField;
	@FXML
	private Button fileToSignBrowseButton;

	@FXML
	private TextField privateKeyLocationField;
	@FXML
	private Button privateKeyBrowseButton;

	@FXML
	private TextField signatureOutputLocationField;
	@FXML
	private Button signatureOutputBrowseButton;

	@FXML
	private Button signButton;

	// Verification section

	@FXML
	private TextField fileToVerifyLocationField;
	@FXML
	private Button fileToVerifyBrowseButton;

	@FXML
	private TextField publicKeyLocationField;
	@FXML
	private Button publicKeyBrowseButton;

	@FXML
	private TextField signatureFileLocationField;
	@FXML
	private Button signatureFileBrowseButton;

	@FXML
	private Button verifyButton;

	// Status bar

	@FXML
	private Label statusMessageLabel;
	private String defaultStatusMessageLabelStyle;

	// FILES TO WORK WITH

	private File fileToSign;
	private File privateKeyFile;
	private File signatureFile;

	private File fileToVerify;
	private File publicKeyFile;

	// HANDLERS

	@FXML
	private void handleClickOnBrowseButton(MouseEvent mouseEvent) {
		Button eventSource = (Button) mouseEvent.getSource(); // Get the source of the event
		FileChooser fileChooser = new FileChooser();

		// Set the corresponding TextField
		if (eventSource == fileToSignBrowseButton) {
			fileToSign = fileChooser.showOpenDialog(thisWindow);
			if (fileToSign != null) {
				fileToSignLocationField.setText(fileToSign.getAbsolutePath());
			}
		} else if (eventSource == privateKeyBrowseButton) {
			privateKeyFile = fileChooser.showOpenDialog(thisWindow);
			if (privateKeyFile != null) {
				privateKeyLocationField.setText(privateKeyFile.getAbsolutePath());
			}
		} else if (eventSource == signatureOutputBrowseButton) {
			signatureFile = fileChooser.showSaveDialog(thisWindow);
			if (signatureFile != null) {
				signatureOutputLocationField.setText(signatureFile.getAbsolutePath());
			}
		} else if (eventSource == fileToVerifyBrowseButton) {
			fileToVerify = fileChooser.showOpenDialog(thisWindow);
			if (fileToVerify != null) {
				fileToVerifyLocationField.setText(fileToVerify.getAbsolutePath());
			}
		} else if (eventSource == publicKeyBrowseButton) {
			publicKeyFile = fileChooser.showOpenDialog(thisWindow);
			if (publicKeyFile != null) {
				publicKeyLocationField.setText(publicKeyFile.getAbsolutePath());
			}
		} else if (eventSource == signatureFileBrowseButton) {
			signatureFile = fileChooser.showOpenDialog(thisWindow);
			if (signatureFile != null) {
				signatureFileLocationField.setText(signatureFile.getAbsolutePath());
			}
		}
	}

	@FXML
	private void handleGenKeysMenuItemAction(ActionEvent actionEvent) {
		keyGenStage.show(); // Show window
	}

	@FXML
	private void handleCloseMenuItemAction(ActionEvent actionEvent) { // Close window
		((Stage) thisWindow).close();
	}

	@FXML
	private void handleClickOnSignButton(MouseEvent mouseEvent) {
		try {
			// Load the file to sign from the specified path
			fileToSign = new File(fileToSignLocationField.getText());
			// Load the private key from the specified path
			privateKeyFile = new File(privateKeyLocationField.getText());

			// If not specified, set to the current working directory path
			if (signatureOutputLocationField.getText().compareTo("") == 0) {
				// Default output filename = fileToSign.getName() + ".signature"
				File tempFile = new File(System.getProperty("user.dir"), fileToSign.getName() + ".signature");
				if (tempFile.exists()) { // Throw if default output file already exists
					throw new FileAlreadyExistsException(
							"File " + tempFile.getCanonicalPath() + " already exists, choose a different one");
				}
				signatureOutputLocationField.setText(tempFile.getAbsolutePath());
			}
			signatureFile = new File(signatureOutputLocationField.getText());

			CryptOperations.signFile(fileToSign, privateKeyFile, signatureFile);
			setLabelTextSuccess(String.format("File %s was successfully signed!", fileToSign.getName()));
		} catch (Exception e) {
			setLabelTextFailure(e.getMessage());
		}
	}

	@FXML
	private void handleClickOnVerifyButton(MouseEvent mouseEvent) {
		try {
			// Load the file to verify from the specified path
			fileToVerify = new File(fileToVerifyLocationField.getText());
			// Load the public key from the specified path
			publicKeyFile = new File(publicKeyLocationField.getText());
			// Load the signature from the specified path
			signatureFile = new File(signatureFileLocationField.getText());

			if (CryptOperations.verifyFile(fileToVerify, publicKeyFile, signatureFile)) {
				setLabelTextSuccess(String.format("File %s was successfully verified!", fileToVerify.getName()));
			} else {
				setLabelTextFailure(String.format("Verification of file %s failed!", fileToVerify.getName()));
			}
		} catch (Exception e) {
			setLabelTextFailure(e.getMessage());
		}
	}

	// UTILITY METHODS

	private void setDefaultLabelStyle() {
		statusMessageLabel.setStyle(defaultStatusMessageLabelStyle);
	}

	void setLabelTextSuccess(String message) { // Message on success
		setDefaultLabelStyle();
		statusMessageLabel.setStyle("-fx-text-fill: green");
		statusMessageLabel.setText(message);
	}

	void setLabelTextFailure(String message) { // Message on failure
		setDefaultLabelStyle();
		statusMessageLabel.setStyle("-fx-text-fill: red");
		statusMessageLabel.setText(message);
	}

	// INITIALIZATION

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		defaultStatusMessageLabelStyle = statusMessageLabel.getStyle(); // Remember the default status message style

		// Finish initialisation by providing status
		statusMessageLabel.setText("Ready");
	}
}
