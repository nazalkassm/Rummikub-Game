package com.rummikub;

import org.junit.jupiter.api.BeforeAll;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class PlayerTest 
{
	static private Stock stock;
	
	//These players are going to be used for all the tests. 
	//We need to keep track of their state.
	static private Player player1;
	
	// This object is to test a newly created player for every test.
	static private Player newPlayer; 
	
	@BeforeAll
	static void setUpClass() 
	{
	}

	@AfterAll
	static void tearDownClass() 
	{
	}

	@BeforeEach
	protected void setUpMethod() 
	{
		newPlayer = new Player("pTester");
	}

	@AfterEach
	protected void tearDownMethod() 
	{
		newPlayer = null;
	}
	
	@Test
	void playerRackTest() 
	{
		assertEquals(14, player1.getPlayerRack().getSize());
	} 
	
	@Test
	void playerGetTileTest()
	{
		newPlayer.getTileFromStock(stock);
		assertEquals(1, newPlayer.getPlayerRack().getSize());
	}
	
	@Test
	void isHumanTest() {
		//Player with strategies 0 are humans (since input required)
		Player human = new Player("some Name", new Strategy0());
		
		assertTrue(human.isHuman());
	} 

}
