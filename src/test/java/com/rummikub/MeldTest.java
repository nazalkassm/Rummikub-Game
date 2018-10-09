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

	@Test
	public void testCheckMeldType() {		
		
		// Testing invalid melds
		List<Tile> tiles = new ArrayList<Tile>();
		tiles.add(new Tile("5", "O"));
		tiles.add(new Tile("4", "R"));
		tiles.add(new Tile("5", "G"));
		assertEquals(Meld.MeldType.INVALID, Meld.checkMeldType(tiles));
		
		tiles.clear();
		tiles.add(new Tile("4", "O"));
		tiles.add(new Tile("5", "R"));
		tiles.add(new Tile("6", "R"));
		assertEquals(Meld.MeldType.INVALID, Meld.checkMeldType(tiles));
		
		tiles.clear();
		tiles.add(new Tile("3", "R"));
		tiles.add(new Tile("5", "R"));
		tiles.add(new Tile("6", "R"));
		assertEquals(Meld.MeldType.INVALID, Meld.checkMeldType(tiles));
		
		
		// testing set melds
		tiles.clear();
		tiles.add(new Tile("5", "O"));
		tiles.add(new Tile("5", "R"));
		tiles.add(new Tile("5", "G"));
		assertEquals(Meld.MeldType.SET, Meld.checkMeldType(tiles));
		
		tiles.clear();
		tiles.add(new Tile("6", "O"));
		tiles.add(new Tile("6", "R"));
		tiles.add(new Tile("6", "G"));
		tiles.add(new Tile("6", "B"));
		assertEquals(Meld.MeldType.SET, Meld.checkMeldType(tiles));
		
		
		// testing run melds
		tiles.clear();
		tiles.add(new Tile("4", "R"));
		tiles.add(new Tile("5", "R"));
		tiles.add(new Tile("6", "R"));
		assertEquals(Meld.MeldType.RUN, Meld.checkMeldType(tiles));
		
		tiles.clear();
		tiles.add(new Tile("1", "O"));
		tiles.add(new Tile("2", "O"));
		tiles.add(new Tile("3", "O"));
		tiles.add(new Tile("4", "O"));
		tiles.add(new Tile("5", "O"));
		tiles.add(new Tile("6", "O"));
		tiles.add(new Tile("7", "O"));
		tiles.add(new Tile("8", "O"));
		tiles.add(new Tile("9", "O"));
		tiles.add(new Tile("10", "O"));
		tiles.add(new Tile("11", "O"));
		tiles.add(new Tile("12", "O"));
		tiles.add(new Tile("13", "O"));
		assertEquals(Meld.MeldType.RUN, Meld.checkMeldType(tiles));
		
	}
}
