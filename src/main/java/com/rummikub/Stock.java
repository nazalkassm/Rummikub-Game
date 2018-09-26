package com.rummikub;

import java.util.ArrayList;

//To be implemented TODO
public class Stock 
{
	private ArrayList<Tile> stock = new ArrayList<>(104);
	
	public Stock() {};
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
	public ArrayList<Tile> getStock() {
		return stock;
	}
	public void setStock(ArrayList<Tile> stock) {
		this.stock = stock;
	}
	public void createStock() {
		// TODO Auto-generated method stub
		
	};
}
