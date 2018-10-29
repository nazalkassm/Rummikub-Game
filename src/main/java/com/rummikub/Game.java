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
	
	//My data Variables
	ViewLogic view = new ViewLogic();
	Stock stock = new Stock();
	Table table = new Table(stock);
	
	//Start game
	view.printer.printIntroduction();
	pName = view.prompter.promptInput("Enter your name: ");
	
	players.add(new Player(stock,pName,new Strategy0(table)));
	players.add(new Player(stock,"Computer 1",new Strategy1(table)));
	players.add(new Player(stock,"Computer 2",new Strategy2(table)));
	players.add(new Player(stock,"Computer 3",new Strategy3(table)));
	
	while(gameRunning)
	{
		//human turn
		
		//c1 turn
		//c2 turn
		//c3 turn
	}
	
	
	
	
	
	
	
	
	
	
	}
	
}
