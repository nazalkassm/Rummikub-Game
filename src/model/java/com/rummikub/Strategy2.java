package com.rummikub;

import java.util.List;

public class Strategy2 implements StrategyBehaviour {
	private TableInfo tableInfo; 
	
	Strategy2() {
		//subject.registerObserver(this);
	}
	
	@Override
	public List<Meld> useStrategy() 
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
