package com.rummikub;

import java.io.IOException;
import java.util.ArrayList;

public class Strategy0 implements StragetyBehaviour 
{
	private TableInfo tableInfo; 
	
	Strategy0() {	}
	
	
	@Override
	public ArrayList<Meld> useStrategy() throws IOException 
	{
		String[] arrayofNumbs = null;
		
		tableInfo.currentRack.sortRack();
		Print.print("It is now your turn :");
		Print.printRacktoUser(tableInfo.currentRack);
		Print.println(tableInfo.currentRack.getMelds().toString());

		
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
