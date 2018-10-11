package com.rummikub;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.rummikub.Tile;

public class Rack 
{
	private List<Tile> rack = new ArrayList<Tile>();
	boolean isSorted = false;

	public int getSize() 
	{
		// TODO Auto-generated method stub
		return rack.size();
	}
	
	public List<Tile> getRackArray()
	{
		return rack;	
	}

	public void addTile(Tile tile) {
		// TODO Auto-generated method stub
		rack.add(tile);	
	}

	public Tile takeTile(Stock stock) {
		// TODO Auto-generated method stub
		Tile draw = stock.dealTile();
		this.rack.add(draw);
		return draw;
		
	}

	public void sortRack() {
		// TODO Auto-generated method stub
		Collections.sort(rack);
		isSorted = true;
	}
	
	public void getRunMelds() {
		
	}

	public void getAlikeMelds() {
		
	}
	
	public Object hasMeld() {
		// TODO Auto-generated method stub
		return null;
	}

	public void sumMeld() {
		// TODO Auto-generated method stub
	}

	public boolean hasThirty() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public String toString() {
		return null;
	}

}
