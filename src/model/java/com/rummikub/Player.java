package com.rummikub;

import java.io.IOException;
import java.util.List;

public class Player {
	/** Name of player */
	private String name;
	
	/** initial 30 var*/
	boolean playedInitial30 = false; // what is this are we using it ?
	private boolean printRackMeldInPlayer = true;
	
	/** Player's rack */	
	Rack rack;
	
	/** Boolean to hold if this player can play on melds that on the table */
  public boolean canPlayOnExistingMelds = false; 
	
	/** The behaviour of the player */
	protected StrategyBehaviour behaviour;
	

	
	/**
	 * Constructor of Player 
	 * 
	 * @param name = The name of player
	 */
	public Player(String name) 
	{
		rack = new Rack();
		if(name.equals(""))
		{
			name = "p1";
		}
		this.name = name;
	}
	
	/**
	 * Constructor of Player 
	 * 
	 * @param name = The name of player
	 * @param behaviour = The behaviour of the player
	 */
	public Player(String name, StrategyBehaviour behaviour) {
		rack = new Rack();
		this.behaviour = behaviour;
		this.name = name;
	}
	
	/**
	 * Adds the player to the table
	 * @param table = The table to join
	 */
	public void playerJoinTable(Subject table) {
		this.behaviour.setSubject(table);
	}

	/**
	 * Fills the rack with 14 tiles 
	 * @param stock = The stock from which to take 14 tiles
	 */
	public void fillRack(Stock stock) {
		this.rack.setRackArray(stock.deal14Tiles());
	}

	/**
	 * Adds to the player's rack from the stock  
	 * @param stock = The stock from which to take a tile
	 */
	public void getTileFromStock(Stock stock) {
		this.rack.takeTile(stock);
	}
	
	/**
	 * Plays the player's turn on a table 
	 * @param table = The table on which to play
	 * @return 
	 * @throws IOException 
	 */
	public List<Meld> play() throws IOException 
	{
		return this.behaviour.useStrategy(this);
	}

	/**
	 * Plays the player's turn on a table 
	 * @param table = The table on which to play
	 * The method is incomplete, now it returns canPlayOnExistingMelds.
	 */
	public boolean canPlayAllTiles(Table table) 
	{
		if (this.canPlayOnExistingMelds) 
		{
			@SuppressWarnings("unused")
			List<Tile> tableTiles = table.getAllTilesOnTable();	
		} 
		return canPlayOnExistingMelds;
	}
	
	public void removeTiles(List<Meld> melds) {
		for(Meld m: melds) {
			for(Tile t: m.getMeld()) {
				this.rack.getRackArray().remove(t);
			}
		}
	}
	
	
	/**
	 * Print the rack of the player
	 */
	public void printRack() 
	{
		System.out.print(rack.toString());
	}
	
	//GETTERS AND SETTERS
	/**
	 * Returns the player rack
	 * @return Rack = The player's rack
	 */
	public Rack getPlayerRack() 
	{
		return this.rack;
	}
	
	/**
	 * set the player rack
	 * 
	 */
	public void setPlayerRack(Rack rack) 
	{
		this.rack = rack;
	}
	
	/**
	 * Return the player's name
	 * @return String = The name of the player
	 */
	public String getName() 
	{
		return name;
	}
	
  /**
   * Returns if current player is human (uses strategy0 as the indicator)
   * @return True if the player is human, false otherwise
   */
	public boolean isHuman() {
		return this.behaviour instanceof Strategy0;
	}

	public boolean isPrint_rack_meld() 
	{
		return printRackMeldInPlayer;
	}	

	public void setPrint_rack_meld(boolean print_rack_meld) 
	{
		this.printRackMeldInPlayer = print_rack_meld;
	}
	
	
}