package com.rummikub;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FileParser {
	public static List<String> playerCommands;
	public static List<Tile> stock;
	public static Boolean inputError = false;
	

	static void parse(String filePath) {
		File file = new File(filePath);
		List<String> fileContents;

		if(file.exists()) {
			List<String> tileList = new ArrayList<String>();
			playerCommands = new ArrayList<String>();

			try(BufferedReader br = new BufferedReader(new FileReader(filePath))) {
				String fileText = "";
				StringBuilder sb = new StringBuilder();
				String line = br.readLine();

				// read the file into a string, and split that string by spaces when added it to a List.
				while (line != null) {
					sb.append(line);
					sb.append(System.lineSeparator());
					line = br.readLine();
				}
				fileText = sb.toString();
				fileContents = Arrays.asList(fileText.split("\\s"));

				stock = new ArrayList<Tile>();
				playerCommands = new ArrayList<String>(); 
				
				for (String element : fileContents) {
					if (isValidTile(element)) {
						stock.add(new Tile(element));
					}
					else if (isInteger(element)) {
						playerCommands.add(element);
					}
					else {
						inputError = true;
					}
				}

				// verify that the stock doesn't have any triplets of cards
				// stock.validate

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				inputError = true;
			}
		}
		else {
			inputError = true;
		}
	}

	public static boolean isInteger(String value) {
	    try {
	        Integer.parseInt(value);
	        return true;
	    } catch (NumberFormatException ex) {
	        return false;
	    }
	}
}
