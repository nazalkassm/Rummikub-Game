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
		
		int sum = 0;
		
		if(!tableInfo.playedInital30) {
			for(Meld m: melds) {
				sum += m.sumMeld(m);
			}
			
			if(sum > 30) {
				tableInfo.playedInital30 = true;
				removeTiles(melds);
				return melds;
			}
			
			else {
				return null;
			}
		}
		
		removeTiles(melds);	
		return melds;
	}
	
	private void removeTiles(List<Meld> melds) {
		for(Meld m: melds) {
			for(Tile t: m.getMeld()) {
				tableInfo.currentRack.getRackArray().remove(t);
			}
		}
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
