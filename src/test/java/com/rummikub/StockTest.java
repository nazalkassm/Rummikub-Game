package com.rummikub;

import static org.junit.jupiter.api.Assertions.*;

//import org.hamcrest.Matcher;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;
import com.rummikub.*;



class StockTest {
	
	private Stock stock1;
	private Stock stock2 = new Stock(stock1);
	private Player player1;

	@BeforeAll
	static void setUpBeforeClass() throws Exception 
	{
		
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception 
	{
		
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
	void createStockTest()
	{
		stock1.createStock();
		assertThat(stock1.getStock().isEmpty(), is(false));
		assertThat(stock1.getLength(),is(104));
	}

	@Test
	void shuffleTest() 
	{    
	      assertTrue(stock1.equals(stock2));
		  stock2.shuffle();
		  
		  //The very very small change the deck is still the same we do another test.
		  boolean stockIsStillTheSame = stock1.equals(stock2);
		  
		  if (stockIsStillTheSame)
		  {
		        stock2.shuffle();
		        assertFalse(stock1.equals(stock2));
		  }
		  
		  for(int t=0; t < stock1.getLength(); t++)
		  {
			  int sameTiles = 0;
			  if(stock1.getStock().get(t).equals(stock2.getStock().get(t)))
			  {
				  sameTiles++;
			  }
			  else
			  {
			  assertThat(stock1.getStock().get(t),is(not(stock2.getStock().get(t))));
			  }
			  System.out.println("The number of same tiles after shuffling is : " + sameTiles);
		  }		  
	}
	

	@Test
	void dealRackTest()
	{
		int stockSizeBeforeDealing = stock1.getStock().size();
		player1.fillRack(stock1.dealRack());
		assertThat(player1.getPlayerRack(),is(notNullValue()));
		assertThat(stockSizeBeforeDealing-14,is(stock1.getStock().size()));
	}
	
	@Test
	void dealTileTest()
	{
		int stockSizeBeforeDealing = stock1.getStock().size();
		int playerTileN = player1.getPlayerRack().getSize();
		player1.getTile(stock1.dealTile());
		assertThat(player1.getPlayerRack().getSize(),is(playerTileN+1));
		assertThat(stock1.getStock().size(),is(stockSizeBeforeDealing-1));
	}
	
	
	
}
