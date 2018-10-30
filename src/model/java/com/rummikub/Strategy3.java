package com.rummikub;

import java.util.ArrayList;

public class Strategy3 implements Behaviour , Observer {
	private TableInfo tableInfo = new TableInfo(); 
	
	Strategy3(Subject subject) {
		subject.registerObserver(this);
	}
	
	@Override
	public ArrayList<Meld> play() 
	{
		return null;
	}

	@Override
	public void update(TableInfo tableInfo) 
	{
		this.tableInfo = tableInfo;		
	}

}
