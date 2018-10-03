package com.rummikub;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Disabled;
//import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.pmw.tinylog.Logger;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
	
import com.rummikub.Tile;

class TileTest {
	//hi
	private static Tile tile1;
	private static Tile tile2;
	private static Tile tile3;
	private static Tile tile4;
	private static ByteArrayOutputStream outContent; 
	
	@BeforeAll
	static void setUpAll() throws Exception
	{
		Logger.info("setUpAll");
		tile1 = new Tile(1,"red");
		tile2 = new Tile(1,"red");
		tile3 = new Tile(4,"red");
		tile4 = new Tile(3,"red");
		outContent= new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
	}
	
	@Test
	void printTest() 
	{		
		tile1.print();
		assertTrue(outContent.toString().contains("red"));
		assertTrue(outContent.toString().contains("1"));
	}
	
	@Test
	void randomTest()
	{
		assertEquals(1,1);
	}
	
	@Test
	void compareTest()
	{
		assertThat(0,is(tile1.compareTo(tile2)));
		assertThat(1,is(tile2.compareTo(tile3)));
		assertThat(-1,is(tile1.compareTo(tile2)));
	}
	
}
