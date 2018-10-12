package com.rummikub;

public class Player 
{
	private String name;
	private Rack playerRack = new Rack();
	protected Strategy strategy;
	
	public Player(String gName)
	{
		this.name = gName;
	}

	public Rack getPlayerRack() 
	{
		return playerRack;
	}

	public void fillRack(Stock stock) 
	{
		this.playerRack.setRackArray(stock.deal14Tiles());
	}

	public void printRack() 
	{
		System.out.println("Player " + this.name + " Rack is :");
		System.out.print(playerRack.toString());
	}
	
	public void getTile(Stock stock) 
	{
		this.playerRack.addTile(stock.dealTile());
	}
	
	public Meld createMeld(Tile...tiles) 
	{
		return null;
	}

	public Strategy getStrategy() 
	{
		return strategy;
	}

	public void setStrategy(Strategy strategy) 
	{
		this.strategy = strategy;
	}
	
	public void useStrategy()
	{
		play();
	}
	
	protected void play()
	{
		this.strategy.play();
	}

	public String getName() 
	{
		return name;
	}
}
