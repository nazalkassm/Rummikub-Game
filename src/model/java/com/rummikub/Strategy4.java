package com.rummikub;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Strategy4 implements StrategyBehaviour
{
	// tableInfo contains the table information, the player observes the table info
	private TableInfo tableInfo; 

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
	//This strategy will always play the initial meld as soon as possible.
	//This strategy will always wait to create a meld if the probability of creating it is higher than 0.(Not play from table)
	//If the probability is 0, it will use the table.
	//The strategy plays all melds possible in hand.
	
	public List<Meld> useStrategy(Player currentPlayer)
	{
	    // A list to store the tiles we will manipulate
		List<Tile> tiles = new ArrayList<>(); 
		
		//Save the current player and table state
		Player.Memento playerState = currentPlayer.saveToMemento();
		TableInfo.Memento tableState = tableInfo.saveToMemento();
		
		//Print the user his rack
		Print.printRacktoUser(currentPlayer.getPlayerRack(),currentPlayer.isPrint_rack_meld());

		//Getting the tiles from the rack
		tiles.addAll(currentPlayer.getPlayerRack().getRackArray());
		
		//If the player can play on the table because he satisfied his first 30.
		//We want to add the tiles from table.
				
				
		return null;
	}

	@Override
	public void playStrategy(Player currentPlayer, List<Meld> possibleMelds, List<Meld> returnMelds) throws Exception 
	{
		
	}

}
