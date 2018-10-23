package com.rummikub;

import com.rummikub.Player;
import com.rummikub.PlayerBehaviour;

public class PlayerMock extends Player
{

	public PlayerMock(String gName) 
	{
		super(gName);
	}

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
