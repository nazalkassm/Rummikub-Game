package com.rummikub;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;
import java.util.List;

import javax.swing.plaf.metal.MetalDesktopIconUI;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class Strategy1TestOld {
	
	private static List<Meld> meld1,meld2;
	private static Player player1;
	private static Player player2;

	@BeforeAll
	static void setUpBeforeClass() 
	{
		//player1
		player1 = new Player("player1",new Strategy1());
		player2 = new Player("player2",new Strategy1());

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
		
		
	}

	@AfterAll
	static void tearDownAfterClass() 
	{
		player1 = null;
		meld1 = null;
		meld2 = null;
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
	void useStrategy_removeTiles_Test() throws Exception 
	{
		//Test initial values
		assertEquals(8,player1.getPlayerRack().getSize());
		assertEquals(8,player2.getPlayerRack().getSize());
		
		//player1 tests
		assertEquals("[R9 R10 R11 R12 , G10 G11 G12 ]", player1.play().toString());
		assertEquals(1,player1.getPlayerRack().getSize());
		
		//player2 tests
		assertEquals(8,player2.play());
		assertEquals(8,player2.getPlayerRack().getSize());
		
	}
}
