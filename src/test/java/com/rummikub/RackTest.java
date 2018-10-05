package com.rummikub;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.*;

public class RackTest {

	static Rack hand;
	static Rack hand2;
	static Rack hand3;
	Rack handTest;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception 
	{
		hand = new Rack();
		hand2 = new Rack();
		hand3 = new Rack();
		hand.addTile("G", 10);
		hand.addTile("B", 4);
		hand.addTile("B", 9);
		
		hand2.addTile("G", 4);
		hand2.addTile("G", 5);
		hand2.addTile("G", 6);
		
		hand3.addTile("G", 10);
		hand3.addTile("R", 10);
		hand3.addTile("O", 10);
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception 
	{
		hand = null;
		hand2 = null;
		hand3 = null;
	}

	@BeforeEach
	void setUp() throws Exception 
	{
		handTest = new Rack();
	}

	@AfterEach
	void tearDown() throws Exception 
	{
		handTest = null;
	}
	@Test
	public void checkRack() 
	{
		assertEquals(14, hand.getSize());
	}
	
	/*
	 * tests whether the tiles in hand are sorted
	 * Rack.Rack() should create a hand with 14 tiles already sorted
	 * Rack.drawTile() should call sort each time a new tile is drawn from Stock
	 */
	@Test
	public void checkSort() {
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
		
		assertEquals(-1 , hand.sumMeld());
		assertEquals(15 , hand2.sumMeld());
		assertEquals(30, hand3.sumMeld());
	}
	
	/*
	 * tests whether current hand has a valid meld with initial 30 value
	 */
	@Test
	public void checkInitialThirty() {
		
		assertEquals(false , hand.hasThirty());
		assertEquals(false , hand2.hasThirty());
		assertEquals(true , hand3.hasThirty());
	}
}

