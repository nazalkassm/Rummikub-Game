package com.rummikub;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ScenarioTest {
	private Game game;
	static private List<Player>players = new ArrayList<Player>();
	Boolean printAllPlayerMelds = false;
	Boolean rigDraw = false;
	Boolean pauseBetweenTurns = false;
	Boolean GUI = false;
	
	@BeforeAll
	static void setUpClass() {
		players.add(new Player("p0",new Strategy0()));
		players.add(new Player("p1",new Strategy1()));
		players.add(new Player("p2",new Strategy2()));
		players.add(new Player("p3",new Strategy3()));
	}
	
	@BeforeEach
	void setUpTest() {
		
		game = new Game(players, printAllPlayerMelds, rigDraw, pauseBetweenTurns, GUI); 
		FileParser.reset();
	}
	
	@Test
	void Scenerio1Test() throws Exception {
		// 4.c)
		FileParser.parse("src/main/resources/inputFiles/test1.txt");
		assertFalse(FileParser.inputError);
		game.stock = FileParser.stock;
		game.table = new Table(game.stock);
		
		assertEquals(60, game.stock.getLength());
		assertEquals(3, FileParser.playerCommands.size());
		
		game.start();
		while (game.gameRunning) {
			game.takeTurn();
		}
		assertEquals("p0", game.winner.getName());
		
		Prompt.init();
	}
	
	@Test
	void Scenerio2Test() throws Exception {
		// 4.c)		
		FileParser.parse("src/main/resources/inputFiles/test2.txt");
		assertFalse(FileParser.inputError);
		
		game.stock = FileParser.stock;
		game.table = new Table(game.stock);
		
		assertEquals(60, game.stock.getLength());
		
		game.start();
		while (game.gameRunning) {
			game.takeTurn();
		}
		assertEquals("p1", game.winner.getName());
		
		Prompt.init();
	}
	
	@Test
	void Scenerio3Test() throws Exception {
		// 4.c)
		FileParser.parse("src/main/resources/inputFiles/test3.txt");
		assertFalse(FileParser.inputError);
		
		game.stock = FileParser.stock;
		game.table = new Table(game.stock);
		
		
		assertEquals(60, game.stock.getLength());
		
		game.start();
		while (game.gameRunning) {
			game.takeTurn();
		}
		assertEquals("p2", game.winner.getName());
		
		Prompt.init();
	}
	
	@Test
	void Scenerio4Test() throws Exception {
		// 4.c)
		
		FileParser.parse("src/main/resources/inputFiles/test4.txt");
		assertFalse(FileParser.inputError);
		
		game.stock = FileParser.stock;
		game.table = new Table(game.stock);
		
		assertEquals(60, game.stock.getLength());
		
		game.start();
		while (game.gameRunning) {
			game.takeTurn();
		}
		assertEquals("p3", game.winner.getName());
		
		Prompt.init();
	}
	
	@Test
	void Scenerio5Test() throws Exception {
		// 4a1, 8b, 10a
		
		FileParser.parse("src/main/resources/inputFiles/test5.txt");
		assertFalse(FileParser.inputError);
		
		game.stock = FileParser.stock;
		game.table = new Table(game.stock);
		
		assertEquals(60, game.stock.getLength());
		
		game.start();
		while (game.gameRunning) {
			game.takeTurn();
		}
		
		Prompt.init();
	}
	
	@Test
	void Scenerio6Test() throws Exception {
		// 4a2, 8a
		
		FileParser.parse("src/main/resources/inputFiles/test6.txt");
		assertFalse(FileParser.inputError);
		
		game.stock = FileParser.stock;
		game.table = new Table(game.stock);
		
		assertEquals(60, game.stock.getLength());
		
		game.start();
		while (game.gameRunning) {
			game.takeTurn();
		}
		
		Prompt.init();
	}
	
	@Test
	void Scenerio7Test() throws Exception {
		// 4a2, 8a
		
		FileParser.parse("src/main/resources/inputFiles/test7.txt");
		assertFalse(FileParser.inputError);
		
		game.stock = FileParser.stock;
		game.table = new Table(game.stock);
		
		assertEquals(60, game.stock.getLength());
		
		game.start();
		while (game.gameRunning) {
			game.takeTurn();
		}
		
		Prompt.init();
	}
	
	@Test
	void Scenerio8Test() throws Exception {
		// 8c
		
		FileParser.parse("src/main/resources/inputFiles/test8.txt");
		assertFalse(FileParser.inputError);
		
		game.stock = FileParser.stock;
		game.table = new Table(game.stock);
		
		assertEquals(60, game.stock.getLength());
		
		game.start();
		while (game.gameRunning) {
			game.takeTurn();
		}
		
		Prompt.init();
	}
	
	@Test
	void Scenerio9Test() throws Exception {
		
		FileParser.parse("src/main/resources/inputFiles/test9.txt");
		assertFalse(FileParser.inputError);
		
		game.stock = FileParser.stock;
		game.table = new Table(game.stock);
		
		assertEquals(64, game.stock.getLength());
		
		game.start();
		while (game.gameRunning) {
			game.takeTurn();
		}
		
		Prompt.init();
	}
	
	@Test
	void Scenerio10Test() throws Exception {
		
		FileParser.parse("src/main/resources/inputFiles/test10.txt");
		assertFalse(FileParser.inputError);
		
		game.stock = FileParser.stock;
		game.table = new Table(game.stock);
		
		assertEquals(60, game.stock.getLength());
		
		game.start();
		while (game.gameRunning) {
			game.takeTurn();
		}
		
		Prompt.init();
	}
	
	@Test
	void Scenerio11Test() throws Exception {
		
		FileParser.parse("src/main/resources/inputFiles/test11.txt");
		assertFalse(FileParser.inputError);
		
		game.stock = FileParser.stock;
		game.table = new Table(game.stock);
		
		assertEquals(64, game.stock.getLength());
		
		game.start();
		while (game.gameRunning) {
			game.takeTurn();
		}
		
		Prompt.init();
	}
	
	@Test
	void Scenario12Test() throws Exception {
		
		FileParser.parse("src/main/resources/inputFiles/test12.txt");
		assertFalse(FileParser.inputError);
		
		game.stock = FileParser.stock;
		game.table = new Table(game.stock);
		
		assertEquals(60, game.stock.getLength());
		
		game.start();
		while (game.gameRunning) {
			game.takeTurn();
		}
		
		Prompt.init();
	}
	
	@Test
	void Scenerio13Test() throws Exception {
		// 4.c)
		FileParser.parse("src/main/resources/inputFiles/test13.txt");
		assertFalse(FileParser.inputError);
		
		game.stock = FileParser.stock;
		game.table = new Table(game.stock);
		
		assertEquals(60, game.stock.getLength());
		
		game.start();
		while (game.gameRunning) {
			game.takeTurn();
		}
		
		assertEquals("p2", game.winner.getName());
		
		Prompt.init();
	}
	
	@Test
	void Scenerio14Test() throws Exception {
		// 4.c)
		FileParser.parse("src/main/resources/inputFiles/test14.txt");
		assertFalse(FileParser.inputError);
		
		game.stock = FileParser.stock;
		game.table = new Table(game.stock);
		
		assertEquals(61, game.stock.getLength());
		
		game.start();
		while (game.gameRunning) {
			game.takeTurn();
		}
		
		assertEquals("p3", game.winner.getName());
		
		Prompt.init();
	}
	
	@Test
	void Scenerio15Test() throws Exception {
		// 4.c)
		FileParser.parse("src/main/resources/inputFiles/test15.txt");
		assertFalse(FileParser.inputError);
		
		game.stock = FileParser.stock;
		game.table = new Table(game.stock);
		
		assertEquals(58, game.stock.getLength());
		
		game.start();
		while (game.gameRunning) {
			game.takeTurn();
		}
		
		Prompt.init();
	}

}
