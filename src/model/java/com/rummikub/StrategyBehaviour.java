package com.rummikub;

import java.io.IOException;
import java.util.List;

public interface StrategyBehaviour extends Observer
{
	public List<Meld> useStrategy(Player currPlayer) throws IOException;
}
