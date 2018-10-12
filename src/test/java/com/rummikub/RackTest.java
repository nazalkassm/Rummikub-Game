package com.rummikub;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.*;

public class RackTest {

	static Rack hand;
	static Rack hand2;
	static Rack hand3;
	static Stock stock;
	Rack handTest;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception 
	{
		
		hand = new Rack();
		hand2 = new Rack();
		hand3 = new Rack();
		hand.addTile(new Tile("G", "2"));
		hand.addTile(new Tile("B", "4"));
		hand.addTile(new Tile("B", "9"));
		
		hand2.addTile(new Tile("G", "6")); //G4
		hand2.addTile(new Tile("G", "5")); //G5
		hand2.addTile(new Tile("G", "4")); //G6
		
		hand3.addTile(new Tile("G", "10")); //G10
		hand3.addTile(new Tile("R", "10")); //R10
		hand3.addTile(new Tile("O", "10")); //O10
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception 
	{
		hand = null;
		hand2 = null;
		hand3 = null;
		stock = null;
	}

	@BeforeEach
	void setUp() throws Exception 
	{
		stock = new Stock();
		handTest = new Rack();
	}

	@AfterEach
	void tearDown() throws Exception 
	{
		handTest = null;
	}
	@Test
	public void rackTest() 
	{
		assertEquals(14, hand.getSize());
	}
	
	/*
	 * tests whether the tiles in hand are sorted
	 * Rack.Rack() should create a hand with 14 tiles already sorted
	 * Rack.drawTile() should call sort each time a new tile is drawn from Stock
	 */
	@Test
	public void sortTest() {
		//assertEquals(false, hand.isSorted); //check if initially sorted
		hand.sortRack();
		System.out.println(hand);
		assertEquals(true, hand.isSorted); //check after sorting 
		
		hand.takeTile(stock);
		hand.takeTile(stock);
		
		assertEquals(true, hand.isSorted);		
	}
	
	/*
	 * tests whether Rack.drawTile() function works
	 * each time draw is called, size of rack should increment by 1
	 */
	@Test
	public void rackSizeTest() {

		assertEquals(14, hand.getSize());
		
		hand.takeTile(stock);
		hand.takeTile(stock);
		
		assertEquals(16, hand.getSize());
	}
	
	/*
	 * tests whether there exists a valid meld
	 * takes a dummy constructor and adds tiles manually
	 */
	@Test
	public void validMeldTest() {
		
		
		assertEquals(false , hand.hasMeld());
		assertEquals(true , hand2.hasMeld());
		assertEquals(true , hand3.hasMeld());
	}
	
	/*
	 * tests whether there exists a valid meld
	 * takes a dummy constructor and adds tiles manually
	 */
	@Test
	public void runsMeldTest() {
		hand.sortRack();
		hand2.sortRack();
		hand3.sortRack();
		
		ArrayList<List<Tile>> lst1 = Rack.convertMaptoArrayList(Rack.getTilesByColorsAndValues(hand.getRackArray()));
		ArrayList<List<Tile>> lst2 = Rack.convertMaptoArrayList(Rack.getTilesByColorsAndValues(hand2.getRackArray()));
		ArrayList<List<Tile>> lst3 = Rack.convertMaptoArrayList(Rack.getTilesByColorsAndValues(hand3.getRackArray()));
		
		System.out.println(lst1.toString());
		System.out.println(lst2.toString());
		System.out.println(lst3.toString());
		
		
		ArrayList<ArrayList<Tile>> a = hand.getRunMelds();
		ArrayList<ArrayList<Tile>> b = hand.getRunMelds();
		ArrayList<ArrayList<Tile>> c = hand.getRunMelds();
		
	
		System.out.println(a.toString());
		System.out.println(b.toString());
		System.out.println(c.toString());
		
		
		
		
		assertEquals(false , hand.hasMeld());
		assertEquals(true , hand2.hasMeld());
		assertEquals(true , hand3.hasMeld());
	}
	
	/*
	 * tests the sum of melds
	 * returns -1 if hand does not have a valid meld
	 */
	@Test
	public void meldSumTest() {
		
		assertEquals(-1 , hand.sumMeld());
		assertEquals(15 , hand2.sumMeld());
		assertEquals(30, hand3.sumMeld());
	}
	
	/*
	 * tests whether current hand has a valid meld with initial 30 value
	 */
	@Test
	public void initialThirtyTest() {
		
		assertEquals(false , hand.hasThirty());
		assertEquals(false , hand2.hasThirty());
		assertEquals(true , hand3.hasThirty());
	}
}

