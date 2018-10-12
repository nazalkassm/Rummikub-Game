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
	
	public  ArrayList<ArrayList<Tile>> getRunMelds() {
        int count = 0;
        ArrayList<List<Tile>> newTileList = new ArrayList<List<Tile>>();
        newTileList = convertMaptoArrayList(getTilesByColorsAndValues(tileList));
        
        ArrayList<ArrayList<Tile>> meldList = new ArrayList<ArrayList<Tile>>();
        boolean isRunOn = false;
        
        for(List<Tile> lst: newTileList) {
        	for (int i = 1; i <= lst.size() ; i++) {
                if (i < lst.size() && (isRunOn = lst.get(i).isRunOn(lst.get(i-1)))) {
                    if (count == 0) {
                        count = 2;
                    } else {
                        count++;
                    }
                    isRunOn = true;
                    
                } else {
                    
                    if (count >= 3) {
                        meldList.add(new ArrayList<Tile>(lst.subList(i - count, i)));    
                    }
                    isRunOn = false;
                    count = 0;
                }
            }
        }     
        return meldList;
    }
	
	/*
	 * converts the tileList (the rack) to a map of tiles sorted by colours
	 * calling sortTiles() before this will ensure sort as well
     * returns a map though, needs to be converted into a List of a List
	 */
	public static Map<Colors, List<Tile>> getTilesByColorsAndValues(List<Tile> tileList) {
	    return tileList.stream()
	                    .collect(Collectors.groupingBy(Tile::getColour));
	}
	
	/*
	 * converts the map from getTilesByColorsAndValues() to an ArrayList<List<Tile>>
	 * and returns that list
	 */
	public static ArrayList<List<Tile>> convertMaptoArrayList(Map<Colors, List<Tile>> map){
		ArrayList<List<Tile>> list = new ArrayList<List<Tile>>();
		for(Entry<Colors, List<Tile>> m: map.entrySet()) {
			list.add(m.getValue());
		}
		return list;
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
