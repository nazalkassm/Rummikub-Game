package com.rummikub;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Strategy0 implements StrategyBehaviour 
{
	private TableInfo tableInfo; 

	Strategy0() {	}



	@Override
	public List<Meld> useStrategy(Player currPlayer) throws IOException 
	{
		List<Meld> possibleRackMelds = new ArrayList<>(currPlayer.getPlayerRack().getMelds());
		List<Meld> possibleTableMelds = new ArrayList<>(tableInfo.getMelds());
		//declare variables
		int sum = 0;
		List<Meld> returnMelds = new ArrayList<>();
		List<Tile> revertList = new ArrayList<>();
		String handOrTable = "";
	
		// This is to revert the player's rack in case what he is playing is not valid.
		revertList.addAll(currPlayer.getPlayerRack().getRackArray());
		
		//print rack
		Print.printRacktoUser(currPlayer.getPlayerRack(),true);
		
		if(currPlayer.canPlayOnExistingMelds)
		{
			Print.print("\nCheck out the melds on the table that you might want to play on: ");
			//Print.printMeldtoUser(possibleTableMelds, true);
			handOrTable = Prompt.promptInput("Play from hand or using the table ? Choose 1 or 2 : (0 to pass)");
			
			if(handOrTable.equals("1"))
			{
				Print.printMeldtoUser(possibleRackMelds, true);
				initialStrategy(currPlayer,possibleRackMelds,returnMelds);
			}
			else if(handOrTable.equals("2"))
			{
				playStrategy(currPlayer,possibleTableMelds,returnMelds); // <------ Change execution path here.
			}
			else if(handOrTable.equals("0"))
			{
				return Collections.emptyList();
			}
		}
		else
		{
			Print.print("\nHere are the melds you can play from your hand: ");
			Print.printMeldtoUser(possibleRackMelds,true);
			initialStrategy(currPlayer, possibleRackMelds, returnMelds); // <------ Change execution path here.
			
			sum = checkSum(returnMelds);
			Print.print("The total sum for melds played is : ", String.valueOf(sum));
			
			if(sum >= 30) 
			{
				currPlayer.canPlayOnExistingMelds = true;
				
				if (!(tableInfo.getMelds().isEmpty()) && returnMelds.size() > 0)
					returnMelds.addAll(tableInfo.getMelds());
			}
			else 
			{
				if(currPlayer.canPlayOnExistingMelds == false && !(returnMelds.isEmpty()))
				{
					Print.print("\nPlayer 1 tried playing melds but the sum is < 30.");
				}
				currPlayer.getPlayerRack().setRack(revertList);
				returnMelds = Collections.emptyList();
			}
		}
		
		if (returnMelds.isEmpty()) 
		{
			Print.print("\n" + currPlayer.getName() + " wants to pass.");
			returnMelds = Collections.emptyList(); 
		}
		
		return returnMelds;
	}
	
////////////////////////////////----------------------------------------////////////////////////////

	private int checkSum(List<Meld> returnMelds) 
	{
		int sum = 0;
		for (Meld m: returnMelds) 
		{
			sum += m.sumMeld();
		}
		return sum;
	}


	@Override
	public void playStrategy(Player currPlayer, List<Meld> possibleMelds, List<Meld> returnMelds) throws IOException 
	{
		List<Integer> inputTiles = new ArrayList<>();
		List<Meld> meldOptions = new ArrayList<>();
		List<Tile> mergedTiles = new ArrayList<>();
		//boolean playerIsPlaying = true;
		String inputString = " ";
		int input = 0;

		while(true)
		{
			mergedTiles.clear();
			inputTiles.clear();
			meldOptions.clear();
			Print.printMeldtoUser(possibleMelds, true);
			inputString = Prompt.promptInput("Choose the meld you want to play on from the table (0 to pass or no melds) : ");
			
			input = Integer.parseInt(inputString);
			
			if(input == 0)
			{
				break;
			}
	
			if(input > 0 && input < possibleMelds.size() + Constants.ONE_INDEX) //confirm range 
			{
				
				inputTiles.addAll(Prompt.promptUserTiles("Choose the list of tiles from your hand you want to play on that meld:"
						+ " (EX: 2 5 6) ",currPlayer.getPlayerRack()));
				
				for(Tile t : possibleMelds.get(input-Constants.ONE_INDEX).getTiles())
				{
					mergedTiles.add(t);
				}
				
				for(Integer i : inputTiles)
				{
					mergedTiles.add(currPlayer.getPlayerRack().getRackArray().get(i-Constants.ONE_INDEX));
				}
				
				meldOptions.addAll(Meld.getMelds(mergedTiles));
				Print.print("\nHere are the melds you can play: ");
				Print.printMeldtoUser(meldOptions, true);
				//initialStrategy(currPlayer,meldOptions,returnMelds);
				String inputString2 = Prompt.promptInput("Enter the melds you want to play (0 to pass or no melds) : ");
				int input2 = Integer.parseInt(inputString2);
				returnMelds.add(possibleMelds.get(input2-Constants.ONE_INDEX));
				currPlayer.getPlayerRack().removeTiles(possibleMelds.get(input2-Constants.ONE_INDEX));
			}
		}// end of while
		useStrategy(currPlayer);
	}
	
	public void initialStrategy(Player currPlayer, List<Meld> possibleMelds, List<Meld> returnMelds) throws IOException 
	{
		String inputString = Prompt.promptInput("Enter the melds you want to play (0 to pass or no melds) : ");

		int input = Integer.parseInt(inputString);

		while(input > 0 && input <= possibleMelds.size() + Constants.ONE_INDEX)
		{
			returnMelds.add(possibleMelds.get(input-Constants.ONE_INDEX));
			currPlayer.getPlayerRack().removeTiles(possibleMelds.get(input-Constants.ONE_INDEX));
			//possibleMelds = new ArrayList<>(currPlayer.getPlayerRack().getMelds());
			possibleMelds.clear();
			possibleMelds.addAll(currPlayer.getPlayerRack().getMelds());
			//Print Hand Info
			Print.printRacktoUser(currPlayer.getPlayerRack(),true);
			Print.print("\nHere are the melds you can play: ");
			Print.printMeldtoUser(possibleMelds,true);
			inputString = Prompt.promptInput("Enter the melds you want to play (0 to pass or no melds): ");
			input = Integer.parseInt(inputString);
		}
		if(input != 0)
			useStrategy(currPlayer);
	}


	@Override
	public void update(TableInfo tableInfo) 
	{
		this.tableInfo = tableInfo;		
	}


	@Override
	public void setSubject(Subject subject) {
		subject.registerObserver(this);
	}
}
