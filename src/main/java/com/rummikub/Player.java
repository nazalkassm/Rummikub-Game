package com.rummikub;

import com.rummikub.Tile;

public class Player 
{
	private Rack rack;
	
	public Player()
	{
		
	}
	
	public Player(Rack r)
	{
		this.setRack(r);
	}

	public Rack getRack() 
	{
		return rack;
	}

	public void setRack(Rack rack) 
	{
		this.rack = rack;
	}

	public void fillRack(Object object) {
		// TODO Auto-generated method stub
		
	}
	
}
