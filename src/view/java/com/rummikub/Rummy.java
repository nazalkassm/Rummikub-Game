package com.rummikub;

import java.net.URL;
import java.util.List;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Rummy extends Application {

	public static List<Player> players;
	public static boolean testingMode;

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		primaryStage.setScene(loadScene("TitleScreen.fxml"));
		primaryStage.setResizable(false);
		primaryStage.show();
	}

	public static Scene loadScene(String fxmlPath) throws Exception {
		URL url = Rummy.class.getResource(fxmlPath);
		Scene scene;

		if (url != null) {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(url);
			Pane root = fxmlLoader.load();

			scene = new Scene(root);
		} else {
			throw new Exception("FXML file \"" + fxmlPath + "\" does not exist.");
		}
		return scene;
	}
}