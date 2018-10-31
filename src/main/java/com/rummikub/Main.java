package com.rummikub;

import java.io.IOException;

public class Main 
{
	/**This is done to use a class as a Game Conroller.
	*Using the main function as our game is a mistake 
	*Because we need to declare methods to use in the class or variables. If we do this in Main they will be global.
	*/
	public static void main(String[] args) throws IOException 
	{
	Game tileRummy = new Game(); 
	tileRummy.start();
	}
}
