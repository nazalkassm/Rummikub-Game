package com.rummikub;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.pmw.tinylog.Logger;

public class Game
{
	//Primitive Variables
	boolean gameRunning = true;
	String pName = "";
	
	//Data Structure Variables
	List<Player> players = new ArrayList<>();
	List<Meld> meldsPlayed;
	
	//My data Variables
	Print printer = new Print(); 
	Prompt prompter = new Prompt();
	Stock stock = new Stock();
	Table table = new Table(stock);
	Player winner;
	
	public void start() throws IOException
	{
		//Start game
		printer.printIntroduction();
		prompter.promptEnterKey();
		pName = Prompt.promptInput("Enter your name: ");
		
		//Adding players to the game
		players.add(new Player(pName,new Strategy0()));
		players.add(new Player("Computer 1",new Strategy1()));
		players.add(new Player("Computer 2",new Strategy2()));
		players.add(new Player("Computer 3",new Strategy3()));
		
		//Add players to the table
		for (Player player: players) 
		{
			table.addPlayers(player);
	    }
		
		//Initializes which player is starting and keeps track of player's turn
		table.initPlayersTurn();
		
		// Game loop the game runs here until it ends.
		do 
		{
			if(stock.getLength() == 0)
			{
				gameRunning = false;
			}
			
			Player currentPlayer = table.getNextPlayerTurn();
			Logger.info(currentPlayer.getName());
			Logger.info(currentPlayer.isHuman());//log to file
			Print.print("It is now " + currentPlayer.getName() + "'s turn: ");
			meldsPlayed = currentPlayer.play();
			
			if (currentPlayer.getPlayerRack().getSize() == Constants.ZERO_TILES) 
			{
				gameRunning = false;
				winner = currentPlayer;
				break;
		    }
			
			Print.print("Melds played by " + currentPlayer.getName() + " are: ");
			Print.printMeldtoUser(meldsPlayed);
			
			//Clears the melds so we can add meldsPlayed
			table.clearMelds();
			
			if(!(meldsPlayed.isEmpty()))
			{
				for(Meld m: meldsPlayed)
				{
					table.updateMeldsOnTable(m); //updating this method now.
				}
				
				table.notifyObservers();
			}
			else
			{
				currentPlayer.getPlayerRack().takeTile(stock);
			}
			
			gameRunning = false; // only for testing
			
		}while(gameRunning );
	
		//Game ending ( we print an ending and maybe who won, also we can reset variables and game state if needed)
		printer.printEnding(winner); // we can maybe give it a winner so that it can print it.
	
	}
}
