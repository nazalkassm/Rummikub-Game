package com.rummikub;

public interface Behaviour {
	public void play(boolean canPlayOnTableMelds, Rack playerRack, Table table);
	public void canPlayOnTableMelds(boolean canPlayOnTableMelds, Rack playerRack, Table table);
}
