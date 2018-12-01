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

public class JokerTest {

	private static List<Tile> tiles;
	
	@BeforeAll
	static void setUpAll() throws Exception 
	{
		tiles = new ArrayList<Tile>();
	}
	
	@Test
	//Requirements 9 tests
	public void setPossibleTilesTest() {
		Joker joke = new Joker();
		//Tiles played on table and meld with joker
		Meld setMeld = new Meld(joke, new Tile("B1", true), new Tile("O1", true));
		
		List<Tile> tiles = Arrays.asList(
				new Tile("JJ"), new Tile("B1"), new Tile("O1"),
				new Tile("G1"), new Tile("R1"), new Tile("O1"),
				new Tile("G2"), new Tile("G2"), new Tile("G3"), new Tile("B2"), new Tile("B3"),new Tile("O2"), new Tile("O3"));
		//Set the tile, it should now be R1 or G1 
		joke.setPossibleTiles(setMeld, tiles);

		assertEquals(true, joke.getPossibleTiles().toString().contains("R1"));
		assertEquals(true, joke.getPossibleTiles().toString().contains("G1"));
		
		//Add a G1 to table 
		tiles.add(new Tile("G1"));
		
		//Set the tile, it should now be R1 
		joke.setPossibleTiles(setMeld, tiles);
	
		assertEquals(true, joke.getPossibleTiles().toString().contains("R1"));
		assertEquals(false, joke.getPossibleTiles().toString().contains("G1"));
		
		Meld rowMeld = new Meld(new Tile("B1"), joke, new Tile("B3"), new Tile("B4"));
		
	  tiles = Arrays.asList(
	  		new Tile("B1"), joke, new Tile("B3"), new Tile("B4"),
	  		new Tile("G2"), new Tile("G2"), new Tile("G3"), new Tile("B2"), new Tile("B3"),new Tile("O2"), new Tile("O3"));
		//Set the tile, it should now be B2 
		joke.setPossibleTiles(rowMeld, tiles);
		
		assertEquals("[ B2 ]", joke.getPossibleTiles().toString());	
	}
	
}
