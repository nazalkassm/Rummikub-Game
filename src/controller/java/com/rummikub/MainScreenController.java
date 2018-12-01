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
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;

public class MainScreenController implements Initializable {

	RummyGame game;
	@FXML
	private AnchorPane root;

	private Pane Player1 = new Pane();
	@FXML
	private Label lbl_Player1;
	@FXML
	private Label lbl_Player2;
	@FXML
	private Label lbl_Player3;
	@FXML
	private Label lbl_Player4;
	@FXML
	private FlowPane player1_pane;
	@FXML
	private FlowPane player2_pane;
	@FXML
	private FlowPane player3_pane;
	@FXML
	private FlowPane player4_pane;
	@FXML
	private Button endTurnButton;
	@FXML
	private Button startGame;

	@FXML
	private Button startGameButton;
	
	private List<Label> labels = new ArrayList<Label>();
	private List<FlowPane> playerPanes = new ArrayList<FlowPane>();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		labels.add(lbl_Player1);
		playerPanes.add(player1_pane);
		labels.add(lbl_Player2);
		playerPanes.add(player2_pane);

		if (lbl_Player3.isVisible()) {
			labels.add(lbl_Player3);
			playerPanes.add(player3_pane);

			if (lbl_Player4.isVisible()) {
				labels.add(lbl_Player4);
				playerPanes.add(player4_pane);
			}
		}
		
		
		game = new RummyGame(Rummy.players);
		game.start();
		
		drawTable();
	}

	@FXML
	public void handleEndTurnBtn(ActionEvent event) throws IOException {
		Print.print("********CALLED HANDLEENDTURNBTN()********");
		
		takeTurn();
	}
	
	@FXML
	public void handleStartGameBtn(ActionEvent event) throws IOException {
		Print.print("********CALLED HANDLESTARTGAMEBTN()********");
		startGameButton.setVisible(false);
		
		takeTurn();
	}
	
	public void takeTurn() throws IOException {
		Print.print("********CALLED TAKETURN()********");
		playerView(game.currentPlayer, playerPanes.get(game.players.indexOf(game.currentPlayer)));
		
		game.takeTurn();
		drawTable();
		
		if (!game.previousPlayer.isHuman()) {
			takeTurn();
		}
	}
	
	public void drawTable() {
		Print.print("********CALLED DRAWTABLE()********");
		for (Player p : game.players) {
			if (game.currentPlayer == p) {
				labels.get(p.getNumber()).setText("CURRENT PLAYER");
			} else {
				labels.get(p.getNumber()).setText("Player " + Integer.toString(p.getNumber()));
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
		Player previousPlayer;
		Boolean humanTurn = false;

		// Things to play with when testing
		boolean waitAferEachTurn = false; // Prompts enter after each turn
		boolean printRackMeld = true; // Turn it off so that you do not print the computers racks and melds.

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
					previousPlayer = currentPlayer;
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

	public void playerView(Player currPlayer, FlowPane pane) {
		for (int i = 0; i < currPlayer.getPlayerRack().getSize(); i++) {
			ImageView tileImg = new ImageView(currPlayer.getPlayerRack().getRackArray().get(i).getTileImage());
			tileImg.relocate(50, 50 + (5 * i));

			pane.getChildren().add(tileImg);
		}
	}

}
