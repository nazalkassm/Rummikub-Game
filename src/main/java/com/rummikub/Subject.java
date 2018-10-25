package com.rummikub;

import java.util.ArrayList;
import java.util.List;

public interface Subject {  
	public void registerObserver(Observer O);
	public void removeObserver(Observer O);
	public void notifyObservers();
}

