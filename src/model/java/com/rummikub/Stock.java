package com.rummikub;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.sun.org.apache.xpath.internal.operations.Bool;

import apple.laf.JRSUIState.TitleBarHeightState;
import javafx.scene.image.Image;

/**
 * This class is meant to model a stock in a tile rummy game. A stock is
 * equivalent to a deck in a card game but instead holds tiles that are numbered
 * from 1 to 13 and coloured Red,Blue,Orange and Green. All non-getter methods
 * have been tested in StockTtest Junit class.
 * 
 * @author Carleton University Team 25
 * @see ArrayList
 * @see Collections
 * @version 1.00, 12 Oct 2018
 */
public class Stock {
	// Stock Size
	int stockS;
	// An Arraylist that stores all the tiles in a stock
	private List<Tile> stockArray = new ArrayList<>(stockS);;

	/**
	 * Suppresses default constructor, ensuring non-instantiability. A stock is only
	 * a stock if it lives with tiles. Therefore, the constructing a stock is
	 * STOCK_SIZE by default.
	 */
	public Stock() {
		this(Constants.STOCK_SIZE,"false");
	}
	
	public Stock(String joker)
	{
		this(Constants.STOCK_SIZE,joker);
	}

	public Stock(Boolean view) {
		this(Constants.STOCK_SIZE, view);
	}

	/**
	 * Constructor to create a stock given a list of tiles.
	 * 
	 * @param tiles given list of tiles
	 */
	public Stock(List<Tile> tiles) {
		stockArray = tiles;
		stockS = tiles.size();
	}

	public Stock(List<Tile> tiles, boolean view) {
		if (view) {
			stockArray.clear();
			for (Tile t : tiles) {
				t = new Tile(t.getColour(), t.getRank(), new Image(Tile.getFilename(t.getColour(), t.getRank())));
				stockArray.add(t);
			}
			stockS = tiles.size();
		} else {
			stockArray = tiles;
			stockS = tiles.size();
		}
	}

	/**
	 * Main Constructor to create a stock you need to give a size. When a stock is
	 * created we add two 52 distinct tiles. Then, they are shuffled.
	 * 
	 * @param sS given stock size
	 */
	public Stock(int sS,String joker) {
		this.stockS = sS;
		if(joker.equals("true"))
		{
		this.createStock();
		}
		else
		{
		this.createStockWithoutJoker();
		}
		
	}

	public Stock(int sS, boolean view) {
		this.stockS = sS;
		if (view) {
			this.createStockWithImages();
		} else {
			this.createStock();
		}
	}

	/**
	 * This constructor is used only for testing purposes.
	 * 
	 * @param stockCopy takes a stock in order to copy its tiles.
	 */
	public Stock(Stock stockCopy) {
		this.stockS = stockCopy.stockS;
		this.stockArray.addAll(stockCopy.getStockArray());
	}

	/**
	 * This method uses Collections shuffle method to shuffle our stock
	 * 
	 * @since version 1.00
	 */
	public void shuffle() {
		Collections.shuffle(this.stockArray);
	}

	/**
	 * This method is used to deal 14 tiles to anyone who asks the stock for an
	 * arraylist. * I made sure to trim the arraylist to space some memory, instead
	 * for waiting for the automatic shrink.
	 * 
	 * @return an arraylist that contains 14 tiles
	 * @since version 1.00
	 */
	public List<Tile> deal14Tiles() {
		List<Tile> returnList = new ArrayList<Tile>();
		for (int i = 0; i < Constants.RACK_SIZE; i++) {
			returnList.add(this.stockArray.remove(0));
		}
		// stockArray.trimToSize(); // NEED TO TEST
		return returnList;
	}

	public void createStock() {
		// Clear the previous stock if there was one
		stockArray.clear();

		for (Colours c : Colours.values()) {
			for (Ranks r : Ranks.values()) {
				if (!(c.equals(Colours.JOKER) || r.equals(Ranks.JOKER))) {
					stockArray.add(new Tile(c, r));
					stockArray.add(new Tile(c, r));
				}
			}
		}
		stockArray.add(new Joker());
		stockArray.add(new Joker());
		this.shuffle();
	}
	
	public void createStockWithoutJoker() {
		// Clear the previous stock if there was one
		stockArray.clear();

		for (Colours c : Colours.values()) {
			for (Ranks r : Ranks.values()) {
				if (!(c.equals(Colours.JOKER) || r.equals(Ranks.JOKER))) {
					stockArray.add(new Tile(c, r));
					stockArray.add(new Tile(c, r));
				}
			}
		}
		this.shuffle();
	}

	public void createStockWithImages() {
		// Clear the previous stock if there was one

		// try {
		stockArray.clear();

		for (Colours c : Colours.values()) {
			for (Ranks r : Ranks.values()) {
				if (!(c.equals(Colours.JOKER) || r.equals(Ranks.JOKER))) {
					stockArray.add(new Tile(c, r, new Image(Tile.getFilename(c, r))));
					stockArray.add(new Tile(c, r, new Image(Tile.getFilename(c, r))));
				}
			}
		}
		stockArray.add(new Joker());
		stockArray.add(new Joker());
		this.shuffle();

		/*
		 * } catch (Exception e) { // TODO: handle exception
		 * System.out.println(e.getMessage()); }
		 */
	}

	/**
	 * This method deals a tile at a time. Players usually need one tile per turn. I
	 * made sure to trim the arraylist to space some memory, instead for waiting for
	 * the automatic shrink.
	 * 
	 * @return the first tile which is random from the stock.
	 * @since version 1.00
	 */
	public Tile dealTile() {
		Tile returnTile;
		returnTile = stockArray.remove(0);
		return returnTile;
	};

	// GETTERS
	/**
	 * This method is only used in testing at the moment.
	 * 
	 * @return the current arraylist that contains all tiles.
	 * @since version 1.00
	 */
	public List<Tile> getStockArray() {
		return this.stockArray;
	}

	/**
	 * This method uses the size method in Collections
	 * 
	 * @return the number of tiles in a stock
	 * @since version 1.00
	 */
	public int getLength() {
		return stockArray.size();
	}
}
