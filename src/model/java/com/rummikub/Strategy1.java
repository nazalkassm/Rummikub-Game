package com.rummikub;

import java.util.List;

public class Strategy1 implements StrategyBehaviour {
	private TableInfo tableInfo; 
	
	Strategy1() {
		//subject.registerObserver(this);
	}
	
	@Override
	public List<Meld> useStrategy() 
	{
		List<Meld> melds = tableInfo.currentRack.getMelds();
		
		Print.print("AI01 cards: " + tableInfo.currentRack);
		Print.print("AI01 melds to play: " + melds);
		
		/*if(!played30) {
			if
		}*/
		
		for(Meld m: melds) {
			for(Tile t: m.getMeld()) {
				tableInfo.currentRack.getRackArray().remove(t);
			}
		}
		
		
		return melds;
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
