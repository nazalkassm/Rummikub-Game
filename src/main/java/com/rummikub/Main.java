package com.rummikub;

import java.io.IOException;

public class Main 
{
	/**This is done to use a class as a Game Conroller.
	*Using the main function as our game is a mistake 
	*Because we need to declare methods or variables to use in the Class. If we do this in Main they will be global.
	*Lastly the game would have no entity itself and live in the main function.
	*/
	public static void main(String[] args) throws IOException 
	{
	Game tileRummy = new Game(); 
	tileRummy.start();
	}
}
