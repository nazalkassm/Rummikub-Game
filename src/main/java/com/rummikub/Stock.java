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
	
	public ArrayList<Tile> dealRack() 
	{
		ArrayList<Tile> returnList = new ArrayList<>();
		int fromCurrentLength = this.getLength();
		int toCurrentMinusRS = this.getLength()- Constants.RACK_SIZE;
		for(int index = fromCurrentLength; index<toCurrentMinusRS; index--)
		{
			returnList.add(stockArray.get(index));
			stockArray.remove(index);
			stockArray.trimToSize();
		}
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
		return stockArray.remove(0);
	};
}
