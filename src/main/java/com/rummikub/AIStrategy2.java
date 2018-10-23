package com.rummikub;

public class AIStrategy2 extends Player{

	public AIStrategy2(Stock stock, String gName) {
		super(stock, gName);
		// TODO Auto-generated constructor stub
		playerBehaviour = new Strategy1();
	}

}
