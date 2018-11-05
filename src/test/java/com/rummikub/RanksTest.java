package com.rummikub;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.pmw.tinylog.Logger;

class RanksTest {

	private static Ranks rank1;
	private static Ranks rank2;
	private static Ranks rank3;
	
	@BeforeAll
	static void setUpAll() throws Exception 
	{
		Logger.info("setUpAll");
		rank1 = Ranks.ONE;
		rank2 = Ranks.SEVEN;
		rank3 = Ranks.TWELVE;
	}
	
	@Test
	void creationTest() {
		//Test the creation of enums through symbol
		//"7" indicates the rank of 7 
		assertThat(Ranks.SEVEN, is(Ranks.getRankFromSymbol("7")));
		//"11" indicates the rank of 11
		assertThat(Ranks.ELEVEN, is(Ranks.getRankFromSymbol("11")));
		
	}
	
	@Test
	void valueTest() {
		//Compare the values of ranks
		assertThat(1, is(rank1.getValue()));
		assertThat(7, is(rank2.getValue()));
		assertThat(12, is(rank3.getValue()));
	}

	@Test
	void symbolTest() {
		//Compare the symbols of ranks
		assertThat("1", is(rank1.getSymbol()));
		assertThat("7", is(rank2.getSymbol()));
		assertThat("12", is(rank3.getSymbol()));
	}

}
