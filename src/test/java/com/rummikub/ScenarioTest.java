package com.rummikub;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.IOException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ScenarioTest {
	private Game game;
	
	@BeforeEach
	void setUpClass() {
		game = new Game();
		game.printRackMeld = true;
		game.waitAferEachTurn = false;
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
		FileParser.parse("src/main/resources/inputFiles/test2.txt");
		assertFalse(FileParser.inputError);
		
		game.stock = FileParser.stock;
		game.table = new Table(game.stock);
		
		assertEquals(60, game.stock.getLength());
		
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
		FileParser.parse("src/main/resources/inputFiles/test3.txt");
		assertFalse(FileParser.inputError);
		
		game.stock = FileParser.stock;
		game.table = new Table(game.stock);
		
		
		assertEquals(60, game.stock.getLength());
		
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
		
		FileParser.parse("src/main/resources/inputFiles/test4.txt");
		assertFalse(FileParser.inputError);
		
		game.stock = FileParser.stock;
		game.table = new Table(game.stock);
		
		assertEquals(60, game.stock.getLength());
		
		try {
			Prompt.init(FileParser.playerCommands);
			game.start();
			
			assertEquals("Player 4", game.winner.getName());
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		Prompt.init();
	}
	
	@Test
	void Scenerio5Test() {
		// 4a1, 8b, 10a
		
		FileParser.parse("src/main/resources/inputFiles/test5.txt");
		assertFalse(FileParser.inputError);
		
		game.stock = FileParser.stock;
		game.table = new Table(game.stock);
		
		assertEquals(60, game.stock.getLength());
		
		try {
			Prompt.init(FileParser.playerCommands);
			game.start();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		Prompt.init();
	}
	
	@Test
	void Scenerio6Test() {
		// 4a2, 8a
		
		FileParser.parse("src/main/resources/inputFiles/test6.txt");
		assertFalse(FileParser.inputError);
		
		game.stock = FileParser.stock;
		game.table = new Table(game.stock);
		
		assertEquals(60, game.stock.getLength());
		
		try {
			Prompt.init(FileParser.playerCommands);
			game.start();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		Prompt.init();
	}
	
	@Test
	void Scenerio7Test() {
		// 4a2, 8a
		
		FileParser.parse("src/main/resources/inputFiles/test7.txt");
		assertFalse(FileParser.inputError);
		
		game.stock = FileParser.stock;
		game.table = new Table(game.stock);
		
		assertEquals(60, game.stock.getLength());
		
		try {
			Prompt.init(FileParser.playerCommands);
			game.start();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		Prompt.init();
	}
	
	@Test
	void Scenerio8Test() {
		// 8c
		
		FileParser.parse("src/main/resources/inputFiles/test8.txt");
		assertFalse(FileParser.inputError);
		
		game.stock = FileParser.stock;
		game.table = new Table(game.stock);
		
		assertEquals(60, game.stock.getLength());
		
		try {
			Prompt.init(FileParser.playerCommands);
			game.start();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		Prompt.init();
	}
}
