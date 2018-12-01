package com.rummikub;

import java.io.IOException;
import java.util.List;

public interface StrategyBehaviour extends Observer
{
	public List<Meld> useStrategy(Player currPlayer) throws Exception;
	
	public void playStrategy(Player currPlayer, List<Meld> possibleMelds, List<Meld> returnMelds);
}
