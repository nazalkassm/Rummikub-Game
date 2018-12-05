package com.rummikub;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public class MainScreenController implements Initializable {

	@FXML
	private AnchorPane root;

	@FXML
	private Pane table_pane;

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

		for (FlowPane flowPane : playerPanes) {
			flowPane.setPadding(new Insets(10, 10, 10, 10));
			flowPane.setVgap(4);
			flowPane.setHgap(4);
		}

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

		Rummy.game.start();
		int max = Rummy.game.players.size();
		while (playerPanes.size() > max) {
			playerPanes.get(max).setVisible(false);
			playerPanes.remove(max);
			playerLabels.get(max).setVisible(false);
			playerLabels.remove(max);
			playerRectangles.get(max).setVisible(false);
			playerRectangles.remove(max);
		}

		for (int i = 0; i < playerPanes.size(); i++) {
			viewTiles(Rummy.game.players.get(i), playerPanes.get(i));
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
		if (Rummy.game.gameRunning) {
			Rummy.game.takeTurn();
			viewTiles(Rummy.game.previousPlayer, playerPanes.get(Rummy.game.previousPlayer.getNumber()));
			viewTiles(Rummy.game.table, table_pane);
			nextTurnButton.setDisable(false);
		}
	}

	public void viewTiles(Player currPlayer, FlowPane pane) {
		pane.getChildren().clear();

		for (Tile tile : currPlayer.getPlayerRack().getRackArray()) {
			Image img = tile.getTileImage();
			if (!Rummy.game.printRackMeld && !currPlayer.isHuman()) {
				img = new Image(Constants.BACK_CARD);
			}
			// Image img = new Image("file:src/main/resources/tiles/G4.png");
			ImageView tileImg = new ImageView(img);
			tileImg.setPreserveRatio(true);
			tileImg.setFitWidth(35);
			pane.getChildren().add(tileImg);
		}
	}

	public void viewTiles(Table table, Pane pane) {
		pane.getChildren().clear();
		double x_axis = 0;
		double y_axis = 0;
		double imgWidth = 35;

		for (Meld meld : table.getAllMelds()) {
			for (Tile tile : meld.getMeld()) {
				Image img = tile.getTileImage();
				// Image img = new Image("file:src/main/resources/cardsImages/JPEG/G4.jpg");
				ImageView tileImg = new ImageView(img);
				tileImg.setPreserveRatio(true);
				tileImg.setFitWidth(imgWidth);

				if (x_axis >= pane.getWidth()) {
					x_axis = 0;
					y_axis += 50;
					tileImg.relocate(x_axis, y_axis);
					pane.getChildren().add(tileImg);

				} else {
					tileImg.relocate(x_axis, y_axis);
					pane.getChildren().add(tileImg);
					x_axis += imgWidth;
				}

			}
			x_axis += (imgWidth + 5);
		}
	}
}
