package com.rummikub;

import com.rummikub.Player;
import com.rummikub.Behaviour;

public class PlayerMock extends Player
{
	public PlayerMock(String gName) 
	{
		super(gName);
	}
	
	private boolean useStrategyCalled = false;
	
	@Override
	public void play() 
	{
		this.useStrategyCalled = true;
		super.play();
	}

	public boolean isUseStrategyCalled() 
	{
		return useStrategyCalled;
	}
}
