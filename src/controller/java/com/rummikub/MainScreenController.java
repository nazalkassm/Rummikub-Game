package com.rummikub;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
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
	@FXML
	private Rectangle background;
	@FXML
	private Rectangle table_rect;

	private List<FlowPane> playerPanes = new ArrayList<FlowPane>();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		init_background_images();
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
		double x_axisOfMeld = 0;
		double y_axisOfMeld = 0;
		double imgWidth = 35;

		for (Meld meld : table.getAllMelds()) {
			double x_axis = 0;
			double y_axis = 0;
			if (x_axisOfMeld + meld.getTiles().size() * imgWidth >= pane.getWidth()) {
				y_axisOfMeld += 50;
				x_axisOfMeld = 0;
				x_axis = 0;
			}
			Pane meldPane = new Pane();
			int k = 0;
			for (Tile tile : meld.getMeld()) {
				Image img = tile.getTileImage();
				// Image img = new Image("file:src/main/resources/cardsImages/JPEG/G4.jpg");
				ImageView tileImg = new ImageView(img);
				tileImg.setPreserveRatio(true);
				tileImg.setFitWidth(imgWidth);
				tileImg.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent event) {
						handleTileSelected(tileImg, tile, table);
					}
				});

				tileImg.relocate(x_axis, y_axis);
				meldPane.getChildren().add(tileImg);
				if (k == 0)
					meldPane.relocate(x_axisOfMeld, y_axisOfMeld);
				x_axis += imgWidth;
				k++;

			}
			x_axisOfMeld += meld.getTiles().size() * imgWidth + 30;
			meldPane.setPrefWidth(meld.getTiles().size() * imgWidth);
			meldPane.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					moveTile(meld, table);

				}
			});
			pane.getChildren().add(meldPane);
			x_axis += (imgWidth + 5);
		}
	}

	public void init_background_images() {
		background.setFill(new ImagePattern(new Image(Constants.BACKGROUND_IMG)));
		player0_rectangle.setFill(new ImagePattern(new Image(Constants.PLAYER_TABLE_IMG)));
		player2_rectangle.setFill(new ImagePattern(new Image(Constants.PLAYER_TABLE_IMG)));
		player1_rectangle.setFill(new ImagePattern(new Image(Constants.PLAYER_TABLE_IMG)));
		player3_rectangle.setFill(new ImagePattern(new Image(Constants.PLAYER_TABLE_IMG)));
		table_rect.setFill(new ImagePattern(new Image(Constants.TABLE_IMG)));
	}

	private void handleTileSelected(ImageView tileImg, Tile tile, Table table) {
		if (table.removing) {
			System.out.println("CLICKED TILE");
			for (Tile t : table.getAllTilesOnTable()) {

				t.selected = false;
			}
			table.removing = false;
		}
		tile.selected = !tile.selected;
		if (tile.selected) {
			tileImg.setFitWidth(tileImg.getFitWidth() - 10);
			tileImg.relocate(tileImg.getLayoutX() + 5, tileImg.getLayoutY());

		} else {
			tileImg.setFitWidth(tileImg.getFitWidth() + 10);
			tileImg.relocate(tileImg.getLayoutX() - 5, tileImg.getLayoutY());
		}
	}

	private void moveTile(Meld meld, Table table) {
		Iterator<Meld> itr = table.getAllMelds().iterator();
		boolean removed = false;
		System.out.println("CLICKED MELD");
		while (itr.hasNext()) {
			Meld currentMeld = itr.next();
			if (currentMeld != meld) {
				Iterator<Tile> tileItr = currentMeld.getMeld().iterator();
				while (tileItr.hasNext()) {
					Tile compareTile = tileItr.next();
					if (compareTile.selected) {
						removed = true;
						meld.addTile(compareTile);
						table.removing = true;

						tileItr.remove();
						compareTile.selected = false;
					}
				}
			}
		}
		
		if (removed) {
			viewTiles(Rummy.game.table, table_pane);
		}
	}

}
