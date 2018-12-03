//package com.rummikub;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//
//public class Strategy1 implements StrategyBehaviour {
//	private TableInfo tableInfo; 
//	
//	Strategy1() {	}
//	
//	@Override
//	public List<Meld> useStrategy(Player currPlayer) 
//	{
//		//declare variables
//		int sum = 0;
//		List<Meld> returnMelds = new ArrayList<>();
//		List<Meld> possibleMelds = new ArrayList<>(currPlayer.getPlayerRack().getMelds());
//		Player.Memento playerState = currPlayer.saveToMemento();
//		TableInfo.Memento tableState = tableInfo.saveToMemento();
//
//		//print rack and possible melds
//		Print.printRacktoUser(currPlayer.getPlayerRack(),currPlayer.isPrint_rack_meld());
//		Print.printMeldtoUser(possibleMelds,Collections.emptyList(), currPlayer.isPrint_rack_meld());
//		
//		
//		//execute play logic for this strategy
//		playStrategy(currPlayer, possibleMelds, returnMelds);
//		
//		//checks for sum of returning melds
//		for (Meld m: returnMelds) 
//		{
//			sum += m.sumMeld();
//		}
//
//		//checks if player has already played its initial 30
//		//if it hasn't then it checks whether the playable meld's sum is 30 or greater
//		//if either true, returns played melds and ends turn
//		if(currPlayer.canPlayOnExistingMelds || sum >= 30) {
//			currPlayer.canPlayOnExistingMelds = true;
//		}
//
//		//if player has not played inital 30 AND playable melds sums less than 30
//		//player cannot place playable melds on table
//		//so player's rack gets reset to when the turn started and ends turn
//		else {
//			Print.print("Player " + currPlayer.getName() + " tried playing melds but their sum is less than 30.");
//			currPlayer.restoreFromMemento(playerState);
//			tableInfo.restoreFromMemento(tableState);
//		}
//			
//			returnMelds.addAll(tableInfo.getMeldsFromTable());
//		
//		return returnMelds;
//	}
//	
//	
//	@Override
//	public void playStrategy(Player currPlayer, List<Meld> possibleMelds, List<Meld> returnMelds) {
//		while (possibleMelds.size() > 0) {
//			//now add Meld with max sum to return melds
//			returnMelds.add(possibleMelds.get(Meld.getMaxIndex(possibleMelds)));
//			//pop the tiles with were added to return melds
//			currPlayer.getPlayerRack().removeTiles(possibleMelds.get(Meld.getMaxIndex(possibleMelds)));
//			//update possible melds to get new list of melds
//			possibleMelds = new ArrayList<>(currPlayer.getPlayerRack().getMelds());
//			
//			//print updated rack and possible melds to UI
//			//Print.printRacktoUser(currPlayer.getPlayerRack(),currPlayer.isPrint_rack_meld());
//			//Print.printMeldtoUser(possibleMelds,currPlayer.isPrint_rack_meld());
//		}
//	}
//
//	@Override
//	public void update(TableInfo tableInfo) {
//		this.tableInfo = tableInfo;		
//	}
//	
//	@Override
//	public void setSubject(Subject subject) {
//		subject.registerObserver(this);
//	}
//
//}