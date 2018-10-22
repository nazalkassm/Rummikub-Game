package com.rummikub;

import java.util.Scanner;


/*
 * USAGE:
 * 	At the start of execution (before a set of tests, or before the game starts) run ConsoleIO.init()
 * 	To get console input, use ConsoleIO.input([optional] String message)
 * 	To print to the console, use ConsoleIO.print([Any number of Strings to be printed on the same line])
 * 	When the execution is finished, run ConsoleIO.close() to close the scanner.
 */

public class ConsoleIO {
	private static Scanner scanner;
	private static boolean initialized = false;
	
	
	/**************
	 * Purpose:  Initialize the scanner. A scanner should only be
	 * 		opened and closed once per execution.
	 **************/
	public static void init() {
		if (!initialized) {
			initialized = true;
			scanner = new Scanner(System.in);
		}
	}
	
	
	/**************
	 * Purpose:  Close the scanner. A scanner should only be
	 * 		opened and closed once per execution.
	 **************/
	public static void close() {
		scanner.close();
	}
	
	
	/**************
	 * Purpose:  Print a message containing any number of strings
	 **************/
	public static void print(String... message) {
		
		for (String m : message) {
			System.out.print(m);
		}
		
	}
	
	
	/**************
	 * Purpose:  Print a message containing any number of strings,
	 * 		and then a newline.
	 **************/
	public static void println(String... message) {
		print(message);
		print(System.lineSeparator());
	}
	
	
	/**************
	 * Purpose:  Receives any number of strings as an input to
	 * 		show the user before asking for input. If the list
	 * 		of player commands parsed from the file is not null,
	 * 		the next player command is supplied instead of 
	 * 		requesting user input.
	 **************/
	public static String input(String... message) {
		if (!initialized) {
			init();
		}
		print(message);
		
		//return validateInput(scanner.nextLine());
		return scanner.nextLine();
	}
	public static String input() {
		if (!initialized) {
			init();
		}
		
		//return validateInput(scanner.nextLine());
		return scanner.nextLine();
	}
	
	/*
	 * Potential use later, if we create a list of valid inputs
	 * 
	protected static String validateInput(Object rawInput) {
		String input = "";
		
		rawInput = rawInput.toString().toUpperCase();
		for (String s : validInputs) {
			if (Objects.equals(rawInput, s)) input = s;
		}
		 
		return input;
	}
	*/
}
