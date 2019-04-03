package com.demianko.ecdsa;

import com.demianko.ecdsa.controllers.KeyGenUIController;
import com.demianko.ecdsa.controllers.MainUIController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		// Load the main window
		FXMLLoader mainWindowLoader = new FXMLLoader(getClass().getClassLoader().getResource("ui_main.fxml"));
		Parent root = mainWindowLoader.load();
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("ECDSA");
		primaryStage.show();

		// Load the keyGen window
		FXMLLoader keyGenWindowLoader = new FXMLLoader(getClass().getClassLoader().getResource("ui_keygen.fxml"));
		Parent keyGenParent = keyGenWindowLoader.load();
		Scene keyGenScene = new Scene(keyGenParent);
		Stage keyGenStage = new Stage();
		keyGenStage.setScene(keyGenScene);

		// Set keyGen stage in the main controller
		mainWindowLoader.<MainUIController>getController().setKeyGenStage(keyGenStage);
		// Set a reference to the main controller in the keyGen one
		keyGenWindowLoader.<KeyGenUIController>getController().setMainUIController(mainWindowLoader.getController());

		keyGenStage.initOwner(scene.getWindow());
		keyGenStage.setTitle("Key generation menu");
	}
}
