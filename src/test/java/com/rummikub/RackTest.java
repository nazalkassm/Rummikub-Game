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
	public static void setup() {
		hand = new Rack();
		hand2 = new Rack();
		hand3 = new Rack();
		stock = new Stock();
		
		hand.addTile(new Tile("G","5"));
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
		assertEquals(3, hand.getSize());
	}
	
	/*
	 * tests whether the tiles in hand are sorted
	 * Rack.Rack() should create a hand with 14 tiles already sorted
	 * Rack.drawTile() should call sort each time a new tile is drawn from Stock
	 */
	@Test
	public void sortTest() {

		System.out.println("Sorting Test");
		
		hand2.sortRack();
 
		assertEquals("G4", hand2.getRackArray().get(0).toString());
		assertEquals("G5", hand2.getRackArray().get(1).toString());
		assertEquals("G6", hand2.getRackArray().get(2).toString());
		
		
		hand.addTile(new Tile("R", "9")); //G4
		hand.addTile(new Tile("G", "10")); //G5
		hand.addTile(new Tile("G", "11")); //G6
		hand.addTile(new Tile("G", "12")); //G6
		hand.addTile(new Tile("R", "9")); //G4
		hand.addTile(new Tile("R", "10")); //G5
		hand.addTile(new Tile("R", "11")); //G6
		hand.addTile(new Tile("R", "12")); //G6
		
		System.out.println(hand.toString());
		hand.sortRack();
		assertEquals("G5 G10 G11 G12 B4 B9 R9 R9 R10 R11 R12 " , hand.toString());
		
	}
	
	/*
	 * tests whether Rack.drawTile() function works
	 * each time draw is called, size of rack should increment by 1
	 */
	@Test
	public void rackSizeTest() {

		//size is different from intial decleration due to adding elements to it in differnt tests
		assertEquals(11, hand.getSize());
		
		hand.takeTile(stock);
		hand.takeTile(stock);
		
		assertEquals(13, hand.getSize());
	}
	
	/*
	 * tests whether there exists a valid run meld
	 * takes a dummy constructor and adds tiles manually
	 */
	@Test
	public void meldRunTest() {

		hand2.addTile(new Tile("R", "9")); //G4
		hand2.addTile(new Tile("G", "10")); //G5
		hand2.addTile(new Tile("G", "11")); //G6
		hand2.addTile(new Tile("G", "12")); //G6
		hand2.addTile(new Tile("R", "10")); //G4
		hand2.addTile(new Tile("R", "10")); //G5
		hand2.addTile(new Tile("R", "11")); //G6
		hand2.addTile(new Tile("R", "12")); //G6
		hand2.sortRack();
		
		System.out.println("Meld Runs Test");
		System.out.println(hand2.toString());
		System.out.println(hand2.getMelds().toString());
		
		assertEquals("[G4 G5 G6 , G10 G11 G12 , R9 R10 R11 R12 ]", 
				hand2.getMelds().toString());
	}
	
	/*
	 * tests whether there exists a valid run meld
	 * takes a dummy constructor and adds tiles manually
	 */
	@Test
	public void meldSetTest() {
		hand3.addTile(new Tile("G", "10"));
		hand3.addTile(new Tile("O", "10"));
		hand3.addTile(new Tile("B", "10"));
		hand3.addTile(new Tile("R", "10"));
		hand3.addTile(new Tile("R", "12"));
		hand3.addTile(new Tile("B", "12"));
		hand3.addTile(new Tile("O", "12"));
		hand3.addTile(new Tile("B", "12"));
		hand3.sortRack();
		
		System.out.println("Meld Sets Test");
		System.out.println(hand3.toString());
		System.out.println(hand3.getMelds().toString());
		
		assertEquals("[G10 B10 R10 O10 , G10 R10 O10 , B12 R12 O12 ]", 
				hand3.getMelds().toString());
	}
	
	/*
	 * Tests all the meld possibilities for melds 
	 */
	@Test
	public void meldTest() {
		hand3 = new Rack();
		
		hand3.addTile(new Tile("B", "8"));
		hand3.addTile(new Tile("G", "8"));
		hand3.addTile(new Tile("R", "8"));
		hand3.addTile(new Tile("G", "9"));
		hand3.addTile(new Tile("G", "10"));
		hand3.addTile(new Tile("R", "6"));
		hand3.addTile(new Tile("R", "7"));
		hand3.addTile(new Tile("B", "10"));
		hand3.addTile(new Tile("O", "10"));
		hand3.addTile(new Tile("O", "8"));
		
		
		hand3.sortRack();
		System.out.println("Full Meld Test");
		System.out.println(hand3.toString());
		System.out.println("All melds");
		System.out.println(hand3.getMelds().toString());
		
		assertEquals("[G8 G9 G10 , R6 R7 R8 , G8 B8 R8 O8 , G10 B10 O10 ]", 
				hand3.getMelds().toString());
	}
	
	/*
	 * tests the sum of melds
	 * returns -1 if hand does not have a valid meld
	 */
	/*@Test
	public void meldSumTest() {
		
		/*assertEquals(-1 , hand.sumMeld());
		assertEquals(15 , hand2.sumMeld());
		assertEquals(30, hand3.sumMeld());
	}
	
	/*
	 * tests whether current hand has a valid meld with initial 30 value
	 */
	/*@Test
	public void initialThirtyTest() {
		
		
	}*/
}

