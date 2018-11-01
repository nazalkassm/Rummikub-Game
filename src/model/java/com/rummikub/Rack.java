package com.rummikub;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
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
	
	public void setRackArray(List<Tile> rackList)
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
	
	public List<Meld> getMelds(){
		return Meld.getMelds(tileList);
	}
	
	
	/*
	 * converts the tileList (the rack) to a map of tiles sorted by colours
	 * calling sortTiles() before this will ensure sort as well
     * returns a map though, needs to be converted into a List of a List
     * Source: https://stackoverflow.com/questions/52660914/searching-through-specific-attributes-of-an-arraylist-of-objects
	 */
	public static Map<Colours, List<Tile>> getTilesByColorsAndValues(List<Tile> tileList) {
	    return tileList.stream()
	                    .collect(Collectors.groupingBy(Tile::getColour));
	}
	/*
	 * sorted by values
	 */
	public static Map<Ranks, List<Tile>> getTilesByValues(List<Tile> tileList) {
	    return tileList.stream()
	                    .collect(Collectors.groupingBy(Tile::getRank));
	}
	
	/*
	 * converts the map from getTilesByColorsAndValues() to an ArrayList<List<Tile>>
	 * and returns that list
	 */
	public static List<List<Tile>> convertMaptoArrayList(Map<Colours, List<Tile>> map){
		List<List<Tile>> list = new ArrayList<List<Tile>>();
		for(Map.Entry<Colours, List<Tile>> m: map.entrySet()) {
			list.add(m.getValue());
		}
		return list;
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
