package com.rummikub;

import com.rummikub.Player;

import java.util.ArrayList;

import com.rummikub.Behaviour;

public class PlayerMock extends Player
{
	public PlayerMock(String gName) 
	{
		super(gName);
	}
	
	private boolean useStrategyCalled = false;
	
	@Override
	public ArrayList<Meld> play() 
	{
		this.useStrategyCalled = true;
		return super.play();
	}

	public boolean isUseStrategyCalled() 
	{
		return useStrategyCalled;
	}
}
