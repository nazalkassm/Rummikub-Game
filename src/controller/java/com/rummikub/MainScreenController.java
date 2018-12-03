package com.rummikub;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.pmw.tinylog.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class MainScreenController implements Initializable {

	Game game;
	
	@FXML
	private AnchorPane root;

	@FXML
	private Rectangle rect_table;
	@FXML
	private FlowPane table_pane;

	@FXML
	private FlowPane player1_pane;
	@FXML
	private FlowPane player2_pane;
	@FXML
	private FlowPane player3_pane;
	@FXML
	private FlowPane player4_pane;
	@FXML
	private Button startGameButton;
	@FXML 
	private Button nextTurnButton;

	private List<FlowPane> playerPanes = new ArrayList<FlowPane>();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		playerPanes.add(player1_pane);
		playerPanes.add(player2_pane);
		playerPanes.add(player3_pane);
		playerPanes.add(player4_pane);
		
		while (playerPanes.size() > Rummy.players.size()) {
			playerPanes.remove(Rummy.players.size());
		}

		Boolean waitAfterEachTurn = false;
		Boolean useGUI = true;
		game = new Game(Rummy.players, Rummy.testingMode, waitAfterEachTurn, useGUI);
		game.start();

		for (int i = 0; i < playerPanes.size(); i++) {
			//BorderStroke b = new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT);
			playerPanes.get(i).setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
			viewTiles(game.players.get(i), playerPanes.get(i));
		}
		
		//ImageView iv1 = new ImageView(new Image("http://icons.iconarchive.com/icons/kidaubis-design/cool-heroes/128/Ironman-icon.png"));
        //iv1.relocate(table_pane.getLayoutX(), table_pane.getLayoutY());
        //table_pane.getChildren().addAll(iv1);
	}

	@FXML
	public void handleNextTurn(ActionEvent event) throws Exception {
		Print.print("Handle next turn");
		if (startGameButton.isVisible()) {
			startGameButton.setVisible(false);
		}
		
		takeTurn();
	}

	public void takeTurn() throws Exception {
		nextTurnButton.setDisable(true);
		if (game.gameRunning) {
			game.takeTurn();
			viewTiles(game.previousPlayer, playerPanes.get(game.previousPlayer.getNumber()));
			viewTiles(game.table, table_pane);
			nextTurnButton.setDisable(false);
		}
	}

	public void viewTiles(Player currPlayer, FlowPane pane) {
		pane.getChildren().clear();
		double x_axis = pane.getLayoutX();
		double y_axis = pane.getLayoutY();

		double x_axis_vertical = pane.getWidth();
		double y_axis_vertical = pane.getLayoutY();

		for (Tile tile : currPlayer.getPlayerRack().getRackArray()) {
			ImageView tileImg = new ImageView(tile.getTileImage());

			if (pane.getOrientation() == Orientation.VERTICAL) {
				tileImg.setRotate(90);

				if (y_axis_vertical >= pane.getHeight()) {
					y_axis_vertical = pane.getLayoutY();
					x_axis_vertical -= 10;
					tileImg.relocate(x_axis_vertical, y_axis_vertical);
					pane.getChildren().add(tileImg);
				} else {
					tileImg.relocate(x_axis_vertical, y_axis_vertical);
					pane.getChildren().add(tileImg);
					y_axis_vertical -= 10;
				}

			}

			else {

				if (x_axis >= pane.getWidth()) {
					x_axis = pane.getLayoutX();
					y_axis -= 10;
					tileImg.relocate(x_axis, y_axis);
					pane.getChildren().add(tileImg);
				} else {
					tileImg.relocate(x_axis, y_axis);
					pane.getChildren().add(tileImg);
					x_axis += 10;
				}
			}
		}
	}

	public void viewTiles(Table table, FlowPane pane) {
		pane.getChildren().clear();
		double x_axis = pane.getLayoutX();
		double y_axis = pane.getLayoutY();

		for (Meld meld : table.getAllMelds()) {
			for (Tile tile : meld.getMeld()) {
				//ImageView tileImg = new ImageView(tile.getTileImage());
				ImageView tileImg = new ImageView(new Image("http://icons.iconarchive.com/icons/kidaubis-design/cool-heroes/128/Ironman-icon.png"));
				if (x_axis >= pane.getWidth()) {
					x_axis = pane.getLayoutX();
					y_axis -= 10;
					tileImg.relocate(x_axis, y_axis);
					pane.getChildren().add(tileImg);
				} else {
					tileImg.relocate(x_axis, y_axis);
					pane.getChildren().add(tileImg);
					x_axis += 10;
				}
			}
			x_axis += 30;
		}
	}
}
