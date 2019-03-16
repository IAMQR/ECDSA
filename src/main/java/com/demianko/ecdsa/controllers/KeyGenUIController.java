package com.demianko.ecdsa.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class KeyGenUIController implements Initializable {

	// ELEMENTS

	@FXML
	private ComboBox<?> curveSelectionComboBox;

	@FXML
	private TextField keyOutputDirectoryLocationField;
	@FXML
	private Button keyOutputDirectoryBrowseButton;

	@FXML
	private Button keyGenButton;

	// HANDLERS

	@FXML
	private void handleClickOnBrowseButton(MouseEvent mouseEvent) {
		// TODO
	}

	@FXML
	private void handleClickOnKeyGenButton(MouseEvent mouseEvent) {
		// TODO
	}

	// INITIALIZATION

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO
	}
}
