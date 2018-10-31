package com.rummikub;

import java.util.ArrayList;

public class Strategy2 implements StragetyBehaviour {
	private TableInfo tableInfo = new TableInfo(); 
	
	Strategy2() {
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

	@Override
	public void setSubject(Subject subject) {
		subject.registerObserver(this);
	}

}
