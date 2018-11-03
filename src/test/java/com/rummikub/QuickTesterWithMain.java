package com.rummikub;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.pmw.tinylog.Logger;


//This class is to test algorithms,data structures and all other ideas to be used in the project quickly so that we can confirm something would work.
//We might extend this class to become a SuiteClass of Junit which would run first display some important information about testing and then run all Junit tests.

public class QuickTesterWithMain 
{
	public static void main(String[] args) 
	{
		Tile tile1 = new Tile(Colours.BLUE, Ranks.ONE);
		Stock stock = new Stock();
		Player player1 = new Player("naz");
		//player1.fillRack(stock);
		Logger.info(stock.getLength());
		Logger.info(stock.getStockArray());
		player1.fillRack(stock);
		
		Print.printRacktoUser(player1.getPlayerRack());
		player1.getPlayerRack().addTile(tile1);
		Print.println("\r\n\r\n");
		Print.printRacktoUser(player1.getPlayerRack());
		//Print.printRacktoUser(player1.getPlayerRack());
		
		/*TableList tl = new TableList(3, "ID", "String 1", "String 2").sortBy(0).withUnicode(true);
		// from a list
		//yourListOrWhatever.forEach(element -> tl.addRow(element.getID(), element.getS1(), element.getS2()));
		// or manually
		tl.addRow("Hi", "I am", "Bob");

		tl.print();
		TableList t2 = new TableList(2, "human","0").sortBy(0).withUnicode(true);
		t2.addRow("Rack","1");
		t2.print();
		
		player1.fillRack(stock);
		player1.getPlayerRack().sortRack();
		Print.print(player1.getPlayerRack().toString());
		Print.print(player1.getPlayerRack().getMelds().toString());*/
	}
	
	public static void rigGame(String filePath) throws IOException {
		Game game = new Game();
		FileParser.parse(filePath);
		
		if (!FileParser.inputError) {
			game.stock = FileParser.stock;
			game.table = new Table(game.stock);
			
			if (FileParser.playerCommands.size() > 0) {
				Prompt.init(FileParser.playerCommands);
			}
			game.start();
		}
	}
}
