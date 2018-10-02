package com.rummikub;

//import com.rummikub.*;

public class Player 
{
	private Rack playerRack;
	protected Strategy strategy;
	
	public Player()
	{
		
	}
	
	public Player(Rack r)
	{
		this.setRack(r);
	}

	public Rack getPlayerRack() 
	{
		return playerRack;
	}

	public void setRack(Rack rack) 
	{
		this.playerRack = rack;
	}

	public void fillRack(Object object) 
	{
		// TODO Auto-generated method stub
	}

	public void getTile(Object object) {
		// TODO Auto-generated method stub
		
	}

	public Strategy getStrategy() {
		return strategy;
	}

	public void setStrategy(Strategy strategy) {
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
}
