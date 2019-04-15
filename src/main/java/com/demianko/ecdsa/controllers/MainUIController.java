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

	// HANDLERS

	@FXML
	private void handleClickOnBrowseButton(MouseEvent mouseEvent) {
		Button eventSource = (Button) mouseEvent.getSource(); // Get the source of the event
		FileChooser fileChooser = new FileChooser();

		// Set the corresponding TextField
		try {
			if (eventSource == fileToSignBrowseButton) {
				fileToSignLocationField.setText(fileChooser.showOpenDialog(thisWindow).getAbsolutePath());
			} else if (eventSource == privateKeyBrowseButton) {
				privateKeyLocationField.setText(fileChooser.showOpenDialog(thisWindow).getAbsolutePath());
			} else if (eventSource == signatureOutputBrowseButton) {
				signatureOutputLocationField.setText(fileChooser.showSaveDialog(thisWindow).getAbsolutePath());
			} else if (eventSource == fileToVerifyBrowseButton) {
				fileToVerifyLocationField.setText(fileChooser.showOpenDialog(thisWindow).getAbsolutePath());
			} else if (eventSource == publicKeyBrowseButton) {
				publicKeyLocationField.setText(fileChooser.showOpenDialog(thisWindow).getAbsolutePath());
			} else if (eventSource == signatureFileBrowseButton) {
				signatureFileLocationField.setText(fileChooser.showOpenDialog(thisWindow).getAbsolutePath());
			}
		} catch (NullPointerException e) { // Do nothing if user doesn't choose a file
		}
	}

	@FXML
	private void handleGenKeysMenuItemAction(ActionEvent actionEvent) {
		keyGenStage.show(); // Show the keygen window
	}

	@FXML
	private void handleCloseMenuItemAction(ActionEvent actionEvent) { // Close window
		((Stage) thisWindow).close();
	}

	@FXML
	private void handleClickOnSignButton(MouseEvent mouseEvent) {
		try {
			if (fileToSignLocationField.getText().equals("")) {
				throw new IllegalArgumentException("Path to the file to sign can't be empty!");
			}
			// Load the file to sign from the specified path
			File fileToSign = new File(fileToSignLocationField.getText());

			if (privateKeyLocationField.getText().equals("")) {
				throw new IllegalArgumentException("Path to private key to sign can't be empty!");
			}
			// Load the private key from the specified path
			File privateKeyFile = new File(privateKeyLocationField.getText());

			// If not specified, set to the current working directory path
			if (signatureOutputLocationField.getText().equals("")) {
				// Default output filename = fileToSign.getName() + ".signature"
				File tempFile = new File(System.getProperty("user.dir"), fileToSign.getName() + ".signature");
				if (tempFile.exists()) { // Throw if default output file already exists
					throw new FileAlreadyExistsException(
							"File " + tempFile.getCanonicalPath() + " already exists, choose a different one!");
				}
				signatureOutputLocationField.setText(tempFile.getAbsolutePath());
			}
			File signatureFile = new File(signatureOutputLocationField.getText());

			setLabelTextInProgress();

			CryptOperations.signFile(fileToSign, privateKeyFile, signatureFile);
			setLabelTextSuccess(String.format("File %s was successfully signed!", fileToSign.getName()));
		} catch (Exception e) {
			setLabelTextFailure(e.getMessage());
		}
	}

	@FXML
	private void handleClickOnVerifyButton(MouseEvent mouseEvent) {
		try {
			if (fileToVerifyLocationField.getText().equals("")) {
				throw new IllegalArgumentException("Path to the file to verify can't be empty!");
			}
			// Load the file to verify from the specified path
			File fileToVerify = new File(fileToVerifyLocationField.getText());

			if (publicKeyLocationField.getText().equals("")) {
				throw new IllegalArgumentException("Path to public key can't be empty!");
			}
			// Load the public key from the specified path
			File publicKeyFile = new File(publicKeyLocationField.getText());

			if (signatureFileLocationField.getText().equals("")) {
				throw new IllegalArgumentException("Path to the signature can't be empty!");
			}
			// Load the signature from the specified path
			File signatureFile = new File(signatureFileLocationField.getText());

			setLabelTextInProgress();

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

	// Notify the user about the process of some action
	void setLabelTextInProgress() {
		statusMessageLabel.setText("Processing...");
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
