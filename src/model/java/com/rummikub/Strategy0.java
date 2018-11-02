package com.rummikub;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Strategy0 implements StrategyBehaviour 
{
	private TableInfo tableInfo; 
	
	Strategy0() {	}
	
	
	@Override
	public List<Meld> useStrategy(Player currPlayer) throws IOException 
	{
		// String[] arrayofNumbs = null;
		List<Meld> returnMelds = new ArrayList<>();
		List<Meld> possibleMelds = new ArrayList<>(currPlayer.getPlayerRack().getMelds());
		
		//Print Hand Info
		Print.printRacktoUser(currPlayer.getPlayerRack());
		Print.printMeldtoUser(possibleMelds);
		
		int sum = 0;
		if(!currPlayer.canPlayOnExistingMelds) {
			Print.print("You have not played your initial 30 points yet");
			for(Meld m: possibleMelds) {
				sum += m.sumMeld(m);
			}
			
			if(sum >= 30) {
				inputPlay(currPlayer, possibleMelds, returnMelds);
				
				if(returnMelds.isEmpty())
				{
					return Collections.emptyList();
				}	
				
				else {
					return returnMelds;
				}
			}
			
			else {
				Print.print("You cannot play your initial 30 this round");
				return Collections.emptyList(); // this might be wrong
			}
			
		}
		
		
		inputPlay(currPlayer, possibleMelds, returnMelds);
		
		if(returnMelds.isEmpty())
		{
			return Collections.emptyList();
		}
		
		
		return returnMelds;
	}
	
	
	private void inputPlay(Player currPlayer, List<Meld> possibleMelds, List<Meld> returnMelds) throws IOException {
		String inputString = Prompt.promptInput("Enter the melds you want to play (0 to pass or no melds) : ");

		int input = Integer.parseInt(inputString);
		
		while(input > 0 && input <= possibleMelds.size() + Constants.ONE_INDEX)
		{
			returnMelds.add(possibleMelds.get(input-Constants.ONE_INDEX));
			
			currPlayer.rack.removeTiles(possibleMelds.get(input-Constants.ONE_INDEX));
			
			inputString = Prompt.promptInput("Enter the melds you want to play (0 to pass): ");
			input = Integer.parseInt(inputString);
		}
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
