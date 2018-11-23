package com.rummikub;

import java.util.Collections;
import java.util.List;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Tile implements Comparable<Tile> {

	/** The rank and colour of the tile */
	private Ranks rank;
	private Colours colour;
	private boolean playedOnTable = false;
	private boolean isJoker = false;
	private Image cardImage;
	private ImageView imageView = new ImageView(cardImage);

	// Constructor
	public Tile(Colours colour, Ranks rank) {
		this.rank = rank;
		this.colour = colour;
	};

	public Tile(Colours colour, Ranks rank, Image img) {
		this.rank = rank;
		this.colour = colour;
		cardImage = img;
	};

	public Tile(String colourSymbol, String rankSymbol) {
		// Get the rank and colour associated with the symbols and call the constructor
		this(Colours.getColourFromSymbol(colourSymbol), Ranks.getRankFromSymbol(rankSymbol));
	}

	public Tile(String colourSymbol, String rankSymbol, Image img) {
		// Get the rank and colour associated with the symbols and call the constructor
		this(Colours.getColourFromSymbol(colourSymbol), Ranks.getRankFromSymbol(rankSymbol), img);
	}

	public static String getFilename(String colourSymbol, String rankSymbol) {
		return "file:resources/cardsImages/JEPG/" + Colours.getColourFromSymbol(colourSymbol)
				+ Ranks.getRankFromSymbol(rankSymbol) + ".jpg";
	}

	public Tile(String tileString) {
		if (!Tile.verifyTile(tileString)) {
			throw new IllegalArgumentException("Invalid tile");
		}

		this.rank = Ranks.getRankFromSymbol(tileString.substring(1));
		this.colour = Colours.getColourFromSymbol(tileString.substring(0, 1).toUpperCase());
	}

	public Tile(String tileString, boolean played) {
		if (!Tile.verifyTile(tileString)) {
			throw new IllegalArgumentException("Invalid tile");
		}

		this.rank = Ranks.getRankFromSymbol(tileString.substring(1));
		this.colour = Colours.getColourFromSymbol(tileString.substring(0, 1).toUpperCase());
		this.playedOnTable = played;
	}

	@Override
	public String toString() {
		return this.colour.getSymbol() + this.rank.getSymbol();
	}

	public boolean isSameRank(Tile tile) {
		return this.rank == tile.rank;
	}

	public boolean isSameColour(Tile tile) {
		return this.colour == tile.colour;
	}

	public boolean equals(Tile tile) {
		return isSameRank((Tile) tile) && isSameColour((Tile) tile);
	}

	// Getters and Setters
	public Colours getColour() {
		return this.colour;
	}

	public Ranks getRank() {
		return this.rank;
	}

	public int getValue() {
		return this.rank.getValue();
	}

	public Image getCardImage() {
		return cardImage;
	}

	@Override
	public int compareTo(Tile tile) {
		// if colours don't match just return -1
		int compareValue;
		if ((compareValue = this.colour.getSymbol().compareTo(tile.colour.getSymbol())) != 0) {
			return compareValue;
		}

		return Integer.compare(this.getValue(), tile.getValue());
	}

	public boolean isRunOn(Tile tile) {
		// If the colours are different return false
		if (this.colour != tile.colour) {
			return false;
		}
		// Otherwise return true if the values are either +1 or -1
		return (this.getValue() == (tile.getValue() - 1) || this.getValue() == (tile.getValue() + 1));
	}

	public static Boolean verifyTile(String element) {
		if (Colours.getColourFromSymbol(element.substring(0, 1).toUpperCase()) != null
				&& Ranks.getRankFromSymbol(element.substring(1)) != null) {
			return true;
		}
		return false;
	}

	public boolean getPlayedOnTable() {
		return playedOnTable;
	}

	public void setPlayedOnTable(boolean bool) {
		this.playedOnTable = bool;
	}

	public List<Tile> getPossibleTiles() {
		return Collections.emptyList();
	}

	public boolean isJoker() {
		return isJoker;
	}

	public void setJoker(boolean isJoker) {
		this.isJoker = isJoker;
	}
}
