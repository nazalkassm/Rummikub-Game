package com.rummikub;

import java.util.List;
import java.util.stream.Collectors;

public class Print 
{
	private static TableList rackTable = new TableList(15, "Tile Number", "1", "2","3","4","5","6","7","8","9","10","11","12","13","14").sortBy(0).withUnicode(true);
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
		List<String> rackStringList = rack.getRackArray().stream()
															.map(Object::toString)
															.collect(Collectors.toList());
		rackStringList.add(0, "Rack");
		String[] rackStringArray = rackStringList.stream().toArray(String[]::new);
		
		rackTable.addRow(rackStringArray);
	    //rackTable.addRow("Rack",rack.getRackArray().get(0).toString(),rack.getRackArray().get(1).toString(),rack.getRackArray().get(2).toString(),rack.getRackArray().get(3).toString(),rack.getRackArray().get(4).toString(),rack.getRackArray().get(5).toString(),rack.getRackArray().get(6).toString(),rack.getRackArray().get(7).toString(),rack.getRackArray().get(8).toString(),rack.getRackArray().get(9).toString(),rack.getRackArray().get(10).toString(),rack.getRackArray().get(11).toString(),rack.getRackArray().get(12).toString(),rack.getRackArray().get(13).toString());
		rackTable.print();
	}
}
