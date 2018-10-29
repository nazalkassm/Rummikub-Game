package com.rummikub;

import java.util.ArrayList;

import org.pmw.tinylog.Logger;


//This class is to test algorithms,data structures and all other ideas to be used in the project quickly so that we can confirm something would work.
//We might extend this class to become a SuiteClass of Junit which would run first display some important information about testing and then run all Junit tests.

public class QuickTesterWithMain 
{
	public static void main(String[] args) 
	{
		Tile tile1 = new Tile(Colours.BLUE, Ranks.ONE);
		Stock stock = new Stock();
		Player player1 = new Player("naz");
		//player1.fillRack(stock);
		Logger.info(stock.getLength());
		Logger.info(stock.getStockArray());
	}
}
