package com.rummikub;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

class PlayerTest 
{
	private Stock stock;
	
	//These players are going to be used for all the tests. 
	//We need to keep track of their state.
	private Player player1;
	private Player player2;
	private Player player3;
	private Player player4;
	
	// This object is to test a newly created player for every test.
	private Player newPlayer; 
	private PlayerMock myPlayerMock;
	
	//Behaviors to assign for each player they are only created once.
	private StragetyBehaviour s1;
	private StragetyBehaviour s2;
	private StragetyBehaviour s3;
	private StragetyBehaviour s4;
	
	@BeforeAll
	void setUpClass() throws Exception 
	{
		player1 = new Player("p1"); 
		player2 = new Player("p2");
		player3 = new Player("p3");
		player4 = new Player("p4");
		s1 = new Strategy0(null);
		s2 = new Strategy1(null);
		s3 = new Strategy2(null);
		s4 = new Strategy3(null);
	}

	@AfterAll
	void tearDownClass() throws Exception 
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
	protected void setUpMethod() throws Exception 
	{
		newPlayer = new Player("pTester");
		myPlayerMock = new PlayerMock("mockPlayer");
	}

	@AfterEach
	protected void tearDownMethod() throws Exception 
	{
		newPlayer = null;
		myPlayerMock = null;
	}
	
	@Test
	void playerRackTest() 
	{
		assertThat(player1.getPlayerRack().getRackArray(), hasSize(14));
	} 
	
	@Test
	void playerGetTileTest()
	{
		newPlayer.getTileFromStock(stock);
		assertThat(newPlayer.getPlayerRack().getRackArray(),hasSize(1));
	}
	
	void isHumanTest() {
		//Player with strategies 0 are humans (since input required)
		Player human = new Player("some Name", new Strategy0()));
		
		assertEquals(true,human.isHuman());
	} 

}
