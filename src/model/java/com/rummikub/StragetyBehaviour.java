package com.rummikub;

import java.io.IOException;
import java.util.ArrayList;

public interface StragetyBehaviour 
{
	public ArrayList<Meld> useStrategy(Rack rack) throws IOException;
}
