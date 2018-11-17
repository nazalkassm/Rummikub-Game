package com.rummikub;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.sun.media.jfxmedia.events.PlayerStateEvent.PlayerState;

import javafx.application.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;
import sun.net.www.content.text.plain;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.*;


public class TitleScreen extends Application {

	private List<ComboBox<String>> strategyType;
	private ComboBox<String> playerCount;
	private Button playButton;
	boolean strategyPicked = false;
	
	
	
	
	public static void main(String[] args) {
		System.out.println("before");
		launch(args);
		System.out.println("after");

	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		initUI(primaryStage);
	}


	private void initUI(Stage primaryStage) {
		// TODO Auto-generated method stub
		Pane canvas = new Pane();
		
		//adding GUI elements to canvas
		addsUIElements(canvas);
	
		Scene scene = new Scene(canvas, 300, 350);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Tile Rummy Title Screen");
		primaryStage.show();
	}
	
	private void addsUIElements(Pane canvas) {
		// TODO Auto-generated method stub
		canvas.setStyle("-fx-background-color: white");
		int y_value = 50;
		
		
		Label label = new Label("Tile Rummy");
		label.setFont(Font.font("Serif", FontWeight.NORMAL, 20));
		label.relocate(30, 30);
		
		playerCount = new ComboBox<String>();
		playerCount.getItems().addAll("2", "3", "4");
		playerCount.setValue("Select");
		playerCount.relocate(75, y_value);
		
		playButton = new Button("Start");
		playButton.relocate(200, 200);
		playButton.setDisable(true);
		
		playerCount.setOnAction(new PlayerCounCBtHandler(canvas));
		playButton.setOnAction(new ButtonStartGame());
		canvas.getChildren().addAll(label, playerCount, playButton);
		
	}
	
	
	private class PlayerCounCBtHandler implements EventHandler<ActionEvent> {
		Pane canvas;
		PlayerCounCBtHandler(Pane canvas){
			this.canvas = canvas;
		}
		
		@Override
        public void handle(ActionEvent event) {
			strategyType = new ArrayList<ComboBox<String>>();
			
			int getCount = Integer.valueOf(playerCount.getValue());				
			for(int i = 0; i < getCount; i++) {
				ComboBox<String> setStrategy = new ComboBox<String>();
				setStrategy.getItems().addAll("Human", "Strategy 1", "Strategy 2", "Strategy 3");
				setStrategy.setValue("Select");
				setStrategy.relocate(100*i, 150);
				
				
				setStrategy.setOnAction(new StrategyCBHandler());
				strategyType.add(setStrategy);
			}
			
			for(ComboBox<String> c: strategyType) {
				canvas.getChildren().add(c);
			}
		}
	}
	
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
        }
    }
	
	private class ButtonStartGame implements EventHandler<ActionEvent> {
		List<Player> players = new ArrayList<Player>(); 
		
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
        				 // Error?
        		 }
        		 i++;
             }
        	 
        	 Game game = new Game(players);
        	 try {
				game.start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    }
}
