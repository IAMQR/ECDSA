package com.demianko.ecdsa;

import java.util.LinkedHashMap;
import java.util.Map;

import com.demianko.ecdsa.controllers.KeyGenUIController;
import com.demianko.ecdsa.controllers.MainUIController;
import com.demianko.ecdsa.curves.ECurve;
import com.demianko.ecdsa.curves.P192;
import com.demianko.ecdsa.curves.P224;
import com.demianko.ecdsa.curves.P256;
import com.demianko.ecdsa.curves.P384;
import com.demianko.ecdsa.curves.P521;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

	private static Map<String, ECurve> curves = new LinkedHashMap<>();

	public static Map<String, ECurve> getCurves() {
		return curves;
	}
	
	// Add curves here, key should be the same as the curve name in its constructor
	static {
		ECurve curve = new P192();
		curves.put(curve.toString(), curve);
		curve = new P224();
		curves.put(curve.toString(), curve);
		curve = new P256();
		curves.put(curve.toString(), curve);
		curve = new P384();
		curves.put(curve.toString(), curve);
		curve = new P521();
		curves.put(curve.toString(), curve);
	}

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

		mainWindowLoader.<MainUIController>getController().setThisWindow(primaryStage);
		// Set keyGen stage in the main controller
		mainWindowLoader.<MainUIController>getController().setKeyGenStage(keyGenStage);
		// Set a reference to the main controller in the keyGen one
		keyGenWindowLoader.<KeyGenUIController>getController().setMainUIController(mainWindowLoader.getController());

		keyGenStage.initOwner(scene.getWindow());
		keyGenStage.setTitle("Key generation menu");
	}
}
