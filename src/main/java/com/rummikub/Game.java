package com.rummikub;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.pmw.tinylog.Logger;

public class Game {

	// Primitive Variables
	int turnsWithoutMoves = 0; // Keeps track of how many turns have been taken without any moves being made.
	boolean printRackMeld = false; // Turn it off so that you do not print the computers racks and melds.
	boolean rigDraw = false;
	boolean waitAferEachTurn = false; // Prompts enter after each turn
	boolean usingGui = false;
	boolean gameRunning = true;
	boolean shouldDraw = false;

	// Data Structure Variables
	List<Player> players = new ArrayList<>();
	List<Meld> meldsPlayed;

	// My data Variables
	Print printer = new Print();
	Prompt prompter = new Prompt();
	Stock stock;
	Table table;
	Player winner;
	Player currentPlayer;
	Player previousPlayer;

	Game(List<Player> players, Boolean printMelds, Boolean rigEachDraw, Boolean waitAfterTurns, Boolean GUI) {
		this.players = players;
		//this.players.add(new Player("P0",new Strategy0()));
		this.printRackMeld = printMelds;
		this.rigDraw = rigEachDraw;
		this.waitAferEachTurn = waitAfterTurns;
		this.usingGui = GUI;

		this.stock = new Stock(GUI);
		this.table = new Table(stock);
	}
	
	Game(List<Player> players, Boolean printMelds, Boolean rigEachDraw, Boolean waitAfterTurns, Boolean GUI, Stock riggedStock) {
		this(players, printMelds, rigEachDraw, waitAfterTurns, GUI);
		
		this.stock = riggedStock;
		this.table = new Table(stock);
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

		currentPlayer = table.getCurrentPlayer();
		if (usingGui) {
			Print.print("Waiting for user to click the 'Start Game!' button...");
		}
	}

	public void takeTurn() {
		shouldDraw = false;
		printer.printGameTable(table);
		Logger.info(currentPlayer.getName());
		Logger.info(currentPlayer.isHuman());// log to file
		Print.print("++++++ It is now " + currentPlayer.getName() + "'s turn: ++++++");
		Print.print("++++++ Round: " + table.getTableRound() + " ++++++");
		meldsPlayed = currentPlayer.play();

		if (currentPlayer.getPlayerRack().getSize() == Constants.ZERO_TILES) {
			gameRunning = false;
			winner = currentPlayer;
			previousPlayer = currentPlayer;
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
				shouldDraw = true;
				if (stock.getLength() == 0) {
					turnsWithoutMoves++;
				} else if (!rigDraw) {
					Print.println(currentPlayer.getName() + " draws a tile from the stock: "
							+ currentPlayer.getPlayerRack().takeTile(stock).toString());
				}
			}
			if (!rigDraw) {
				Print.println(currentPlayer.getName() + " rack size is " + currentPlayer.getPlayerRack().getSize());
				// print rack and possible melds
				System.out.println(currentPlayer.getName() + " players new hand is");
				Print.printRacktoUser(currentPlayer.getPlayerRack(), currentPlayer.isPrint_rack_meld());
				prompter.promptEnterKey(waitAferEachTurn);
			}

			if (turnsWithoutMoves >= 4) {
				Print.println("The stock is empty, and no one has played in 4 turns.");
				gameRunning = false;
			} else {
				previousPlayer = currentPlayer;
				currentPlayer = table.getNextPlayerTurn();

				if (usingGui) {
					Print.print("Waiting for user to click the 'next turn' button...");
				}
			}
		}

		if (!gameRunning) {
			this.end();
		}

	}

	public void end() {
		// Game ending ( we print an ending and maybe who won, also we can reset
		// variables and game state if needed)
		printer.printEnding(winner, waitAferEachTurn);
	}
}