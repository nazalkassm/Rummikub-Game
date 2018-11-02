package com.rummikub;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
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


	@BeforeAll
	static void setUpBeforeClass() throws Exception 
	{
		//player1
		player1 = new Player("Naz",new Strategy0());
		player2 = new Player("Prady",new Strategy0());
		player3 = new Player("zz", new Strategy0());

		player1.getPlayerRack().addTile(new Tile("R", "9")); //G4
		player1.getPlayerRack().addTile(new Tile("G", "10")); //G5
		player1.getPlayerRack().addTile(new Tile("G", "11")); //G6
		player1.getPlayerRack().addTile(new Tile("G", "12")); //G6
		player1.getPlayerRack().addTile(new Tile("R", "9")); //G4
		player1.getPlayerRack().addTile(new Tile("R", "10")); //G5
		player1.getPlayerRack().addTile(new Tile("R", "11")); //G6
		player1.getPlayerRack().addTile(new Tile("R", "12")); //G6
		
		
		//player2
		player2.getPlayerRack().addTile(new Tile("R", "9")); //G4
		player2.getPlayerRack().addTile(new Tile("G", "2")); //G5
		player2.getPlayerRack().addTile(new Tile("G", "3")); //G6
		player2.getPlayerRack().addTile(new Tile("G", "4")); //G6
		player2.getPlayerRack().addTile(new Tile("R", "4")); //G4
		player2.getPlayerRack().addTile(new Tile("B", "4")); //G5
		player2.getPlayerRack().addTile(new Tile("G", "4")); //G6
		player2.getPlayerRack().addTile(new Tile("O", "4")); //G6
		
		player3.getPlayerRack().addTile(new Tile("R", "9")); //G4
		player3.getPlayerRack().addTile(new Tile("G", "2")); //G5
		player3.getPlayerRack().addTile(new Tile("G", "5")); //G6
		player3.getPlayerRack().addTile(new Tile("G", "7")); //G6
		player3.getPlayerRack().addTile(new Tile("R", "3")); //G4
		player3.getPlayerRack().addTile(new Tile("B", "11")); //G5
		player3.getPlayerRack().addTile(new Tile("G", "13")); //G6
		player3.getPlayerRack().addTile(new Tile("O", "1")); //G6
		
		
		
		
		//meld
		meld1 = player1.getPlayerRack().getMelds();
		meld2 = player2.getPlayerRack().getMelds();
		meld3 = player3.getPlayerRack().getMelds();
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
		List<Meld> list1 = player3.play(); 
		
		System.out.println(player3.getPlayerRack().toString());
		/*//List<Meld> list2 = player2.play(); 
		
		//Tests getMelds()
		assertEquals(2,meld1.size());
		assertEquals(2,meld2.size());
		
		//Test initial values
		assertEquals(8,player1.getPlayerRack().getSize());
		//assertEquals(8,player2.getPlayerRack().getSize());
		
		//player1 tests
		assertEquals("[G10 G11 G12 , R9 R10 R11 R12 ]", list1.toString());
		assertEquals(1,player1.getPlayerRack().getSize());
		
		///player2 tests
		assertEquals(Collections.emptyList(), list2);
		assertEquals(8,player2.getPlayerRack().getSize());*/
		
	}
}
