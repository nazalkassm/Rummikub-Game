package com.rummikub;

import java.util.ArrayList;
import java.util.List;

import com.rummikub.Player.Memento;

public class TableInfo 
{
	
	private int lowestHandCount;
	private List<Meld> melds = new ArrayList<Meld>();
	
	public TableInfo(int lowestHandCount, List<Meld> melds) {
		this.lowestHandCount = lowestHandCount;
		this.melds = melds;
	}
	
	public int getLowestHandCount() {
		return lowestHandCount;
	}
	
	public List<Meld> getMelds() {
		return melds;
	}
	
	public Memento saveToMemento() {
		System.out.println("Saving time to Memento");
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
				this.savedMelds.add(new Meld((Tile[]) meld.getMeld().toArray()));
			}
		}

		// accessible by outer class only
    private List<Meld> getSavedMelds() {
        return savedMelds;
    }
	}

}
