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

class Strategy0Test {
	
	private static Player player1;
	private static Player player2;
	private static Table table;

	@BeforeAll
	static void setUpBeforeClass() throws Exception 
	{
			Stock stock = new Stock();
			table = new Table(stock);
			
			player1 = new Player("Naz",new Strategy0());
			player2 = new Player("p2",new Strategy2());
			
			table.addPlayers(player1,player2);
			table.initPlayersTurn();
			
			player1.getPlayerRack().setRack(Collections.emptyList());
			player1.getPlayerRack().addTile(new Tile("O", "1")); //G5
			player1.getPlayerRack().addTile(new Tile("O", "2")); //G6
			player1.getPlayerRack().addTile(new Tile("O", "5")); //G6
			player1.getPlayerRack().addTile(new Tile("O", "11")); //G4
			player1.getPlayerRack().addTile(new Tile("B", "10")); //G5
			player1.getPlayerRack().addTile(new Tile("B", "4")); //G6
			player1.getPlayerRack().addTile(new Tile("B", "11")); //G6
			player1.getPlayerRack().addTile(new Tile("R", "11")); //G6
			
			
			player2.getPlayerRack().setRack(Collections.emptyList());
			player2.getPlayerRack().addTile(new Tile("B", "11")); //G4
			player2.getPlayerRack().addTile(new Tile("B", "12")); //G5
			player2.getPlayerRack().addTile(new Tile("B", "13")); //G6
			player2.getPlayerRack().addTile(new Tile("B", "12")); //G6
			player2.getPlayerRack().addTile(new Tile("O", "12")); //G4
			player2.getPlayerRack().addTile(new Tile("R", "12")); //G5
			player2.getPlayerRack().addTile(new Tile("G", "6")); //G6
			player2.getPlayerRack().addTile(new Tile("O", "6")); //G6
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception 
	{
		player1 = null;
		player2 = null;
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
	{
		/** P1 Turn*/ 
		//Regardless of what player plays, player 2 will get rid of initial 30 
		List<Meld> melds = new ArrayList<>(table.getNextPlayerTurn().play());
		table.updateMeldsOnTable(melds);
		assertTrue(melds.toString().contains("B11 O11 R11"));
		
		/** P2 Turn*/ 
		//Player plays first
		table.updateMeldsOnTable(table.getNextPlayerTurn().play());
		
		/** P1 Turn*/
		List<Meld> tableMelds = table.getAllMelds();
		Print.printMeldtoUser(tableMelds,Collections.emptyList(),true);
		melds = table.getNextPlayerTurn().play();
		assertTrue(melds.toString().contains("B10 B11 B12 B13"));
		table.updateMeldsOnTable(melds);
	}
}
