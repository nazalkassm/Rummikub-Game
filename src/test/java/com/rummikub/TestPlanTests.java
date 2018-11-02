package com.rummikub;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.pmw.tinylog.Logger;

class TestPlanTests {
	
	@Test
	public void scenarioTest1() {
		Stock stock = new Stock();
		Player playerX = new Player("p1", new Strategy0());
		Player player1 = new Player("p2", new Strategy1());
		Player player2 = new Player("p3", new Strategy2());
		Player player3 = new Player("p4", new Strategy3());
		//Create the new table
		Table tableScenario1 = new Table(stock);
		tableScenario1.addPlayers(player1, player2, player3, playerX);
		//Set initial racks of each player
		playerX.setRack(new Tile("7G"), new Tile("11R"), new Tile("10G"), new Tile("11R"), new Tile("3G"), new Tile("8G"), new Tile("3O"), new Tile("10G"), new Tile("8O"), new Tile("5B"), new Tile("3B"), new Tile("7O"), new Tile("6R"), new Tile("3G");
		player1.setRack(new Tile("11G"), new Tile("6B"), new Tile("6O"), new Tile("7G"), new Tile("6G"), new Tile("12O"), new Tile("8B"), new Tile("9R"), new Tile("5R"), new Tile("8R"), new Tile("13G"), new Tile("10R"), new Tile("7R"), new Tile("9R"));
		player2.setRack(new Tile("5O"), new Tile("5O"), new Tile("13O"), new Tile("1B"), new Tile("4B"), new Tile("7B"), new Tile("8B"), new Tile("10B"), new Tile("13B"), new Tile("1R"), new Tile("4R"), new Tile("6R"), new Tile("9G"), new Tile("12G"));
		player3.setRack(new Tile("1R"), new Tile("3R"), new Tile("4R"), new Tile("7R"), new Tile("12R"), new Tile("13R"), new Tile("13R"), new Tile("1O"), new Tile("10O"), new Tile("11O"), new Tile("12O"), new Tile("1G"), new Tile("1G"), new Tile("11B"));
		
		
		//PlayerX can't play anything 
		assertThat(true, is(playerX.play().isEmpty()));
		//Add a 2G to player's rack
		playerX.getPlayerRack().addTile(new Tile("2G"));
		
		//Player1 plays 6 as a row
		Meld meldToPlay = new Meld("6B 6O 6G");
		List<Meld> meldsPlayed = new ArrayList<Meld>();
		meldsPlayed.add(meldToPlay);
		assertThat("6B 6O 6G ", is(meldToPlay.toString()));
		assertEquals(meldsPlayed, playerX.play(meldToPlay));
		tableScenario1.addMeldToTable(meldToAdd)
	}
	
	
}
