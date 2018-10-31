package com.rummikub;

import java.util.ArrayList;
import java.util.List;

public class TableInfo 
{
	
	public int lowestHandCount;
	public List<Meld> melds = new ArrayList<Meld>();
	public boolean canPlayOnMelds;
	
	public TableInfo(int lowestHandCount, List<Meld> melds) {
		this.lowestHandCount = lowestHandCount;
		this.melds = melds;
		this.canPlayOnMelds = false;
	}

}
