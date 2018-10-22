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
	
	public  ArrayList<ArrayList<Tile>> getRunMelds() {
		int count = 0;
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

	/*
	 * finding set-type melds in the rack
	 * first gets rid of duplicate objects (so if there are two R5's)
	 * then compares them to other colours and returns a list of same values
	 */
	public ArrayList<ArrayList<Tile>> getSetMelds() {
		int count = 0;
		List<Tile> meldSet = new ArrayList<Tile>();
		ArrayList<ArrayList<Tile>> meldList = new ArrayList<ArrayList<Tile>>();
		
		//gets rid of duplicate objects
		List<Tile> tileList2 = new ArrayList<Tile>();
		Set<Tile> hs = new HashSet<Tile>();
		hs.addAll(tileList);
		tileList2.addAll(hs);
		
		for (int i = 0; i < tileList2.size() - 1; i++) {
			for(int j = i+1; j < tileList2.size(); j++) {
					Tile tile1 = tileList2.get(i);
					Tile tile2 = tileList2.get(j);
				if(!tile1.isSameColour(tile2) && tile1.isSameRank(tile2)) {
					if (count == 0) {
						count = 2;
						meldSet.add(tile1);
						meldSet.add(tile2);
					} else {
						count++;
						meldSet.add(tile2);
					}
				}
			}
			if(count >= 3) {
				meldList.add(new ArrayList<Tile>(meldSet));		
			}
			meldSet.clear();
			count = 0;
		}
		return meldList;
	}
	
	public Object hasMeld() {
		// TODO Auto-generated method stub
		//this should be moved to table class
		return null;
	}

	public int sumMeld(Meld list) {
		// TODO Auto-generated method stub
		int sum = 0;
		for(int i = 0; i < list.getMeld().size(); i++) {
			sum += list.getMeld().get(i).getValue();
		}
		return sum;
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
