package com.rummikub;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public class Table implements Subject {
	/** Players is indexed such that the int is the turn of the player */
	HashMap<Integer, Player> players = new HashMap<Integer, Player>();

	/** Holds the melds on the table */
	private List<Meld> melds = new ArrayList<Meld>();

	/** All the observers for the table */
	private List<Observer> observers = new ArrayList<Observer>();

	private Stock stock;
	private int currentPlayerTurn = -1;
	private int tableRound = 0;
	boolean removing = false;

	/**
	 * Construct the table with just a stock so that the players can be added later
	 * Note: We need to do this in order for observer pattern to work (Table needs
	 * to exist first before players)
	 * 
	 * @param stock = The stock with which to play
	 */
	Table(Stock stock) {
		this.stock = stock;
	}

	/**
	 * Add an N amount of players to this table
	 * 
	 * @param Players...players = Any amount of players to be added to the table
	 */
	public void addPlayers(Player... players) {

		// Since the game might already have players, we will start the counter the
		// player count
		int counter = this.getPlayerCount();
		// Add all the players passed to the player collection
		for (Player player : players) {
			// By default all players are at 0
			this.players.put(counter, player);
			player.playerJoinTable(this);
			counter++;
		}
	}

	/**
	 * Initialize all the player's turn using the human as first turn
	 * 
	 * @return Boolean = True if in sorted order by tile value, false otherwise
	 */
	public boolean initPlayersTurn() {

		int indexOfCardToGet = 0;

		// For each player we will get the tile
		// We will temporarily set the key as the value of the tile
		TreeMap<Integer, Player> sortedList = new TreeMap<Integer, Player>();
		for (int i = 0; i < this.getPlayerCount(); i++) {
			Player currP = players.get(i);
			System.out.println(currP.getName() + " got tile " + stock.getStockArray().get(indexOfCardToGet));

			// If joker or someone else already has card get the next card
			while (stock.getStockArray().get(indexOfCardToGet) instanceof Joker
					|| sortedList.get(stock.getStockArray().get(indexOfCardToGet).getValue()) != null) {
				indexOfCardToGet++;
				System.out.println("Invalid tile so getting next tile: " + stock.getStockArray().get(indexOfCardToGet));
			}

			// Put the player back into the players with tile value as key
			sortedList.put(stock.getStockArray().get(indexOfCardToGet).getValue(), currP);
			indexOfCardToGet++;
		}

		int turnNum = 0;
		HashMap<Integer, Player> newPlayerTurns = new HashMap<Integer, Player>();
		Player firstPlayer = sortedList.lastEntry().getValue();
		boolean getNextEntries = false;
		Iterator<Entry<Integer, Player>> it = players.entrySet().iterator();

		while (it.hasNext()) {
			Entry<Integer, Player> curr = it.next();
			if (curr.getValue() == firstPlayer) {
				getNextEntries = true;
				System.out.println(curr.getValue().getName() + " goes first since highest tile");
			}

			if (getNextEntries) {
				newPlayerTurns.put(turnNum, curr.getValue());
				it.remove();
				turnNum++;
			}
		}

		for (Map.Entry<Integer, Player> entry : players.entrySet()) {
			newPlayerTurns.put(turnNum, entry.getValue());
			turnNum++;
		}

		this.players = newPlayerTurns;
		for (Player p : players.values()) {
			p.fillRack(this.stock);
		}

		tableRound = 1;
		// We update observers
		this.notifyObservers();
		// Set current player turn to 1
		this.currentPlayerTurn = 0;
		return true;
	}

	/**
	 * Add a meld to the table
	 * 
	 * @param list = A VALID meld to be added to the table
	 */
	public boolean updateMeldsOnTable(List<Meld> list) {
		this.melds.clear();
		for (Meld m : list) {
			// Make sure the meld type isn't invalid
			if (m.getMeldType() == Meld.MeldType.INVALID) {
				return false;
			}
			this.melds.add(m);
		}
		return true;
	}

	public void clearMelds() {
		this.melds.clear();
	}

	/**
	 * Returns all the tiles on the meld
	 * 
	 * @return Tiles = A list of all the tiles
	 */
	public List<Tile> getAllTilesOnTable() {
		// tiles will hold all of the tiles
		List<Tile> tiles = new ArrayList<Tile>();
		// Loop over all the melds
		for (Meld meld : melds) {
			// Add all the tiles of this meld to the tiles list
			tiles.addAll(meld.getTiles());
		}
		// Return all tiles
		return tiles;
	}

	/**
	 * Get the meld at the index specified
	 * 
	 * @param index = The index of the meld to get
	 * @return Meld = The meld at the index
	 */
	public Meld getMeld(int index) {
		// If index is larger or equal to size of total melds return null
		if (this.getMeldCount() <= index) {
			return null;
		}
		// Otherwise we return the meld at the index
		return this.melds.get(index);
	}

	/**
	 * Get the number of melds on the table
	 * 
	 * @return int = The number of melds
	 */
	public int getMeldCount() {
		return this.melds.size();
	}

	/**
	 * Get the player count for the table
	 * 
	 * @return int = The player count
	 */
	public int getPlayerCount() {
		return this.players.size();
	}

	/**
	 * Get the player who's turn it is
	 * 
	 * @return Player = The player who's turn it is
	 * @return Null = If player's turn has not been initialised
	 */
	public Player getCurrentPlayer() {

		if (this.currentPlayerTurn == -1) {
			return null;
		}

		return this.players.get(this.currentPlayerTurn);
	}

	/**
	 * Get the player who's turn it is next
	 * 
	 * @return Player = The player who's turn it is next
	 * @return Null = If player's turn has not been initialised
	 */
	public Player getNextPlayerTurn() {

		if (this.currentPlayerTurn == -1) {
			return null;
		}

		if (this.currentPlayerTurn == this.getPlayerCount() - 1) {
			tableRound++;
			this.currentPlayerTurn = -1;
		}

		this.currentPlayerTurn++;
		// After every player turn we update observers
		this.notifyObservers();
		return this.players.get(this.currentPlayerTurn);
	}

	public Player checkNextPlayer() {
		int nextPlayer = this.currentPlayerTurn;
		nextPlayer = (nextPlayer + 1) % this.getPlayerCount();

		return this.players.get(nextPlayer);
	}

	@Override
	public void registerObserver(Observer O) {
		this.observers.add(O);
	}

	@Override
	public void removeObserver(Observer O) {
		// If we have observers to remove, we can try and remove it
		if (!this.observers.isEmpty()) {
			this.observers.remove(O);
		}
	}

	@Override
	public void notifyObservers() {
		int[] players_rack_count = this.getPlayersRackCount();
		// Construct the table Info here
		TableInfo tableState = new TableInfo(players_rack_count, this.melds);
		for (Observer observer : observers) {
			observer.update(tableState);
		}
	}

	public int[] getPlayersRackCount() {
		int players_count[] = new int[this.getPlayerCount()];
		int counter = 0;
		for (Map.Entry<Integer, Player> entry : players.entrySet()) {
			players_count[counter] = entry.getValue().getPlayerRack().getSize();
			counter++;
		}
		return players_count;
	}

	// To be deleted:
//	if (lowestCount > entry.getValue().getPlayerRack().getSize()) {
//		lowestCount = entry.getValue().getPlayerRack().getSize();
//	}

	public List<Meld> getAllMelds() {
		return this.melds;
	}

	public static List<Meld> getDiffMelds(List<Meld> meldsOld, List<Meld> meldsNew) {
		List<Meld> difMelds = new ArrayList<Meld>();
		int newCount = 0;
		int oldCount = 0;
		for (Meld m : meldsNew) {
			newCount += m.getTiles().size();
		}
		for (Meld mOld : meldsOld) {
			oldCount += mOld.getTiles().size();
		}
		// For each new meld
		for (Meld m : meldsNew) {

			boolean isNewMeld = true;
			// If the old meld don't contain the new meld
			for (Meld mOld : meldsOld) {
				if (mOld.getTiles().containsAll(m.getTiles())) {
					isNewMeld = false;
				}
			}

			if (isNewMeld && oldCount != newCount) {
				// Add it to diff melds if new meld
				difMelds.add(m);
			}
		}
		return difMelds;
	}

	public int getTableRound() {
		return this.tableRound;
	}

	public Memento saveToMemento() {
		return new Memento(this.melds);
	}

	public void restoreFromMemento(Memento memento) {
		this.melds = memento.getSavedMelds();
	}

	public static class Memento {
		private final List<Meld> savedMelds;

		public Memento(List<Meld> meldsToSave) {
			// Melds to save is a new list of melds
			this.savedMelds = new ArrayList<Meld>();

			for (Meld meld : meldsToSave) {
				List<Tile> tiles = new ArrayList<Tile>(meld.getTiles());

				this.savedMelds.add(new Meld(tiles.toArray(new Tile[tiles.size()])));
			}
		}

		// accessible by outer class only
		public List<Meld> getSavedMelds() {
			return savedMelds;
		}
	}

}
