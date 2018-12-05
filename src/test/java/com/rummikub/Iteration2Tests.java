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

import com.sun.org.apache.bcel.internal.generic.NEW;

class Iteration2Tests
{
	
	private static List<Meld> meld1,meld2;
	private static Player player1;
	private static Player player2;
	private static Player player3;
	private static Player player4;
	private static Table table;
	private static Stock stock;

	@BeforeAll
	static void setUpBeforeClass()  
	{		
		
		stock = new Stock();
		table = new Table(stock);
		
		player1 = new Player("Strategy0 Player1",new Strategy0());
		player2 = new Player("Strategy0 Player2",new Strategy0());
		player3 = new Player("Strategy0 Player3",new Strategy0());
		player4 = new Player("Strategy0 Player4",new Strategy0());
		
		
		table.addPlayers(player1,player2,player3,player4);
		table.initPlayersTurn();
		
		//Clear player racks 
		player1.getPlayerRack().setRack(Collections.emptyList());
		player1.getPlayerRack().addTile(new Tile("B", "1")); 
		player1.getPlayerRack().addTile(new Tile("B", "5")); 
		player1.getPlayerRack().addTile(new Tile("B", "6")); 
		player1.getPlayerRack().addTile(new Tile("G", "10")); 
		player1.getPlayerRack().addTile(new Tile("O", "11")); 
		player1.getPlayerRack().addTile(new Tile("R", "13")); 
		player1.getPlayerRack().addTile(new Tile("R", "1")); 
		
		
		player2.getPlayerRack().setRack(Collections.emptyList());
		player2.getPlayerRack().addTile(new Tile("R", "9"));
		player2.getPlayerRack().addTile(new Tile("R", "10"));
		player2.getPlayerRack().addTile(new Tile("R", "11")); 
		player2.getPlayerRack().addTile(new Tile("B", "2")); 
		player2.getPlayerRack().addTile(new Tile("G", "3")); 
		player2.getPlayerRack().addTile(new Tile("O", "4"));
		player2.getPlayerRack().addTile(new Tile("G", "13")); 
		player2.getPlayerRack().addTile(new Tile("O", "1"));
		
		player3.getPlayerRack().setRack(Collections.emptyList());
		player3.getPlayerRack().addTile(new Tile("B", "1")); 
		player3.getPlayerRack().addTile(new Tile("B", "5")); 
		player3.getPlayerRack().addTile(new Tile("B", "6")); 
		player3.getPlayerRack().addTile(new Tile("G", "10")); 
		player3.getPlayerRack().addTile(new Tile("O", "11")); 
		player3.getPlayerRack().addTile(new Tile("R", "13")); 
		player3.getPlayerRack().addTile(new Tile("R", "1")); 
		
		player4.getPlayerRack().setRack(Collections.emptyList());
		player4.getPlayerRack().addTile(new Tile("R", "6"));
		player4.getPlayerRack().addTile(new Tile("R", "7"));
		player4.getPlayerRack().addTile(new Tile("R", "8")); 
		player4.getPlayerRack().addTile(new Tile("O", "8")); 
		player4.getPlayerRack().addTile(new Tile("G", "8")); 
		player4.getPlayerRack().addTile(new Tile("B", "8"));
		player4.getPlayerRack().addTile(new Tile("G", "13")); 
		player4.getPlayerRack().addTile(new Tile("O", "1")); 
		
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
	/**
	 * All the functionality shown is in game. This is a test to prove that this functionality works.
	 * @throws Exception
	 */
	@Test
	void scenarios() throws Exception 
	{
		String player_name = "empty";
		System.out.println("===========\n Tests from Iteration 1 \n==========");
	
		while (!player_name.equals("Strategy0 Player1"))
		{
			System.out.println("Finding the correct player to start");
			player_name = table.getNextPlayerTurn().getName();
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
		Print.println(player1.getName() + " draws a tile from the stock: "
				+ player1.getPlayerRack().takeTile(stock).toString());
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		meldsPlayed.clear();
		/** P2 Turn*/ 
		Print.print("++++++ It is now " + table.getNextPlayerTurn().getName()  + " turn: ++++++");
		meldsPlayed = new ArrayList<>(table.getCurrentPlayer().play());
 		changedMelds = new ArrayList<>(Table.getDiffMelds(table.getAllMelds(), meldsPlayed));
		Print.print("\nTable is: ");
		Print.printMeldtoUser(meldsPlayed, changedMelds, true);
		table.updateMeldsOnTable(meldsPlayed);
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		meldsPlayed.clear();
		/** P3 Turn*/
		Print.print("++++++ It is now " + table.getNextPlayerTurn().getName() +  " turn: ++++++");
		meldsPlayed = new ArrayList<>(table.getCurrentPlayer().play());
 		changedMelds = new ArrayList<>(Table.getDiffMelds(table.getAllMelds(), meldsPlayed));
		Print.print("\nTable is: ");
		Print.printMeldtoUser(meldsPlayed, changedMelds, true);
		table.updateMeldsOnTable(meldsPlayed);
		Print.println(player1.getName() + " draws a tile from the stock: "
				+ player1.getPlayerRack().takeTile(stock).toString());
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		meldsPlayed.clear();
		/** P4 Turn*/
		Print.print("++++++ It is now " + table.getNextPlayerTurn().getName() +  " turn: ++++++"); 
		meldsPlayed = new ArrayList<>(table.getCurrentPlayer().play());
 		changedMelds = new ArrayList<>(Table.getDiffMelds(table.getAllMelds(), meldsPlayed));
		Print.print("\nTable is: ");
		Print.printMeldtoUser(meldsPlayed, changedMelds, true);
		table.updateMeldsOnTable(meldsPlayed);
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	}
}