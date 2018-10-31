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
	private static BufferedReader bi;
	private static boolean initialized = false;
	
	
	/**
	 * Purpose:  Initialize the bufferReader. A reader should only be
	 * 		opened and closed once per execution.
	 */
	public static void init() 
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
	public static String promptInput(String... message) throws IOException 
	{
		if (!initialized) 
		{
			init();
		}
		Print.println(message);
		
		return bi.readLine();
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

	public static void promptInputForMelds() 
	{
//				outerloop:
//				while(elementSize > 0 )
//				{
//				String[] arrayofNumbs = null;
//				System.out.println("\nThere are " + elementSize + " elements.");
//				System.out.println("\nYou are now deducting from the number " + elementNumber + " element. Enter (-1) to repeat a component.");
//				System.out.println("\nEnter the sub-elements you want to deduct, enter " + component.getElements().get(elementNumber-1).getElements().size() + " numbers separated by space: ");	
//				
//					try 
//					{
//						arrayofNumbs = bi.readLine().split("\\s");
//					}  
//					catch (IOException e) 
//					{
//						e.printStackTrace();
//					}
//						for(int i=0; i<arrayofNumbs.length; i++) 
//						{
//							try 
//							{
//								if (Integer.parseInt(arrayofNumbs[i]) > 1 || Integer.parseInt(arrayofNumbs[i]) < 0 || arrayofNumbs.length != component.getElements().get(elementNumber-1).getElements().size())
//								{
//									throw new IllegalArgumentException();
//								}
//								else
//								{
//								returnArray.add(Integer.parseInt(arrayofNumbs[i]));
//								}
//							}
//							catch (IllegalArgumentException e)
//							{
//								System.out.println("----------Wrong input try again-----------");
//								elementNumber = 1;
//								elementSize = component.getElements().size();
//								returnArray.clear();
//								continue outerloop;
//							}
//						}
//				elementSize--;
//				elementNumber++;
//				returnArray.add(-1);
//				}
	}
}
