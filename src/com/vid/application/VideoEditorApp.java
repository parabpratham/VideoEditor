package com.vid.application;

import com.vid.controller.VideoEditorController;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;

public class VideoEditorApp extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		new NativeDiscovery().discover();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/VideoEditor.fxml"));
		Parent root = (Parent) loader.load();
		VideoEditorController controller = (VideoEditorController) loader.getController();
		controller.setPrimaryStage(primaryStage);
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				Platform.exit();
				System.exit(0);
			}
		});
		Platform.setImplicitExit(false);
		primaryStage.show();
	}

	public static void main(String[] args) {
		Application.launch(VideoEditorApp.class);
	}

}
