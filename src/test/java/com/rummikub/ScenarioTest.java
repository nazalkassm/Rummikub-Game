package com.rummikub;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.pmw.tinylog.Logger;

public class ScenarioTest {
	
	@Test
	void Scenerio1Test() {
		Game game = new Game();
		
		FileParser.parse("src/main/resources/inputFiles/test1.txt");
		game.stock = FileParser.stock;
		game.table = new Table(game.stock);
		
		assertEquals(56, game.stock.getLength());
		assertEquals(3, FileParser.playerCommands.size());
		
		try {
			game.start();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
}
