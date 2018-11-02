package com.rummikub;

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

	public void printEnding() 
	{
		print("That's all folks!");
	}
	
	public static void printRacktoUser(Rack rack)
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
	
	public static void printMeldtoUser(List<Meld> melds)
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
