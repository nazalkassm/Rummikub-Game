package com.rummikub;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pmw.tinylog.Logger;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

class StockTest {
	
	private static Stock stock1;
	private static Stock stock2;
	private static Player player1;

	@BeforeAll
	static void setUpBeforeClass() throws Exception 
	{
		player1 = new Player("Naz");
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception 
	{
		player1 = null;
	}

	@BeforeEach
	void setUp() throws Exception 
	{
		stock1 = new Stock(104);
		stock2 = new Stock(stock1);
	}

	@AfterEach
	void tearDown() throws Exception 
	{
		stock1 = null;
		stock2 = null;
	}
	
	@Test
	void createStockTest()
	{
		assertThat(stock1.getStockArray().isEmpty(), is(false));
		assertThat(stock1.getLength(),is(104));
	}

	@Test
	void shuffleTest() 
	{    
	      assertTrue(stock1.getStockArray().equals(stock2.getStockArray()));
		  stock2.shuffle();
		  
		  //The very very small change the deck is still the same we do another test.
		  boolean stockIsStillTheSame = stock1.equals(stock2);
		  
		  if (stockIsStillTheSame)
		  {
		        stock2.shuffle();
		        assertFalse(stock1.equals(stock2));
		  }
		  int sameTiles = 0;
		  for(int t=0; t < stock1.getLength(); t++)
		  {
			  
			  if(stock1.getStockArray().get(t).equals(stock2.getStockArray().get(t)))
			  {
				  sameTiles++;
			  }
			  else
			  {
			  assertThat(stock1.getStockArray().get(t),is(not(stock2.getStockArray().get(t))));
			  }
		  }		
		  Logger.info("The number of same are : " + sameTiles);
	}
	
	@Test
	void deal14TilesTest()
	{
		int stockSizeBeforeDealing = stock1.getLength();
		player1.fillRack(stock1);
		assertThat(player1.getPlayerRack().getRackArray(),is(notNullValue()));
		assertThat(stockSizeBeforeDealing-14,is(stock1.getLength()));
	}
	
	@Test
	void dealTileTest()
	{
		int stockSize = stock1.getLength();
		int playerRackSize = player1.getPlayerRack().getSize();
		int ONE_TILE = 1;
		player1.getTile(stock1);
		assertThat(player1.getPlayerRack().getSize(),is(playerRackSize + ONE_TILE));
		assertThat(stock1.getLength(),is(stockSize - ONE_TILE));
	}
}
