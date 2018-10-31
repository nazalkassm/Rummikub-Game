package com.rummikub;

public class AI extends Player {

	/**
	 * Constructor of AI 
	 * 
	 * @param stock = The stock that we use to get the initial rack from
	 * @param name = The name of player
	 * @param behaviour = The behaviour the player uses 
	 * 
	 * Setting behaviour: Player(stock, "p1", new Strategy0());
	 */
	public AI(Stock stock, String name, StragetyBehaviour behaviour) {
		super(stock, name, behaviour);
	}

}
