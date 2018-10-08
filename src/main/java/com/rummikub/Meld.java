package com.rummikub;

import java.util.ArrayList;
import java.util.List;

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
		return MeldType.INVALID;
	}
}
