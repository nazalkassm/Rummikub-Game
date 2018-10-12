package com.rummikub;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import com.rummikub.Tile;

public class Rack 
{
	private List<Tile> tileList = new ArrayList<Tile>();
	boolean isSorted = false;
	
	public Rack() {};

	public int getSize() 
	{
		return tileList.size();
	}
	
	public List<Tile> getRackArray()
	{
		return tileList;	
	}
	
	public void setRackArray(ArrayList<Tile> rackList)
	{
		for(int i = 0; i < Constants.RACK_SIZE; i++)
		{
			tileList.add(rackList.get(i));
		}
	}

	public void addTile(Tile tile) {
		// TODO Auto-generated method stub
		tileList.add(tile);	
		isSorted = false;
		//sortRack();
	}

	public Tile takeTile(Stock stock) {
		// TODO Auto-generated method stub
		Tile draw = stock.dealTile();
		this.tileList.add(draw);
		isSorted = false;
		return draw;
		
	}

	public void sortRack() {
		// TODO Auto-generated method stub
		Collections.sort(tileList);
		isSorted = true;
	}
	
	public  ArrayList<ArrayList<Tile>> getRunMelds() {
		int count = 0;
		List<Tile> tileMList = new ArrayList<Tile>();
		ArrayList<ArrayList<Tile>> meldList = new ArrayList<ArrayList<Tile>>();
		boolean isRunOn = false;
		for (int i = 1; i <= tileList.size() ; i++) {
			if (i < tileList.size() && (isRunOn = tileList.get(i).isRunOn(tileList.get(i-1)))) {
				if (count == 0) {
					count = 2;
				} else {
					count++;
				}
				isRunOn = true;
				
			} else {
				
				if (count >= 3) {
					meldList.add(new ArrayList<Tile>(tileList.subList(i - count, i)));	
				}
				isRunOn = false;
				count = 0;
			}
		}
		return meldList;
	}

	public void getSetMelds() {
		
	}
	
	public Object hasMeld() {
		// TODO Auto-generated method stub
		return null;
	}

	public int sumMeld() {
		// TODO Auto-generated method stub
		return -1;
	}

	public boolean hasThirty() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public String toString() {
		String a = "";
		for(Tile s: this.tileList) {
			a += s.toString() + " ";
		}
		return a;
	}

}
