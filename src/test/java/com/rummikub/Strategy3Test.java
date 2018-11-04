package com.rummikub;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pmw.tinylog.Logger;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

class Strategy3Test {
	
	private static List<Meld> meld1,meld2;
	private static Player player1;
	private static Player player2;
	private static Player player3;
	private static Table table;

	@BeforeAll
	static void setUpBeforeClass() throws Exception 
	{
	//Table because strategy 3 needs to observe
			Stock stock = new Stock();
			table = new Table(stock);
			
			//player1 has tiles greater than 30 and can play them.
			player1 = new Player("Naz",new Strategy0());
			player2 = new Player("p3",new Strategy3());
			
			table.addPlayers(player1,player2);
			table.initPlayersTurn();
			
			//Clear player racks 
			player1.getPlayerRack().setRack(Collections.emptyList());
			//player 1 has two nice runs should play them all.
			player1.getPlayerRack().addTile(new Tile("G", "10")); //G5
			player1.getPlayerRack().addTile(new Tile("G", "11")); //G6
			player1.getPlayerRack().addTile(new Tile("G", "12")); //G6
			player1.getPlayerRack().addTile(new Tile("B", "9")); //G4
			player1.getPlayerRack().addTile(new Tile("B", "10")); //G5
			player1.getPlayerRack().addTile(new Tile("B", "11")); //G6
			player1.getPlayerRack().addTile(new Tile("B", "12")); //G6
			player1.getPlayerRack().addTile(new Tile("O", "1")); //G6
			
			
			//player2 does not have tiles greater than 30
			player2.getPlayerRack().setRack(Collections.emptyList());
			player2.getPlayerRack().addTile(new Tile("R", "9")); //G4
			player2.getPlayerRack().addTile(new Tile("R", "10")); //G5
			player2.getPlayerRack().addTile(new Tile("R", "11")); //G6
			player2.getPlayerRack().addTile(new Tile("G", "2")); //G6
			player2.getPlayerRack().addTile(new Tile("R", "3")); //G4
			player2.getPlayerRack().addTile(new Tile("B", "4")); //G5
			player2.getPlayerRack().addTile(new Tile("G", "6")); //G6
			player2.getPlayerRack().addTile(new Tile("O", "6")); //G6
			table.notifyObservers();
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception 
	{
		player1 = null;
		meld1 = null;
		meld2 = null;
	}

	@BeforeEach
	void setUp() throws Exception 
	{
		
	}

	@AfterEach
	void tearDown() throws Exception 
	{
		
	}
	
	@Test
	void useStrategy_removeTiles_Test() throws IOException
	{/*p1:		
		"G", "10"
		"G", "11"
		"G", "12"
		"B", "9"
		"B", "10"
		"B", "11"
		"B", "12"
		"O", "1"
		         		         
		P2:
		"R", "9")
		"R", "10"
		"R", "11"
		"G", "2")
		"R", "3")
		"B", "4")
		"G", "6")
		"O", "6")*/
		
		/** P1 Turn*/ 
		//Player plays first
		table.updateMeldsOnTable(table.getNextPlayerTurn().play());
		
		/** P2 Turn*/ 
		//Regardless of what player plays, player 2 will get rid of initial 30 
		List<Meld> melds = new ArrayList<>(table.getNextPlayerTurn().play());
		table.updateMeldsOnTable(melds);
		//so...R9, R10, R11
		assertTrue(melds.toString().contains("R9 R10 R11"));
		
		/** P1 Turn*/
		melds = table.getNextPlayerTurn().play();
		if (!melds.isEmpty()) {
			table.updateMeldsOnTable(melds);
		}
		//Give p2 R12, R6, B6
		player2.getPlayerRack().addTile(new Tile("R", "12"));
		player2.getPlayerRack().addTile(new Tile("R", "6")); 
		player2.getPlayerRack().addTile(new Tile("B", "6")); 
		/*P2:
		"G", "2")
		"R", "3")
		"B", "4")
		"G", "6")
		"O", "6")
		"R", "12"
		"R", "6"
		"B", "6"*/
		
		/** P2 Turn*/ 
	
		//If the player 1 hand is less then or equal to 3, p3 plays all cards  
		if (player1.getPlayerRack().getSize() <= (player2.getPlayerRack().getSize()-3)) {
			melds = table.getNextPlayerTurn().play();
			//Since playing all we will be playing All 6s and the R12 so..
			assertTrue(melds.toString().contains("R9 R10 R11 R12"));
			assertTrue(melds.toString().contains("B6 G6 O6 R6"));
		} else {
			melds = table.getNextPlayerTurn().play();
			//Otherwise only playing those that require reuse so R12
			assertTrue(melds.toString().contains("R9 R10 R11 R12"));
			//Make sure the new meld wasn't played since it didn't require the 
			assertFalse(melds.toString().contains("B6 G6 O6 R6"));
		}
	}
}
