package com.rummikub;

import java.util.ArrayList;
import java.util.List;

public class Player {
	/** Name of player */
	private String name;
	
	/** Player's rack */	
	private Rack rack;
	
	/** Boolean to hold if this player can play on melds that on the table */
    public boolean canPlayOnExistingMelds; 
	
	/** The behaviour of the player */
	protected Behaviour behaviour;
	
	/**
	 * Constructor of Player 
	 * 
	 * @param stock = The stock that we use to get the initial rack from
	 * @param name = The name of player
	 * @param behaviour = The behaviour the player uses 
	 * 
	 * Setting behaviour: Player(stock, "p1", new Strategy0());
	 */
	public Player(Stock stock, String name, Behaviour behaviour) 
	{
		//Init the default attributes
		this.rack = new Rack();
		this.name = name;
		this.behaviour = behaviour; 
		this.canPlayOnExistingMelds = false;
		
		//For the size of rack size (14), take a tile from stock
		for (int i = 0; i < Constants.RACK_SIZE; i++) 
		{
			this.rack.takeTile(stock);
		}
	}
	
	/**
	 * Constructor of Player 
	 * 
	 * @param name = The name of player
	 */
	public Player(String name) 
	{
		rack = new Rack();
		this.name = name;
	}
	
	/**
	 * Constructor of Player 
	 * 
	 * @param name = The name of player
	 * @param behaviour = The behaviour of the player
	 */
	public Player(String name, Behaviour behaviour) 
	{
		rack = new Rack();
		this.behaviour = behaviour;
		this.name = name;
	}

	/**
	 * Fills the rack with 14 tiles 
	 * @param stock = The stock from which to take 14 tiles
	 */
	 /*
	public void fillRack(Stock stock) {
		this.rack.setRackArray(stock.deal14Tiles());
	}*/

	/**
	 * Adds to the player's rack from the stock  
	 * @param stock = The stock from which to take a tile
	 */
	public void getTileFromStock(Stock stock) 
	{
		this.rack.takeTile(stock);
	}
	
	/**
	 * Plays the player's turn on a table 
	 * @param table = The table on which to play
	 * @return 
	 */
	public ArrayList<Meld> play() 
	{
		return this.behaviour.play();
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
			List<Tile> tableTiles = table.getAllTilesOnTable();	
		} 
		return canPlayOnExistingMelds;
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
	 * Return the player's name
	 * @return String = The name of the player
	 */
	public String getName() 
	{
		return name;
	}
}