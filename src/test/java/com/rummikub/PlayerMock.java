package com.rummikub;

import com.rummikub.Player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.rummikub.StrategyBehaviour;

public class PlayerMock extends Player
{
	public PlayerMock(String gName) 
	{
		super(gName);
	}
	
	private boolean useStrategyCalled = false;
	
	@Override
	public List<Meld> play() throws IOException 
	{
		this.useStrategyCalled = true;
		return super.play();
	}

	public boolean isUseStrategyCalled() 
	{
		return useStrategyCalled;
	}
}
