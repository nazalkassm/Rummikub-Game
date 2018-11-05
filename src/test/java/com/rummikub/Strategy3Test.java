package com.rummikub;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pmw.tinylog.Logger;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

class Strategy3Test {
	
	private static List<Meld> meld1,meld2;
	private static Player player1;
	private static Player player2;
	private static Player player3;
	private static Table table;

	@BeforeAll
	static void setUpBeforeClass() throws Exception 
	{
	//Table because strategy 3 needs to observe
			Stock stock = new Stock();
			table = new Table(stock);
			
			//player1 has tiles greater than 30 and can play them.
			player1 = new Player("Naz",new Strategy0());
			player2 = new Player("p3",new Strategy3());
			
			table.addPlayers(player1,player2);
			table.initPlayersTurn();
			
			//Clear player racks 
			player1.getPlayerRack().setRack(Collections.emptyList());
			//player 1 has two nice runs should play them all.
			player1.getPlayerRack().addTile(new Tile("G", "10")); //G5
			player1.getPlayerRack().addTile(new Tile("G", "11")); //G6
			player1.getPlayerRack().addTile(new Tile("G", "12")); //G6
			player1.getPlayerRack().addTile(new Tile("B", "9")); //G4
			player1.getPlayerRack().addTile(new Tile("B", "10")); //G5
			player1.getPlayerRack().addTile(new Tile("B", "11")); //G6
			player1.getPlayerRack().addTile(new Tile("B", "12")); //G6
			player1.getPlayerRack().addTile(new Tile("O", "1")); //G6
			
			
			//player2 does not have tiles greater than 30
			player2.getPlayerRack().setRack(Collections.emptyList());
			player2.getPlayerRack().addTile(new Tile("R", "9")); //G4
			player2.getPlayerRack().addTile(new Tile("R", "10")); //G5
			player2.getPlayerRack().addTile(new Tile("R", "11")); //G6
			player2.getPlayerRack().addTile(new Tile("G", "2")); //G6
			player2.getPlayerRack().addTile(new Tile("R", "3")); //G4
			player2.getPlayerRack().addTile(new Tile("B", "4")); //G5
			player2.getPlayerRack().addTile(new Tile("G", "6")); //G6
			player2.getPlayerRack().addTile(new Tile("O", "6")); //G6
			table.notifyObservers();
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception 
	{
		player1 = null;
		meld1 = null;
		meld2 = null;
	}

	@BeforeEach
	void setUp() throws Exception 
	{
		
	}

	@AfterEach
	void tearDown() throws Exception 
	{
		
	}
	
	@Test
	void useStrategy_removeTiles_Test() throws IOException
	{/*p1:		
		"G", "10"
		"G", "11"
		"G", "12"
		"B", "9"
		"B", "10"
		"B", "11"
		"B", "12"
		"O", "1"
		         		         
		P2:
		"R", "9")
		"R", "10"
		"R", "11"
		"G", "2")
		"R", "3")
		"B", "4")
		"G", "6")
		"O", "6")*/
		
		/** P1 Turn*/ 
		//Player plays first
		table.updateMeldsOnTable(table.getNextPlayerTurn().play());
		
		/** P2 Turn*/ 
		//Regardless of what player plays, player 2 will get rid of initial 30 
		List<Meld> melds = new ArrayList<>(table.getNextPlayerTurn().play());
		table.updateMeldsOnTable(melds);
		//so...R9, R10, R11
		assertTrue(melds.toString().contains("R9 R10 R11"));
		
		/** P1 Turn*/
		melds = table.getNextPlayerTurn().play();
		if (!melds.isEmpty()) {
			table.updateMeldsOnTable(melds);
		}
		//Give p2 R12, R6, B6
		player2.getPlayerRack().addTile(new Tile("R", "12"));
		player2.getPlayerRack().addTile(new Tile("R", "6")); 
		player2.getPlayerRack().addTile(new Tile("B", "6")); 
		/*P2:
		"G", "2")
		"R", "3")
		"B", "4")
		"G", "6")
		"O", "6")
		"R", "12"
		"R", "6"
		"B", "6"*/
		
		/** P2 Turn*/ 
	
		//If the player 1 hand is less then or equal to 3, p3 plays all cards  
		if (player1.getPlayerRack().getSize() <= (player2.getPlayerRack().getSize()-3)) {
			melds = table.getNextPlayerTurn().play();
			//Since playing all we will be playing All 6s and the R12 so..
			assertTrue(melds.toString().contains("R9 R10 R11 R12"));
			assertTrue(melds.toString().contains("B6 G6 O6 R6"));
		} else {
			melds = table.getNextPlayerTurn().play();
			//Otherwise only playing those that require reuse so R12
			assertTrue(melds.toString().contains("R9 R10 R11 R12"));
			//Make sure the new meld wasn't played since it didn't require the 
			assertFalse(melds.toString().contains("B6 G6 O6 R6"));
		}
	}
	
	@Test
	/*
	 * accounts for 9a) 9e)
	 */
	public void scenario9Testa() throws IOException {
		Stock stock = new Stock();
		table = new Table(stock);
		
		//player1 has tiles greater than 30 and can play them.
		player1 = new Player("Naz",new Strategy0());
		player2 = new Player("p3",new Strategy3());
		
		table.addPlayers(player1,player2);
		table.initPlayersTurn();
		
		player1.getPlayerRack().setRack(Collections.emptyList());
		player2.getPlayerRack().setRack(Collections.emptyList());
	
		
		Tile t1 = new Tile("G","1");
		Tile t2 = new Tile("G","2");
		Tile t3 = new Tile("G","3");
		Tile t4 = new Tile("G","4");
		Tile t5 = new Tile("R","4");
		Tile t6 = new Tile("R","5");
		Tile t7 = new Tile("R","6");
		Tile t8 = new Tile("O","4");
		Tile t9 = new Tile("B","4");
		Tile t10 = new Tile("G","4");
		Tile t11 = new Tile("B","3");
		Tile t12 = new Tile("B","5");
		Tile t13 = new Tile("R","4");
		Tile t14 = new Tile("G","5");
		
		Meld m1 = new Meld(t1,t2,t3,t4);
		Meld m2 = new Meld(t5,t6,t7);
		Meld m3 = new Meld(t8,t9,t10);
		
		List<Meld> tMelds = new ArrayList<Meld>();
		tMelds.add(m1);
		tMelds.add(m2);
		tMelds.add(m3);
		
		player1.getPlayerRack().addTile(new Tile("G", "13"));
		
		player2.getPlayerRack().addTile(t11);
		player2.getPlayerRack().addTile(t12);
		player2.getPlayerRack().addTile(t13);
		player2.getPlayerRack().addTile(t14);
		
		table.updateMeldsOnTable(tMelds);
		
		player2.play();	
	}
	
	@Test
	/*
	 * accounts for 9b) 9f)
	 */
	public void scenario9Testb() throws IOException {
		Stock stock = new Stock();
		table = new Table(stock);
		
		//player1 has tiles greater than 30 and can play them.
		player1 = new Player("Naz",new Strategy0());
		player2 = new Player("p3",new Strategy3());
		
		table.addPlayers(player1,player2);
		table.initPlayersTurn();
		
		player1.getPlayerRack().setRack(Collections.emptyList());
		player2.getPlayerRack().setRack(Collections.emptyList());
	
		
		Tile t1 = new Tile("O","11");
		Tile t2 = new Tile("G","11");
		Tile t3 = new Tile("B","11");
		Tile t4 = new Tile("G","1");
		Tile t5 = new Tile("G","2");
		Tile t6 = new Tile("G","3");
		Tile t7 = new Tile("R","5");
		Tile t8 = new Tile("R","6");
		Tile t9 = new Tile("R","7");
		Tile t10 = new Tile("R","11");
		Tile t11 = new Tile("G","4");
		Tile t12 = new Tile("R","4");
		
		Meld m1 = new Meld(t1,t2,t3);
		Meld m2 = new Meld(t4,t5,t6);
		Meld m3 = new Meld(t7,t8,t9);
		
		List<Meld> tMelds = new ArrayList<Meld>();
		tMelds.add(m1);
		tMelds.add(m2);
		tMelds.add(m3);
		
		player1.getPlayerRack().addTile(new Tile("O", "1"));
		
		player2.getPlayerRack().addTile(t10);
		player2.getPlayerRack().addTile(t11);
		player2.getPlayerRack().addTile(t12);
		
		table.updateMeldsOnTable(tMelds);
		
		player2.play();	
	}
	
	@Test
	/*
	 * accounts for 9c) 9d)
	 */
	public void scenario9Testc() throws IOException {
		Stock stock = new Stock();
		table = new Table(stock);
		
		//player1 has tiles greater than 30 and can play them.
		player1 = new Player("Naz",new Strategy0());
		player2 = new Player("p3",new Strategy3());
		
		table.addPlayers(player1,player2);
		table.initPlayersTurn();
		
		player1.getPlayerRack().setRack(Collections.emptyList());
		player2.getPlayerRack().setRack(Collections.emptyList());
	
		
		Tile t1 = new Tile("R","1");
		Tile t2 = new Tile("R","2");
		Tile t3 = new Tile("R","3");
		Tile t4 = new Tile("R","4");
		Tile t5 = new Tile("O","5");
		Tile t6 = new Tile("O","6");
		Tile t7 = new Tile("O","7");
		Tile t8 = new Tile("B","4");
		Tile t9 = new Tile("G","4");
		Tile t10 = new Tile("O","8");
		Tile t11 = new Tile("O","9");
		
		Meld m1 = new Meld(t1,t2,t3,t4);
		Meld m2 = new Meld(t5,t6,t7);
		
		List<Meld> tMelds = new ArrayList<Meld>();
		tMelds.add(m1);
		tMelds.add(m2);
		
		player1.getPlayerRack().addTile(new Tile("G", "13"));
		
		player2.getPlayerRack().addTile(t8);
		player2.getPlayerRack().addTile(t9);
		player2.getPlayerRack().addTile(t10);
		player2.getPlayerRack().addTile(t11);
		
		table.updateMeldsOnTable(tMelds);
		
		player2.play();	
	}
	
	@Test
	/*
	 * accounts for 9g)
	 */
	public void scenario9Testd() throws IOException {
		Stock stock = new Stock();
		table = new Table(stock);
		
		//player1 has tiles greater than 30 and can play them.
		player1 = new Player("Naz",new Strategy0());
		player2 = new Player("p3",new Strategy3());
		
		table.addPlayers(player1,player2);
		table.initPlayersTurn();
		
		player1.getPlayerRack().setRack(Collections.emptyList());
		player2.getPlayerRack().setRack(Collections.emptyList());
	
		
		Tile t1 = new Tile("R","1");
		Tile t2 = new Tile("R","2");
		Tile t3 = new Tile("R","3");
		Tile t4 = new Tile("R","4");
		Tile t5 = new Tile("O","4");
		Tile t6 = new Tile("O","5");
		Tile t7 = new Tile("O","6");
		Tile t8 = new Tile("O","7");
		Tile t9 = new Tile("B","4");
		
		Meld m1 = new Meld(t1,t2,t3,t4);
		Meld m2 = new Meld(t5,t6,t7,t8);
		
		List<Meld> tMelds = new ArrayList<Meld>();
		tMelds.add(m1);
		tMelds.add(m2);
		
		player1.getPlayerRack().addTile(new Tile("G", "13"));
		
		player2.getPlayerRack().addTile(t9);
		
		table.updateMeldsOnTable(tMelds);
		
		player2.play();	
	}
}
