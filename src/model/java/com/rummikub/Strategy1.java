package com.rummikub;

import java.util.ArrayList;

public class Strategy1 implements StragetyBehaviour, Observer {
	private TableInfo tableInfo = new TableInfo(); 
	
	Strategy1() {
		//subject.registerObserver(this);
	}
	
	@Override
	public ArrayList<Meld> useStrategy(Rack rack) 
	{
		return null;
	}

	@Override
	public void update(TableInfo tableInfo) {
		this.tableInfo = tableInfo;		
	}

}
