
package com.rummikub;

import java.util.ArrayList;
import java.util.List;

public class Strategy1 implements StrategyBehaviour 
{
	private TableInfo tableInfo; 
	
	Strategy1() {	}
	
	@Override
	public List<Meld> useStrategy(Player currentPlayer) 
	{
		// An integer that keeps track of the sum of a meld
		int sum = 0;
		
		// A list that will store the melds this player will play and the melds already on the table
		List<Meld> return_melds = new ArrayList<>(tableInfo.getMeldsFromTable());
		
		// A list that will store the tiles in this player's rack
		List<Tile> tiles = new ArrayList<>();
		
		//Storing the tiles from the rack
		tiles.addAll(currentPlayer.getPlayerRack().getRackArray());
		
		// A list that will store the melds in this player's rack
		List<Meld> melds = new ArrayList<>();
	
		//Storing the melds in the rack
		melds.addAll(Meld.getMeldsWithTable(tiles));
		
		//Saving the state of the player and table at this point
		Player.Memento playerState = currentPlayer.saveToMemento();
		TableInfo.Memento tableState = tableInfo.saveToMemento();

		//Print the player's rack
		Print.printRacktoUser(currentPlayer.getPlayerRack(),currentPlayer.isPrint_rack_meld());		
		
		//Calculate the sum of all melds to be played
		for (Meld m: melds) 
		{
			sum += m.sumMeld();
		}

		//If this player has a melds with a sum of greater than 30 or already played his initial turn
		if(currentPlayer.canPlayOnExistingMelds || sum >= 30) 
		{
			currentPlayer.canPlayOnExistingMelds = true;
			return_melds.addAll(melds);
		} // This player did not play his initial turn or has a sum of melds of less than 30
		else 
		{
			Print.print("Player " + currentPlayer.getName() + " tried playing melds but their sum is less than 30.");
			currentPlayer.restoreFromMemento(playerState);
			tableInfo.restoreFromMemento(tableState);
		}
		
		currentPlayer.removeTiles(return_melds);
		return return_melds;
	}
	

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

}

