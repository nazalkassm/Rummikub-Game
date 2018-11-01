package com.rummikub;

import java.io.IOException;
import java.util.List;

public class Strategy0 implements StrategyBehaviour 
{
	private TableInfo tableInfo; 
	
	Strategy0() {	}
	
	
	@Override
	public List<Meld> useStrategy() throws IOException 
	{
		String[] arrayofNumbs = null;
		List<Meld> meldsPlayed;
		tableInfo.currentRack.sortRack();
		Print.print("It is now your turn :");
		Print.printRacktoUser(tableInfo.currentRack);
		Print.print(tableInfo.currentRack.getMelds().toString());
		
		
		
		//String choice = Prompt.promptInput("Enter the melds you want to play:");
		
		
		return null;
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
