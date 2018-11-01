package com.rummikub;

import java.util.ArrayList;
import java.util.List;

public class TableInfo 
{
	
	private int lowestHandCount;
	private List<Meld> melds = new ArrayList<Meld>();
	
	public TableInfo(int lowestHandCount, List<Meld> melds) {
		this.lowestHandCount = lowestHandCount;
		this.melds = melds;
	}

}
