package com.rummikub;

public class Tile implements Comparable<Tile> {
	
	/**The rank and color of the tile */
	private Ranks rank;
	private Colors color;
	
	//Constructor
	public Tile(Ranks rank, Colors color) {
		this.rank = rank;
		this.color = color;
	};
	
	public Tile(String rankSymbol, String colorSymbol) {
		//Get the rank and color associated with the symbols and call the constructor 
		this(Ranks.getRankFromSymbol(rankSymbol), Colors.getColorFromSymbol(colorSymbol));
	}
	
	public String toString() { 
		return this.rank.getSymbol() + this.color.getSymbol();
	}
	
	public boolean isSameRank(Tile tile) {
		return this.rank == tile.rank;
	}

	public boolean isSameColor(Tile tile) {
		return this.color == tile.color;
	}
	
	public boolean equals(Tile tile) {
		return isSameRank(tile) && isSameColor(tile);
	}
	
	//Getters and Setters
	public Colors getColour() {
		return this.color;
	}
	
	public Ranks getRank() {
		return this.rank;
	}

	public int getValue() {
		return this.rank.getValue();
	}
	
	@Override
	public int compareTo(Tile tile) {
		return Integer.compare(this.getValue(), tile.getValue());
	}

	public boolean isRunOn(Tile tile) {
		//If the colors are different return false
		if (this.color != tile.color) {
			return false;
		}
		//Otherwise return true if the values are either +1 or -1
		return (this.getValue() == (tile.getValue() - 1) || this.getValue() == (tile.getValue() + 1));
	}
}
