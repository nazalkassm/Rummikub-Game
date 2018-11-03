package com.rummikub;

import java.util.List;

public class Strategy2 implements StrategyBehaviour {
	private TableInfo tableInfo; 
	
	Strategy2() {
		//subject.registerObserver(this);
	}
	
	@Override
	public List<Meld> useStrategy(Player currPlayer) 
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

	@Override
	public void playStrategy(Player currPlayer, List<Meld> possibleMelds, List<Meld> returnMelds) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
