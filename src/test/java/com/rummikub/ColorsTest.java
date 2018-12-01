package com.rummikub;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.pmw.tinylog.Logger;

class ColorsTest {

	private static Colours color1;
	private static Colours color2;
	private static Colours color3;
	private static Colours color4;
	
	@BeforeAll
	static void setUpAll() {
		Logger.info("setUpAll");
		color1 = Colours.ORANGE;
		color2 = Colours.RED;
		color3 = Colours.GREEN;
		color4 = Colours.BLUE;
	}
	
	@Test
	void creationTest() {
		//Test the creation of enums through symbol
		//"R" indicates the Color of red 
		assertThat(color2, is(Colours.getColourFromSymbol("R")));
		//"B" indicates the Color of blue
		assertThat(color4, is(Colours.getColourFromSymbol("B")));	
	}
	
	@Test
	void symbolTest() {
		//Compare the symbols of Colors
		assertThat("O", is(color1.getSymbol()));
		assertThat("R", is(color2.getSymbol()));
		assertThat("G", is(color3.getSymbol()));
		assertThat("B", is(color4.getSymbol()));
	}

}
