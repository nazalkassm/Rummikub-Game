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
		players.add(new Player("p1",new Strategy1()));
		players.add(new Player("p2",new Strategy2()));
		players.add(new Player("p3",new Strategy3()));
		
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
			//Get list of changed melds 
			List<Meld> changedMelds = new ArrayList<>(Table.getDiffMelds(table.getAllMelds(), meldsPlayed));
			
			
			Print.print("\nTable is: ");
			Print.printMeldtoUser(meldsPlayed, changedMelds, true);
			
			//If the changed melds is not empty, then add we're updating things
			if(!(changedMelds.isEmpty()))
			{
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
					Print.println(currentPlayer.getName() + " draws a tile from the stock: " + currentPlayer.getPlayerRack().takeTile(stock).toString());					
				}
			}
			Print.println(currentPlayer.getName() + " rack size is " + currentPlayer.getPlayerRack().getSize());
			//print rack and possible melds
			System.out.println(currentPlayer.getName() + " players new hand is" );
			Print.printRacktoUser(currentPlayer.getPlayerRack(),currentPlayer.isPrint_rack_meld());
			prompter.promptEnterKey(waitAferEachTurn);
			
			if (turnsWithoutMoves >= 4) 
			{
				Print.println("The stock is empty, and no one has played in 4 turns.");
				gameRunning = false;
			}
			
		}while(gameRunning );
	
		//Game ending ( we print an ending and maybe who won, also we can reset variables and game state if needed)
		printer.printEnding(winner, waitAferEachTurn);
	
	}
}
