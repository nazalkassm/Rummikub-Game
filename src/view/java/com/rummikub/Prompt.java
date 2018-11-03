package com.rummikub;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.List;

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
	private static BufferedReader bi;
	private static boolean initialized = false;
	
	
	/**
	 * Purpose:  Initialize the bufferReader. A reader should only be
	 * 		opened and closed once per execution.
	 */
	public static void init() 
	{
		initialized = true;
		bi = new BufferedReader(new InputStreamReader(System.in));
	}
	
	/**
	 * Purpose:  Initialize the bufferReader with a list of commands.
	 */
	public static void init(List<String> commands) 
	{
		initialized = true;
		
		StringBuilder sb = new StringBuilder();
		sb.append("\nHuman\n");
		for (String s : commands)
		{
		    sb.append(s);
		    sb.append("\n");
		}
		
		String commandsString = sb.toString();
		
		bi = new BufferedReader(new StringReader(commandsString));
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
	public static String promptInput(String... message) throws IOException 
	{
		String input = null;
		if (!initialized) 
		{
			init();
		}
		Print.println(message);
		
		input = bi.readLine();
		if (input == null) init();
		input = bi.readLine();
		
		return input;
	}
	
	/**
	 * 
	 */
	public void promptEnterKey()
	{
		if (!initialized) 
		{
			init();
		}
		
		System.out.println("Press \"ENTER\" to start game...");
			
			try {
				bi.readLine();
			} 
			catch (IOException e) 
			{
				System.exit(0);
			}	
	}

}
