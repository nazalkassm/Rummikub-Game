package com.rummikub;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.*;

public class TableTest {
	
	private static Table table;
	private static Meld m1 = new Meld(new Tile("B","2"),new Tile("B", "3"),new Tile("B", "4"));
	private static Meld m2 = new Meld(new Tile("G", "6"),new Tile("G", "5"),new Tile("G", "4"));
	private static Meld m3 = new Meld(new Tile("G", "10"),new Tile("R", "10"),new Tile("O", "10"));
	private static Player player1 = new Player("p1"); 
	private static Player player2 = new Player("p2");
	private static Player player3 = new Player("p3");
	private static Player player4 = new Player("p4");		
	
	@BeforeAll
	static void setUpClass() throws Exception {
		
		//Create the new player
		table = new Table(new Stock(), player1, player2, player3, player4);
		//Add 3 melds to table
		assertTrue(table.addMeldToTable(m1));
		assertTrue(table.addMeldToTable(m2));
		assertTrue(table.addMeldToTable(m3));
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
		
		assertTrue(table.addMeldToTable(m1));
		
		//After adding 1 more meld to the table, ensure the new count is 4
		assertEquals(4, table.getMeldCount());
	}
	
	@Test
	public void getAllTilesOnTable() {
		Stock stockTest = new Stock();
		//Create the new table
		Table tableTileTest = new Table(stockTest, player1, player2, player3, player4);
		//Add 2 melds to table
		assertTrue(tableTileTest.addMeldToTable(m1));
		assertTrue(tableTileTest.addMeldToTable(m2));
		
    List<Tile> tileList = tableTileTest.getAllTilesOnTable(); 
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
		Stock stockTest = new Stock();
		//Create the new table
		Table tableTileTest = new Table(stockTest, player1, player2, player3, player4);
		//Add 2 melds to table
		assertTrue(tableTileTest.addMeldToTable(m1));
		assertTrue(tableTileTest.addMeldToTable(m2));
	
	  //Get the meld of an undefined index:
		assertEquals(null, tableTileTest.getMeld(8));
		//Get the first meld in the table which should be the m1 as that was added first
		assertEquals(m1, table.getMeld(0));
	}
	
	@Test
	public void getPlayerCountTest() {
		Stock stockTest = new Stock();
		//Create the new table
		Table tableTileTest = new Table(stockTest, player1, player2, player3, player4);
	  //We had 4 players so make sure 4 player count
		assertEquals(4, tableTileTest.getPlayerCount());
		//Add 2 new players
		Player player6 = new Player("p1"); 
		Player player7 = new Player("p2"); 
		table.addPlayers(player6, player7);
    //Assert that now the player count is 6	  
	  assertEquals(6, tableTileTest.getPlayerCount());
	}
}
