package com.rummikub;

public class Tile {
	
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
	
	public Boolean isSameRank(Tile tile) {
		return this.rank == tile.rank;
	}

	public Boolean isSameColor(Tile tile) {
		return this.color == tile.color;
	}
	
	public Boolean equals(Tile tile) {
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
}
