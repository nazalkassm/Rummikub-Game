package com.rummikub;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
 * USAGE:
 * 	At the start of execution (before a set of tests, or before the game starts) run ConsoleIO.init()
 * 	To get console input, use ConsoleIO.input([optional] String message)
 * 	To print to the console, use ConsoleIO.print([Any number of Strings to be printed on the same line])
 * 	When the execution is finished, run ConsoleIO.close() to close the scanner.
 */

// TO BE DONE VALIDATION OF INPUTS.
public class Prompt 
{
	private BufferedReader bi;
	private static boolean initialized = false;
	
	
	/**
	 * Purpose:  Initialize the bufferReader. A reader should only be
	 * 		opened and closed once per execution.
	 */
	public void init() 
	{
		if (!initialized) 
		{
			initialized = true;
			bi = new BufferedReader(new InputStreamReader(System.in));
		}
	}
	
	/**
	 * @throws IOException 
	 */
	public void close() throws IOException 
	{
		bi.close();
	}

	/**
	 * We are never going to prompt for an input without printing a message first.
	 * @throws IOException 
	 */
	public String promptInput(String message) throws IOException 
	{
		if (!initialized) 
		{
			init();
		}
		System.out.println(message);
		
		return bi.readLine();
	}
	
	/**
	 * 
	 */
	public void promptEnterKey()
	{
		System.out.println("Press \"ENTER\" to continue...");
		try {
			bi.readLine();
		} 
		catch (IOException e) 
		{
			System.exit(0);
		}
	}
}
