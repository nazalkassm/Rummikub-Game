package com.rummikub;

import java.io.Console;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class Meld 
{
	public enum MeldType 
	{
		SET,
		RUN,
		INVALID
	}
	
	private MeldType meldType = MeldType.INVALID;
	private List<Tile> tiles = new ArrayList<Tile>();
	
	public Meld (Tile...newTiles) {
		for (Tile tile : newTiles) {
			this.tiles.add(tile);
		}
		this.meldType = checkMeldType(this.tiles);
	}
	
	public List<Tile> getMeld() {
		return tiles;
	}
	
	public void addTile(Tile tile) {
		tiles.add(tile);
	}
	
	public List<Tile> getTiles() {
		return tiles;
	}
	
	/*
	 * finds the sum of the meld
	 */
	public int sumMeld(Meld list) {
		// TODO Auto-generated method stub
		int sum = 0;
		for(int i = 0; i < list.getMeld().size(); i++) {
			sum += list.getMeld().get(i).getValue();
		}
		return sum;
	}
	
	/*
	 * takes an array of melds
	 * adds sum of each meld into a list
	 * finds the max of that list
	 * and returns the index where that max element is
	 * because sum of each meld will be stored in the same index as its corresponding meld in the list of melds
	 */
	public int getMaxIndex(List<Meld> list) {
		List<Integer> sums = new ArrayList<Integer>();
		for(Meld m: list) {
			sums.add(m.sumMeld(m));
		}
		
		Integer max = sums
				.stream()
				.mapToInt(v -> v)
				.max().orElseThrow(NoSuchElementException::new);
		
		return sums.indexOf(max);
	}
	
	public static List<ArrayList<Tile>> getMelds(List<Tile> tileList) {
		List<ArrayList<Tile>> meldList = new ArrayList<ArrayList<Tile>>();
		meldList.addAll(getRunMelds(tileList));
		meldList.addAll(getSetMelds(tileList));
		return meldList;
	}
	
	public  static List<ArrayList<Tile>> getRunMelds(List<Tile> tileList) {
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
	public static List<ArrayList<Tile>> getSetMelds(List<Tile> tileList) {
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
	
	static public MeldType checkMeldType(List<Tile> newTiles) {
		MeldType newMeldType = MeldType.INVALID;
		 Map<Colours, List<Tile>> tilesByColour = newTiles.stream()
				 .collect(Collectors.groupingBy(Tile::getColour));
		
		 // a meld is a set iff: 
		 // - There is only one tile per colour
		 // - Each tile of each colour is equal
		 if (tilesByColour.keySet().size() == newTiles.size() && 
			 checkEqualRanks(newTiles)) {
			 newMeldType = MeldType.SET;
		 }
		 // a meld is a run iff:
		 // - the tiles are of only one colour
		 // - the tiles are in a sequence
		 else if (checkSequence(newTiles)) {
			 newMeldType = MeldType.RUN;
		 }
		 
		return newMeldType;
	}
	
	// Uses Java streams to check if each tile has the same rank.
	static protected boolean checkEqualRanks(List<Tile> tiles) {
		return tiles.stream()
		        	.filter(tile -> tile.getRank().equals(tiles.get(0).getRank()))
		        	.count() == tiles.size();
	}
	
	// sorts tiles by colour and then rank, and uses Tile::isRunOn() to check if the next tile's value is 1 greater of 1 less than.
	// Tile::isRunOn() returns false if the colour are not equal.
	static protected boolean checkSequence(List<Tile> tiles) {
		boolean check = true;
		Collections.sort(tiles);
		for (int i = 0; i < tiles.size() - 1; i++) {
			if (!tiles.get(i).isRunOn(tiles.get(i+1))) {
				check = false;
				break;
			}
		}
		return check;
	}

	/**
	 * @return the meldType
	 */
	public MeldType getMeldType() {
		return meldType;
	}

}
