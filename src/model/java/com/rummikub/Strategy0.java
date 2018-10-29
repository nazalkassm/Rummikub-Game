package com.rummikub;

public class Strategy0 implements Behaviour, Observer 
{
	private TableInfo tableInfo = new TableInfo(); 
	
	Strategy0(Subject subject) 
	{
		subject.registerObserver(this);
	}
	
	@Override
	public void play() 
	{
		
	}

	@Override
	public void update(TableInfo tableInfo) 
	{
		this.tableInfo = tableInfo;		
	}
}
