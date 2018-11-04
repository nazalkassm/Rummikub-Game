package com.rummikub;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.pmw.tinylog.Logger;


//This class is to test algorithms,data structures and all other ideas to be used in the project quickly so that we can confirm something would work.
//We might extend this class to become a SuiteClass of Junit which would run first display some important information about testing and then run all Junit tests.

public class QuickTesterWithMain 
{
	public static void main(String[] args) throws IOException 
	{		
		rigGame("src/main/resources/inputFiles/test12.txt");
	}
	
	public static void rigGame(String filePath) throws IOException {
		Game game = new Game();
		game.printRackMeld = true;
		game.waitAferEachTurn = false;
		
		FileParser.parse(filePath);
		
		if (!FileParser.inputError) {
			game.stock = FileParser.stock;
			game.table = new Table(game.stock);
			
			game.start();
		}
	}
}
