package com.rummikub;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Print 
{
	/**
	 * Purpose: Prints as many sentences you enter on the same line.
	 *  Then goes to next line to prepare for any other prints.
	 * @param message
	 * @since version 2
	 */
	public static void print(String... message) 
	{
		for (String s : message) 
		{
			System.out.print(s);
		}
		System.out.println();
	}
	
	/**
	 * Purpose: Prints as many sentences you enter on different lines.
	 * @param message
	 * @since version 2
	 */
	public static void println(String... message) 
	{
		for(String s : message)
		{
			System.out.println(s);
		}
	}

	public void printIntroduction() 
	{
		println("Hello","Welcome to TileRummy");
	}

	public void printEnding(Player winner) throws IOException
	{
		int counter = 0;
		System.out.print("Game ending\nThe winner is:");
		while(counter < 3)
		{
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.print(" ...");
			counter ++;
		}
		print(" "+ winner.getName());
	}
	
	public static void printRacktoUser(Rack rack, boolean print_Rack)
	{
		if(print_Rack)
		{
			if(!(rack.getSize() == 0)) 
			{
				int rackSize = rack.getSize();
				List<String> columnHeaders = new ArrayList<String>();
				for (int i = 1; i < rackSize + 1; i++) {
					columnHeaders.add(Integer.toString(i));
				}
				columnHeaders.add(0, "Tile Number");
				
				TableList rackTable = new TableList(rackSize + 1, columnHeaders.stream().toArray(String[]::new)).sortBy(0).withUnicode(true);
				
				List<String> rackStringList = rack.getRackArray().stream()
																	.map(Object::toString)
																	.collect(Collectors.toList());
				rackStringList.add(0, "Rack");
				String[] rackStringArray = rackStringList.stream().toArray(String[]::new);
				
				rackTable.addRow(rackStringArray);
				rackTable.print();
			}
			else
			{
				print("There are no tiles in the rack");
			}
		}
	}
	
	public static void printMeldtoUser(List<Meld> melds, boolean print_Meld)
	{
		if(print_Meld)
		{
			if (melds.isEmpty()) 
			{
				print("There are no melds\n");
			}
			else 
			{
				List<String> meldStringList = melds.stream()
		                .map(Object::toString)
		               .collect(Collectors.toList());
				
				TableList meldTable = new TableList(2, "Meld Number", "Meld").sortBy(0).withUnicode(true);
				
				int meldNumber = 1;
				for(String meld : meldStringList) {
				    meldTable.addRow("Meld " + Integer.toString(meldNumber), meld);
				    meldNumber++;
				}
				meldTable.print();
			}
		}
	}
	
	public void printGameTable(Table table)
	{
		List <Meld> boardMelds = table.getAllMelds();
			
		if (boardMelds.isEmpty()) 
		{
			println("\n_____GAME TABLE_____","There are no melds on the table\n");
		}
		else 
		{
			List<String> meldStringList = boardMelds.stream()
		               .map(Object::toString)
		              .collect(Collectors.toList());
				
			TableList meldTable = new TableList(2, "Meld Number", "Table Melds").sortBy(0).withUnicode(true);
				
			int meldNumber = 1;
			for(String meld : meldStringList) 
			{
				    meldTable.addRow("Meld " + Integer.toString(meldNumber), meld);
				meldNumber++;
			}
			meldTable.print();
		}
	}

	public static void printMessageWithDelay(String message) 
	{
		int counter = 0;
		System.out.print(message);
		while(counter < 5)
		{
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.print(" ...");
			counter ++;
		}
		System.out.println();
	}

	public static void printMeldtoUser(List<Meld> meldsPlayed) 
	{
		printMeldtoUser(meldsPlayed,true);
	}	
}
