package com.rummikub;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ScenarioTest {
	private Game game;
	static private List<Player>players = new ArrayList<Player>();
	
	@BeforeAll
	static void setUpClass() {
		players.add(new Player("p0",new Strategy0()));
		players.add(new Player("p1",new Strategy1()));
		players.add(new Player("p2",new Strategy2()));
		players.add(new Player("p3",new Strategy3()));
	}
	
	@BeforeEach
	void setUpTest() {
		game = new Game(players); 
		game.printRackMeld = true;
		game.waitAferEachTurn = false;
		FileParser.reset();
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
			game.start();
			
			assertEquals("p1", game.winner.getName());
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
			game.start();
			
			assertEquals("p2", game.winner.getName());
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
			game.start();
			
			assertEquals("p3", game.winner.getName());
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
			game.start();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		Prompt.init();
	}
	
	@Test
	void Scenerio9Test() {
		
		FileParser.parse("src/main/resources/inputFiles/test9.txt");
		assertFalse(FileParser.inputError);
		
		game.stock = FileParser.stock;
		game.table = new Table(game.stock);
		
		assertEquals(64, game.stock.getLength());
		
		try {
			game.start();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		Prompt.init();
	}
	
	@Test
	void Scenerio10Test() {
		
		FileParser.parse("src/main/resources/inputFiles/test10.txt");
		assertFalse(FileParser.inputError);
		
		game.stock = FileParser.stock;
		game.table = new Table(game.stock);
		
		assertEquals(60, game.stock.getLength());
		
		try {
			game.start();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		Prompt.init();
	}
	
	@Test
	void Scenerio11Test() {
		
		FileParser.parse("src/main/resources/inputFiles/test11.txt");
		assertFalse(FileParser.inputError);
		
		game.stock = FileParser.stock;
		game.table = new Table(game.stock);
		
		assertEquals(64, game.stock.getLength());
		
		try {
			game.start();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		Prompt.init();
	}
	
	@Test
	void Scenario12Test() {
		
		FileParser.parse("src/main/resources/inputFiles/test12.txt");
		assertFalse(FileParser.inputError);
		
		game.stock = FileParser.stock;
		game.table = new Table(game.stock);
		
		assertEquals(60, game.stock.getLength());
		
		try {
			game.start();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		Prompt.init();
	}
	
	@Test
	void Scenerio13Test() {
		// 4.c)
		FileParser.parse("src/main/resources/inputFiles/test13.txt");
		assertFalse(FileParser.inputError);
		
		game.stock = FileParser.stock;
		game.table = new Table(game.stock);
		
		assertEquals(60, game.stock.getLength());
		
		try {
			game.start();
			
			assertEquals("p2", game.winner.getName());
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		Prompt.init();
	}
	
	@Test
	void Scenerio14Test() {
		// 4.c)
		FileParser.parse("src/main/resources/inputFiles/test14.txt");
		assertFalse(FileParser.inputError);
		
		game.stock = FileParser.stock;
		game.table = new Table(game.stock);
		
		assertEquals(61, game.stock.getLength());
		
		try {
			game.start();
			
			assertEquals("p3", game.winner.getName());
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		Prompt.init();
	}
	
	@Test
	void Scenerio15Test() {
		// 4.c)
		FileParser.parse("src/main/resources/inputFiles/test15.txt");
		assertFalse(FileParser.inputError);
		
		game.stock = FileParser.stock;
		game.table = new Table(game.stock);
		
		assertEquals(58, game.stock.getLength());
		
		try {
			game.start();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		Prompt.init();
	}

}
