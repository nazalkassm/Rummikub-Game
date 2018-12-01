package com.rummikub;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
	
	
	
	/**
	 * Construct the table with just a stock so that the players can be added later
	 * Note: We need to do this in order for observer pattern to work (Table needs to exist first before players)
	 * @param stock = The stock with which to play 
	 */
	Table(Stock stock) {
		this.stock = stock;
	}
	
	/**
	 * Add an N amount of players to this table
	 * @param Players...players = Any amount of players to be added to the table 
	 */
	public void addPlayers(Player...players) {
		
		//Since the game might already have players, we will start the counter the player count
		int counter = this.getPlayerCount() ;
		//Add all the players passed to the player collection
		for (Player player : players) {
			//By default all players are at 0
			this.players.put(counter, player);
			player.playerJoinTable(this);
			player.fillRack(this.stock);
			counter++;
		}
	}
	
	/**
	 * Initialize all the player's turn using the human as first turn
	 * @return Boolean = True if in sorted order by tile value, false otherwise 
	 */
	public boolean initPlayersTurn() {
		/*int turnNum = 1;
		HashMap<Integer, Player> newPlayerTurns = new HashMap<Integer, Player>();
		for (Map.Entry<Integer, Player> entry : players.entrySet()) {			
			if (entry.getValue().isHuman()) {
				newPlayerTurns.put(1, entry.getValue());
			} else {
				newPlayerTurns.put(turnNum, entry.getValue());
				turnNum++;
			}
		}
		this.players = newPlayerTurns;
		tableRound = 1;*/
		Stock tempStock = new Stock();
		int toRemove[] = {0,0};
		int toRemoveEmptyIndex = 0;
		int counter = 0;
		for (Tile t :tempStock.getStockArray()) {
			if (t.getColour() == Colours.JOKER && t.getRank() == Ranks.JOKER) {
				toRemove[toRemoveEmptyIndex] = counter;
				toRemoveEmptyIndex++;
			}
			counter++;
		}
	//	tempStock.getStockArray().remove(toRemove[0]);
	//	tempStock.getStockArray().remove(toRemove[1]-1);
		//For each player we will get the tile
		//We will temporarily set the key as the value of the tile
	  HashMap<Integer, Player> tempplayers = new HashMap<Integer, Player>(players);
		for (int i = 0; i < this.getPlayerCount(); i++) {
		  //Put the player back into the players with tile value as key
			players.put(tempStock.dealTile().getValue() + 5, players.remove(i));
		}
		
		//Putting players into tree map will sort by key (lowest to highest)
		Map<Integer, Player> sortedList = new TreeMap<Integer, Player>(players);
		Map.Entry<Integer, Player> maxEntry = null;

		for (Map.Entry<Integer, Player> entry : sortedList.entrySet()) {
		    if (maxEntry == null || entry.getKey().compareTo(maxEntry.getKey()) > 0)
		    {
		        maxEntry = entry;
		    }
		}
		
		int turnNum = 1;
		HashMap<Integer, Player> newPlayerTurns = new HashMap<Integer, Player>();
		for (Map.Entry<Integer, Player> entry : tempplayers.entrySet()) {			
			if (maxEntry.getValue() == entry.getValue()) {
				newPlayerTurns.put(0, entry.getValue());
			} else {
				newPlayerTurns.put(turnNum, entry.getValue());
				turnNum++;
			}
		}
		this.players = newPlayerTurns;
		tableRound = 1;
		
		//Set current player turn to 1
		this.currentPlayerTurn = 0;
		return true;
	}
	
	/**
	 * Add a meld to the table 
	 * @param list = A VALID meld to be added to the table 
	 */
	public boolean updateMeldsOnTable(List<Meld> list) {
		this.melds.clear();
		for (Meld m: list) {
			//Make sure the meld type isn't invalid
			if (m.getMeldType() == Meld.MeldType.INVALID) 
			{
				return false;
			}
			this.melds.add(m);
		}
		return true;
	}
	
	public void clearMelds()
	{
		this.melds.clear();
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
	
	/**
	 * Get the player who's turn it is next
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
		//After every player turn we update observers
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
		//If we have observers to remove, we can try and remove it 
		if (!this.observers.isEmpty()) {
			this.observers.remove(O);
		}
	}

	@Override
	public void notifyObservers() {
		int lowestHandCount = this.lowestTableHandCount();
		//Construct the table Info here
		TableInfo tableState = new TableInfo(lowestHandCount, this.melds);
		for (Observer observer : observers) {
			observer.update(tableState);
		}
	}

	public int lowestTableHandCount() {
		int lowestCount = Constants.STOCK_SIZE;
		for (Map.Entry<Integer, Player> entry : players.entrySet()) {			
			if (lowestCount > entry.getValue().getPlayerRack().getSize()) {
				lowestCount = entry.getValue().getPlayerRack().getSize();
			}
		}
		return lowestCount;
	}
	
	public List<Meld> getAllMelds()
	{
		return this.melds;
	}

	public static List<Meld> getDiffMelds(List<Meld> meldsOld, List<Meld> meldsNew) {
		List<Meld> difMelds = new ArrayList<Meld>();
		int newCount = 0;
		int oldCount = 0;
		for (Meld m: meldsNew) {
			newCount += m.getTiles().size();
		}
		for (Meld mOld: meldsOld) {
			oldCount += mOld.getTiles().size();
		}
		//For each new meld
		for (Meld m: meldsNew) {
		
			boolean isNewMeld = true;
			//If the old meld don't contain the new meld  
			for (Meld mOld: meldsOld) {
				if (mOld.getTiles().containsAll(m.getTiles())) {
					isNewMeld = false;
				}
			}
			
			if (isNewMeld && oldCount != newCount) {
				//Add it to diff melds if new meld
				difMelds.add(m);
			}
		}
		return difMelds;
	}
	
	public int getTableRound() {
		return this.tableRound;
	}

}
