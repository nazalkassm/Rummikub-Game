package com.rummikub;

import java.lang.reflect.Executable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.print.CancelablePrintJob;

public class Strategy3 implements StrategyBehaviour {
	private TableInfo tableInfo;
	
	Strategy3() 
	{
		//subject.registerObserver(this);
	}
	
	@Override
	public List<Meld> useStrategy(Player currPlayer) 
	{
		
		// String[] arrayofNumbs = null;
		List<Meld> returnMelds = new ArrayList<>();
		List<Meld> possibleMelds = new ArrayList<>(currPlayer.getPlayerRack().getMelds());
		
		//Print Hand Info
		Print.printRacktoUser(currPlayer.getPlayerRack());
		
		if (possibleMelds.isEmpty()) 
		{
			return Collections.emptyList();
		}
		else 
		{
			Print.printMeldtoUser(possibleMelds);
		}
		
		int sum = 0;
		if(!currPlayer.canPlayOnExistingMelds) {
			for(Meld m: possibleMelds) {
				sum += m.sumMeld(m);
			}
			
			if(sum >= 30) {
				
			}
			
			else {
				return Collections.emptyList();
			}
			
		}
		
		executePlay(currPlayer, possibleMelds, returnMelds);
			
		return returnMelds;
	}
	
	
	public void executePlay(Player currPlayer, List<Meld> possibleMelds, List<Meld> returnMelds) {
		
	}

	@Override
	public void update(TableInfo tableInfo) 
	{
		this.tableInfo = tableInfo;		
	}
	
	@Override
	public void setSubject(Subject subject) {
		subject.registerObserver(this);
	}

}
