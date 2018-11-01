package com.rummikub;

import java.io.IOException;
import java.util.List;

public interface StragetyBehaviour extends Observer
{
	public List<Meld> useStrategy() throws IOException;
}
