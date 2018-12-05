package com.rummikub;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;


//In case the list is not of type String, a joining collector can be used:
//
//String listString = list.stream().map(Object::toString)
//                        .collect(Collectors.joining(", "));


/**
 * This class is a factory that creates hands for each player in the game.
 * There are 5 kinds of hands so far and each are decoupled from the main and created using this factory.
 * @author nazalkassm
 * @see ArrayList
 * @see Collections
 * @see Random
 * @version 1.00, 4 Dec 2018
 */
public class RackBuilder
{
	public RackBuilder(){}
	

	Rack no_meld_rack = new Rack();
	Rack one_meld_rack = new Rack();
	Rack two_meld_rack = new Rack();
	Rack one_incomplete_meld_rack = new Rack();
	Rack two_incomplete_meld_rack = new Rack();
	  
		/**
		 * This method is responsible for knowing which hand you want to create and writes the hand to a file.
		 * @since version 1.2
		 * @param handType takes a string of the hand type or name you want to create, it is not case sensitive.
		 * @return returns a hand of a specific type, these types are defined in other classes.
		 */
	   public Rack createHand(String handType)
	   {  
		 
	      if(handType.equalsIgnoreCase("No Melds"))
	      {
	    	  return no_meld_rack;
	      } 
	      else if(handType.equalsIgnoreCase("One Melds"))
	      {
	    	  return one_meld_rack;
	      } 
	      else if(handType.equalsIgnoreCase("Two Melds"))
	      {
	    	  return two_meld_rack;
	      } 
	      else if(handType.equalsIgnoreCase("One Incomplete Melds"))
	      {
	    	  return one_incomplete_meld_rack;
	      }
	      else if(handType.equalsIgnoreCase("Two Incomplete Melds"))
	      {
	    	  return two_incomplete_meld_rack;
	      }
	      
		return null;

	   }
	   
		private Set<Tile> findDuplicates(List<Tile> listContainingDuplicates) 
		{
			final Set<Tile> setToReturn = new HashSet<Tile>();
			final Set<String> set1 = new HashSet<String>();

			for (Tile yourInt : listContainingDuplicates) {
				if (!set1.add(yourInt.toString())) {
					setToReturn.add(yourInt);
				}
			}
			return setToReturn;
		}
		
		public void runAlgorithm(Table table, List<Player> players)
		{
			  // A List that will store the tiles that can't be removed from the stock.
			  List<Tile> not_in_stock = new ArrayList<>();
			  
			  // Add the tiles on the table
			  for(Meld meld_on_table : table.getAllMelds())
			  {
				  not_in_stock.addAll(meld_on_table.getTiles());
			  }
			  
			  // Add the tiles in players' hand
			  for(Player player : players)
			  {
				  not_in_stock.addAll(player.getPlayerRack().getRackArray());
			  }
			  
			  // A list that will store tiles that can't be used because both tiles are either used in the table or are in a player's rack.
			  List<Tile> cannot_use = new ArrayList<>();
			  
			  // Finding the tiles that can't be played ( duplicates ) in the list
			  cannot_use.addAll(findDuplicates(not_in_stock));
		      
			  // An rack that will store the playerHand.  
			  Rack dummy_rack = new Rack();
			  
			  boolean no_meld_created = false;
			  boolean one_meld_created = false;
			  boolean two_meld_created = false;
			  boolean one_incomplete_meld_rack_created = false;
			  boolean two_incomplete_meld_rack_created = false;
			  
			  
			  
			  while(true)
		      {
				  dummy_rack.getRackArray().clear();
				  Stock dummy_stock = new Stock("false");
				  
				  for (int i = 0 ; i < dummy_stock.getStockArray().size(); i++)
				  {
					  for(Tile tile_to_remove : cannot_use)
					  {
						  if(tile_to_remove.equals(dummy_stock.getStockArray().get(i)))
						  {
							  dummy_stock.getStockArray().remove(i);
						  }
					  }
				  }
				  
				  dummy_rack.setRack(dummy_stock.deal14Tiles());
				  
				  List<Meld> melds = new ArrayList<>(Meld.getMeldsWithTable(dummy_rack.getRackArray()));

				  if(melds.isEmpty() && no_meld_created == false)
				  {
					  no_meld_rack = new Rack();
					  no_meld_rack.setRack(dummy_rack.getRackArray());
					  no_meld_created = true;
				  }
				  else if(melds.size() == 1 && one_meld_created == false)
				  {
					  one_meld_rack = new Rack();
					  one_meld_rack.setRack(dummy_rack.getRackArray());
					  one_meld_created = true;
				  }
				  else if(melds.size() == 2 && two_meld_created == false)
				  {
					  two_meld_rack = new Rack();
					  two_meld_rack.setRack(dummy_rack.getRackArray());
					  two_meld_created = true;
				  }
				  
				  if(no_meld_created == true && one_meld_created == true && two_meld_created == true )
				  {
					  break;
				  }       	 
		      }
		}
}
