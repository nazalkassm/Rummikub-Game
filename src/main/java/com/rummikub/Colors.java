package com.rummikub;

public enum Colors {
	ORANGE("O"),
	RED("R"),
	BLUE("B"),
	GREEN("G");
	
	//Hold the symbol that represents the color
	private String colorSymbol;
	
	private Colors(String colorSymbol) { 
		this.colorSymbol = colorSymbol;
	}
	
	public static Colors getColorFromSymbol(String symbol) {
		//Loop over all ranks and...
		for (Colors color : Colors.values()) {
			//If the symbols match for this color... 
			if (color.colorSymbol.equals(symbol)) { 
				//color is the Color associated with the symbol
				return color;
			}
		}
		
		//If not matched return null
		return null;
	}
	
	public String getSymbol() {
		return this.colorSymbol;
	}
}