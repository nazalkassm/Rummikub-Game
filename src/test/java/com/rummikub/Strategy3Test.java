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
		player1 = new Player("Naz",new Strategy3());
		player2 = new Player("Prady",new Strategy3());

		//player 1 has two nice runs should play them all.
		player1.getPlayerRack().addTile(new Tile("G", "10")); //G5
		player1.getPlayerRack().addTile(new Tile("G", "11")); //G6
		player1.getPlayerRack().addTile(new Tile("G", "12")); //G6
		player1.getPlayerRack().addTile(new Tile("R", "9")); //G4
		player1.getPlayerRack().addTile(new Tile("R", "10")); //G5
		player1.getPlayerRack().addTile(new Tile("R", "11")); //G6
		player1.getPlayerRack().addTile(new Tile("R", "12")); //G6
		
		
		//player2 does not have tiles greater than 30
		player2.getPlayerRack().addTile(new Tile("R", "9")); //G4
		player2.getPlayerRack().addTile(new Tile("G", "2")); //G5
		player2.getPlayerRack().addTile(new Tile("G", "3")); //G6
		player2.getPlayerRack().addTile(new Tile("G", "4")); //G6
		player2.getPlayerRack().addTile(new Tile("R", "4")); //G4
		player2.getPlayerRack().addTile(new Tile("B", "4")); //G5
		player2.getPlayerRack().addTile(new Tile("G", "4")); //G6
		player2.getPlayerRack().addTile(new Tile("O", "4")); //G6
		
		//player 3 
		player3.getPlayerRack().addTile(new Tile("G", "11")); //G6
		player3.getPlayerRack().addTile(new Tile("G", "12")); //G6
		player3.getPlayerRack().addTile(new Tile("R", "9"));  //G4
		player3.getPlayerRack().addTile(new Tile("R", "10")); //G5
		player3.getPlayerRack().addTile(new Tile("R", "11")); //G6
		player3.getPlayerRack().addTile(new Tile("R", "12")); //G6
		
		table.addPlayers(player1,player2,player3);
		table.notifyObservers();
		
		//meld
		meld1 = player1.getPlayerRack().getMelds();
		meld2 = player2.getPlayerRack().getMelds();
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
	{
		//Test initial values
		assertEquals(7,player1.getPlayerRack().getSize());
		assertEquals(8,player2.getPlayerRack().getSize());
		
		//player1 plays all his tiles in one shot test
		assertEquals("[G10 G11 G12 , R9 R10 R11 R12 ]", player1.play().toString());
		assertEquals(0,player1.getPlayerRack().getSize());
		
		//player2 does not play anything cuz < 30
		assertEquals(Collections.emptyList(), player2.play());
		assertEquals(8,player2.getPlayerRack().getSize());
		
		//We dont want other players to have 3 fewer so we add tiles
		player1.getPlayerRack().addTile(new Tile("G", "10")); //G5
		player1.getPlayerRack().addTile(new Tile("G", "11")); //G6
		player1.getPlayerRack().addTile(new Tile("G", "12")); //G6
		player1.getPlayerRack().addTile(new Tile("R", "9")); //G4
		player1.getPlayerRack().addTile(new Tile("R", "10")); //G5
		player1.getPlayerRack().addTile(new Tile("R", "11")); //G6
		player1.getPlayerRack().addTile(new Tile("R", "12")); //G6
		
		//table.notifyObservers(); <-- not going to notify to test that the p3 actually still plays his initial turn even if a player has 3 fewer.
		//player3 plays his initial meld of > 30
		assertEquals("[R9 R10 R11 R12 ]", player3.play());
		assertEquals(2,player2.getPlayerRack().getSize());
		//player 3 now plays again (knowing he played his first 30). 
		//He has to check if other players have 3 fewer tiles than him.
		//player 3 add 4 tiles now he has 6 , player 1 has 7 , and player 2 has 8
		player3.getPlayerRack().addTile(new Tile("R", "4")); 
		player3.getPlayerRack().addTile(new Tile("B", "4")); 
		player3.getPlayerRack().addTile(new Tile("G", "4")); 
		player3.getPlayerRack().addTile(new Tile("O", "4")); 
		
		// update the info so that player 3 knows that no other player has 3 fewer
		table.notifyObservers();
		//Size should not change because player does not play anything from his rack since not fewer than 3
		assertEquals(Collections.emptyList(),player3.play());
		assertEquals(6,player3.getPlayerRack().getSize());
		//now we make it so that there is a player with fewer 3 by doing:
		player1.play();
		
		//update lowest count
		table.notifyObservers();
		//player1 should have 0 tiles now player3 will play every meld he has :
		assertEquals(Collections.emptyList(),player3.play());
		assertEquals(2,player3.getPlayerRack().getSize());
	}
}
