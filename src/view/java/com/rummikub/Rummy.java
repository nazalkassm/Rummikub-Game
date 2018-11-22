package com.rummikub;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;


public class Rummy extends Application{
	
	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		primaryStage.setScene(loadScene("TitleScreen.fxml"));
		primaryStage.setResizable(false);
		primaryStage.show();
	}
	
	public static Scene loadScene(String fxmlPath) throws Exception  {
		URL url = Rummy.class.getResource(fxmlPath);
		Scene scene;
		
		if (url != null) {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(url);
			Parent root = fxmlLoader.load();
			
			scene = new Scene(root);
		}
		else {
			throw new Exception("FXML file \"" + fxmlPath +  "\" does not exist.");
		}
		return scene;
	}
}