package comp.rummikub;

import static org.junit.jupiter.api.Assertions.assertTrue;

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
	private Player newPlayer; // This object is to test a newly created player in every test.
	private PlayerMock myPlayerMock;
	private static Strategy s1;
	private static Strategy s2;
	private static Strategy s3;
	private static Strategy s4;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception 
	{
		player1 = new Player(); //These objects are going to be used in all the tests. We need to keep track of their state.
		player2 = new Player();
		player3 = new Player();
		player4 = new Player();
		s1 = new Strategy1();
		s2 = new Strategy2();
		s3 = new Strategy3();
		s4 = new Strategy4();
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
		myPlayerMock= new PlayerMock();
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
	void playerGetTileTest()
	{
		newPlayer.getTile(stock.dealTile());
		assertThat(newPlayer.getPlayerRack().getRackArray(),hasSize(1));
	}
	
	@Test
	void playerUseStrategyTest() // This test uses a mock player class that inherets from player. It makes sure that a player's strategy calls it's play() method and confirms it using a boolean.
	{
		myPlayerMock.setStrategy(s1);
		myPlayerMock.useStrategy();
		assertTrue(myPlayerMock.isUseStrategyCalled());
	}
}
