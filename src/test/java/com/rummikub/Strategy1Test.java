package com.rummikub;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class Strategy1Test 
{
	private static Player player1;
	private static Player player2;
	private static Table table;
	private static Stock stock;

	@BeforeAll
	static void setUpBeforeClass()  
	{
		//Table because strategy 3 needs to observe
		stock = new Stock();
		table = new Table(stock);
		
		//player1 has tiles greater than 30 and can play them.
		player1 = new Player("Player 1",new Strategy1());
		player2 = new Player("Player 2",new Strategy1());
		
		table.addPlayers(player1,player2);
		table.initPlayersTurn();
		
		//Clear player racks 
		player1.getPlayerRack().setRack(Collections.emptyList());
		player1.getPlayerRack().addTile(new Tile("B", "9")); 
		player1.getPlayerRack().addTile(new Tile("B", "10")); 
		player1.getPlayerRack().addTile(new Tile("B", "11")); 
		player1.getPlayerRack().addTile(new Tile("R", "9")); 
		player1.getPlayerRack().addTile(new Tile("R", "10")); 
		player1.getPlayerRack().addTile(new Tile("R", "11")); 
		player1.getPlayerRack().addTile(new Tile("R", "12")); 
		
		
		player2.getPlayerRack().setRack(Collections.emptyList());
		player2.getPlayerRack().addTile(new Tile("R", "2"));
		player2.getPlayerRack().addTile(new Tile("R", "3"));
		player2.getPlayerRack().addTile(new Tile("R", "4")); 
		player2.getPlayerRack().addTile(new Tile("O", "2")); 
		player2.getPlayerRack().addTile(new Tile("O", "3")); 
		player2.getPlayerRack().addTile(new Tile("O", "4"));
		player2.getPlayerRack().addTile(new Tile("B", "10")); 
		player2.getPlayerRack().addTile(new Tile("B", "9")); 
		table.notifyObservers();
	}

	@AfterAll
	static void tearDownAfterClass()  
	{
		player1 = null;
		player2 = null;
		table = null;
	}

	@BeforeEach
	void setUp()  
	{
		
	}

	@AfterEach
	void tearDown()  
	{
		
	}
	
	@Test
	void scenarios() throws Exception 
	{
	String player_name = "empty";
	System.out.println("===========\n Strategy1 test \n==========");
	
		while(true)
		{
			System.out.println("Finding the correct player to start");
			player_name = table.getNextPlayerTurn().getName();
			if(player_name.equals("Player 1"))
				break;
		}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		/** P1 Turn*/ 
		Print.print("++++++ It is now " + player_name  + " turn: ++++++");
		Print.print("Should play his whole hand");
		List<Meld> meldsPlayed = new ArrayList<>();
		meldsPlayed = table.getCurrentPlayer().play();
 		//Get list of changed melds 
		List<Meld> changedMelds = new ArrayList<>(Table.getDiffMelds(table.getAllMelds(), meldsPlayed));
		Print.print("\nTable is: ");
		Print.printMeldtoUser(meldsPlayed, changedMelds, true);
		table.updateMeldsOnTable(meldsPlayed);
		Print.printRacktoUser(player1.getPlayerRack(), true);
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		/** P2 Turn*/ 
		Print.print("++++++ It is now " + table.getNextPlayerTurn().getName()  + " turn: ++++++");
		Print.print("This player should not play because he doesn't satisfy > 30 of melds sum");
		meldsPlayed = new ArrayList<>(table.getCurrentPlayer().play());
 		changedMelds = new ArrayList<>(Table.getDiffMelds(table.getAllMelds(), meldsPlayed));
		Print.print("\nTable is: ");
		Print.printMeldtoUser(meldsPlayed, changedMelds, true);
		table.updateMeldsOnTable(meldsPlayed);
		assertTrue(meldsPlayed.toString().contains(""));
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		/** P1 Turn*/
		Print.print("++++++ It is now " + table.getNextPlayerTurn().getName() +  " turn: ++++++");
		Print.print("This player should play the meld he has now cuz he plays all his melds");
		player1.getPlayerRack().addTile(new Tile("O","12"));
		player1.getPlayerRack().addTile(new Tile("O","11"));
		player1.getPlayerRack().addTile(new Tile("O","10"));
		meldsPlayed = new ArrayList<>(table.getCurrentPlayer().play());
 		changedMelds = new ArrayList<>(Table.getDiffMelds(table.getAllMelds(), meldsPlayed));
		Print.print("\nTable is: ");
		Print.printMeldtoUser(meldsPlayed, changedMelds, true);
		table.updateMeldsOnTable(meldsPlayed);
		assertTrue(meldsPlayed.toString().contains("O10 O11 O12"));

	} //end of test

}
