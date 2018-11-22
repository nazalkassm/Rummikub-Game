package com.rummikub;

public enum Ranks {

	ONE("1"),
	TWO("2"),
	THREE("3"),
	FOUR("4"),
	FIVE("5"),
	SIX("6"),
	SEVEN("7"),
	EIGHT("8"),
	NINE("9"),
	TEN("10"),
	ELEVEN("11"),
	TWELVE("12"),
	THIRTEEN("13"),
	JOKER("0");
	
	//Hold the symbol and value that represents the rank 
	private String rankSymbol;
	private int rankValue;
	
	private Ranks(String rankSymbol) { 
		this.rankSymbol = rankSymbol; 
		this.rankValue = Integer.parseInt(rankSymbol); 
	}
	
	public static Ranks getRankFromSymbol(String symbol) {
		//Loop over all ranks and...
		for (Ranks rank : Ranks.values()) {
			//If the symbols match for this rank... 
			if (rank.rankSymbol.equals(symbol)) { 
				//suit is the suit associated with the symbol
				return rank;
			}
		}
		//If not matched return null
		return null;
	}
	
	public String getSymbol() {
		return this.rankSymbol;
	}
	
	public int getValue() {
		return this.rankValue;
	}
}