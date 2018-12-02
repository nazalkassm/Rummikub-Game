package com.rummikub;

import java.util.List;

public interface StrategyBehaviour extends Observer
{
	public List<Meld> useStrategy(Player currPlayer);
	
	public void playStrategy(Player currPlayer, List<Meld> possibleMelds, List<Meld> returnMelds);
}
