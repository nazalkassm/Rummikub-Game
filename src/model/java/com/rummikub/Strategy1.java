package com.rummikub;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Strategy1 implements StrategyBehaviour {
	private TableInfo tableInfo; 
	
	Strategy1() {
		//subject.registerObserver(this);
	}
	
	@Override
	public List<Meld> useStrategy() 
	{
		// Data Structure Variables
		List<Meld> melds = new ArrayList<Meld>(tableInfo.currentRack.getMelds());
		
		tableInfo.currentRack.sortRack();
		Print.printRacktoUser(tableInfo.currentRack);
		Print.print("AI01 melds to play: " + melds); // to change to printUserMeld
		
		int sum = 0;
		
		if(!tableInfo.canPlayOnMelds) {
			for(Meld m: melds) {
				sum += m.sumMeld(m);
			}
			
			if(sum >= 30) {
				removeTiles(melds);
				return melds;
			}
			
			else {
				return Collections.emptyList(); // this might be wrong
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
