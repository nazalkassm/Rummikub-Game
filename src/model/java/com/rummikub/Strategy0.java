package com.rummikub;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Strategy0 implements StrategyBehaviour 
{
	private TableInfo tableInfo; 

	Strategy0() {}

	@Override
	public void update(TableInfo tableInfo) 
	{
		this.tableInfo = tableInfo;		
	}

	@Override
	public void setSubject(Subject subject)
	{
		subject.registerObserver(this);
	}

	@Override
	public List<Meld> useStrategy(Player currentPlayer) throws IOException 
	{
		//Basic Variables Needed 
		List<Meld> possibleRackMelds = new ArrayList<>(currentPlayer.getPlayerRack().getMelds());
		List<Meld> tableMelds = new ArrayList<>(tableInfo.getMelds());
		List<Meld> returnMelds = new ArrayList<>();
		
		List<Tile> playerHand = new ArrayList<>(currentPlayer.getPlayerRack().getRackArray());
		List<Meld> meldsPlayedThisTurn = new ArrayList<>(); // possible to make it part of strategy
		
		String choiceOfPlayS = "";
		int choiceOfPlayI = -10;
		boolean userIsPlaying = true;
		int sum=0;
		
		//Print
		Print.print("Here is your rack :");
		Print.printRacktoUser(currentPlayer.getPlayerRack(),true);
		//Now we check if he played his initial
		if(currentPlayer.canPlayOnExistingMelds)
		{
			while(userIsPlaying)
			{
			choiceOfPlayS = Prompt.promptInput("Choose 1 to play from hand, and 2 to play using the Table: (0 to play noting)(-1 to end turn)");
			choiceOfPlayI = Integer.parseInt(choiceOfPlayS);
				switch(choiceOfPlayI) 
				{
				   case -1:
					  userIsPlaying = false;
					  break;
				   case 0 :
				      returnMelds = Collections.emptyList();
				      userIsPlaying = false;
				      break; 
				   case 1 :
				      initialStrategy(currentPlayer,possibleRackMelds,returnMelds);
				      break; 
				   case 2:
					  playStrategy(currentPlayer,tableMelds,returnMelds);
					  break;
				   default :
					  Print.print("Wong input, leaving the game");
					  System.exit(0);
				}
			}
		}
		else
		{
			Print.print("\nHere are the melds you can play from your hand: ");
			Print.printMeldtoUser(possibleRackMelds,true);
			initialStrategy(currentPlayer, possibleRackMelds, returnMelds); // <------ Change execution path here.
			
			sum = checkSum(returnMelds);
			Print.print("The total sum for melds played is : ", String.valueOf(sum));

			//checks for sum of returning melds
			sum = checkSum(returnMelds);
			if(sum >= 30) 
			{
				currentPlayer.canPlayOnExistingMelds = true;
				
				if (!(tableInfo.getMelds().isEmpty()) && returnMelds.size() > 0)
					returnMelds.addAll(tableInfo.getMelds());
			}
			//if player has not played inital 30 AND playable melds sums less than 30
			//player cannot place playable melds on table
			//so player's rack gets reset to when the turn started and ends turn	
			else 
			{
				if(currentPlayer.canPlayOnExistingMelds == false && !(returnMelds.isEmpty()))
				{
					Print.print("\nPlayer 1 tried playing melds but the sum is < 30.");
				}
				currentPlayer.getPlayerRack().setRack(playerHand);
				returnMelds = Collections.emptyList();
			}
		}

		if (returnMelds.isEmpty()) 
		{
			Print.print("\n" + currentPlayer.getName() + " wants to pass.");
			returnMelds = Collections.emptyList(); 
		}

		if (!(tableInfo.getMelds().isEmpty()) && returnMelds.size() > 0)

		{
			returnMelds.addAll(tableInfo.getMelds());
		}
		
		return returnMelds;
	}

	@Override
	public void playStrategy(Player currentPlayer, List<Meld> tableMelds, List<Meld> returnMelds) throws IOException 
	{
		List<Tile> mergedTiles = new ArrayList<>(currentPlayer.getPlayerRack().getRackArray());
		List<Meld> MergedMeld = new ArrayList<>();
		
		List<Integer> inputIntegerList = Prompt.promptUserTableMelds("Choose the melds that you are sure you can play on from the table :(Ex: 2 4 5)",tableMelds);
		
		boolean playerIsChoosing = true;
		
		for(Integer i : inputIntegerList)
		{
			mergedTiles.addAll(tableMelds.get(i.intValue()).getTiles());
		}
		
		inputIntegerList = Prompt.promptUserTiles("Choose the tiles that you want to play on the melds you have chosen (Ex: 1 10 11 12)",currentPlayer.getPlayerRack());
		
		for(Integer i : inputIntegerList)
		{
			mergedTiles.add(currentPlayer.getPlayerRack().getRackArray().get(i.intValue()));

		}
		
		MergedMeld = Meld.getMelds(mergedTiles);
		
		Print.print("Here is the combination you can play from your choices :");
		Print.printMeldtoUser(MergedMeld, true);
		
		initialStrategy(currentPlayer,MergedMeld,returnMelds);
		
	}
	
	public void initialStrategy(Player currentPlayer, List<Meld> possibleMelds, List<Meld> returnMelds) throws IOException 
	{
		boolean playerIsChoosing = true;
		
		while(playerIsChoosing)
		{
			String inputString = Prompt.promptInput("Enter the melds you want to play (0 to pass) : ");
			int inputInteger = Integer.parseInt(inputString);
			if(inputInteger == 0)
			{
				break;
			}
			
			//Add the melds chosen to returnMelds, removeTheTiles played, and update the melds he can play.
			returnMelds.add(possibleMelds.get(inputInteger-Constants.ONE_INDEX));
			currentPlayer.getPlayerRack().removeTiles(possibleMelds.get(inputInteger-Constants.ONE_INDEX));
			possibleMelds.clear();
			possibleMelds.addAll(currentPlayer.getPlayerRack().getMelds());
			
			//Print Hand Info
			Print.printRacktoUser(currentPlayer.getPlayerRack(),true);
			Print.print("\nHere are the melds you can play: ");
			Print.printMeldtoUser(possibleMelds,true);
		}
	}
	
	private int checkSum(List<Meld> returnMelds) 
	{
		int sum = 0;
		for (Meld m: returnMelds) 
		{
			sum += m.sumMeld();
		}
		return sum;
	}
}
