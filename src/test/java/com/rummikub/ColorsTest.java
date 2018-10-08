package com.rummikub;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.pmw.tinylog.Logger;

class ColorsTest {

	private static Colors color1;
	private static Colors color2;
	private static Colors color3;
	private static Colors color4;
	
	@BeforeAll
	static void setUpAll() throws Exception {
		Logger.info("setUpAll");
		color1 = Colors.ORANGE;
		color2 = Colors.RED;
		color3 = Colors.GREEN;
		color4 = Colors.BLUE;
	}
	
	@Test
	void creationTests() {
		//Test the creation of enums through symbol
		//"R" indicates the Color of red 
		assertThat(color2, is(Colors.getColorFromSymbol("R")));
		//"B" indicates the Color of blue
		assertThat(color4, is(Colors.getColorFromSymbol("B")));	
	}
	
	@Test
	void symbolTests() {
		//Compare the symbols of Colors
		assertThat("O", is(color1.getSymbol()));
		assertThat("R", is(color2.getSymbol()));
		assertThat("G", is(color3.getSymbol()));
		assertThat("B", is(color3.getSymbol()));
	}

}
