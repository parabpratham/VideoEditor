package com.vid.application;

import java.io.IOException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) throws IOException {
		new NativeDiscovery().discover();

		Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/addcompcont/Markers_add_popup.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				Platform.exit();
				System.exit(0);
			}
		});

		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
