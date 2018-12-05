package com.rummikub;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.pmw.tinylog.Logger;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Game {

	// Primitive Variables
	int turnsWithoutMoves = 0; // Keeps track of how many turns have been taken without any moves being made.
	boolean printRackMeld = false; // Turn it off so that you do not print the computers racks and melds.
	boolean rigDraw = false;
	boolean waitAferEachTurn = false; // Prompts enter after each turn
	boolean usingGui = false;
	boolean gameRunning = true;
	boolean shouldDraw = false;
	long start = System.nanoTime();
	long finish = System.nanoTime();
	boolean manualStart =false;
	Player.Memento playerMomento1 ;
	Table.Memento tableMomento1 ;
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
		this.printRackMeld = printMelds;
		this.rigDraw = rigEachDraw;
		this.waitAferEachTurn = waitAfterTurns;
		this.usingGui = GUI;
		
		this.stock = new Stock(GUI);
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
		previousPlayer = table.getCurrentPlayer();
		playerMomento1 = currentPlayer.saveToMemento();
		tableMomento1 = table.saveToMemento();
		
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
		//The player Hand we want to save
		
		meldsPlayed = currentPlayer.play();
	
		if (meldsPlayed != null || manualStart || ((float)(finish - start)) / 1_000_000_000.0 > 60) {
			boolean valid = true;
			if (((float)(finish - start)) / 1_000_000_000.0 > 60) {
				valid = false;
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Warning!");
				alert.setContentText("Time is over 60 seconds. Resetting table ");      
				alert.showAndWait();
			} 
			 if (currentPlayer.isHuman()) {
				List<Tile> tilesOnTable = new ArrayList<Tile>();
				
				//If player has things that were played 
				for (Tile t : currentPlayer.getPlayerRack().getRackArray()) {
					if (t.getPlayedOnTable()) {
						valid = false;
					}
				}
				for (Meld m: table.getAllMelds()) {
					if (Meld.checkMeldType(m.getTiles()) == Meld.MeldType.INVALID) {
							valid = false;
					}
				}
				if (!currentPlayer.canPlayOnExistingMelds) {
					int sum = 0;
					for (Meld m: table.getAllMelds()) {
						for (Tile t: m.getTiles()) {
							if (!t.getPlayedOnTable()) {
								if (t instanceof Joker) {
									((Joker) t).setPossibleTiles(m, (ArrayList<Tile>) table.getAllTilesOnTable());
								}
								sum+= t.getValue();	
							}
						}
					}
					if (sum >= 30) {
							currentPlayer.canPlayOnExistingMelds = true;
							
					} else {
						valid = false;
					}
				}
			 if (!valid) {
			currentPlayer.restoreFromMemento(playerMomento1);
			table.restoreFromMemento(tableMomento1);
			meldsPlayed = Collections.emptyList();
			 } else {
					for (Meld m: table.getAllMelds()) {
						for (Tile t: m.getTiles()) {
							if (t instanceof Joker) {
								((Joker) t).setPossibleTiles(m, (ArrayList<Tile>) table.getAllTilesOnTable());
							}
							t.setPlayedOnTable(true);
						}
						
						}
					}
			 }
		if (currentPlayer.getPlayerRack().getSize() == Constants.ZERO_TILES) {
			gameRunning = false;
			winner = currentPlayer;
		} else {
			// Get list of changed melds
			List<Meld> changedMelds = new ArrayList<>(Table.getDiffMelds(table.getAllMelds(), meldsPlayed));
			start = System.nanoTime();
			
			// If the changed melds is not empty, then add we're updating things
			if (!(changedMelds.isEmpty())) {
				Print.print("\nTable is: ");
				Print.printMeldtoUser(meldsPlayed, changedMelds, true);

				turnsWithoutMoves = 0;

				table.updateMeldsOnTable(meldsPlayed);

				table.notifyObservers();
			} else {
				table.notifyObservers();
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
				//The player Hand we want to save
				Player.Memento playerMomento1 = currentPlayer.saveToMemento();
				Table.Memento tableMomento1 = table.saveToMemento();
				if (usingGui) {
					Print.print("Waiting for user to click the 'next turn' button...");
				}
			}
		}
		} else {
			if (Rummy.game.currentPlayer.isHuman()) {
				System.out.println("HUMAN PLEASE PLAY");
					finish = System.nanoTime();
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