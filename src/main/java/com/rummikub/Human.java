package com.rummikub;

public class Human extends Player {


	/**
	 * Constructor of Human 
	 * 
	 * @param stock = The stock that we use to get the initial rack from
	 * @param name = The name of player
	 * @param behaviour = The behaviour the player uses 
	 * 
	 * Setting behaviour: Player(stock, "p1", new Strategy0());
	 */
	public Human(Stock stock, String name, Behaviour behaviour) {
		super(stock, name, behaviour);
	}

}
