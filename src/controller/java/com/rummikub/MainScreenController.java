package com.rummikub;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.pmw.tinylog.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class MainScreenController implements Initializable {

	RummyGame game;
	@FXML
	private AnchorPane root;

	@FXML
	private Label lbl_Player1;
	@FXML
	private Label lbl_Player2;
	@FXML
	private Label lbl_Player3;
	@FXML
	private Label lbl_Player4;
	@FXML
	private Button endTurnButton;

	private List<Label> labels = new ArrayList<Label>();

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		game = new RummyGame(Rummy.players);
		try {
			game.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// drawHands();
		try {
			game.takeTurn();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		labels.add(lbl_Player1);
		labels.add(lbl_Player2);

		if (lbl_Player3.isVisible()) {
			labels.add(lbl_Player3);

			if (lbl_Player4.isVisible()) {
				labels.add(lbl_Player4);
			}
		}

	}

	@FXML
	public void handleEndTurnBtn(ActionEvent event) throws IOException {
		game.takeTurn();
		drawTable();

		if (!(game.currentPlayer.isHuman() && !game.table.checkNextPlayer().isHuman())) {
			handleEndTurnBtn(event);
		}
	}

	public void drawTable() {
		for (Player p : game.players) {
			if (game.currentPlayer == p) {
				labels.get(p.getNumber() - 1).setText("CURRENT PLAYER");
			} else {
				labels.get(p.getNumber() - 1).setText("Player " + Integer.toString(p.getNumber()));
			}
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
		Boolean humanTurn = false;

		// Things to play with when testing
		boolean waitAferEachTurn = false; // Prompts enter after each turn
		boolean printRackMeld = true; // Turn it off so that you do not print the computers racks and melds.

		RummyGame(List<Player> players) {
			this.players = players;
		}

		public void start() throws IOException {
			// Start game
			printer.printIntroduction();
			prompter.promptEnterKey();

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

		public void takeTurn() throws IOException {
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
					currentPlayer = table.getNextPlayerTurn();
				}
			}

		}

		public void end() throws IOException {
			// Game ending ( we print an ending and maybe who won, also we can reset
			// variables and game state if needed)
			printer.printEnding(winner, waitAferEachTurn);
		}
	}

	public void playerView() {
		for (int i = 0; i < game.currentPlayer.getPlayerRack().getSize(); i++) {
			ImageView view = new ImageView(game.currentPlayer.getPlayerRack().getRackArray().get(i).getTileImage());
			view.relocate(50, 50 + (5 * i));

			root.getChildren().add(view);
		}
	}

}
