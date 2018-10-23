package com.rummikub;

public class AIStrategy1 extends Player{

	public AIStrategy1(Stock stock, String gName) {
		super(stock, gName);
		// TODO Auto-generated constructor stub
		playerBehaviour = new Strategy0();
	}

}
