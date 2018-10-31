package com.rummikub;

import java.io.IOException;
import java.util.ArrayList;

public interface StragetyBehaviour extends Observer
{
	public ArrayList<Meld> useStrategy() throws IOException;
}
