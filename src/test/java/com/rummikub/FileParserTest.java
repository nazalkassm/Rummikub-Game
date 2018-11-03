package com.rummikub;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.pmw.tinylog.Logger;

class FileParserTest {

	@Test
	void initialStockCreationTest() {
		FileParser.parse("src/main/resources/inputFiles/test1.txt");
		
		assertFalse(FileParser.inputError);
		assertEquals(56, FileParser.stock.getLength());
		assertEquals(3, FileParser.playerCommands.size());
	}
	
}