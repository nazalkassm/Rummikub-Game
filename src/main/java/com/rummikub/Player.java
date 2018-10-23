package com.rummikub;

public class Player 
{
	private String name;
	private Rack playerRack;
	protected PlayerBehaviour playerBehaviour;
	
	
	public Player(Stock stock,  String gName)
	{
		playerRack = new Rack();
		this.name = gName;
		for(int i = 0; i < 14; i++) {
			playerRack.addTile(stock.dealTile());
		}
	}
	
	public Player(String gName) {
		playerRack = new Rack();
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

	public PlayerBehaviour getStrategy() 
	{
		return playerBehaviour;
	}

	public void setPlayerBehaviour(PlayerBehaviour pB) 
	{
		this.playerBehaviour = pB;
	}
	
	public void useStrategy()
	{
		play();
	}
	
	protected void play()
	{
		this.playerBehaviour.play();
	}

	public String getName() 
	{
		return name;
	}
}
