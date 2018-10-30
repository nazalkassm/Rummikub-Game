package com.rummikub;

import java.io.IOException;
import java.util.ArrayList;

public class Game
{
	public static void main(String[] args) throws IOException 
	{
	//Primitive Variables
	boolean gameRunning = true;
	String pName = "";
	
	//Data Structure Variables
	ArrayList<Player> players = new ArrayList<>();
	ArrayList<Meld> meldsPlayed = new ArrayList<>();
	
	//My data Variables
	Print printer = new Print(); // Some methods are static and other are not in this class.
	Prompt prompter = new Prompt(); // Some methods are static and other are not in this class.
	Stock stock = new Stock();
	Table table = new Table(stock);
	
	//Start game
	printer.printIntroduction();
	prompter.promptEnterKey();
	pName = Prompt.promptInput("Enter your name: ");
	
	players.add(new Player(stock,pName,new Strategy0(table)));
	players.add(new Player(stock,"Computer 1",new Strategy1(table)));
	players.add(new Player(stock,"Computer 2",new Strategy2(table)));
	players.add(new Player(stock,"Computer 3",new Strategy3(table)));
	
	do
	{
		meldsPlayed = players.get(0).play();
		
		
		gameRunning = false;
	}while(gameRunning);
	
	printer.printEnding(); // we can maybe give it a winner so that it can print it.
	
	
	
	
	
	
	
	
	}
	
}
