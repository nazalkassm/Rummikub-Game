package com.rummikub;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class MeldTest {

	private static List<Tile> tiles;
	
	@BeforeAll
	static void setUpAll() throws Exception 
	{
	tiles = new ArrayList<Tile>();
	}
	
	@AfterAll
	static void tearDownAfterClass() throws Exception
	{
	tiles = null;
	}
	
	@Test
	public void checkMeldTypeTest() {			
		// Testing invalid melds
		
		tiles.add(new Tile("O", "5"));
		tiles.add(new Tile("R", "4"));
		tiles.add(new Tile("G", "5"));
		assertEquals(Meld.MeldType.INVALID, Meld.checkMeldType(tiles));
		
		tiles.clear();
		tiles.add(new Tile("O", "4"));
		tiles.add(new Tile("R", "5"));
		tiles.add(new Tile("R", "6"));
		assertEquals(Meld.MeldType.INVALID, Meld.checkMeldType(tiles));
		
		tiles.clear();
		tiles.add(new Tile("R", "3"));
		tiles.add(new Tile("R", "5"));
		tiles.add(new Tile("R", "6"));
		assertEquals(Meld.MeldType.INVALID, Meld.checkMeldType(tiles));
		
		
		// testing set melds
		tiles.clear();
		tiles.add(new Tile("O", "5"));
		tiles.add(new Tile("R", "5"));
		tiles.add(new Tile("G", "5"));
		assertEquals(Meld.MeldType.SET, Meld.checkMeldType(tiles));
		
		tiles.clear();
		tiles.add(new Tile("O", "6"));
		tiles.add(new Tile("R", "6"));
		tiles.add(new Tile("G", "6"));
		tiles.add(new Tile("B", "6"));
		assertEquals(Meld.MeldType.SET, Meld.checkMeldType(tiles));
		
		
		// testing run melds
		tiles.clear();
		tiles.add(new Tile("R", "6"));
		tiles.add(new Tile("R", "4"));
		tiles.add(new Tile("R", "5"));
		assertEquals(Meld.MeldType.RUN, Meld.checkMeldType(tiles));
		
		tiles.clear();
		tiles.add(new Tile("O", "8"));
		tiles.add(new Tile("O", "9"));
		tiles.add(new Tile("O", "10"));
		tiles.add(new Tile("O", "11"));
		tiles.add(new Tile("O", "12"));
		tiles.add(new Tile("O", "1"));
		tiles.add(new Tile("O", "2"));
		tiles.add(new Tile("O", "3"));
		tiles.add(new Tile("O", "4"));
		tiles.add(new Tile("O", "5"));
		tiles.add(new Tile("O", "6"));
		tiles.add(new Tile("O", "7"));
		tiles.add(new Tile("O", "13"));
		assertEquals(Meld.MeldType.RUN, Meld.checkMeldType(tiles));
		
	}
	
	@Test
	public void checkEqualRanksTest() {
		tiles.add(new Tile("O", "5"));
		tiles.add(new Tile("R", "4"));
		tiles.add(new Tile("G", "5"));
		assertEquals(false, Meld.checkEqualRanks(tiles));
		
		tiles.clear();
		tiles.add(new Tile("R", "3"));
		tiles.add(new Tile("R", "5"));
		tiles.add(new Tile("R", "6"));
		assertEquals(false, Meld.checkEqualRanks(tiles));
		
		tiles.clear();
		tiles.add(new Tile("O", "5"));
		tiles.add(new Tile("R", "5"));
		tiles.add(new Tile("G", "5"));
		assertEquals(true, Meld.checkEqualRanks(tiles));
		
		tiles.clear();
		tiles.add(new Tile("O", "6"));
		tiles.add(new Tile("R", "6"));
		tiles.add(new Tile("G", "6"));
		tiles.add(new Tile("B", "6"));
		assertEquals(true, Meld.checkEqualRanks(tiles));
		
		tiles.clear();
		tiles.add(new Tile("O", "6"));
		tiles.add(new Tile("R", "6"));
		tiles.add(new Tile("G", "6"));
		tiles.add(new Tile("B", "6"));
		tiles.add(new Tile("O", "6"));
		assertEquals(false, Meld.checkEqualRanks(tiles));
	}
	
	@Test
	public void checkSequencesTest() {		
		tiles.add(new Tile("O", "4"));
		tiles.add(new Tile("R", "5"));
		tiles.add(new Tile("R", "6"));
		assertEquals(true, Meld.checkSequence(tiles));
		
		tiles.add(new Tile("O", "6"));
		tiles.add(new Tile("R", "6"));
		tiles.add(new Tile("G", "6"));
		tiles.add(new Tile("B", "6"));
		assertEquals(false, Meld.checkSequence(tiles));
		
		tiles.add(new Tile("R", "6"));
		tiles.add(new Tile("R", "4"));
		tiles.add(new Tile("R", "5"));
		assertEquals(true, Meld.checkSequence(tiles));
		
		tiles.add(new Tile("O", "8"));
		tiles.add(new Tile("O", "9"));
		tiles.add(new Tile("O", "10"));
		tiles.add(new Tile("O", "11"));
		tiles.add(new Tile("O", "12"));
		tiles.add(new Tile("O", "1"));
		tiles.add(new Tile("O", "2"));
		tiles.add(new Tile("O", "3"));
		tiles.add(new Tile("O", "4"));
		tiles.add(new Tile("O", "5"));
		tiles.add(new Tile("O", "6"));
		tiles.add(new Tile("O", "7"));
		tiles.add(new Tile("O", "13"));
		assertEquals(true, Meld.checkSequence(tiles));
	}
}
