package com.rummikub;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Joker extends Tile
{
	//Array that holds the possible values this Joker can be played as at a certain time.
	//Empty list means everything
	private List<Tile> possibleTiles = new ArrayList<Tile>();
	
	public Joker(Colours colour, Ranks rank) {
		super(colour, rank);
	}
	
	public Joker() {
		super ("J", "0");
	}
	
	//@overide 
	public boolean isRunOn(Tile tile) {
		//For empty possible tiles return true at all times 
		if (possibleTiles.isEmpty()) {
			return true;
		}
		//Loop over all the 
		for (Tile possibleTile: possibleTiles) {
			if (possibleTile.isRunOn(tile))
					return true;
		}
		
		return false;
	}
	
	public void setPossibleTiles(Meld meldWithJoker, List<Tile> tilesOnTable) {
		//Remove the tiles in the meld from the tiles on the table 
		tilesOnTable.removeAll(meldWithJoker.getTiles());
		List<Tile> tiles = meldWithJoker.getTiles();
		int jokerIndex = tiles.indexOf(this);
		if (Meld.checkMeldType(meldWithJoker.getTiles()) == Meld.MeldType.RUN) {
			//If the joker is the first one get the next one 
			if (jokerIndex == 0) {
				possibleTiles.add(new Tile(tiles.get(1).getColour().getSymbol(), Integer.toString(tiles.get(1).getRank().getValue() - 1)));
			} else {
				possibleTiles.add(new Tile(tiles.get(jokerIndex - 1).getColour().getSymbol(), Integer.toString(tiles.get(jokerIndex - 1).getRank().getValue() + 1)));
			}
		} else if (Meld.checkMeldType(meldWithJoker.getTiles()) == Meld.MeldType.SET) {
			int i = 0;
			List<Colours> possibleColors = new ArrayList<>(Arrays.asList(Colours.values()));
			for (Tile t: tiles) {
				possibleColors.remove(t.getColour());
				if (t == this)
					i++;
			}
			for (Colours possibleColor: possibleColors)
				possibleTiles.add(new Tile(possibleColor, tiles.get(i).getRank()));
		} else {
			System.out.println("You done fucked up");
      System.exit(0); 
		}
	}
	
	public List<Tile> getPossibleTiles()
	{
		return this.possibleTiles;
	}
	
}
