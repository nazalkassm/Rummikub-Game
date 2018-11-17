package com.rummikub;

public enum Colours {
	ORANGE("O"),
	RED("R"),
	BLUE("B"),
	GREEN("G"),
	JOKER("J");
	
	//Hold the symbol that represents the colour
	private String colourSymbol;
	
	private Colours(String colourSymbol) { 
		this.colourSymbol = colourSymbol;
	}
	
	public static Colours getColourFromSymbol(String symbol) 
	{
		//Loop over all ranks and...
		for (Colours colour : Colours.values()) 
		{
			//If the symbols match for this colour... 
			if (colour.colourSymbol.equals(symbol)) 
			{ 
				//colour is the Colour associated with the symbol
				return colour;
			}
		}
		
		//If not matched return null
		return null;
	}
	
	public String getSymbol() {
		return this.colourSymbol;
	}
}