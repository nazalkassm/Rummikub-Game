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
		//declare variables
		int sum = 0;
		List<Meld> returnMelds = new ArrayList<>();
		List<Meld> possibleMelds = new ArrayList<>(currPlayer.getPlayerRack().getMelds());
		List<Tile> tempList = new ArrayList<>();
		tempList.addAll(currPlayer.getPlayerRack().getRackArray());

		//print table and possible melds
		Print.printRacktoUser(currPlayer.getPlayerRack());
		Print.print("Here are the melds you can play: ");
		Print.printMeldtoUser(possibleMelds);

		
		//execute play logic for this strategy
		playStrategy(currPlayer, possibleMelds, returnMelds);
		
		//checks for sum of returning melds
		for (Meld m: returnMelds) {
			sum += m.sumMeld();
		}

		//checks if player has already played its initial 30
		//if it hasn't then it checks whether the playable meld's sum is 30 or greater
		//if either true, returns played melds and ends turn
		if(currPlayer.canPlayOnExistingMelds || sum >= 30) {
			currPlayer.canPlayOnExistingMelds = true;
			return returnMelds;
		}

		//if player has not played inital 30 AND playable melds sums less than 30
		//player cannot place playable melds on table
		//so player's rack gets reset to when the turn started and ends turn
		else {
			Print.print("There were no melds played this round because your melds do not sum up to 30.");
			currPlayer.getPlayerRack().setRack(tempList);
			return Collections.emptyList(); 
		}

	}

	@Override
	public void playStrategy(Player currPlayer, List<Meld> possibleMelds, List<Meld> returnMelds) throws IOException {
		String inputString = Prompt.promptInput("Enter the melds you want to play (0 to pass or no melds) : ");

		int input = Integer.parseInt(inputString);

		while(input > 0 && input <= possibleMelds.size() + Constants.ONE_INDEX)
		{
			returnMelds.add(possibleMelds.get(input-Constants.ONE_INDEX));
			currPlayer.getPlayerRack().removeTiles(possibleMelds.get(input-Constants.ONE_INDEX));
			possibleMelds = new ArrayList<>(currPlayer.getPlayerRack().getMelds());
			//Print Hand Info
			Print.printRacktoUser(currPlayer.getPlayerRack());
			Print.print("Here are the melds you can play: ");
			Print.printMeldtoUser(possibleMelds);
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
