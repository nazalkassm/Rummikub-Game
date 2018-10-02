package comp.rummikub;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.*;

import com.rummikub.*;

class PlayerTest {
	
	private Stock stock;
	private static Player player1;
	private static Player player2;
	private static Player player3;
	private static Player player4;
	private static Player newPlayer; // This object is to test a newly created player.

	@BeforeAll
	static void setUpBeforeClass() throws Exception 
	{
		player1 = new Player(); //These objects are going to be used in all the tests. We need to keep track of their state.
		player2 = new Player();
		player3 = new Player();
		player4 = new Player();
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception 
	{
		player1 = new Player();
		player2 = new Player();
		player3 = new Player();
		player4 = new Player();
	}

	@BeforeEach
	void setUp() throws Exception 
	{
		newPlayer = new Player();
	}

	@AfterEach
	void tearDown() throws Exception 
	{
		newPlayer = null;
	}
	
	@Test
	void playerFillRackTest() 
	{
		player1.fillRack(stock.dealRack());
		assertThat(player1.getPlayerRack().getRackArray(), hasSize(14));
	} 
	
	@Test
	void player()
	{
		
	}
	

}
