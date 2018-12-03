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

class Strategy4Test 
{
	private static List<Meld> meld1,meld2;
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
		player1 = new Player("Strategy1 Player",new Strategy1());
		player2 = new Player("Strategy4 Player",new Strategy4());
		
		table.addPlayers(player1,player2);
		table.initPlayersTurn();
		
		//Clear player racks 
		player1.getPlayerRack().setRack(Collections.emptyList());
		player1.getPlayerRack().addTile(new Tile("B", "9")); 
		player1.getPlayerRack().addTile(new Tile("B", "10")); 
		player1.getPlayerRack().addTile(new Tile("B", "11")); 
		player1.getPlayerRack().addTile(new Tile("G", "9")); 
		player1.getPlayerRack().addTile(new Tile("O", "10")); 
		player1.getPlayerRack().addTile(new Tile("R", "11")); 
		player1.getPlayerRack().addTile(new Tile("R", "12")); 
		
		
		player2.getPlayerRack().setRack(Collections.emptyList());
		player2.getPlayerRack().addTile(new Tile("R", "9"));
		player2.getPlayerRack().addTile(new Tile("R", "10"));
		player2.getPlayerRack().addTile(new Tile("R", "11")); 
		player2.getPlayerRack().addTile(new Tile("O", "2")); 
		player2.getPlayerRack().addTile(new Tile("O", "3")); 
		player2.getPlayerRack().addTile(new Tile("O", "4"));
		player2.getPlayerRack().addTile(new Tile("G", "5")); 
		player2.getPlayerRack().addTile(new Tile("O", "6")); 
		table.notifyObservers();
	}

	@AfterAll
	static void tearDownAfterClass()  
	{
		player1 = null;
		player2 = null;
		meld1 = null;
		meld2 = null;
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
	void scenario1() throws Exception 
	{
	String player_name = "empty";
	System.out.println("===========\n Scenario1 \n==========");
	
		while(true)
		{
			System.out.println("Finding the correct player to start");
			player_name = table.getNextPlayerTurn().getName();
			if(player_name.equals("Strategy1 Player"))
				break;
		}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		/** P1 Turn*/ 
		Print.print("++++++ It is now " + player_name  + " turn: ++++++");
		List<Meld> meldsPlayed = new ArrayList<>();
		
		
		meldsPlayed = table.getCurrentPlayer().play();
		
 		//Get list of changed melds 
		List<Meld> changedMelds = new ArrayList<>(Table.getDiffMelds(table.getAllMelds(), meldsPlayed));
		Print.print("\nTable is: ");
		Print.printMeldtoUser(meldsPlayed, changedMelds, true);
		table.updateMeldsOnTable(meldsPlayed);
		
		Print.print("++++++ It is now " + table.getNextPlayerTurn().getName()  + " turn: ++++++");
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		/** P2 Turn*/ 
		meldsPlayed = new ArrayList<>(table.getCurrentPlayer().play());
 		changedMelds = new ArrayList<>(Table.getDiffMelds(table.getAllMelds(), meldsPlayed));
		Print.print("\nTable is: ");
		Print.printMeldtoUser(meldsPlayed, changedMelds, true);
		table.updateMeldsOnTable(meldsPlayed);
		assertTrue(meldsPlayed.toString().contains("R9 R10 R11"));
		assertTrue(meldsPlayed.toString().contains("O2 O3 O4"));
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		/** P1 Turn*/
		Print.print("++++++ It is now " + table.getNextPlayerTurn().getName() +  " turn: ++++++");
		meldsPlayed = new ArrayList<>(table.getCurrentPlayer().play());
 		changedMelds = new ArrayList<>(Table.getDiffMelds(table.getAllMelds(), meldsPlayed));
		Print.print("\nTable is: ");
		Print.printMeldtoUser(meldsPlayed, changedMelds, true);
		table.updateMeldsOnTable(meldsPlayed);
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		/** P2 Turn*/
		Print.print("++++++ It is now " + table.getNextPlayerTurn().getName() +  " turn: ++++++");
		Print.print("This player should not play the meld he has here because the probability of him getting an out is high (to improve his meld)");
		player2.getPlayerRack().addTile(new Tile("O", "9"));
		player2.getPlayerRack().addTile(new Tile("O", "10"));
		player2.getPlayerRack().addTile(new Tile("O", "11")); 
		meldsPlayed = new ArrayList<>(table.getCurrentPlayer().play());
 		changedMelds = new ArrayList<>(Table.getDiffMelds(table.getAllMelds(), meldsPlayed));
		Print.print("\nTable is: ");
		Print.printMeldtoUser(meldsPlayed, changedMelds, true);
		table.updateMeldsOnTable(meldsPlayed);
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		/** P1 Turn*/
		Print.print("++++++ It is now " + table.getNextPlayerTurn().getName() +  " turn: ++++++");
		Print.print("This player should play this meld to decrease the probability of the other player's outs");
		player1.getPlayerRack().addTile(new Tile("O","8"));
		player1.getPlayerRack().addTile(new Tile("G","8"));
		player1.getPlayerRack().addTile(new Tile("B","8"));
		meldsPlayed = new ArrayList<>(table.getCurrentPlayer().play());
 		changedMelds = new ArrayList<>(Table.getDiffMelds(table.getAllMelds(), meldsPlayed));
		Print.print("\nTable is: ");
		Print.printMeldtoUser(meldsPlayed, changedMelds, true);
		table.updateMeldsOnTable(meldsPlayed);
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		/** P2 Turn*/
		Print.print("++++++ It is now " + table.getNextPlayerTurn().getName() +  " turn: ++++++");
		Print.print("Let's see if he plays it now");
		player2.getPlayerRack().addTile(new Tile("O", "9"));
		player2.getPlayerRack().addTile(new Tile("O", "10"));
		player2.getPlayerRack().addTile(new Tile("O", "11")); 
		meldsPlayed = new ArrayList<>(table.getCurrentPlayer().play());
 		changedMelds = new ArrayList<>(Table.getDiffMelds(table.getAllMelds(), meldsPlayed));
		Print.print("\nTable is: ");
		Print.printMeldtoUser(meldsPlayed, changedMelds, true);
		table.updateMeldsOnTable(meldsPlayed);
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		






	} //end of test

}
