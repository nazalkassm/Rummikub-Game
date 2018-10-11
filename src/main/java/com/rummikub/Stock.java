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
		this.stockArray.addAll(stockCopy.getStock());
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
		}
		return returnList;
	}
	
	public ArrayList<Tile> getStock() {
		return this.stockArray;
	}
	public void setStock(ArrayList<Tile> stock) {
		this.stockArray = stock;
	}
	public void createStock() 
	{
		for(Ranks r: Ranks.values())
		{
			for(Colors c:Colors.values())
			{
				stockArray.add(new Tile(r,c));
				stockArray.add(new Tile(r,c));
			}
		}
	}

	public Object dealTile() {
		// TODO Auto-generated method stub
		return null;
	};
}
