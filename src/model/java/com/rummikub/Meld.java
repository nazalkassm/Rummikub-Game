package com.rummikub;

import static org.junit.jupiter.api.Assumptions.assumingThat;

import java.io.Console;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
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
	public int sumMeld() {
		int sum = 0;
		for(int i = 0; i < this.getMeld().size(); i++) {
			sum += this.getMeld().get(i).getValue();
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
	public static int getMaxIndex(List<Meld> list) {
		List<Integer> sums = new ArrayList<Integer>();
		for(Meld m: list) {
			sums.add(m.sumMeld());
		}
		
		Integer max = sums
				.stream()
				.mapToInt(v -> v)
				.max().orElseThrow(NoSuchElementException::new);
		
		return sums.indexOf(max);
	}
	
	public static List<Meld> getMelds(List<Tile> tileList) 
	{
		List<Meld> meldList = new ArrayList<Meld>();
//		List<Tile> originalList = new ArrayList<Tile>(tileList);
//		
//		Iterator<Tile> iterator = tileList.iterator();
//		
//		while (iterator.hasNext())
//		{
//			Tile tile = iterator.next();
//			if(tile.isJoker())
//			{
//				tileList.addAll(tile.getPossibleTiles());
//			}
//		}
		
		meldList.addAll(getRunMelds(tileList));
		meldList.addAll(getSetMelds(tileList));
		
		return meldList;
	}
	
	public static void figureOutWhereToPutJoker(List<Tile> tileList) 
	{
		
	}
	
	
	public static List<Meld> getMeldsWithTable(List<Tile> tileList) {
		//??
		List<Meld> meldList = new ArrayList<Meld>();
		//To hold all the tiles that were on the table 
		List<Tile> tilesOnTable = new ArrayList<Tile>();
		//For each of the tiles in tile list, add only those with the played on table
		//boolean true  to the tilesOnTable list
		for (Tile t: tileList) {
			if (t.getPlayedOnTable()) {
				tilesOnTable.add(t);
			}
		}
		
		//To hold all the possible meld combinations that we could play
		List<List<Meld>> combinationsOfMeld = new ArrayList<List<Meld>>();
		
		//Get all possible melds with this tileList
		List<Meld> possibleMelds = new ArrayList<>(Meld.getMelds(tileList));
	
		for (int i = 0; i < possibleMelds.size(); i++ ) {
		  List<Meld> secondLevelArrayList = new ArrayList<Meld>();
		  combinationsOfMeld.add(secondLevelArrayList);
		}
		
		int i = 0;
		//For each these tiles 
		for (Meld inititalMeldToPlay: possibleMelds) {
			Rack tempRack = new Rack();
			tempRack.setRack(tileList);
			
			Meld currMeld = inititalMeldToPlay;
			List<Meld> currentPossibleMelds = new ArrayList<Meld>();
			do {
				//Get all possible melds with this tileList
				
				//now add Meld with max sum to return melds
				combinationsOfMeld.get(i).add(currMeld);
				//pop the tiles with were added to return melds
				tempRack.removeTiles(currMeld);
				currentPossibleMelds = new ArrayList<>(tempRack.getMelds());
				
				//update possible melds to get new list of melds
				if (currentPossibleMelds.size() > 0) {
				currMeld = currentPossibleMelds.get(Meld.getMaxIndex(currentPossibleMelds));
				} else {
					break;
				}
			} while (true);
			i++;
		}
		
		int combinationToPlay = -1;
		int countPlayed = 1;
		int j = 0;
		for (List<Meld> melds: combinationsOfMeld) {

			List<Tile> tiles = new ArrayList<>();
			
			for (Meld m: melds) {
				tiles.addAll(m.getTiles());
			}
			
			if (tiles.containsAll(tilesOnTable)) {
			
				if (tiles.size() > countPlayed) {
					combinationToPlay = j;
					countPlayed = tiles.size();
				}
			}
			j++;
		}
		
		if (combinationToPlay == -1) {
			return Collections.emptyList(); 
		} 
		
		return combinationsOfMeld.get(combinationToPlay);
	}
	
	public static List<Meld> getRunMelds(List<Tile> tileList) {
		int count = 0;
		List<Meld> meldList = new ArrayList<Meld>();
		//Init array list of 2,all the sorted things in it 
		List<List<Tile>> collectedTings = new ArrayList<List<Tile>>();
		for (int i = 0; i < 2; ++i ) {
		  List<Tile> secondLevelArrayList = new ArrayList<Tile>();
		  collectedTings.add(secondLevelArrayList);
		}
		
		for (int i = 0; i < tileList.size() ; i++) {
			Tile currTile = tileList.get(i); 
			boolean containTile = false;
			//Check if the current tile's color is already in the first list
			for (Tile tile : collectedTings.get(0)) {
        if (tile.equals(currTile)) {
        	containTile = true;
        }
			}
			if (containTile) {
				collectedTings.get(1).add(currTile);
			} else {
				collectedTings.get(0).add(currTile);
			}
		}
		
		Collections.sort(collectedTings.get(0));
		Collections.sort(collectedTings.get(1));

		Meld meld = null;
		
		boolean isRunOn = false;
		for (List<Tile> tiles: collectedTings) {
			for (int i = 1; i <= tiles.size() ; i++) {
				if (i < tiles.size() && (isRunOn = tiles.get(i).isRunOn(tiles.get(i-1)))) {
					if (count == 0) {
						count = 2;
					} else {
						count++;
					}
					if (count >= 3) {
						Meld meld2 = new Meld();
						meld2.tiles = new ArrayList<Tile>(tiles.subList(i - count + 1, i+1));
						meldList.add(meld2);	
					}
					isRunOn = true;
					
				} else {
					
						
						isRunOn = false;
						count = 0;
					}
				
			}
		}
		return meldList;
	}

	/*
	 * finding set-type melds in the rack
	 * first gets rid of duplicate objects (so if there are two R5's)
	 * then compares them to other colours and returns a list of same values
	 */
	public static List<Meld> getSetMelds(List<Tile> tileList) {
	  //Initialize array list of 13, with 2 lists each of tiles
		List<List<List<Tile>>> collectedSets = new ArrayList<List<List<Tile>>>();
		for (int i = 0; i < 13; ++i ) {
		  List<List<Tile>> secondLevelArrayList = new ArrayList<List<Tile>>();
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
		
		List<Meld> setList = new ArrayList<Meld>();
		Meld meld = null;
		//Loop over all the collected sets and add all of size => 3 to setList 
		for (int i = 0; i < 13; i++) {
			for (int j = 0; j < 2; j++) {
				if (collectedSets.get(i).get(j).size() >=3 ) {
					meld = new Meld();
					meld.tiles = collectedSets.get(i).get(j);
					setList.add(meld);
				}
			}
		}
		
		return setList;
	}
	
	static public MeldType checkMeldType(List<Tile> newTiles) {
		MeldType newMeldType = MeldType.INVALID;
		List<Tile> tilesToCheck = new ArrayList<Tile>(newTiles = newTiles.stream().filter(p -> !(p instanceof Joker)).collect(Collectors.toList()));
		if (tilesToCheck.size() == 1) {
			return MeldType.RUN;
		} 
		 Map<Colours, List<Tile>> tilesByColour = tilesToCheck.stream()
				 .collect(Collectors.groupingBy(Tile::getColour));
		
		 // a meld is a set iff: 
		 // - There is only one tile per colour
		 // - Each tile of each colour is equal
		 if (tilesByColour.keySet().size() == tilesToCheck.size() && 
			 checkEqualRanks(tilesToCheck)) {
			 newMeldType = MeldType.SET;
		 }
		 // a meld is a run iff:
		 // - the tiles are of only one colour
		 // - the tiles are in a sequence
		 else if (checkSequence(tilesToCheck)) {
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
	
	@Override
	public String toString() {
		String a = "";
		for(Tile s: this.tiles) {
			a += s.toString() + " ";
		}
		return a;
	}

}
