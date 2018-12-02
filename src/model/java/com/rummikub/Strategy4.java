package com.rummikub;

import java.util.ArrayList;
import java.util.List;

public class Strategy4 implements StrategyBehaviour
{
	// tableInfo contains the table information, the player observes the table info
	private TableInfo tableInfo; 

	@Override
	public void update(TableInfo tableInfo) 
	{
		this.tableInfo = tableInfo;		
	}

	@Override
	public void setSubject(Subject subject) 
	{
		subject.registerObserver(this);
	}

	@Override
	//This strategy will always play the initial 30 meld/melds as soon as possible.
	//The strategy will not play the tiles of a pre-run(two numbers about to be a run) or a pre-set using the table if the probability of drawing a card to create a meld is > 0
	//The strategy plays all melds possible in hand.
	public List<Meld> useStrategy(Player currentPlayer) 
	{
	    // A list to store the rack tiles
		List<Tile> tiles = new ArrayList<>();
		
		// A list to store the rack and table tiles
		List<Tile> combined_tiles = new ArrayList<>();
		
		// A list to store the melds in player rack
		List<Meld> melds = new ArrayList<>();
		
		// A list to store the melds in the player rack combined with the table
		List<Meld> combined_melds = new ArrayList<>();
		
		// A list of melds we want to add to the table, always contains the table melds
		List<Meld> return_melds = new ArrayList<>(tableInfo.getMeldsFromTable());
		
		// An integer that will store the total number of tiles played on the table
		int total_table_count = 0;

		//Save the current player and table state
		Player.Memento playerState = currentPlayer.saveToMemento();
		TableInfo.Memento tableState = tableInfo.saveToMemento();
		
		//Print the user his rack
		Print.printRacktoUser(currentPlayer.getPlayerRack(),currentPlayer.isPrint_rack_meld());

		//Storing the tiles from the rack
		tiles.addAll(currentPlayer.getPlayerRack().getRackArray());
		
		//Storing the tiles from the rack and table
		
		for (Meld m: tableInfo.getMeldsFromTable())
		{
		combined_tiles.addAll(m.getTiles());
		}
		
		total_table_count = combined_tiles.size();
		
		combined_tiles.addAll(tiles);
		
		
		
		//Storing the melds in the rack
		melds.addAll(Meld.getMeldsWithTable(tiles));
		
		//Storing the melds in the rack combined with table
		combined_melds.addAll(Meld.getMeldsWithTable(combined_tiles));
		
		//If the player can't play on the table yet, let's satisfy the 30 and play.
		if (!currentPlayer.canPlayOnExistingMelds) 
		{
			// Keeps track of the sum of melds
			int sum = 0;
			
			// Sum up all the melds
			for (Meld m: melds)
			{
				sum += m.sumMeld();
			}
			
			//Check if the player can play
			if(sum >= 30)
			{
				currentPlayer.canPlayOnExistingMelds = true;
				return_melds.addAll(melds);
			}
			else
			{
				Print.print("Player " + currentPlayer.getName() + " tried playing melds but their sum is less than 30.");
				currentPlayer.restoreFromMemento(playerState);
				tableInfo.restoreFromMemento(tableState);
			}
		}
		else //The player can play on existing melds, play the probability strategy you want to play
		{
			
		
			/*
			 * Here this strategy will find out the probability of drawing a card to create a meld in it's rack 
			 * 
			 * Otherwise it will use the table and play all the melds from the table.
			 */
			
			//Get players rack sizes and store them all in an array
			int[] players_rack_count = tableInfo.getPlayersRackCount();
			
			//This variable will store the total number of tiles of all players' racks
			int total_rack_count = 0;
			
			//This variable will store the total number of tiles remaining in the stock
			int total_tiles_in_stock = 0;
			
			// A list that contains all the pre-run melds
			List<Meld> pre_run_melds = new ArrayList<>(Meld.getPreRunMelds(tiles));
			
			// A list that contains all the pre-set melds
			List<Meld> pre_set_melds = new ArrayList<>(Meld.getPreSetMelds(tiles));
			
			// A list that stores the outs to create a meld
			List<Tile> outs = new ArrayList<>();
		
			//Sum the counts
			for(int i=0; i < players_rack_count.length; i++)
			{
				total_rack_count += players_rack_count[i];
			}
			
			//Finding out the remaining tiles in stock
			total_tiles_in_stock = Constants.STOCK_SIZE - total_rack_count - total_table_count;
			
			//Finding the number of outs to create a meld from a pre-run or a pre-set
			for(Meld m: pre_run_melds)
			{
				List<Tile> meld_tiles = new ArrayList<Tile>();
				meld_tiles = m.getTiles();
				
			}
			
			
			
			
		}
				
		return return_melds;
	}

	@Override
	public void playStrategy(Player currentPlayer, List<Meld> possibleMelds, List<Meld> returnMelds)
	{
		
	}

}
