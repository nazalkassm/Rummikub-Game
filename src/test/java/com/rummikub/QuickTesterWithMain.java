package com.rummikub;

import java.util.ArrayList;


//This class is to test algorithms,data structures and all other ideas to be used in the project quickly so that we can confirm something would work.
//We might extend this class to become a SuiteClass of Junit which would run first display some important information about testing and then run all Junit tests.

public class QuickTesterWithMain 
{
	public static void main(String[] args) 
	{
		Tile tile1 = new Tile(Ranks.ONE,Colors.BLUE);
		Stock stock = new Stock();
		System.out.println("stock1 length is : " + stock.getLength());
		stock.stockArray.add(tile1);
		System.out.println("stock1 length is : " + stock.getLength());
	}
}
