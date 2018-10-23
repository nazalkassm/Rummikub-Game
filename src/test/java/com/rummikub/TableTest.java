package com.rummikub;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

public class TableTest {
	
	@BeforeAll
	public void init() {
		Table table = new Table();
		
		Tile t1 = new Tile("B","2");
		Tile t2 = new Tile("B", "3");
		Tile t3 = new Tile("B", "4");
		
		Tile t4 = new Tile("G", "6"); //G4
		Tile t5 = new Tile("G", "5"); //G5
		Tile t6 = new Tile("G", "4"); //G6
		
		Tile t7 = new Tile("G", "10"); //G10
		Tile t8 = new Tile("R", "10"); //R10
		Tile t9 = new Tile("O", "10"); //O10
		
		Meld m1 = new Meld(t1,t2,t3);
		Meld m2 = new Meld(t4,t5,t6);
		Meld m3 = new Meld(t7,t8,t9);
		
		table.addMeld(m1);
		table.addMeld(m2);
		table.addMeld(m3);
	}
	
	@Test
	public void tableSizeTest() {
		assertEquals(3, table.getSize());
		
		Meld m1 = new Meld(t1,t2,t3);
	}
	
  //Need test for getting least amount of cards that a player has on the table
	
}
