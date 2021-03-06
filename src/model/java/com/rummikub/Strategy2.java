package com.rummikub;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class Strategy2 implements StrategyBehaviour 
{
	private TableInfo tableInfo; 
	
	Strategy2() 
	{
	}
	
	@Override
	public List<Meld> useStrategy(Player currPlayer) {
		List<Tile> tiles = new ArrayList<>(); 
		Player.Memento playerState = currPlayer.saveToMemento();
		TableInfo.Memento tableState = tableInfo.saveToMemento();
		//print rack and possible melds
		Print.printRacktoUser(currPlayer.getPlayerRack(),currPlayer.isPrint_rack_meld());
		//If no other player has played on the table
		if (tableInfo.getMeldsFromTable().isEmpty()) {
			//Can't play so passes
			currPlayer.restoreFromMemento(playerState);
			tableInfo.restoreFromMemento(tableState);
			return tableInfo.getMeldsFromTable();
		}
	
		//Add current player to tiles
		tiles.addAll(currPlayer.getPlayerRack().getRackArray());
		
		//If the player can play on existing melds 
		if (currPlayer.canPlayOnExistingMelds) {	
			for (Meld m: tableInfo.getMeldsFromTable()) {
				tiles.addAll(m.getTiles());
			}
		}
		
		List<Meld> melds = new ArrayList<>(Meld.getMeldsWithTable(tiles));
		List<Tile> tempTiles = new ArrayList<>(); 
		for (Meld m: melds) {
			tempTiles.addAll(m.getTiles());
		}
		
		if (!currPlayer.canPlayOnExistingMelds) {
			int sum = 0;
			//checks for sum of returning melds
			for (Meld m: melds) {
				sum += m.sumMeld();
			}
	
			if (sum >= 30) {
				currPlayer.canPlayOnExistingMelds = true;
				melds.addAll(tableInfo.getMeldsFromTable());
			} else {
				Print.print("Player " + currPlayer.getName() + " tried playing melds but their sum is less than 30.");
				currPlayer.restoreFromMemento(playerState);
				tableInfo.restoreFromMemento(tableState);
				return tableInfo.getMeldsFromTable();
			}
		} else {
		
			//If the tempTiles which has all the tiles that we should play, contains the player rack in it then.. 
			if (tempTiles.containsAll(currPlayer.getPlayerRack().getRackArray())) {
				
			}
			//Otherwise we can't get rid of all the tiles so we need to 
			else {
				ListIterator<Meld> iter = melds.listIterator();
				while (iter.hasNext()){
					boolean poop = false;
					for (Tile t: iter.next().getTiles()) {
						poop = poop || t.getPlayedOnTable();
					}
					if (!poop) 
						iter.remove();
				}
			}
		}
		//We can play this entire hand and win
		currPlayer.removeTiles(melds);
		//print rack and possible melds
		//Print.printRacktoUser(currPlayer.getPlayerRack(),currPlayer.isPrint_rack_meld());
		//Print.printMeldtoUser(melds,currPlayer.isPrint_rack_meld());
		return melds;
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
