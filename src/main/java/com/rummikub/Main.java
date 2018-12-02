package com.rummikub;

import java.util.ArrayList;
import java.util.List;

public class Main 
{
	/**This is done to use a class as a Game Conroller.
	*Using the main function as our game is a mistake 
	*Because we need to declare methods or variables to use in the Class. If we do this in Main they will be global.
	*Lastly the game would have no entity itself and live in the main function.
	*/
	public static void main(String[] args) 
	{
		List<Player>players = new ArrayList<Player>();
		
		players.add(new Player("p0",new Strategy0()));
		players.add(new Player("p1",new Strategy1()));
		players.add(new Player("p2",new Strategy2()));
		players.add(new Player("p3",new Strategy3()));
		
		Game tileRummy = new Game(players); 
		tileRummy.start();
	}
}
