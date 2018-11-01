package com.rummikub;

import java.util.ArrayList;
import java.util.List;

public class TableInfo 
{
	
	public int lowestHandCount;
	public List<Meld> melds = new ArrayList<Meld>();
	public boolean canPlayOnMelds;
	public Rack currentRack;
	public boolean playedInital30;
	
	
	public TableInfo(int lowestHandCount, List<Meld> melds, Rack rack, boolean init30) {
		this.lowestHandCount = lowestHandCount;
		this.melds = melds;
		this.canPlayOnMelds = false;
		this.currentRack = rack;
		this.playedInital30 = init30;
	}

}
