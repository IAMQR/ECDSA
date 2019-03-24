package com.demianko.ecdsa;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		// Load the main window
		Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("ui_main.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("ECDSA");
		primaryStage.show();
	}
}
