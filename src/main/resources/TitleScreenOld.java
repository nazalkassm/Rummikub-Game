package com.rummikub;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;


public class TitleScreenOld extends Application {

	private List<ComboBox<String>> strategyType;
	private List<Label> strategyLabel;
	private ComboBox<String> playerCount;
	private Button playButton;
	private Pane canvas;

	private final int SCENE_WIDTH = 300;
	private final int SCENE_HEIGHT = 350;


	public static void main(String[] args) {
		Application.launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		initUI(primaryStage);
	}


	private void initUI(Stage primaryStage) {
		// TODO Auto-generated method stub
		canvas = new Pane();

		//adding GUI elements to canvas
		addsUIElements(canvas);

		Scene scene = new Scene(canvas, SCENE_WIDTH, SCENE_HEIGHT);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Tile Rummy Title Screen");
		primaryStage.show();
	}

	private void addsUIElements(Pane canvas) {
		// TODO Auto-generated method stub
		canvas.setStyle("-fx-background-color: white");

		Label label = new Label("Please select the number of players.");
		label.setFont(Font.font("Serif", FontWeight.NORMAL, 18));
		label.relocate(20, 25);

		playerCount = new ComboBox<String>();
		playerCount.setPrefWidth(100);
		playerCount.getItems().addAll("2", "3", "4");
		playerCount.setValue("Select");
		playerCount.relocate((SCENE_WIDTH - playerCount.getPrefWidth())/2, 50);
		playerCount.setOnAction(new PlayerCountCBtHandler());

		playButton = new Button("Start");
		playButton.relocate(200, 300);
		playButton.setDisable(true);
		playButton.setOnAction(new ButtonStartGame());

		canvas.getChildren().addAll(label, playerCount, playButton);

	}


	private class PlayerCountCBtHandler implements EventHandler<ActionEvent> {
		public PlayerCountCBtHandler() {
			strategyType = new ArrayList<ComboBox<String>>();
			strategyLabel = new ArrayList<Label>();
		}

		@Override
		public void handle(ActionEvent event) {
			canvas.getChildren().removeAll(strategyType);
			canvas.getChildren().removeAll(strategyLabel);

			strategyType.clear();
			strategyLabel.clear();

			int getCount = Integer.valueOf(playerCount.getValue());		
			for(int i = 0; i < getCount; i++) {
				ComboBox<String> setStrategy = new ComboBox<String>() {};
				setStrategy.setPrefWidth(100);
				setStrategy.getItems().addAll("Human", "Strategy 1", "Strategy 2", "Strategy 3");
				setStrategy.setValue("Select");
				setStrategy.relocate((SCENE_WIDTH - setStrategy.getPrefWidth())/2, 100 + (30*i));

				setStrategy.setOnAction(new StrategyCBHandler());
				strategyType.add(setStrategy);

				Label label = new Label("Player " + (i + 1) + ":");
				label.setFont(Font.font("Serif", FontWeight.NORMAL, 18));
				label.relocate(5, 100 + (30*i));
				strategyLabel.add(label);
			}

			canvas.getChildren().addAll(strategyType);
			canvas.getChildren().addAll(strategyLabel);

			playButton.setDisable(true);
		}
	}

	// Handles when the strategy selection combobox changes. Sets the play button to be
	// enabled or not, depending on if all of the strategies are selected.
	private class StrategyCBHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			boolean check = true;
			for (ComboBox<String> c: strategyType) {
				if (c.getValue() == "Select") check = false;
			}

			if (check) {
				playButton.setDisable(false);
			}
			else {
				playButton.setDisable(true);
			}
		}
	}

	// Handles when the play button is clicked. Starts the game with the number of players
	// and the strategies selected.
	private class ButtonStartGame implements EventHandler<ActionEvent> {
		List<Player> players = new ArrayList<Player>(); 
		boolean error = false;

		@Override
		public void handle(ActionEvent event) {
			int i = 1;
			for (ComboBox<String> c: strategyType) {
				switch (c.getValue()) {
				case "Human":
					players.add(new Player("p" + i, new Strategy0())); 
					break;
				case "Strategy 1":
					players.add(new Player("p" + i, new Strategy1())); 
					break;
				case "Strategy 2":
					players.add(new Player("p" + i, new Strategy2())); 
					break;
				case "Strategy 3":
					players.add(new Player("p" + i, new Strategy3())); 
					break;
					//case "Strategy 4":
					//players.add(new Player("p" + i, new Strategy4())); 
					//break;
				default:
					error = true;
				}
				i++;
			}

			if (!error) {
				Game game = new Game(players);
				try {
					game.start();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else {
				Print.print("Unknown strategy selected.");
			}

		}
	}
}
