package com.rummikub;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.rummikub.Tile;

public class Rack 
{
	private List<Tile> tileList = new ArrayList<Tile>();
	boolean isSorted = false;

	public int getSize() 
	{
		// TODO Auto-generated method stub
		return tileList.size();
	}
	
	public List<Tile> getRackArray()
	{
		return tileList;	
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
	
	public void getRunMelds() {
		int count = 0;
		
		
		
		
	}
	
	public static Map<Colors, List<Tile>> getTilesByColorsAndValues(List<Tile> tileList) {
	    return tileList.stream()
	                    .collect(Collectors.groupingBy(Tile::getColour));
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
