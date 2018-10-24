package com.rummikub;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Table {
	/** players is indexed such that the int is the turn of the player */
  HashMap<Integer, Player> players = new HashMap<Integer, Player>();
	private List<Meld> melds = new ArrayList<Meld>();
	private Stock stock;
	private int currentPlayerTurn = -1;
	
	Table(Stock stock, Player...players) {
		this.stock = stock;
		//Add all the players passed to the player collection
		int counter = 0;
		for (Player player : players) {
			//By default all players are at 0
			this.players.put(counter, player);
			counter++;
		}
	}
	
	/**
	 * Initialise all the player's turn using the stock
	 * @return Boolean = True if in sorted order by tile value, false otherwise 
	 */
	public boolean initPlayersTurn() {
		this.stock.createStock();
		//For each player we will get the tile
		//We will temporarily set the key as the value of the tile
		for (int i = 0; i < this.getPlayerCount(); i++) {
		  //Put the player back into the players with tile value as key
			players.put(this.stock.dealTile().getValue(), players.remove(i));
		}
		
		//Putting players into tree map will sort by key (lowest to highest)
		Map<Integer, Player> sortedList = new TreeMap<Integer, Player>(players);
		
		//Note that we use set the last turn first since the first entry is lowest numbered tile
		int turnNumber = this.getPlayerCount();
		Integer prevKey = 0;
		for (Map.Entry<Integer, Player> entry : sortedList.entrySet()) {
			
		  Integer key = entry.getKey();
		  //Ensure that the prev key was equal or less then the current key 
		  //then we are sorting correctly
		  if (prevKey != 0 && prevKey <= key) {
				return false;
			}
		  
		  players.put(turnNumber, players.remove(key));
		  //We've assigned the player turn number for this player, so decrement
		  turnNumber--;
		}
		
		//Set current player turn to 1
		this.currentPlayerTurn = 1;
		return true;
	}
	
	/**
	 * Add a meld to the table 
	 * @param meldToAdd = A VALID meld to be added to the table 
	 */
	public void addMeldToTable(Meld meldToAdd) {
		//Make sure the meld type isn't invalid
		if (meldToAdd.getMeldType() == Meld.MeldType.INVALID) {
			return;
		}
		
		this.melds.add(meldToAdd);
	}
	
  /**
   * Returns all the tiles on the meld
   * @return Tiles = A list of all the tiles 
   */
	public List<Tile> getAllTilesOnTable() {
		//tiles will hold all of the tiles 
		List<Tile> tiles = new ArrayList<Tile>();
		//Loop over all the melds 
		for (Meld meld : melds) {
			//Add all the tiles of this meld to the tiles list
			tiles.addAll(meld.getTiles());
		}
		//Return all tiles 
		return tiles;
	}
	
	/**
	 * Get the meld at the index specified
	 * @param index = The index of the meld to get
	 * @return Meld = The meld at the index
	 */
	public Meld getMeld(int index) {
		//If index is larger or equal to size of total melds return null
		if (this.getMeldCount() <= index) {
			return null;
		}
		//Otherwise we return the meld at the index
		return this.melds.get(index);
	}
	
	/**
	 * Get the number of melds on the table
	 * @return int = The number of melds 
	 */
	public int getMeldCount() {
		return this.melds.size();
	}
	
	/**
	 * Get the player count for the table
	 * @return int = The player count
	 */
	public int getPlayerCount() {
		return this.players.size();
	}
	
	/**
	 * Get the player who's turn it is 
	 * @return Player = The player who's turn it is 
	 * @return Null = If player's turn has not been initialised
	 */
	public Player getCurrentPlayer() {
		if (this.currentPlayerTurn == -1) {
			return null;
		}
		
		return this.players.get(this.currentPlayerTurn); 	
	}
}
