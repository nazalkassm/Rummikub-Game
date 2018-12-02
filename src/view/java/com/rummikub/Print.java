package com.rummikub;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Print {
	/**
	 * Purpose: Prints as many sentences you enter on the same line. Then goes to
	 * next line to prepare for any other prints.
	 * 
	 * @param message
	 * @since version 2
	 */
	public static void print(String... message) {
		for (String s : message) {
			System.out.print(s);
		}
		System.out.println();
	}

	public static void print(int number) {
		Print.print(Integer.toString(number));
	}

	public static void print(boolean bool) {
		Print.print(Boolean.toString(bool));
	}

	/**
	 * Purpose: Prints as many sentences you enter on different lines.
	 * 
	 * @param message
	 * @since version 2
	 */
	public static void println(String... message) {
		for (String s : message) {
			System.out.println(s);
		}
	}

	public static void println(int number) {
		Print.println(Integer.toString(number));
	}

	public static void println(boolean bool) {
		Print.println(Boolean.toString(bool));
	}

	public void printIntroduction() {
		println("Hello", "Welcome to TileRummy");
	}

	public void printEnding(Player winner, boolean wait) {
		int counter = 0;
		System.out.print("Game ending\nThe winner is:");
		if (wait) {
			while (counter < 3) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.print(" ...");
				counter++;
			}
		} else {
			System.out.print(" ... ... ...");
		}

		if (winner == null) {
			print(" no one!");
		} else {
			print(" " + winner.getName());
		}
	}

	public static void printRacktoUser(Rack rack, boolean print_Rack) {
		if (print_Rack) {
			if (!(rack.getSize() == 0)) {
				int rackSize = rack.getSize();
				List<String> columnHeaders = new ArrayList<String>();
				for (int i = 1; i < rackSize + 1; i++) {
					columnHeaders.add(Integer.toString(i));
				}
				columnHeaders.add(0, "Tile Number");

				TableList rackTable = new TableList(rackSize + 1, columnHeaders.stream().toArray(String[]::new))
				    .withUnicode(true);

				List<String> rackStringList = rack.getRackArray().stream().map(Object::toString).collect(Collectors.toList());
				rackStringList.add(0, "Rack");
				String[] rackStringArray = rackStringList.stream().toArray(String[]::new);

				rackTable.addRow(rackStringArray);
				rackTable.print();
			} else {
				print("There are no tiles in the rack");
			}
		}
	}

	public static void printMeldtoUser(List<Meld> melds, List<Meld> newMelds, boolean print_Meld) {
		if (print_Meld) {
			if (melds.isEmpty()) {
				print("There are no melds\n");
			} else {
				List<String> meldStringList = new ArrayList<String>();
				for (Meld meld : melds) {
					boolean addHighlight = false;
					for (Meld newMeld : newMelds) {
						// If same add "*"
						if (meld == newMeld) {
							addHighlight = true;
						}
					}
					if (addHighlight) {
						meldStringList.add("* " + meld.toString() + "*");
					} else {
						meldStringList.add(meld.toString());
					}
				}
				
				TableList meldTable = new TableList(2, "Meld Number", "Meld").withUnicode(true);

				int meldNumber = 1;
				for (String meld : meldStringList) {
					meldTable.addRow("Meld " + Integer.toString(meldNumber), meld);
					meldNumber++;
				}
				meldTable.print();
			}
		}
	}

	public void printGameTable(Table table) {
		List<Meld> boardMelds = table.getAllMelds();

		print("\n_____GAME TABLE_____");

		if (boardMelds.isEmpty()) {
			println("There are no melds on the table\n");
		} else {
			List<String> meldStringList = boardMelds.stream().map(Object::toString).collect(Collectors.toList());

			TableList meldTable = new TableList(2, "Meld Number", "Table Melds").withUnicode(true);

			int meldNumber = 1;
			for (String meld : meldStringList) {
				meldTable.addRow("Meld " + Integer.toString(meldNumber), meld);
				meldNumber++;
			}
			meldTable.print();
		}
	}

	public static void printMessageWithDelay(String message) {
		int counter = 0;
		System.out.print(message);
		while (counter < 5) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.print(" ...");
			counter++;
		}
		System.out.println();
	}
}
