package com.rummikub;

public class Strategy1 implements Behaviour, Observer {
	private TableInfo tableInfo = new TableInfo(); 
	
	Strategy1(Subject subject) {
		subject.registerObserver(this);
	}
	
	@Override
	public void play() {
		// TODO Auto-generated method stub
		
		
	}

	@Override
	public void update(TableInfo tableInfo) {
		this.tableInfo = tableInfo;		
	}

}
