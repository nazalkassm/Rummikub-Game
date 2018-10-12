package com.rummikub;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Stock 
{
	int stockS;
	ArrayList<Tile> stockArray = new ArrayList<>(stockS);;
	
	public Stock() 
	{
		this(Constants.STOCK_SIZE);
	}
	
	public Stock(int sS) 
	{
		this.stockS = sS;
	}
	
	public Stock (Stock stockCopy) 
	{
		this.stockS = stockCopy.stockS;
		this.stockArray.addAll(stockCopy.getStockArray());
	}
	
	public void shuffle() 
	{
		Collections.shuffle(this.stockArray);
	}
	
	public int getLength() 
	{
		return stockArray.size();
	}
	
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
	
	public ArrayList<Tile> getStockArray() {
		return this.stockArray;
	}
	public void setStock(ArrayList<Tile> stock) {
		this.stockArray = stock;
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
	
	public Tile dealTile() 
	{
		Tile returnTile;
		returnTile = stockArray.remove(0);
		stockArray.trimToSize();
		return returnTile;
	};
}
