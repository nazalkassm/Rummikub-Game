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
	private static Stock stock;

	@BeforeAll
	static void setUpBeforeClass() throws Exception 
	{
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

		//Table because strategy 3 needs to observe
				stock = new Stock();
				table = new Table(stock);
				
				//player1 has tiles greater than 30 and can play them.
				player1 = new Player("human",new Strategy0());
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

	@AfterEach
	void tearDown() throws Exception 
	{
		
	}
	
	@Test
	void scenario14AandB() throws IOException
	{System.out.println("===========\n 14A followed by 14B test \n ==========");/*p1:		
		/*p1:		
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
		Print.print("++++++ It is now human's turn: ++++++");
		//Player plays first
		List<Meld> meldsPlayed = table.getNextPlayerTurn().play();
		//Get list of changed melds 
		List<Meld> changedMelds = new ArrayList<>(Table.getDiffMelds(table.getAllMelds(), meldsPlayed));
		Print.print("\nTable is: ");
		Print.printMeldtoUser(meldsPlayed, changedMelds, true);
		table.updateMeldsOnTable(meldsPlayed);
		
		Print.print("++++++ It is now p3's turn: ++++++");
		/** P2 Turn*/ 
		//Regardless of what player plays, player 2 will get rid of initial 30 
		List<Meld> melds = new ArrayList<>(table.getNextPlayerTurn().play());
		changedMelds = new ArrayList<>(Table.getDiffMelds(table.getAllMelds(), melds));
		Print.print("\nTable is: ");
		Print.printMeldtoUser(melds, changedMelds, true);
		table.updateMeldsOnTable(melds);
		//so...R9, R10, R11
		assertTrue(melds.toString().contains("R9 R10 R11"));
		
		/** P1 Turn*/
		Print.print("++++++ It is now human's turn: ++++++");
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
		
		/** P3 Turn*/ 
		Print.print("++++++ It is now p3's turn: ++++++");
		
			melds = table.getNextPlayerTurn().play();
			changedMelds = new ArrayList<>(Table.getDiffMelds(table.getAllMelds(), melds));
			Print.print("\nTable is: ");
			Print.printMeldtoUser(melds, changedMelds, true);
			//Otherwise only playing those that require reuse so R12
			assertTrue(melds.toString().contains("R9 R10 R11 R12"));
			//Make sure the new meld wasn't played since it didn't require the 
			assertFalse(melds.toString().contains("B6 G6 O6 R6"));
		
			/** P1 Turn*/ 
			Print.print("++++++ It is now human's turn: ++++++");
			//Player plays first
			 meldsPlayed = table.getNextPlayerTurn().play();
			//Get list of changed melds 
			 changedMelds = new ArrayList<>(Table.getDiffMelds(table.getAllMelds(), meldsPlayed));
			Print.print("\nTable is: ");
			Print.printMeldtoUser(meldsPlayed, changedMelds, true);
			table.updateMeldsOnTable(meldsPlayed);
			
			Print.print("++++++ It is now p3's turn: ++++++");
			
			melds = table.getNextPlayerTurn().play();
			changedMelds = new ArrayList<>(Table.getDiffMelds(table.getAllMelds(), melds));
			Print.print("\nTable is: ");
			Print.printMeldtoUser(melds, changedMelds, true);
			//Since can't play anything, it will return an empty list of things changed
			assertTrue(changedMelds.isEmpty());
			//If not playing player draws another tile 
			if (changedMelds.isEmpty()) {
				int prevCountHand = table.getCurrentPlayer().getPlayerRack().getSize();
				table.getCurrentPlayer().getPlayerRack().takeTile(stock);
				//Assert that the player was able draw a new tile:
				assertTrue(prevCountHand + 1 == table.getCurrentPlayer().getPlayerRack().getSize());
			}
		
			
	}
	
	@Test
	void scenario13b() throws IOException
	{
		System.out.println("===========\n 13B test \n ==========");/*p1:		
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
		"O", "6")
		"R", "13"
		"R", "6"
		"B", "6*/
		//Give p2 R12, R6, B6
		player2.getPlayerRack().addTile(new Tile("G", "13"));
		player2.getPlayerRack().addTile(new Tile("R", "6")); 
		player2.getPlayerRack().addTile(new Tile("B", "6")); 
		/** P1 Turn*/ 
		Player human = table.getNextPlayerTurn();
		Print.print("++++++ It is now " + table.getCurrentPlayer().getName() + "'s turn: ++++++");
		Print.print("\nTable is: ");
		Print.printMeldtoUser(table.getAllMelds(), Collections.emptyList(), true);
		
		//Player plays first
		List<Meld> meldsPlayed = human.play();
		//Get list of changed melds 
		List<Meld> changedMelds = new ArrayList<>(Table.getDiffMelds(table.getAllMelds(), meldsPlayed));
		Print.print("\nTable is: ");
		Print.printMeldtoUser(meldsPlayed, changedMelds, true);
		//Player will play all 2 melds
		table.updateMeldsOnTable(meldsPlayed);
		
		//Assume player has played on initial cards...
		player2.canPlayOnExistingMelds = true;
		Print.print("++++++ It is now p3's turn: ++++++");
		/** P2 Turn*/ 
		// player has less then 3 tiles so it plays everything, including reusing what's on the table
		List<Meld> melds = new ArrayList<>(table.getNextPlayerTurn().play());
		changedMelds = new ArrayList<>(Table.getDiffMelds(table.getAllMelds(), melds));
		Print.print("\nTable is: ");
		Print.printMeldtoUser(melds, changedMelds, true);
		table.updateMeldsOnTable(melds);
		//Since playing all we will be playing All 6s and the G13 on the existing play by human so..
		assertTrue(melds.toString().contains("R9 R10 R11"));
		assertTrue(melds.toString().contains("G10 G11 G12 G13"));
		assertTrue(melds.toString().contains("B6 G6 O6 R6"));

	}
	
	@Test
	void scenario13a() throws IOException
	{System.out.println("===========\n 13A test \n ==========");
		/*p1:		
		"G", "10"
		"G", "11"
		"G", "12"
		"B", "9"
		"B", "10"
		"B", "11"
		"B", "12"
		"O", "1"
		         		         
		P2:
		"R", "6"
		"B", "6*/
		//Give p2, R6, B6
		player2.getPlayerRack().setRack(Collections.emptyList());
		player2.getPlayerRack().addTile(new Tile("R", "6")); 
		player2.getPlayerRack().addTile(new Tile("B", "6")); 
		
		/** P1 Turn*/ 
		/** P1 Turn*/ 
		Player human = table.getNextPlayerTurn();
		Print.print("++++++ It is now " + table.getCurrentPlayer().getName() + "'s turn: ++++++");
		Print.print("\nTable is: ");
		Print.printMeldtoUser(table.getAllMelds(), Collections.emptyList(), true);
		
		//Player plays first
		List<Meld> meldsPlayed = human.play();
		//Get list of changed melds 
		List<Meld> changedMelds = new ArrayList<>(Table.getDiffMelds(table.getAllMelds(), meldsPlayed));
		Print.print("\nTable is: ");
		Print.printMeldtoUser(meldsPlayed, changedMelds, true);
		//Player will play all 2 melds
		table.updateMeldsOnTable(meldsPlayed);
		
		//Assume player has played on initial cards...
		player2.canPlayOnExistingMelds = true;
		
		/** P2 Turn*/ 
		Print.print("++++++ It is now p3's turn: ++++++");
		// player 1 has less then 3 tiles so it player 2 will play everything, including reusing what's on the table
		//But player can't play so it tries but it can't
		List<Meld> melds = new ArrayList<>(table.getNextPlayerTurn().play());
		changedMelds = new ArrayList<>(Table.getDiffMelds(table.getAllMelds(), melds));
		Print.print("\nTable is: ");
		Print.printMeldtoUser(melds, changedMelds, true);
		table.updateMeldsOnTable(melds);
		//Since can't play anything, it will return an empty list of things changed
		assertTrue(changedMelds.isEmpty());
		//If not playing player draws another tile 
		if (changedMelds.isEmpty()) {
			int prevCountHand = table.getCurrentPlayer().getPlayerRack().getSize();
			table.getCurrentPlayer().getPlayerRack().takeTile(stock);
			//Assert that the player was able draw a new tile:
			assertTrue(prevCountHand + 1 == table.getCurrentPlayer().getPlayerRack().getSize());
		}
	}
}
