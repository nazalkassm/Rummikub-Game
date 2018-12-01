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
	static private Player player2;
	static private Player player3;
	static private Player player4;
	
	// This object is to test a newly created player for every test.
	static private Player newPlayer; 
	static private PlayerMock myPlayerMock;
	
	//Behaviors to assign for each player they are only created once.
	static private StrategyBehaviour s1;
	static private StrategyBehaviour s2;
	static private StrategyBehaviour s3;
	static private StrategyBehaviour s4;
	
	@BeforeAll
	static void setUpClass() 
	{
		stock = new Stock();
		
		player1 = new Player("p1"); 
		player1.fillRack(stock);
		player2 = new Player("p2");
		player2.fillRack(stock);
		player3 = new Player("p3");
		player3.fillRack(stock);
		player4 = new Player("p4");
		player4.fillRack(stock);
		
		s1 = new Strategy0();
		s2 = new Strategy1();
		s3 = new Strategy2();
		s4 = new Strategy3();
	}

	@AfterAll
	static void tearDownClass() 
	{
		player1 = null;
		player2 = null;
		player3 = null;
		player4 = null;
		
		s1 = null;
		s2 = null;
		s3 = null;
		s4 = null;
	}

	@BeforeEach
	protected void setUpMethod() 
	{
		newPlayer = new Player("pTester");
		myPlayerMock = new PlayerMock("mockPlayer");
	}

	@AfterEach
	protected void tearDownMethod() 
	{
		newPlayer = null;
		myPlayerMock = null;
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
