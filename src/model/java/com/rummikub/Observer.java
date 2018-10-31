package com.rummikub;

public interface Observer {
	public void update(TableInfo tableInfo);
	public void setSubject(Subject subject);
}
