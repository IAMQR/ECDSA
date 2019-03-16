package com.demianko.ecdsa.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class MainUIController implements Initializable {

	// ELEMENTS

	// Menu Bar

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

	// FILES TO WORK WITH

	// TODO

	// HANDLERS

	@FXML
	private void handleClickOnBrowseButton(MouseEvent mouseEvent) {
		// TODO
	}

	@FXML
	private void handleGenKeysMenuItemAction(ActionEvent actionEvent) {
		// TODO
	}
	
	@FXML
	private void handleCloseMenuItemAction(ActionEvent actionEvent) {
		// TODO
	}

	@FXML
	private void handleClickOnSignButton(MouseEvent mouseEvent) {
		// TODO
	}

	@FXML
	private void handleClickOnVerifyButton(MouseEvent mouseEvent) {
		// TODO
	}

	// INITIALIZATION

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO
	}
}
