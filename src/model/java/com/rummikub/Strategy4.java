package com.rummikub;

import java.io.IOException;
import java.util.List;

public class Strategy4 implements StrategyBehaviour
{
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
	public List<Meld> useStrategy(Player currentPlayer) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void playStrategy(Player currentPlayer, List<Meld> possibleMelds, List<Meld> returnMelds) throws Exception {
		 
		//Saving Memento for both the table and player
		Player.Memento playerMomento1 = currentPlayer.saveToMemento();
		TableInfo.Memento  tableMomento1 = tableInfo.saveToMemento();
		
		//Print rack and possible melds
		Print.printRacktoUser(currentPlayer.getPlayerRack(), currentPlayer.isPrint_rack_meld());
		
		
		
	}

}
