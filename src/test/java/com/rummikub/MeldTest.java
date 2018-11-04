package com.rummikub;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
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
	}
	
	@Test
	public void checkSequencesTest() {		
		tiles.add(new Tile("O", "4"));
		tiles.add(new Tile("R", "5"));
		tiles.add(new Tile("R", "6"));
		assertEquals(false, Meld.checkSequence(tiles));
		
		tiles.clear();
		tiles.add(new Tile("O", "6"));
		tiles.add(new Tile("R", "6"));
		tiles.add(new Tile("G", "6"));
		tiles.add(new Tile("B", "6"));
		assertEquals(false, Meld.checkSequence(tiles));
		
		tiles.clear();
		tiles.add(new Tile("R", "6"));
		tiles.add(new Tile("R", "4"));
		tiles.add(new Tile("R", "5"));
		assertEquals(true, Meld.checkSequence(tiles));
		
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
		assertEquals(true, Meld.checkSequence(tiles));
	}
	
	@Test
	public void sumMeldTest() {
		Meld m1 = new Meld(new Tile("B","2"),new Tile("B", "3"),new Tile("B", "4"));    
		assertEquals(9, m1.sumMeld());
	}
	
	@Test
	public void getMaxIndexTest() {
		Meld m1 = new Meld(new Tile("B","2"),new Tile("B", "3"),new Tile("B", "4"));    
		Meld m2 = new Meld(new Tile("G", "6"),new Tile("G", "5"),new Tile("G", "4"));   
		Meld m3 = new Meld(new Tile("G", "10"),new Tile("R", "10"),new Tile("O", "10"));
		
		List<Meld> melds = Arrays.asList(m1, m2, m3);
		assertEquals(2,Meld.getMaxIndex(melds));
	}
	
	@Test
	public void getAllMeldsWithTableTest() {
	
		List<Tile> tiles = Arrays.asList(
				//Tiles played on table
				new Tile("R1", true), new Tile("B1", true), new Tile("O1", true)/*, new Tile("R4", true),new Tile("R5", true), new Tile("R6", true),*/
				,new Tile("R6", true), new Tile("R7", true), new Tile("R8", true),
				//Hand
				new Tile("R9", false), new Tile("R5", false), new Tile("R2", false), new Tile("R3", false),new Tile("B2", false), new Tile("B3", false),new Tile("O2", false), new Tile("O3", false));
	
		//This is the case where a meld is played by rearranging multiple melds.
		//The R1 to R4 is played and R4 to R6 is played by splitting R1 to R6 on the table
		assertEquals("[R1 R2 R3 R4 R5 , R4 R5 R6 ]", 
				Meld.getMeldsWithTable(tiles).toString());

	}
	
}
