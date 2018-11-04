package com.rummikub;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Strategy0 implements StrategyBehaviour 
{
	@SuppressWarnings("unused")
	private TableInfo tableInfo; 

	Strategy0() {	}


	@Override
	public List<Meld> useStrategy(Player currPlayer) throws IOException 
	{
		//declare variables
		int sum = 0;
		List<Meld> returnMelds = new ArrayList<>();
		List<Meld> possibleMelds = new ArrayList<>(currPlayer.getPlayerRack().getMelds());
		List<Tile> tempList = new ArrayList<>();
		tempList.addAll(currPlayer.getPlayerRack().getRackArray());
		
		//print rack and possible melds
		Print.printRacktoUser(currPlayer.getPlayerRack(),true);	
		Print.print("\nHere are the melds you can play from your rack: ");
		Print.printMeldtoUser(possibleMelds,true);
		Print.print("\nHere are the melds you can play from a combination of your hand and rack: ");

		
		//execute play logic for this strategy
		playStrategy(currPlayer, possibleMelds, returnMelds); // <------ Change execution path here.
		if (returnMelds.isEmpty()) 
		{
			Print.print("\n" + currPlayer.getName() + " wants to pass.");
			return Collections.emptyList(); 
		
		}
		//checks for sum of returning melds
		for (Meld m: returnMelds) 
		{
			sum += m.sumMeld();
		}
		
		//checks if player has already played its initial 30
		//if it hasn't then it checks whether the playable meld's sum is 30 or greater
		//if either true, returns played melds and ends turn
		if(currPlayer.canPlayOnExistingMelds || sum >= 30) {
			currPlayer.canPlayOnExistingMelds = true;
		}
		//if player has not played inital 30 AND playable melds sums less than 30
		//player cannot place playable melds on table
		//so player's rack gets reset to when the turn started and ends turn
		else {
			Print.print("\nPlayer 1 tried playing melds but the sum is < 30.");
			currPlayer.getPlayerRack().setRack(tempList);
			returnMelds = Collections.emptyList();
		}
		if (!tableInfo.getMelds().isEmpty() && returnMelds.size() > 0)
			returnMelds.addAll(tableInfo.getMelds());
		return returnMelds;
	}

	@Override
	public void playStrategy(Player currPlayer, List<Meld> possibleMelds, List<Meld> returnMelds) throws IOException 
	{
		String inputString = Prompt.promptInput("Enter the melds you want to play (0 to pass or no melds) : ");

		int input = Integer.parseInt(inputString);

		while(input > 0 && input <= possibleMelds.size() + Constants.ONE_INDEX)
		{
			returnMelds.add(possibleMelds.get(input-Constants.ONE_INDEX));
			currPlayer.getPlayerRack().removeTiles(possibleMelds.get(input-Constants.ONE_INDEX));
			possibleMelds = new ArrayList<>(currPlayer.getPlayerRack().getMelds());
			//Print Hand Info
			Print.printRacktoUser(currPlayer.getPlayerRack(),true);
			Print.print("\nHere are the melds you can play: ");
			Print.printMeldtoUser(possibleMelds,true);
			inputString = Prompt.promptInput("Enter the melds you want to play (0 to pass or no melds): ");
			input = Integer.parseInt(inputString);
		}
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
