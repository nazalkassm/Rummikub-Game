package com.rummikub;


public class ViewLogic 
{
	public Prompt prompter = new Prompt();
	public Print printer = new Print();
	public ReadWrite readWriter = new ReadWrite();
	
	
	/*
	 * Potential use later, if we create a list of valid inputs
	 * Naz we are not checking for inputs at all. The game assumes we have valid input always.
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
