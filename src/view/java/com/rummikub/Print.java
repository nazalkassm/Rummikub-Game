package com.rummikub;

public class Print 
{
	/**
	 * Purpose: Prints as many sentences you enter on the same line.
	 *  Then goes to next line to prepare for any other prints.
	 * @param message
	 * @since version 2
	 */
	public void print(String... message) 
	{
		for (String s : message) 
		{
			System.out.print(s);
		}
		System.out.println();
	}
	
	/**
	 * Purpose: Prints as many sentences you enter on different lines.
	 * @param message
	 * @since version 2
	 */
	public void println(String... message) 
	{
		for(String s : message)
		{
			System.out.println(s);
		}
	}

	public void printIntroduction() 
	{
		println("Hello","Welcome to TileRummy","The rules are :","bla bla bla bla bla");
	}
}
