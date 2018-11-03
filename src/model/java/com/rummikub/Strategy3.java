package com.rummikub;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
		List<Meld> initialMelds = new ArrayList<>(currPlayer.getPlayerRack().getMelds());
		List<Tile> temp = new ArrayList<>();
		
		for(Meld m: tableInfo.getMelds()) {
			for(Tile t: m.getTiles()) {
				temp.add(t);
			}
		}
		
		List<Tile> mergedMelds = new ArrayList<Tile>();
		mergedMelds.addAll(currPlayer.getPlayerRack().getRackArray());
		mergedMelds.addAll(temp);
		
		List<Meld> allPossibleMelds = new ArrayList<>(Meld.getMeldsWithTable(mergedMelds));
		
		List<Tile> tempList = new ArrayList<>();
		tempList.addAll(currPlayer.getPlayerRack().getRackArray());

		//print table and possible melds
		Print.printRacktoUser(currPlayer.getPlayerRack(),currPlayer.isPrint_rack_meld());
		if(!currPlayer.canPlayOnExistingMelds) {
			Print.printMeldtoUser(initialMelds,currPlayer.isPrint_rack_meld());
		}
		else {
			Print.printMeldtoUser(allPossibleMelds,currPlayer.isPrint_rack_meld());
		}
		
		
		//checks if player has already played its initial 30
		//if it hasn't then it checks whether the playable meld's sum is 30 or greater
		//
		if(!currPlayer.canPlayOnExistingMelds) 
		{
			logic(currPlayer, initialMelds, returnMelds);
			if(getSum(sum, returnMelds) >= 30){
				currPlayer.canPlayOnExistingMelds = true;
			}
		}
		//if either true, returns played melds and ends turn
		else if(currPlayer.canPlayOnExistingMelds)
		{
			playStrategy(currPlayer, allPossibleMelds, returnMelds);
		}
		else
		{
			Print.print("Player 4 tried playing melds but the sum is < 30.");
			currPlayer.getPlayerRack().setRack(tempList);
			returnMelds = Collections.emptyList();
		}
		
		return returnMelds;
		
		//if player has not played inital 30 AND playable melds sums less than 30
		//player cannot place playable melds on table
		//so player's rack gets reset to when the turn started and ends turn
		
	}
	
	@Override
	public void playStrategy(Player currPlayer, List<Meld> possibleMelds, List<Meld> returnMelds) {
		boolean track = false;
		
		if(tableInfo.getLowestHandCount() <= currPlayer.getPlayerRack().getSize() - 3) {
			logic(currPlayer, possibleMelds, returnMelds);
		}
		else {
			//play only table cards 
			for(Meld m: possibleMelds) {	
				for(Tile t: m.getTiles()) {
					track = track || t.getPlayedOnTable();
				}
				if(track) {
					returnMelds.add(m);
					for(Tile t: m.getTiles()) {
						if(!t.getPlayedOnTable()) {
							currPlayer.getPlayerRack().getRackArray().remove(t);
						}
					}
				}
			}
		}
	}
	
	public void logic(Player currPlayer, List<Meld> possibleMelds, List<Meld> returnMelds) {
		while (possibleMelds.size() > 0) {
			//now add Meld with max sum to return melds
			returnMelds.add(possibleMelds.get(Meld.getMaxIndex(possibleMelds)));
			//pop the tiles with were added to return melds
			currPlayer.getPlayerRack().removeTiles(possibleMelds.get(Meld.getMaxIndex(possibleMelds)));
			//update possible melds to get new list of melds
			possibleMelds = new ArrayList<>(currPlayer.getPlayerRack().getMelds());
			
			//print updated rack and possible melds to UI
			Print.printRacktoUser(currPlayer.getPlayerRack(),currPlayer.isPrint_rack_meld());
			Print.printMeldtoUser(possibleMelds,currPlayer.isPrint_rack_meld());
		}
	}
	
	public int getSum(int sum, List<Meld> returnMelds) {
		//checks for sum of returning melds
		for (Meld m: returnMelds) {
			sum += m.sumMeld();
		}
		
		return sum;
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
