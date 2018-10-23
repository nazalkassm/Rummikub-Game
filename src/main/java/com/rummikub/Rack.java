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
	
	public List<ArrayList<Tile>> getMelds() {
		List<ArrayList<Tile>> meldList = new ArrayList<ArrayList<Tile>>();
		meldList.addAll(this.getRunMelds());
		meldList.addAll(this.getSetMelds());
		return meldList;
	}
	
	public  List<ArrayList<Tile>> getRunMelds() {
		int count = 0;
		List<ArrayList<Tile>> meldList = new ArrayList<ArrayList<Tile>>();
		boolean isRunOn = false;
		for (int i = 1; i <= tileList.size() ; i++) {
			if (i < tileList.size() && (isRunOn = tileList.get(i).isRunOn(tileList.get(i-1)))) {
				if (count == 0) {
					count = 2;
				} else {
					count++;
				}
				isRunOn = true;
				if (count >= 3) {
				//	meldList.add(new ArrayList<Tile>(tileList.subList(i - count, i)));	
				}
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

	/*
	 * finding set-type melds in the rack
	 * first gets rid of duplicate objects (so if there are two R5's)
	 * then compares them to other colours and returns a list of same values
	 */
	public List<ArrayList<Tile>> getSetMelds() {
	  //Initialize array list of 13, with 2 lists each of tiles
		List<ArrayList<ArrayList<Tile>>> collectedSets = new ArrayList<ArrayList<ArrayList<Tile>>>();
		for (int i = 0; i < 13; ++i ) {
		  ArrayList<ArrayList<Tile>> secondLevelArrayList = new ArrayList<ArrayList<Tile>>();
		  collectedSets.add(secondLevelArrayList);
		  for (int j = 0; j < 2; ++j ) {
		    secondLevelArrayList.add(new ArrayList<Tile>());
		  }
		}
		//Loop over all the tiles 
		for (int i = 0; i < tileList.size(); i++) {
			Tile currTile = tileList.get(i);
			//We use the value -1 as the respective index in the collectedSets
			//Ex: If currTile is O4, then we would use collectedSets[3] to store all 4's
			int index = currTile.getValue() - 1;
			boolean containColor = false;
			//Check if the current tile's color is already in the first list
			for (Tile tile : collectedSets.get(index).get(0)) {
        if (tile.isSameColour(currTile)) {
        	containColor = true;
        }
			}
			
			//If the color is in the first list then add it to the second list 
			if (containColor) {
				collectedSets.get(index).get(1).add(currTile);
			} 
			//Otherwise we add it to the first list
			else {
				collectedSets.get(index).get(0).add(currTile);
			}
		}
		
		List<ArrayList<Tile>> setList = new ArrayList<ArrayList<Tile>>();
		//Loop over all the collected sets and add all of size => 3 to setList 
		for (int i = 0; i < 13; i++) {
			for (int j = 0; j < 2; j++) {
				if (collectedSets.get(i).get(j).size() >=3 ) {
					setList.add(collectedSets.get(i).get(j));
				}
			}
		}
		
		return setList;
	}
	
	public Object hasMeld() {
		// TODO Auto-generated method stub
		//this should be moved to table class
		return null;
	}

	

	public boolean hasThirty() {
		// TODO Auto-generated method stub
		return false;
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
	public static ArrayList<List<Tile>> convertMaptoArrayList(Map<Colours, List<Tile>> map){
		ArrayList<List<Tile>> list = new ArrayList<List<Tile>>();
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
