package com.rummikub;

import java.util.ArrayList;
import java.util.List;

//To be implemented TODO
public class Stock 
{
	private int stockL;
	private List<Tile> stock = new ArrayList<Tile>(stockL);
	
	public Stock(int sL) 
	{
		this.stockL = sL;
	};
	
	public Stock (Stock stockCopy) 
	{
		//copy the stock given. TODO
	}
	public void shuffle() 
	{
		// TODO Auto-generated method stub
	}
	public int getLength() {
		// TODO Auto-generated method stub
		return stock.toArray().length;
	}
	public Object dealRack() {
		// TODO Auto-generated method stub
		return null;
	}
	public List<Tile> getStock() {
		return stock;
	}
	public void setStock(ArrayList<Tile> stock) {
		this.stock = stock;
	}
	public void createStock() {
		// TODO Auto-generated method stub
		
	}

	public Object dealTile() {
		// TODO Auto-generated method stub
		return null;
	};
}
