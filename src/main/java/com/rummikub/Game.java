package com.rummikub;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
	int turnsWithoutMoves = 0;
	
	//Things to play with when testing
	boolean waitAferEachTurn = true; //Prompts enter after each turn
	boolean printRackMeld = true; // Turn it off so that you do not print the computers racks and melds.
	
	public void start() throws IOException
	{
		//Start game
		printer.printIntroduction();
		prompter.promptEnterKey();
		pName = Prompt.promptInput("Enter your name: ");
		
		//Adding players to the game
		players.add(new Player(pName,new Strategy0()));
		players.add(new Player("Player 2",new Strategy1()));
		players.add(new Player("Player 3",new Strategy2()));
		players.add(new Player("Player 4",new Strategy3()));
		
		//Print the racks and melds of players, yes or no.
		for(Player p : players)
		{
			p.setPrint_rack_meld(printRackMeld);
		}
		
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
			// we might need to add this. I think we are adding empty collections to our table which was fucking us up
			for(int i =0; i < table.getAllMelds().size(); i++)
			{
				if(table.getMeld(i).getTiles().isEmpty())
				{
					table.getAllMelds().remove(i);
				}
			}
			
			printer.printGameTable(table);
			
			Player currentPlayer = table.getNextPlayerTurn();
			Logger.info(currentPlayer.getName());
			Logger.info(currentPlayer.isHuman());//log to file
			Print.print("++++++ It is now " + currentPlayer.getName() + "'s turn: ++++++");
			meldsPlayed = currentPlayer.play();
			
			if (currentPlayer.getPlayerRack().getSize() == Constants.ZERO_TILES) 
			{
				gameRunning = false;
				winner = currentPlayer;
				break;
		    }
			
			Print.print("\nMelds played by " + currentPlayer.getName() + " are: ");
			Print.printMeldtoUser(meldsPlayed,true);
			
			if(!(meldsPlayed.isEmpty()))
			{
				//Clears the melds so we can add meldsPlayed
				table.clearMelds();
				
				turnsWithoutMoves = 0;
			
					table.updateMeldsOnTable(meldsPlayed);
				
				
				table.notifyObservers();
			}
			else
			{
				if(stock.getLength() == 0)
				{
					turnsWithoutMoves++;
				}
				else {
					currentPlayer.getPlayerRack().takeTile(stock);
					Print.println(currentPlayer.getName() + " draws a tile from the stock.");
				}
			}
			
			prompter.promptEnterKey(waitAferEachTurn);
			
			if (turnsWithoutMoves >= 4) {
				Print.println("The stock is empty, and no one has played in 4 turns.");
				gameRunning = false;
			}
			
		}while(gameRunning );
	
		//Game ending ( we print an ending and maybe who won, also we can reset variables and game state if needed)
		printer.printEnding(winner, waitAferEachTurn);
	
	}
}
