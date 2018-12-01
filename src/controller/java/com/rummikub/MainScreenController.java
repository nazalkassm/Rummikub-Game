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
import javafx.scene.layout.FlowPane;
import javafx.scene.shape.Rectangle;

public class MainScreenController implements Initializable {

	RummyGame game;
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

	private List<FlowPane> playerPanes = new ArrayList<FlowPane>();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		playerPanes.add(player1_pane);
		playerPanes.add(player2_pane);

		if (player3_pane.isVisible()) {
			playerPanes.add(player3_pane);

			if (player4_pane.isVisible()) {
				playerPanes.add(player4_pane);
			}
		}

		game = new RummyGame(Rummy.players);
		game.start();

		for (int i = 0; i < playerPanes.size(); i++) {
			viewTiles(game.players.get(i), playerPanes.get(i));
		}
		
		//ImageView iv1 = new ImageView(new Image("http://icons.iconarchive.com/icons/kidaubis-design/cool-heroes/128/Ironman-icon.png"));
        //iv1.relocate(table_pane.getLayoutX(), table_pane.getLayoutY());
        //table_pane.getChildren().addAll(iv1);
	}
	

	@FXML
	public void handleEndTurnBtn(ActionEvent event) {
		takeTurn();
	}

	@FXML
	public void handleStartGameBtn(ActionEvent event) {
		startGameButton.setVisible(false);

		takeTurn();
	}

	public void takeTurn() {
		if (game.gameRunning) {
			game.takeTurn();
			viewTiles(game.previousPlayer, playerPanes.get(game.previousPlayer.getNumber()));
			viewTiles(game.table, table_pane);

			if (!game.previousPlayer.isHuman()) {
				takeTurn();
			}
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

	public class RummyGame {

		// Primitive Variables
		boolean gameRunning = true;
		String pName = "";
		// Data Structure Variables
		List<Player> players = new ArrayList<>();
		List<Meld> meldsPlayed;

		// My data Variables
		Print printer = new Print();
		Prompt prompter = new Prompt();
		Stock stock = new Stock(true);
		Table table = new Table(stock);
		Player winner;
		int turnsWithoutMoves = 0;
		Player currentPlayer;
		Player previousPlayer;
		Boolean humanTurn = false;

		// Things to play with when testing
		boolean waitAferEachTurn = false; // Prompts enter after each turn
		boolean printRackMeld = Rummy.testingMode; // Turn it off so that you do not print the computers racks and
													// melds.

		RummyGame(List<Player> players) {
			this.players = players;
		}

		public void start() {
			// Start game
			printer.printIntroduction();

			// Print the racks and melds of players, yes or no.
			for (Player p : players) {
				p.setPrint_rack_meld(printRackMeld);

			}

			// Add players to the table
			for (Player player : players) {
				table.addPlayers(player);
			}

			// Initializes which player is starting and keeps track of player's turn
			table.initPlayersTurn();

			currentPlayer = table.getNextPlayerTurn();
		}

		public void takeTurn() {
			printer.printGameTable(table);
			Logger.info(currentPlayer.getName());
			Logger.info(currentPlayer.isHuman());// log to file
			Print.print("++++++ It is now " + currentPlayer.getName() + "'s turn: ++++++");
			Print.print("++++++ Round: " + table.getTableRound() + " ++++++");
			meldsPlayed = currentPlayer.play();

			if (currentPlayer.getPlayerRack().getSize() == Constants.ZERO_TILES) {
				gameRunning = false;
				winner = currentPlayer;
			} else {
				// Get list of changed melds
				List<Meld> changedMelds = new ArrayList<>(Table.getDiffMelds(table.getAllMelds(), meldsPlayed));

				// If the changed melds is not empty, then add we're updating things
				if (!(changedMelds.isEmpty())) {
					Print.print("\nTable is: ");
					Print.printMeldtoUser(meldsPlayed, changedMelds, true);

					turnsWithoutMoves = 0;

					table.updateMeldsOnTable(meldsPlayed);

					table.notifyObservers();
				} else {
					if (stock.getLength() == 0) {
						turnsWithoutMoves++;
					} else {
						Print.println(currentPlayer.getName() + " draws a tile from the stock: "
								+ currentPlayer.getPlayerRack().takeTile(stock).toString());
					}
				}
				Print.println(currentPlayer.getName() + " rack size is " + currentPlayer.getPlayerRack().getSize());
				// print rack and possible melds
				System.out.println(currentPlayer.getName() + " players new hand is");
				Print.printRacktoUser(currentPlayer.getPlayerRack(), currentPlayer.isPrint_rack_meld());
				prompter.promptEnterKey(waitAferEachTurn);

				if (turnsWithoutMoves >= 4) {
					Print.println("The stock is empty, and no one has played in 4 turns.");
					gameRunning = false;
				} else {
					previousPlayer = currentPlayer;
					currentPlayer = table.getNextPlayerTurn();
				}
			}

		}

		public void end() {
			// Game ending ( we print an ending and maybe who won, also we can reset
			// variables and game state if needed)
			printer.printEnding(winner, waitAferEachTurn);
		}
	}
}
