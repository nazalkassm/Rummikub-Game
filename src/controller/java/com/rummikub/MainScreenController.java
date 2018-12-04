package com.rummikub;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.shape.Rectangle;

public class MainScreenController implements Initializable {

	Game game;

	@FXML
	private AnchorPane root;

	@FXML
	private FlowPane table_pane;

	@FXML
	private FlowPane player0_pane;
	@FXML
	private FlowPane player1_pane;
	@FXML
	private FlowPane player2_pane;
	@FXML
	private FlowPane player3_pane;
	@FXML
	private Label player0_label;
	@FXML
	private Label player1_label;
	@FXML
	private Label player2_label;
	@FXML
	private Label player3_label;
	@FXML
	private Rectangle player0_rectangle;
	@FXML
	private Rectangle player1_rectangle;
	@FXML
	private Rectangle player2_rectangle;
	@FXML
	private Rectangle player3_rectangle;

	@FXML
	private Rectangle startGameRectangle;
	@FXML
	private Button startGameButton;
	@FXML
	private Button nextTurnButton;

	private List<FlowPane> playerPanes = new ArrayList<FlowPane>();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		List<Label> playerLabels = new ArrayList<Label>();
		List<Rectangle> playerRectangles = new ArrayList<Rectangle>();

		playerPanes.add(player0_pane);
		playerPanes.add(player1_pane);
		playerPanes.add(player2_pane);
		playerPanes.add(player3_pane);
		playerLabels.add(player0_label);
		playerLabels.add(player1_label);
		playerLabels.add(player2_label);
		playerLabels.add(player3_label);

		playerRectangles.add(player0_rectangle);
		playerRectangles.add(player1_rectangle);
		playerRectangles.add(player2_rectangle);
		playerRectangles.add(player3_rectangle);

		int max = Rummy.players.size();
		while (playerPanes.size() > max) {
			playerPanes.get(max).setVisible(false);
			playerPanes.remove(max);
			playerLabels.get(max).setVisible(false);
			playerLabels.remove(max);
			playerRectangles.get(max).setVisible(false);
			playerRectangles.remove(max);
		}

		Boolean waitAfterEachTurn = false;
		Boolean useGUI = true;
		game = new Game(Rummy.players, Rummy.testingMode, waitAfterEachTurn, useGUI);
		game.start();

		for (int i = 0; i < playerPanes.size(); i++) {
			viewTiles(game.players.get(i), playerPanes.get(i));
		}
	}

	@FXML
	public void handleNextTurn(ActionEvent event) throws Exception {
		if (startGameButton.isVisible()) {
			startGameButton.setVisible(false);
			startGameRectangle.setVisible(false);
			nextTurnButton.setDisable(false);
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
			Image img = tile.getTileImage();
			// Image img = new Image("file:src/main/resources/tiles/G4.png");
			ImageView tileImg = new ImageView(img);
			tileImg.setPreserveRatio(true);
			tileImg.setFitWidth(50);

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
				Image img = tile.getTileImage();
				// Image img = new Image("file:src/main/resources/cardsImages/JPEG/G4.jpg");
				ImageView tileImg = new ImageView(img);
				tileImg.setPreserveRatio(true);
				tileImg.setFitWidth(85);

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
