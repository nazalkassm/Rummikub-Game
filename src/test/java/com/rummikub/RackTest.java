package com.rummikub;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;

public class RackTest {

	@BeforeEach
	public void init() {
		
	}
	
	/*
	 * creates a new hand
	 * tests whether hand initially has x many tiles
	 * Rack.Rack() should create a hand with 14 tiles
	 */
	@Test
	public void checkRack() {
		Rack hand = new Rack();
		assertEquals(14, hand.getSize());
	}
	
	/*
	 * tests whether the tiles in hand are sorted
	 * Rack.Rack() should create a hand with 14 tiles already sorted
	 * Rack.drawTile() should call sort each time a new tile is drawn from Stock
	 */
	@Test
	public void checkSort() {
		Rack hand = new Rack();
		assertEquals(true, hand.sorted());
		
		hand.drawTile();
		hand.drawTile();
		
		assertEquals(true, hand.sorted());		
	}
	
	/*
	 * tests whether Rack.drawTile() function works
	 * each time draw is called, size of rack should increment by 1
	 */
	@Test
	public void checkRackSize() {
		Rack hand = new Rack();
		assertEquals(14, hand.getSize());
		
		hand.drawTile();
		hand.drawTile();
		
		assertEquals(16, hand.getSize());
	}
	
	/*
	 * tests whether there exists a valid meld
	 * takes a dummy constructor and adds tiles manually
	 */
	@Test
	public void checkValidMeld() {
		Rack hand = new Rack("hi");
		Rack hand2 = new Rack("hi");
		Rack hand3 = new Rack("hi");
		
		hand.addTile("G", 10);
		hand.addTile("B", 4);
		hand.addTile("B", 9);
		
		hand2.addTile("G", 4);
		hand2.addTile("G", 5);
		hand2.addTile("G", 6);
		
		hand3.addTile("G", 10);
		hand3.addTile("R", 10);
		hand3.addTile("O", 10);
		
		assertEquals(false , hand.hasMeld());
		assertEquals(true , hand2.hasMeld());
		assertEquals(true , hand3.hasMeld());
	}
	
	/*
	 * tests the sum of melds
	 * returns -1 if hand does not have a valid meld
	 */
	@Test
	public void checkMeldSum() {
		Rack hand = new Rack("hi");
		Rack hand2 = new Rack("hi");
		Rack hand3 = new Rack("hi");
		
		hand.addTile("G", 10);
		hand.addTile("B", 4);
		hand.addTile("B", 9);
		
		hand2.addTile("G", 4);
		hand2.addTile("G", 5);
		hand2.addTile("G", 6);
		
		hand3.addTile("G", 10);
		hand3.addTile("R", 10);
		hand3.addTile("O", 10);
		
		assertEquals(-1 , hand.sumMeld());
		assertEquals(15 , hand2.sumMeld());
		assertEquals(30, hand3.sumMeld());
	}
	
	/*
	 * tests whether current hand has a valid meld with initial 30 value
	 */
	@Test
	public void checkInitialThirty() {
		Rack hand = new Rack("hi");
		Rack hand2 = new Rack("hi");
		Rack hand3 = new Rack("hi");
		
		hand.addTile("G", 10);
		hand.addTile("B", 4);
		hand.addTile("B", 9);
		
		hand2.addTile("G", 4);
		hand2.addTile("G", 5);
		hand2.addTile("G", 6);
		
		hand3.addTile("G", 10);
		hand3.addTile("R", 10);
		hand3.addTile("O", 10);
		
		assertEquals(false , hand.hasThirty());
		assertEquals(false , hand2.hasThirty());
		assertEquals(true , hand3.hasThirty());
	}
}

