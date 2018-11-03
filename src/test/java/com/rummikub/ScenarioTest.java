package com.rummikub;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.IOException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ScenarioTest {
	private static Game game;
	
	@BeforeAll
	static void setUpClass() {
		game = new Game();
	}
	
	@Test
	void Scenerio1Test() {
		// 4.c)
		FileParser.parse("src/main/resources/inputFiles/test1.txt");
		assertFalse(FileParser.inputError);
		
		game.stock = FileParser.stock;
		game.table = new Table(game.stock);
		
		assertEquals(60, game.stock.getLength());
		assertEquals(3, FileParser.playerCommands.size());
		
		try {
			Prompt.init(FileParser.playerCommands);
			game.start();
			
			assertEquals("Human", game.winner.getName());
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		Prompt.init();
	}
	
	@Test
	void Scenerio2Test() {
		// 4.c)
		Game game = new Game();
		
		FileParser.parse("src/main/resources/inputFiles/test2.txt");
		assertFalse(FileParser.inputError);
		
		game.stock = FileParser.stock;
		game.table = new Table(game.stock);
		
		assertEquals(60, game.stock.getLength());
		//assertEquals(3, FileParser.playerCommands.size());
		
		try {
			Prompt.init(FileParser.playerCommands);
			game.start();
			
			assertEquals("Player 2", game.winner.getName());
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		Prompt.init();
	}
	
	@Test
	void Scenerio3Test() {
		// 4.c)
		Game game = new Game();
		
		FileParser.parse("src/main/resources/inputFiles/test3.txt");
		assertFalse(FileParser.inputError);
		
		game.stock = FileParser.stock;
		game.table = new Table(game.stock);
		
		assertEquals(60, game.stock.getLength());
		//assertEquals(3, FileParser.playerCommands.size());
		
		try {
			Prompt.init(FileParser.playerCommands);
			game.start();
			
			assertEquals("Player 3", game.winner.getName());
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		Prompt.init();
	}
	
	@Test
	void Scenerio4Test() {
		// 4.c)
		Game game = new Game();
		
		FileParser.parse("src/main/resources/inputFiles/test4.txt");
		assertFalse(FileParser.inputError);
		
		game.stock = FileParser.stock;
		game.table = new Table(game.stock);
		
		assertEquals(60, game.stock.getLength());
		//assertEquals(3, FileParser.playerCommands.size());
		
		try {
			Prompt.init(FileParser.playerCommands);
			game.start();
			
			assertEquals("Player 4", game.winner.getName());
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		Prompt.init();
	}
	
}
