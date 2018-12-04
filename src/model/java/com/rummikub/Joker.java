package com.rummikub;

import java.util.ArrayList;
import java.util.List;

public class Joker extends Tile {
	// Array that holds the possible values this Joker can be played as at a certain
	// time.
	private List<Tile> possibleTiles = new ArrayList<Tile>();

	public Joker(Colours colour, Ranks rank) {
		super(colour, rank);
		this.setJoker(true);
	}

	public Joker() {
		super("J", "0");
		this.setJoker(true);
		initalPossibleTiles();
	}

	public void initalPossibleTiles() {
		for (Colours c : Colours.values()) {
			for (Ranks r : Ranks.values()) {
				if (!(c.equals(Colours.JOKER) || r.equals(Ranks.JOKER))) {
					Joker j = new Joker(c, r);
					possibleTiles.add(j);
				}
			}
		}
	}

	public void setPossibleTiles(String[]... tilesToAdd) {
		possibleTiles.clear();

		for (String[] s : tilesToAdd) {
			possibleTiles.add(new Tile(s[0], s[1]));
		}
	}

	public List<Tile> getPossibleTiles() {
		return this.possibleTiles;
	}

}
