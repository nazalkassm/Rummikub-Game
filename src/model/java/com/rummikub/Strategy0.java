package com.rummikub;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Strategy0 implements StrategyBehaviour 
{
	private TableInfo tableInfo; 
	
	Strategy0() {	}
	
	
	@Override
	public List<Meld> useStrategy(Player currPlayer) throws IOException 
	{
		// String[] arrayofNumbs = null;
		List<Meld> meldsPlayed = new ArrayList<Meld>();
		currPlayer.getPlayerRack().sortRack();
		Print.print("It is now your turn :");
		Print.printRacktoUser(currPlayer.getPlayerRack());
		Print.print(currPlayer.getPlayerRack().getMelds().toString());
		
		
		//String choice = Prompt.promptInput("Enter the melds you want to play:");
		
		
		return meldsPlayed;
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
