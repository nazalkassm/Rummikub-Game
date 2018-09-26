package comp.rummikub;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class StockTest {
	
	private Stock stock1;
	private Stock stock2 = new Stock(stock1);

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
	void shuffleTest() 
	{    
	      assertTrue(stock1.equals(stock2));
		  stock2.shuffle();
		  
		  //The very very small change the deck is still the same we do another test.
		  boolean stockIsStillTheSame = stock1.equals(stock2);
		  
		  if (stockIsStillTheSame)
		  {
		        //stock2.shuffle();
		        assertFalse(stock1.equals(stock2));
		  }
	}
	
	@Test
	void dealHandTest()
	{
		//assetThat(playerHand.size,is(14));
	}
	
	@Test
	void dealTileTest()
	{
		//int playerCardN = player1.playerHand.size;
		//player1.dealTile();
		//assertThat(playerCardN,++player1.playerHand.size);
	}
	
}
