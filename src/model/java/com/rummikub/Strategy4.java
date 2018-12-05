package com.rummikub;

import java.util.ArrayList;
import java.util.List;

import com.rummikub.Meld.MeldType;

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
	//Next, If it played it's initial 30. This strategy will:
	//1.)Find all it's rack melds
	//2.)Check if the probability of improving the meld is greater than 0 (because does not want to give other players the chance to play on it)
	//3.)If > 0, the strategy will hold that meld. It will play all other tiles with the table.
	//4.)Otherwise, the strategy will just play all the tiles combined with the table.
	
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
		
		// A list of tiles that will contain the tables only on the table. We need it to know what has been played already.
		List<Tile> table_tiles = new ArrayList<>();
		
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
			table_tiles.addAll(m.getTiles());
		}
		
		// Add table tiles to the combined_tiles
		combined_tiles.addAll(table_tiles);
		
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
			 * Here this strategy will find out the probability of improving a meld:
			 * 1.) Decides whether to play it or save it for later.
			 * 2.) If he does, save it. He plays all other tiles with table
			 * 3.) If he does not save it. He plays all the tiles including it with table.
			 */
			
			//Get players rack sizes and store them all in an array
			int[] players_rack_count = tableInfo.getPlayersRackCount();
			
			//This variable will store the total number of tiles of all players' racks
			int total_rack_count = 0;
			
			//This variable will store the total number of tiles remaining in the stock
			double total_tiles_in_stock = 0;
			
			// A list stores the melds we don't want to play because the probability of getting an out is greater than 0.05
			List<Meld> melds_save = new ArrayList<>();
			
		
			//Sum the counts
			for(int i=0; i < players_rack_count.length; i++)
			{
				total_rack_count += players_rack_count[i];
			}
			
			//Finding out the remaining tiles in stock
			total_tiles_in_stock = Constants.STOCK_SIZE - total_rack_count - total_table_count;
			
			//Calling a function to find the outs and calculate the probability that decides if we want to play or not play a certain meld
			melds_save = findProbability(melds,total_tiles_in_stock,table_tiles);
			
			//If melds_save is empty we play all the tiles possible with the table
			if(melds_save.isEmpty())
			{
				return_melds.clear();
				return_melds.addAll(combined_melds);
			}
			else //otherwise we keep the melds we don't want to play
			{
				// Empty the return melds,the combined_melds, and the combinted_tiles to reset the process
				return_melds.clear();
				combined_melds.clear();
				combined_tiles.clear();
				
				//Remove the melds we want to save in our rack
				melds.removeAll(melds_save);
				
				// We create a new combined_melds without the ones we don't want to play
				
				//Add table_tiles to the combined tiles.
				combined_tiles.addAll(table_tiles);
				
				for(Meld m: melds)
				{
				combined_tiles.addAll(m.getTiles());
				}
				
				
				combined_melds.addAll(Meld.getMeldsWithTable(combined_tiles));
				
				return_melds.addAll(combined_melds);		
			}
			

		} // end of the most outer if loop
		
		/*
		 * remove the melds the player will play, we need to store the tiles he played
		 */
		
		// An Array to store the tiles the player played from his hand.
		
		
		currentPlayer.removeTiles(return_melds);
		
		return return_melds;
	}
	
	private List<Meld> findProbability(List<Meld> melds,double total_tiles_in_stock,List<Tile> table_tiles)
	{
		// Integer that stores the outs for each meld to improve
		double outs_number = 0;
		// double that stores the probability of saving the meld and not playing with it.
		double probability = -1;
		// A list to store the melds we want to save and not play
		List<Meld> melds_save = new ArrayList<>();
		
		// For every meld in the Player's rack find the outs.
		for(Meld m: melds)
		{
			//An array that will store the tiles of the current meld we are looking at.
			List<Tile> meld_tiles = new ArrayList<>();
			meld_tiles.addAll(m.getTiles());
			
			//An array that will store the actual outs
			List<Tile> outs_tiles = new ArrayList<>();
			
			Integer outs_integer = new Integer(0);
			
			//A tile that stores the tile we are playing with
			
			//Finding the outs in a meld if it's a run
			if(Meld.checkMeldType(meld_tiles) == (MeldType.RUN))
			{
			
			// This string stores the colour of this run
			String meld_colour = meld_tiles.get(0).getColour().getSymbol();
			
		    //--------------------------------------if the first tile is a 1 or the last tile is a 13------------------------------------------
				if(meld_tiles.get(0).getRank().getValue() == 1 && meld_tiles.get(meld_tiles.size()-1).getRank().getValue() == 13)
				{
					outs_number += 9;
					// Algorithm to put all outs inside the outs_string
					int index = 2;
					for(int i =0; i < outs_number; i++)
					{
						outs_tiles.add(meld_tiles.get(index));
						index++;
					}
					
				}
				//-------------------------------if the first tile is a 1------------------------------------------
				else if(meld_tiles.get(0).getRank().getValue() == 1)
				{
					int index = 0;
					outs_integer = meld_tiles.get(meld_tiles.size()-1).getValue() + 1;
					if(meld_tiles.size() >= 5)
					{	
							outs_number += meld_tiles.size() - 3;
							index = 2;
							for(int i =0; i < outs_number-1; i++)
							{
								outs_tiles.add(meld_tiles.get(index));
								index++;
							}	
							outs_tiles.add(new Tile(meld_colour.toString(),outs_integer.toString()));
					}
					else
					{
						outs_number += 1;    
						outs_tiles.add(new Tile(meld_colour.toString(),outs_integer.toString()));
					}
				}
				//-------------------------------if the last tile is a 13------------------------------------------

				else if (meld_tiles.get(meld_tiles.size()-1).getRank().getValue() == 13) 
				{
					int index = 0;
					outs_integer = meld_tiles.get(0).getValue() - 1;
					if(meld_tiles.size() >= 5)
					{	
							outs_number += meld_tiles.size() - 3;
							index = 2;
							for(int i =0; i <outs_number-1; i++)
							{
								outs_tiles.add(meld_tiles.get(index));
								index++;
							}
							outs_tiles.add(new Tile(meld_colour.toString(),outs_integer.toString()));
					}
					else
					{
						outs_number += 1;
					    
						outs_tiles.add(new Tile(meld_colour.toString(),outs_integer.toString()));
					}
				}
				else
				//-------------------------------if the first tile is not 1 and the last tile is not 13------------------------------------------

				{
					outs_integer = meld_tiles.get(0).getValue() - 1;
					Integer outs_integer2 = new Integer(meld_tiles.get(meld_tiles.size()-1).getValue() + 1);
					if(meld_tiles.size() >= 5)
					{
						outs_number += meld_tiles.size() - 2;
						int index = 2;
						for(int i =0; i <outs_number-2; i++)
						{
							outs_tiles.add(meld_tiles.get(index));
							index++;
						}
						outs_tiles.add(new Tile(meld_colour.toString(),outs_integer.toString()));
						outs_tiles.add(new Tile(meld_colour.toString(),outs_integer2.toString()));
					}
					else
					{
						outs_number += 2;
						outs_tiles.add(new Tile(meld_colour.toString(),outs_integer.toString()));
						outs_tiles.add(new Tile(meld_colour.toString(),outs_integer2.toString()));
					}
				}// end of inner if
				
			} // Now we find the outs if the meld is a set
			else if(Meld.checkMeldType(meld_tiles) == (MeldType.SET))
			{
				// An array of strings, each string represents a colour symbol
				List<String> colour_symbols = new ArrayList<>();
				colour_symbols.add("O");
				colour_symbols.add("G");
				colour_symbols.add("B");
				colour_symbols.add("R");
				
				// An integer object to store the value of a tile
				Integer value_of_tile = new Integer(meld_tiles.get(0).getValue());
				
				if (meld_tiles.size() == 3)
				{
					outs_number += 1;
					
					// Get the actual outs tiles
					for(Tile meld_tile: meld_tiles)
					{
						colour_symbols.remove(meld_tile.getColour().getSymbol());
					}
					
					outs_tiles.add(new Tile(colour_symbols.get(0),value_of_tile.toString()));
				}
				else if(meld_tiles.size() == 2)
				{
					outs_number += 2;
					
					// Get the actual outs tiles
					for(Tile meld_tile: meld_tiles)
					{
						colour_symbols.remove(meld_tile.getColour().getSymbol());
					}
					
					outs_tiles.add(new Tile(colour_symbols.get(0),value_of_tile.toString()));
					outs_tiles.add(new Tile(colour_symbols.get(1),value_of_tile.toString()));
				}
				else
				{
					outs_number += 0;
				}
			}
			else
			{
				System.out.println("An error happened inside strategy 4, The meld type is invalid");
			}// end of outer if
			
//----------------------------------------------------------------------------------------------------------------//
			
			/**
			 * Logic to take care of removing the outs that have already been played on the table.
			 */
			
			
			
			//First multiply the number of outs by two because we have 2 of each card
			outs_number *= 2;
			
			// Now we remove outs if some of the table tiles are in outs tiles
			for(Tile table_tile: table_tiles)
			{
				for(Tile out_tile:outs_tiles)
				{
					if(table_tile.equals(out_tile))
					{
						outs_number--;
					}
				}
			}
			
			//Logic for comparing with the table.
			
			//Find the probability of not playing this meld
			if (outs_number == 0 || total_tiles_in_stock == 0)
			{
				probability = 0;
			}
			else
			{
				probability = outs_number/total_tiles_in_stock;
			}
			
			if(probability > Constants.PROBABILITY_TO_PLAY)
			{
				melds_save.add(m);
			}
			
		} // end of for loop
		
		return melds_save;
	}//end of function

}
