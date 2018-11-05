package com.rummikub;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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

class Strategy2Test {
	
	private static List<Meld> meld1,meld2;
	private static Player player1;
	private static Player player2;
	private static Player player3;
	private static Table table;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		//Table because strategy 3 needs to observe
		Stock stock = new Stock();
		table = new Table(stock);
		
		//player1 has tiles greater than 30 and can play them.
		player1 = new Player("Naz",new Strategy0());
		player2 = new Player("p2",new Strategy2());
		
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
		
		
		//player2 does not have tiles greater than 30
		player2.getPlayerRack().setRack(Collections.emptyList());
		player2.getPlayerRack().addTile(new Tile("R", "9")); //G4
		player2.getPlayerRack().addTile(new Tile("R", "10")); //G5
		player2.getPlayerRack().addTile(new Tile("R", "11")); //G6
		player2.getPlayerRack().addTile(new Tile("G", "2")); //G6
		player2.getPlayerRack().addTile(new Tile("R", "3")); //G4
		player2.getPlayerRack().addTile(new Tile("B", "4")); //G5
		player2.getPlayerRack().addTile(new Tile("G", "5")); //G6
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
	void useStrategyTest() throws IOException {
		/*p1:		
		"G", "10"
		"G", "11"
		"G", "12"
		"B", "9"
		"B", "10"
		"B", "11"
		"B", "12"
		         		         
		P2:
		"R", "9")
		"R", "10"
		"R", "11"
		"G", "2")
		"R", "3")
		"B", "4")
		"G", "5")
		"O", "6")*/
		table.updateMeldsOnTable(table.getNextPlayerTurn().play());
		
		if (table.getAllMelds().isEmpty()) {
			//If the first player played something then we play our hand so...R9, R10, R11
			assertEquals("[]", table.getNextPlayerTurn().play().toString());
		} else {
			List<Meld> melds = new ArrayList<>(table.getNextPlayerTurn().play());
			System.out.println(melds.toString());
			//If the first player played something then we play our hand so...R9, R10, R11
			assertEquals("[R9 R10 R11 ]", melds.toString());
		}
		
		
	}
}
