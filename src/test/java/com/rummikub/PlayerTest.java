package com.rummikub;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.*;

class PlayerTest {
	
	private Stock stock;
	private Player player1;
	private Player player2;
	private Player player3;
	private Player player4;
	private PlayerMock myPlayerMock;
	private Player newPlayer; // This object is to test a newly created player in every test.
	private PlayerBehaviour s1;
	private PlayerBehaviour s2;
	private PlayerBehaviour s3;
	private PlayerBehaviour s4;
	
	@BeforeAll
	void setUpClass() throws Exception 
	{
		player1 = new Player("p1"); //These objects are going to be used in all the tests. We need to keep track of their state.
		player2 = new Player("p2");
		player3 = new Player("p3");
		player4 = new Player("p4");
		s1 = new Strategy1();
		s2 = new Strategy2();
		s3 = new Strategy3();
		s4 = new Strategy4();
	}

	@AfterAll
	void tearDownClass() throws Exception 
	{
		player1 = null;
		player2 = null;
		player3 = null;
		player4 = null;
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
	}
	
	@Test
	void playerFillRackTest() 
	{
		//player1.fillRack(stock.deal14Tiles());
		assertThat(player1.getPlayerRack().getRackArray(), hasSize(14));
	} 
	
	@Test
	void playerGetTileTest()
	{
		newPlayer.getTile(stock);
		assertThat(newPlayer.getPlayerRack().getRackArray(),hasSize(1));
	}
	
	@Test
	void playerUseStrategyTest() // This test uses a mock player class that inherets from player. It makes sure that a player's strategy calls it's play() method and confirms it using a boolean.
	{
		myPlayerMock.setPlayerBehaviour(s1);
		myPlayerMock.useStrategy();
		assertTrue(myPlayerMock.isUseStrategyCalled());
	}
}
