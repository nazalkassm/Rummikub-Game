package com.rummikub;

import java.util.ArrayList;
import java.util.List;


//This class is to test algorithms,data structures and all other ideas to be used in the project quickly so that we can confirm something would work.
//We might extend this class to become a SuiteClass of Junit which would run first display some important information about testing and then run all Junit tests.

public class QuickTesterWithMain 
{
	public static void main(String[] args) throws Exception
	{		
		rigGame("src/main/resources/inputFiles/test13.txt");
		
		List<String> test_list = new ArrayList<>();
		test_list.add("O");
		test_list.add("G");
		test_list.add("B");
		test_list.add("R");
		System.out.println(test_list);
		
		test_list.remove("O");
		System.out.println(test_list);
		
		test_list.remove("B");
		System.out.println(test_list);
	}
	
	public static void rigGame(String filePath) throws Exception
	{
		List<Player>players = new ArrayList<Player>();
		players.add(new Player("p0",new Strategy0()));
		players.add(new Player("p1",new Strategy1()));
		players.add(new Player("p2",new Strategy2()));
		players.add(new Player("p3",new Strategy3()));
		Boolean printAllPlayerMelds = true;
		Boolean pauseBetweenTurns = false;
		Boolean GUI = false;
		
		Game game = new Game(players, printAllPlayerMelds, pauseBetweenTurns, GUI); 
		
		FileParser.parse(filePath);
		
		if (!FileParser.inputError) 
		{
			game.stock = FileParser.stock;
			game.table = new Table(game.stock);
			
			game.start();
		}
	}
}
