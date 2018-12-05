package com.rummikub;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class JokerTest {

	@BeforeAll
	static void setUpAll() throws Exception 
	{
		new ArrayList<Tile>();
	}
	
	@Test
	//Requirements 9 tests
	public void setPossibleTilesTest() {
		Joker joke = new Joker("J1");
		Tile b1 = new Tile("B1", true);
		Tile o1 = new Tile("O1", true);
		//Tiles played on table and meld with joker
		Meld setMeld = new Meld(joke, b1, o1);
		
		ArrayList<Tile> tiles = new ArrayList<Tile>(Arrays.asList(
				joke, b1, o1,
				new Tile("G1"), new Tile("R1"), new Tile("O1"),
				new Tile("G2"), new Tile("G2"), new Tile("G3"), new Tile("B2"), new Tile("B3"),new Tile("O2"), new Tile("O3")));
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
		Tile b3 = new Tile("B2", true);
		Tile b4 = new Tile("B4", true);
		Meld rowMeld = new Meld(b1, joke, b3, b4);
		
	
		tiles = new ArrayList<Tile>( Arrays.asList(
	  		b1, joke, b3 , b4,
	  		new Tile("G2"), new Tile("G2"), new Tile("G3"), new Tile("B2"), new Tile("B3"),new Tile("O2"), new Tile("O3")));
		//Set the tile, it should now be B2 
		joke.setPossibleTiles(rowMeld, tiles);
		
		assertEquals("[B2]", joke.getPossibleTiles().toString());	
	}
	
}
