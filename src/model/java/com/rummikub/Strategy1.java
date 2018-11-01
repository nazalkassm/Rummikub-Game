package com.rummikub;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Strategy1 implements StrategyBehaviour {
	private TableInfo tableInfo; 
	
	Strategy1() {	}
	
	@Override
	public List<Meld> useStrategy(Player currPlayer) 
	{
		// Data Structure Variables
		List<Meld> melds = new ArrayList<Meld>(currPlayer.getPlayerRack().getMelds());
		
		currPlayer.getPlayerRack().sortRack();
		Print.printRacktoUser(currPlayer.getPlayerRack());
		Print.print("AI01 melds to play: " + melds); // to change to printUserMeld
		
		int sum = 0;
		
		if(!currPlayer.canPlayOnExistingMelds) {
			for(Meld m: melds) {
				sum += m.sumMeld(m);
			}
			
			if(sum >= 30) {
				currPlayer.canPlayOnExistingMelds = true;
				currPlayer.removeTiles(melds);
				return melds;
			}
			
			else {
				return Collections.emptyList(); // this might be wrong
			}
		}
		
		currPlayer.removeTiles(melds);	
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
