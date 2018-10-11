package com.rummikub;

import com.rummikub.Player;
import com.rummikub.Strategy;

public class PlayerMock extends Player
{

	private boolean useStrategyCalled = false;
	

	@Override
	public void useStrategy() {
		this.useStrategyCalled = true;
		super.useStrategy();
	  }

	public boolean isUseStrategyCalled() {
		return useStrategyCalled;
	}
}
