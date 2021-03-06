package com.rummikub;

import static org.junit.jupiter.api.Assertions.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.*;

public class TableTest {
	
	private static Table table;
	private static Stock s = new Stock();
	private static Meld m1 = new Meld(new Tile("B","2"),new Tile("B", "3"),new Tile("B", "4"));
	private static Meld m2 = new Meld(new Tile("G", "6"),new Tile("G", "5"),new Tile("G", "4"));
	private static Meld m3 = new Meld(new Tile("G", "10"),new Tile("R", "10"),new Tile("O", "10"));
	private static Player player1 = new Player("p1", new Strategy0()); 
	private static Player player2 = new Player("p2", new Strategy1()); 
	private static Player player3 = new Player("p3", new Strategy2()); 
	private static Player player4 = new Player("p4", new Strategy3()); 
	
	@BeforeAll
	static void setUpClass() {
		
		//Create the new player
		table = new Table(s);
		table.addPlayers(player1, player2, player3, player4);
		List<Meld> melds = Arrays.asList(m1,m2,m3);
		table.updateMeldsOnTable(melds);
	}
	
	@Test
	public void initTurnTest() {
		 //Player 1 get's R13, player 2 get  R1, player 3 get R1 but since someone already has it he get's the next tile so B12. Player 4 gets B4
		 List<Tile> tiles = Arrays.asList(new Tile ("R10"), new Tile ("R1"), new Tile ("R1"), new Tile ("B9"), new Tile ("B4"));
		 Collections.reverse(tiles);
		 for (Tile t: tiles )
			 s.getStockArray().add(0, t);
		 //P3 has highest card so should run first
		 table.initPlayersTurn();
		//Init turn will return true if the turns are decided by highest tile number
		assertEquals("p1", table.getCurrentPlayer().getName());
	}
	
	@Test
	public void tableMeldCountTest() {
		//We added 3 melds so this should be 3
		assertEquals(3, table.getMeldCount());

		List<Meld> melds = Arrays.asList(m1,m3);

		assertTrue(table.updateMeldsOnTable(melds));
		
		//After updating meld to the table, ensure the new count is 2
		assertEquals(2, table.getMeldCount());
	}
	
	@Test
	public void getAllTilesOnTable() {
	    List<Tile> tileList = table.getAllTilesOnTable(); 
	    //To hold the tiles that we should have 
	    List<Tile> tilesToHave = new ArrayList<Tile>();
	    //Add the m1 and m2 melds to it 
	    tilesToHave.addAll(m1.getTiles());
	    tilesToHave.addAll(m2.getTiles());
	    
	    //We assert that we have all the required tiles 
	    for (Tile tileToCheck : tilesToHave) {
	    	assertEquals(true, tileList.contains(tileToCheck));
	    }
    
	}
	
	@Test
	public void getMeldTest() {
		//Get the meld of an undefined index:
		assertEquals(null, table.getMeld(8));
		//Get the first meld in the table which should be the m1 as that was added first
		assertEquals(m1, table.getMeld(0));
	}
	
	@Test
	public void getPlayerCountTest() {
		//We had 4 players so make sure 4 player count
		assertEquals(4, table.getPlayerCount());
	}
	
	@Test
	public void getLowestHandCountTest() {
		//We have 14 tiles for each player
		assertEquals(14, table.getPlayersRackCount());
		player1.getPlayerRack().setRack(Arrays.asList(new Tile("B","4")));
		//Set the player's rack to 1 so now the lowest table hand count will be 1
		assertEquals(1, table.getPlayersRackCount());
	}
	
	@Test
	public void getDiffMeldsTest() {
		Meld m4 = new Meld(new Tile("B2", false),new Tile("B3"),new Tile("B4"));
		List<Meld> meldsOld = Arrays.asList(m1,m3,m2);
		List<Meld> meldsNew = Arrays.asList(m1,m3,m2,m4);
		
		Meld m1New = new Meld(m1.getTiles().get(0), m1.getTiles().get(1), m1.getTiles().get(2));
		List<Meld> meldsNew2 = Arrays.asList(m2,m3,m1New);
		//This should return the melds that are new/changed
		List<Meld> difMelds = Table.getDiffMelds(meldsOld, meldsNew);
		List<Meld> difMelds2 = Table.getDiffMelds(meldsOld, meldsNew2);
		//In this case only m4 should be there one meld returned
		assertThat(difMelds.size(), is(1));
		assertThat(difMelds.get(0), is(m4));
		assertThat(difMelds2.size(), is(0));
	}
	
}
