package com.rummikub;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.*;

public class TableTest {
	
	private static Table table;
	private static Meld m1 = new Meld(new Tile("B","2"),new Tile("B", "3"),new Tile("B", "4"));
	private static Meld m2 = new Meld(new Tile("G", "6"),new Tile("G", "5"),new Tile("G", "4"));
	private static Meld m3 = new Meld(new Tile("G", "10"),new Tile("R", "10"),new Tile("O", "10"));
	private static Player player1 = new Player("p1", new Strategy0()); 
	private static Player player2 = new Player("p2", new Strategy1()); 
	private static Player player3 = new Player("p3", new Strategy2()); 
	private static Player player4 = new Player("p4", new Strategy3()); 
	
	@BeforeAll
	static void setUpClass() throws Exception {
		
		//Create the new player
		table = new Table(new Stock());
		table.addPlayers(player1, player2, player3, player4);
		List<Meld> melds = Arrays.asList(m1,m2,m3);
		table.updateMeldsOnTable(melds);
	}
	
	@Test
	public void initTurnTest() {
		//Init turn will return true if the turns are decided by highest tile number
		assertEquals(true, table.initPlayersTurn());
	}
	
	@Test
	public void tableMeldCountTest() {
		//We added 3 melds so this should be 3
		assertEquals(3, table.getMeldCount());

		List<Meld> melds = Arrays.asList(m1);

		assertTrue(table.updateMeldsOnTable(melds));
		
		//After adding 1 more meld to the table, ensure the new count is 4
		assertEquals(4, table.getMeldCount());
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
	
}
