package com.rummikub;

import java.io.Console;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class Meld {
	public enum MeldType {
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
	public int getMaxIndex(ArrayList<Meld> list) {
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
}
