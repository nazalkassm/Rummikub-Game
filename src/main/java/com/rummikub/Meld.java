package com.rummikub;

import java.io.Console;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
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
	}
	
	public void addTile(Tile tile) {
		tiles.add(tile);
	}
	
	public List<Tile> getTiles() {
		return tiles;
	}
	
	static public MeldType checkMeldType(List<Tile> newTiles) {
		MeldType newMeldType = MeldType.INVALID;
		 Map<Colours, List<Tile>> tilesByColour = newTiles.stream().collect(Collectors.groupingBy(Tile::getColour));
		
		 // a meld is a set iff: 
		 // - There is only one tile per colour
		 // - Each tile of each colour is equal
		 if (tilesByColour.keySet().size() == newTiles.size() && 
			 checkEqualRanks(newTiles)) {
			 newMeldType = MeldType.SET;
		 }
		 else if (tilesByColour.keySet().size() == 1 &&
				  checkSequence(newTiles)) {
			 newMeldType = MeldType.RUN;
		 }
		 
		return newMeldType;
	}
	
	static protected boolean checkEqualRanks(List<Tile> tiles) {
		return false;
	}
	
	static protected boolean checkSequence(List<Tile> tiles) {
		return false;
	}
}
