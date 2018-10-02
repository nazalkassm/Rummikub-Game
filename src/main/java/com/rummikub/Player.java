package com.rummikub;

//import com.rummikub.*;

public class Player 
{
	private Rack playerRack;
	
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
	
}
