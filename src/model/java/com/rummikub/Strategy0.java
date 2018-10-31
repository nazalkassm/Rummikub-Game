package com.rummikub;

import java.io.IOException;
import java.util.ArrayList;

public class Strategy0 implements StragetyBehaviour, Observer 
{
	private TableInfo tableInfo = new TableInfo(); 
	
	Strategy0(Subject subject) 
	{
		subject.registerObserver(this);
	}
	
	@Override
	public ArrayList<Meld> useStrategy(Rack rack) throws IOException 
	{
		rack.sortRack();
		Print.print("Human Turn :");
		Print.printRacktoUser(rack);
		String choice = Prompt.promptInput("Enter the melds you want to play:");
		
		return null;
	}

	@Override
	public void update(TableInfo tableInfo) 
	{
		this.tableInfo = tableInfo;		
	}
}
