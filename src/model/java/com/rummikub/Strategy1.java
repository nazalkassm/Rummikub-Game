package com.rummikub;

import java.util.ArrayList;

public class Strategy1 implements StragetyBehaviour {
	private TableInfo tableInfo; 
	
	Strategy1() {
		//subject.registerObserver(this);
	}
	
	@Override
	public ArrayList<Meld> useStrategy() 
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
