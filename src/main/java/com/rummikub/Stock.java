package com.rummikub;

import java.util.ArrayList;
import java.util.Collections;

/**
 * This class is meant to model a stock in a tile rummy game. 
 * A stock is equivalent to a deck in a card game but instead holds tiles that are numbered from 1 to 13 and colored Red,Blue,Orange and Green.
 * All non-getter methods have been tested in StockTtest Junit class.
 * @author Carleton University Team 25
 * @see ArrayList
 * @see Collections
 * @version 1.00, 12 Oct 2018
 */
public class Stock 
{
	//Stock Size
	int stockS;
	//An Arraylist that stores all the tiles in a stock
	private ArrayList<Tile> stockArray = new ArrayList<>(stockS);;
	
	/**
	 *Supresses default constructor, ensuring non-instantiability.
	 *A stock is only a stock if it lives with tiles. Therefore, the constructing a stock is STOCK_SIZE by defualt.
	*/
	public Stock() 
	{
		this(Constants.STOCK_SIZE);
	}
	
	/**
	 * Main Constructor to create a stock you need to give a size.
	 * When a stock is created we add two 52 distinct tiles. Then, they are shuffled.
	 * @param sS given stock size
	*/
	public Stock(int sS) 
	{
		this.stockS = sS;
		this.createStock();
	}
	
	/**
	 * This constructor is used only for testing purposes.
	 * @param stockCopy takes a stock in order to copy its tiles.
	 */
	public Stock (Stock stockCopy) 
	{
		this.stockS = stockCopy.stockS;
		this.stockArray.addAll(stockCopy.getStockArray());
	}
	
	/**
	 * This method uses Collections shuffle method to shuffle our stock
	 * @since version 1.00
	 */
	public void shuffle() 
	{
		Collections.shuffle(this.stockArray);
	}
	
	/**
	 * This method is used to deal 14 tiles to anyone who asks the stock for an arraylist.
	 * * I made sure to trim the arraylist to space some memory, instead for waiting for the automatic shrink.
	 * @return an arraylist that contains 14 tiles 
	 * @since version 1.00
	 */
	public ArrayList<Tile> deal14Tiles() 
	{
		ArrayList<Tile> returnList = new ArrayList<>(14);
		for(int i = 0; i < Constants.RACK_SIZE;i++)
		{
			returnList.add(this.stockArray.remove(i));
		}
		stockArray.trimToSize();
		return returnList;
	}
	
	public void createStock() 
	{
		for(Colors c: Colors.values())
		{
			for(Ranks r:Ranks.values())
			{
				stockArray.add(new Tile(c, r));
				stockArray.add(new Tile(c, r));
			}
		}
		this.shuffle();
	}
	
	/**
	 * This method deals a tile at a time. Players usually need one tile per turn. 
	 * I made sure to trim the arraylist to space some memory, instead for waiting for the automatic shrink.
	 * @return the first tile which is random from the stock.
	 * @since version 1.00
	 */
	public Tile dealTile() 
	{
		Tile returnTile;
		returnTile = stockArray.remove(0);
		stockArray.trimToSize();
		return returnTile;
	};
	
	//GETTERS
	/**
	 * This method is only used in testing at the moment.
	 * @return the current arraylist that contains all tiles.
	 * @since version 1.00
	 */
	public ArrayList<Tile> getStockArray() 
	{
		return this.stockArray;
	}
	
	/**
	 * This method uses the size method in Collections
	 * @return the number of tiles in a stock
	 * @since version 1.00
	 */
	public int getLength() 
	{
		return stockArray.size();
	}
}
