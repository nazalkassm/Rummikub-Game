package com.rummikub;

import java.util.ArrayList;
import java.util.List;

public class TableInfo 
{
	
	private int[] players_rack_count;
	private List<Meld> melds = new ArrayList<Meld>();
	
	public TableInfo(int[] player_rack_count, List<Meld> melds) {
		this.players_rack_count = player_rack_count;
		this.melds = melds;
	}
	
	public int[] getPlayersRackCount() {
		return players_rack_count;
	}
	
	public List<Meld> getMeldsFromTable() {
		return melds;
	}
	
	public Memento saveToMemento() {
		return new Memento(this.melds);
	}

	public void restoreFromMemento(Memento memento) {
		this.melds = memento.getSavedMelds();
	}

	public static class Memento {
		private final List<Meld> savedMelds;

		public Memento(List<Meld> meldsToSave) {
			//Melds to save is a new list of melds 
			this.savedMelds = new ArrayList<Meld>();
			
			for (Meld meld : meldsToSave) {
				List<Tile> tiles = new ArrayList<Tile>(meld.getTiles());
	
				this.savedMelds.add(new Meld(tiles.toArray(new Tile[tiles.size()])));
			}
		}

		// accessible by outer class only
    private List<Meld> getSavedMelds() {
        return savedMelds;
    }
	}

}
