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
		tiles.add(new Tile(5, "orange"));
		tiles.add(new Tile(4, "red"));
		tiles.add(new Tile(5, "green"));
		assertEquals(Meld.MeldType.INVALID, Meld.checkMeldType(tiles));
		
		tiles.clear();
		tiles.add(new Tile(4, "orange"));
		tiles.add(new Tile(5, "red"));
		tiles.add(new Tile(6, "red"));
		assertEquals(Meld.MeldType.INVALID, Meld.checkMeldType(tiles));
		
		tiles.clear();
		tiles.add(new Tile(3, "red"));
		tiles.add(new Tile(5, "red"));
		tiles.add(new Tile(6, "red"));
		assertEquals(Meld.MeldType.INVALID, Meld.checkMeldType(tiles));
		
		
		// testing set melds
		tiles.clear();
		tiles.add(new Tile(5, "orange"));
		tiles.add(new Tile(5, "red"));
		tiles.add(new Tile(5, "green"));
		assertEquals(Meld.MeldType.SET, Meld.checkMeldType(tiles));
		
		tiles.clear();
		tiles.add(new Tile(6, "orange"));
		tiles.add(new Tile(6, "red"));
		tiles.add(new Tile(6, "green"));
		tiles.add(new Tile(6, "blue"));
		assertEquals(Meld.MeldType.SET, Meld.checkMeldType(tiles));
		
		
		// testing run melds
		tiles.clear();
		tiles.add(new Tile(4, "red"));
		tiles.add(new Tile(5, "red"));
		tiles.add(new Tile(6, "red"));
		assertEquals(Meld.MeldType.RUN, Meld.checkMeldType(tiles));
		
		tiles.clear();
		tiles.add(new Tile(1, "orange"));
		tiles.add(new Tile(2, "orange"));
		tiles.add(new Tile(3, "orange"));
		tiles.add(new Tile(4, "orange"));
		tiles.add(new Tile(5, "orange"));
		tiles.add(new Tile(6, "orange"));
		tiles.add(new Tile(7, "orange"));
		tiles.add(new Tile(8, "orange"));
		tiles.add(new Tile(9, "orange"));
		tiles.add(new Tile(10, "orange"));
		tiles.add(new Tile(11, "orange"));
		tiles.add(new Tile(12, "orange"));
		tiles.add(new Tile(13, "orange"));
		assertEquals(Meld.MeldType.RUN, Meld.checkMeldType(tiles));
		
	}
}
