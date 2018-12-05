package com.rummikub;

import java.util.ArrayList;
import java.util.List;

public class Main {
	/**
	 * This is done to use a class as a Game Conroller. Using the main function as
	 * our game is a mistake Because we need to declare methods or variables to use
	 * in the Class. If we do this in Main they will be global. Lastly the game
	 * would have no entity itself and live in the main function.
	 * 
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		List<Player> players = new ArrayList<Player>();

		players.add(new Player("p0", new Strategy3()));
		players.add(new Player("p1", new Strategy1()));
		players.add(new Player("p2", new Strategy2()));
		players.add(new Player("p3", new Strategy3()));

		Boolean printAllPlayerMelds = false;
		Boolean rigDraw = false;
		Boolean pauseBetweenTurns = false;
		Boolean GUI = false;

		Game tileRummy = new Game(players, printAllPlayerMelds, rigDraw, pauseBetweenTurns, GUI);
		tileRummy.start();

		while (tileRummy.gameRunning) {
			tileRummy.takeTurn();
		}
	}
}
