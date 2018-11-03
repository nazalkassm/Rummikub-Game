package com.rummikub;

import java.lang.reflect.Executable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.print.CancelablePrintJob;

public class Strategy3 implements StrategyBehaviour {
	private TableInfo tableInfo;
	
	Strategy3() 
	{
		//subject.registerObserver(this);
	}
	
	@Override
	public List<Meld> useStrategy(Player currPlayer) 
	{
		//declare variables
		int sum = 0;
		List<Meld> returnMelds = new ArrayList<>();
		List<Meld> possibleMelds = new ArrayList<>(currPlayer.getPlayerRack().getMelds());
		
		List<Tile> tempList = new ArrayList<>();
		tempList.addAll(currPlayer.getPlayerRack().getRackArray());

		//print table and possible melds
		Print.printRacktoUser(currPlayer.getPlayerRack());
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
			Print.print("You cannot play your initial 30 this round");
			currPlayer.getPlayerRack().setRack(tempList);
			return Collections.emptyList(); 
		}
	}
	
	@Override
	public void playStrategy(Player currPlayer, List<Meld> possibleMelds, List<Meld> returnMelds) {
		if(tableInfo.getLowestHandCount() <= currPlayer.getPlayerRack().getSize() - 3) {
			while (possibleMelds.size() > 0) {
				//now add Meld with max sum to return melds
				returnMelds.add(possibleMelds.get(Meld.getMaxIndex(possibleMelds)));
				//pop the tiles with were added to return melds
				currPlayer.getPlayerRack().removeTiles(possibleMelds.get(Meld.getMaxIndex(possibleMelds)));
				//update possible melds to get new list of melds
				possibleMelds = new ArrayList<>(currPlayer.getPlayerRack().getMelds());
				
				//print updated rack and possible melds to UI
				Print.printRacktoUser(currPlayer.getPlayerRack());
				Print.printMeldtoUser(possibleMelds);
			}
			if (possibleMelds.size() == 0) {
				//play using the table
			}
		}
		else {
			//play only table cards 
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
