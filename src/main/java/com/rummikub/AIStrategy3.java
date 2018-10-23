package com.rummikub;

public class AIStrategy3 extends Player{

	public AIStrategy3(Stock stock, String gName) {
		super(stock, gName);
		// TODO Auto-generated constructor stub
		playerBehaviour = new Strategy2();
	}

}
