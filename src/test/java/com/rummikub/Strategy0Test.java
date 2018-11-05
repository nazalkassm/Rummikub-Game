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

class Strategy0Test {
	
	private static List<Meld> meld1,meld2, meld3;
	private static Player player1;
	private static Player player2;
	private static Player player3;
	private static Player player4;
	static Game game;
	static Stock stock;
	static Table table;
	List<Player> players = new ArrayList<>();

	
	@BeforeAll
	static void setUpBeforeClass() throws Exception 
	{
		//player1
		player1 = new Player("Naz",new Strategy0());
		player2 = new Player("Prady",new Strategy0());
		player3 = new Player("Shiraj", new Strategy0());
		player4 = new Player("Elijah", new Strategy0());

		player1.getPlayerRack().addTile(new Tile("R", "9"));
		player1.getPlayerRack().addTile(new Tile("G", "10"));
		player1.getPlayerRack().addTile(new Tile("G", "11"));
		player1.getPlayerRack().addTile(new Tile("G", "12"));
		player1.getPlayerRack().addTile(new Tile("R", "10")); 
		player1.getPlayerRack().addTile(new Tile("R", "11")); 
		player1.getPlayerRack().addTile(new Tile("R", "12")); 
		
		
		//player2
		player2.getPlayerRack().addTile(new Tile("R", "9")); //G4
		player2.getPlayerRack().addTile(new Tile("G", "2")); //G5
		player2.getPlayerRack().addTile(new Tile("G", "3")); //G6
		player2.getPlayerRack().addTile(new Tile("G", "4")); //G6
		player2.getPlayerRack().addTile(new Tile("R", "4")); //G4
		player2.getPlayerRack().addTile(new Tile("B", "4")); //G5
		player2.getPlayerRack().addTile(new Tile("G", "4")); //G6
		player2.getPlayerRack().addTile(new Tile("O", "4")); //G6
		
		//player3
		player3.getPlayerRack().addTile(new Tile("R", "9")); //G4
		player3.getPlayerRack().addTile(new Tile("G", "2")); //G5
		player3.getPlayerRack().addTile(new Tile("G", "5")); //G6
		player3.getPlayerRack().addTile(new Tile("G", "7")); //G6
		player3.getPlayerRack().addTile(new Tile("R", "3")); //G4
		player3.getPlayerRack().addTile(new Tile("B", "11")); //G5
		player3.getPlayerRack().addTile(new Tile("G", "13")); //G6
		player3.getPlayerRack().addTile(new Tile("O", "1")); //G6
		
		//player4
		player4.getPlayerRack().addTile(new Tile("R", "9"));
		
		//meld
		meld1 = player1.getPlayerRack().getMelds();
		meld2 = player2.getPlayerRack().getMelds();
		meld3 = player3.getPlayerRack().getMelds();
		
		//test
		game = new Game();
		stock = new Stock();
		table = new Table(stock);
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception 
	{
		player1 = null;
		meld1 = null;
		meld2 = null;
		meld3 = null;
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
		//Tests getMelds()
		assertEquals(3,meld1.size());
		assertEquals(2,meld2.size());
		assertEquals(0,meld3.size());
		
		//Test initial values
		assertEquals(7,player1.getPlayerRack().getSize());
		assertEquals(8,player2.getPlayerRack().getSize());
		assertEquals(8,player3.getPlayerRack().getSize());
		
		
		//runs the execution of strategy 0 under different scenarios
		String filePath = ("src/main/resources/Strategy0Tests/test1.txt");
		String filePath2 = ("src/main/resources/Strategy0Tests/test2.txt");
		//Scenario1
		//QuickTesterWithMain.rigGame(filePath);
		//QuickTesterWithMain.rigGame(filePath2);
		
		players.add(player1);
		players.add(player2);
		players.add(player3);
		players.add(player4);

		for (Player player: players) 
		{
			table.addPlayers(player);
	    }
		
		List<Meld> meldsPlayed = player1.play();
	}
}
