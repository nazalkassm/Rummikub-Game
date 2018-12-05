package com.rummikub;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javafx.scene.image.Image;

public class Joker extends Tile {
	// Array that holds the possible values this Joker can be played as at a certain
	// time.
	// Empty list means everything
	private List<Tile> possibleTiles = new ArrayList<Tile>();
	public String name;

	public Joker(Colours colour, Ranks rank) {
		super(colour, rank, new Image(Tile.getFilename(colour, rank)));
		name = colour.getSymbol()+""+rank.getSymbol();
	}

	public Joker(String name) {
		super("J0");
		this.name = name;
	}

	public Joker() {
		this(Colours.JOKER, Ranks.JOKER);
	}

	public Joker(String string, boolean b) {
		super("J0", b);
		this.name = string;
	}

	// @overide
	public boolean isRunOn(Tile tile) {
		// For empty possible tiles return true at all times
		if (possibleTiles.isEmpty()) {
			return true;
		}
		// Loop over all the
		for (Tile possibleTile : possibleTiles) {
			if (possibleTile.isRunOn(tile))
				return true;
		}

		return false;
	}

//@overide 
	public boolean isRunOn(Joker joker) {
		// For empty possible tiles return true at all times
		if (possibleTiles.isEmpty() || joker.getPossibleTiles().isEmpty()) {
			return true;
		}
		// Loop over all the
		for (Tile possibleTile : possibleTiles) {
			// Loop over all the
			for (Tile possibleTileJ : joker.getPossibleTiles()) {
				if (possibleTile.isRunOn(possibleTileJ))
					return true;
			}
		}

		return false;
	}

	public void setPossibleTilesToAll() {
		possibleTiles.clear();
	}

	public void setPossibleTiles(Meld meldWithJoker, ArrayList<Tile> tilesOnTable) {
		possibleTiles.clear();
		// Remove the tiles in the meld from the tiles on the table
		tilesOnTable.removeAll(meldWithJoker.getTiles());
		List<Tile> tiles = meldWithJoker.getTiles();
		int jokerIndex = tiles.indexOf(this);
		Meld.MeldType typeOfMeld = Meld.checkMeldType(tiles);

		if (typeOfMeld == Meld.MeldType.RUN) {
			// If the joker is the first one get the next one
			if (jokerIndex == 0) {
				possibleTiles.add(new Tile(tiles.get(1).getColour().getSymbol(),
						Integer.toString(tiles.get(1).getRank().getValue() - 1)));
			} else {
				possibleTiles.add(new Tile(tiles.get(jokerIndex - 1).getColour().getSymbol(),
						Integer.toString(tiles.get(jokerIndex - 1).getRank().getValue() + 1)));
			}
		} else if (typeOfMeld == Meld.MeldType.SET) {
			int i = 0;
			List<Colours> possibleColors = new ArrayList<>(Arrays.asList(Colours.values()));
			for (Tile t : tiles) {
				possibleColors.remove(t.getColour());
				if (i == jokerIndex && i == 0)
					i++;
			}

			for (Colours possibleColor : possibleColors)
				possibleTiles.add(new Tile(possibleColor, tiles.get(i).getRank()));
			Set<Tile> dupTiles = findDuplicates(tilesOnTable);
			int toRemove = -1;
			for (Tile t : dupTiles) {
				for (i = 0; i < possibleTiles.size(); i++) {
					if (possibleTiles.get(i).equals(t)) {
						toRemove = i;
					}
				}
			}
			if (toRemove != -1)
				possibleTiles.remove(toRemove);

		} else {
			System.out.println("You done fucked up");
			throw new NullPointerException("Joker was not a part of a valid meld"); 
		}
	}

	private Set<Tile> findDuplicates(List<Tile> listContainingDuplicates) {
		final Set<Tile> setToReturn = new HashSet();
		final Set<String> set1 = new HashSet();

		for (Tile yourInt : listContainingDuplicates) {
			if (!set1.add(yourInt.toString())) {
				setToReturn.add(yourInt);
			}
		}
		return setToReturn;
	}

	public List<Tile> getPossibleTiles() {
		return this.possibleTiles;
	}

	@Override
	public int compareTo(Tile tile) {
		// if colours don't match just return -1
		if (possibleTiles.size() == 0)
			return -1;
		int compareValue;
		if ((compareValue = this.possibleTiles.get(0).colour.getSymbol().compareTo(tile.colour.getSymbol())) != 0) {
			return compareValue;
		}

		return Integer.compare(this.possibleTiles.get(0).getValue(), tile.getValue());
	}

	public int getValue() {
		if (this.possibleTiles.isEmpty()) {
			return -1;
		}

		return this.possibleTiles.get(0).getValue();
	}

	@Override
	public String toString() {
		return this.name;
	}

	public void setPossibleTiles(List<Tile> list) {
		possibleTiles = list;
	}

}
