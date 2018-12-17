package com.rummikub;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Meld {
	public enum MeldType {
		SET, RUN, INVALID
	}

	private MeldType meldType = MeldType.INVALID;
	private List<Tile> tiles = new ArrayList<Tile>();

	public Meld(Tile... newTiles) {
		for (Tile tile : newTiles) {
			this.tiles.add(tile);
		}
		this.meldType = checkMeldType(this.tiles);
	}

	public List<Tile> getMeld() {
		return tiles;
	}

	public void addTile(Tile tile) {
		tiles.add(tile);
	}

	public List<Tile> getTiles() {
		return tiles;
	}

	/*
	 * finds the sum of the meld
	 */
	public int sumMeld() {
		int sum = 0;
		for (int i = 0; i < this.getMeld().size(); i++) {
			sum += this.getMeld().get(i).getValue();
		}
		return sum;
	}

	/*
	 * takes an array of melds adds sum of each meld into a list finds the max of
	 * that list and returns the index where that max element is because sum of each
	 * meld will be stored in the same index as its corresponding meld in the list
	 * of melds
	 */
	public static int getMaxIndex(List<Meld> list) {
		List<Integer> sums = new ArrayList<Integer>();
		for (Meld m : list) {
			sums.add(m.sumMeld());
		}

		Integer max = sums.stream().mapToInt(v -> v).max().orElseThrow(NoSuchElementException::new);

		return sums.indexOf(max);
	}

	public static List<Meld> getMelds(List<Tile> tileList) {
		List<Meld> meldList = new ArrayList<Meld>();
//		List<Tile> originalList = new ArrayList<Tile>(tileList);
//		
//		Iterator<Tile> iterator = tileList.iterator();
//		
//		while (iterator.hasNext())
//		{
//			Tile tile = iterator.next();
//			if(tile.isJoker())
//			{
//				tileList.addAll(tile.getPossibleTiles());
//			}
//		}

		meldList.addAll(getRunMelds(tileList));
		meldList.addAll(getSetMelds(tileList));

		return meldList;
	}

	public static List<Meld> getMeldsWithTable(List<Tile> tileList) {
		new ArrayList<Meld>();
		// To hold all the tiles that were on the table
		List<Tile> tilesOnTable = new ArrayList<Tile>();
		// For each of the tiles in tile list, add only those with the played on table
		// boolean true to the tilesOnTable list
		for (Tile t : tileList) {
			if (t.getPlayedOnTable()) {
				tilesOnTable.add(t);
			}
		}

		// To hold all the possible meld combinations that we could play
		List<List<Meld>> combinationsOfMeld = new ArrayList<List<Meld>>();

		// Get all possible melds with this tileList
		List<Meld> possibleMelds = new ArrayList<>(Meld.getMelds(tileList));

		for (int i = 0; i < possibleMelds.size(); i++) {
			List<Meld> secondLevelArrayList = new ArrayList<Meld>();
			combinationsOfMeld.add(secondLevelArrayList);
		}

		int i = 0;
		// For each these tiles
		for (Meld inititalMeldToPlay : possibleMelds) {
			Rack tempRack = new Rack();
			tempRack.setRack(tileList);

			Meld currMeld = inititalMeldToPlay;
			List<Meld> currentPossibleMelds = new ArrayList<Meld>();
			do {
				// Get all possible melds with this tileList

				// now add Meld with max sum to return melds
				combinationsOfMeld.get(i).add(currMeld);
				// pop the tiles with were added to return melds
				tempRack.removeTiles(currMeld);
				currentPossibleMelds = new ArrayList<>(tempRack.getMelds());

				// update possible melds to get new list of melds
				if (currentPossibleMelds.size() > 0) {
					currMeld = currentPossibleMelds.get(Meld.getMaxIndex(currentPossibleMelds));
				} else {
					break;
				}
			} while (true);
			i++;
		}

		int combinationToPlay = -1;
		int countPlayed = 1;
		int j = 0;
		for (List<Meld> melds : combinationsOfMeld) {

			List<Tile> tiles = new ArrayList<>();

			for (Meld m : melds) {
				tiles.addAll(m.getTiles());
			}

			if (tiles.containsAll(tilesOnTable)) {

				if (tiles.size() > countPlayed) {
					combinationToPlay = j;
					countPlayed = tiles.size();
				}
			}
			j++;
		}

		if (combinationToPlay == -1) {
			return Collections.emptyList();
		}

		return combinationsOfMeld.get(combinationToPlay);
	}

	public static List<Meld> getRunMelds(List<Tile> tileList) {
		List<Tile> tilesToCheck = new ArrayList<Tile>(
		    tileList.stream().filter(p -> !(p instanceof Joker)).collect(Collectors.toList()));
		List<Tile> playerTiles = new ArrayList<Tile>(
		    tileList.stream().filter(p -> !p.getPlayedOnTable()).collect(Collectors.toList()));
		List<Joker> jokers = new ArrayList<Joker>(
		    tileList.stream().filter(p -> (p instanceof Joker)).map(p -> (Joker) p).collect(Collectors.toList()));
		List<List<Tile>> possibleTiles = new ArrayList<List<Tile>>();

		for (int i = 0; i < jokers.size(); ++i) {
			List<Tile> secondLevelArrayList = new ArrayList<Tile>(jokers.get(i).getPossibleTiles());
			possibleTiles.add(secondLevelArrayList);
		}
		for (Tile t : playerTiles) {
			for (Joker j : jokers) {
				if (new ArrayList<String>(j.getPossibleTiles().stream().map(p -> p.toString()).collect(Collectors.toList())).contains(t.toString())) {
					j.setPossibleTilesToAll();
				}
			}
		}
		int jokerCount = jokers.size();
		List<Meld> meldList = new ArrayList<Meld>();
		// Init array list of 2,all the sorted things in it
		List<List<Tile>> collectedTings = new ArrayList<List<Tile>>();
		for (int i = 0; i < 2; ++i) {
			List<Tile> secondLevelArrayList = new ArrayList<Tile>();
			collectedTings.add(secondLevelArrayList);
		}

		for (int i = 0; i < tilesToCheck.size(); i++) {
			Tile currTile = tilesToCheck.get(i);
			boolean containTile = false;
			// Check if the current tile's color is already in the first list
			for (Tile tile : collectedTings.get(0)) {
				if (tile.equals(currTile)) {
					containTile = true;
				}
			}
			if (containTile) {
				collectedTings.get(1).add(currTile);
			} else {
				collectedTings.get(0).add(currTile);
			}
		}

		Collections.sort(collectedTings.get(0));
		Collections.sort(collectedTings.get(1));


		int jokerUsed = 0;
		int jokerUsedIndex = -1;
		for (List<Tile> tiles : collectedTings) {
			if (tiles.isEmpty())
				continue;
			Meld meld = new Meld();
			meld.addTile(tiles.get(0));

			for (int i = 0; i < tiles.size() - 1; i++) {
				Tile currTile = tiles.get(i);
				Tile nextTile = tiles.get(i + 1);

				if (i + 1 < tiles.size() && (currTile.isRunOn(nextTile))) {
					meld.addTile(nextTile);
				} else {
					if (jokerUsed < jokerCount && currTile.getColour() == nextTile.getColour()) {
						int diff = nextTile.getRank().getValue() - currTile.getRank().getValue();
						// IF diff is 3 and joker count is 2 and jokers are run on each other
						if (diff == 3 && jokerCount == 2 && jokers.get(0).isRunOn(jokers.get(1))) {
							// Can first joker play on current one after
							boolean firstJokerOnCurrent = (jokers.get(0).getPossibleTiles().isEmpty()) ? true
							    : (currTile.getRank().getValue() == (jokers.get(0).getPossibleTiles().get(0).getRank().getValue() - 1)
							        && currTile.getColour() == (jokers.get(0).getPossibleTiles().get(0).getColour())) ? true : false;
							boolean secondJokerOnCurrent = (jokers.get(1).getPossibleTiles().isEmpty()) ? true
							    : (currTile.getRank().getValue() == (jokers.get(1).getPossibleTiles().get(0).getRank().getValue() - 1)
							        && currTile.getColour() == (jokers.get(1).getPossibleTiles().get(0).getColour())) ? true : false;
							// IF both can play on first card
							if (firstJokerOnCurrent && secondJokerOnCurrent) {
								// Get the one with lowest possible values size
								int indexOfLowest = -1;
								int other = -1;
								if (jokers.get(0).getPossibleTiles().size() <= jokers.get(1).getPossibleTiles().size()) {
									indexOfLowest = 0;
									other = 1;
								} else {
									indexOfLowest = 1;
									other = 0;
								}
								if (jokers.get(other).getPossibleTiles().isEmpty()
								    || jokers.get(other).getPossibleTiles().get(0).getValue() + 1 == nextTile.getValue()) {
									// Set it up
									meld.addTile(jokers.get(indexOfLowest));
									meld.addTile(jokers.get(other));
									meld.addTile(nextTile);
									jokerUsed = 2;
								}
							}
						} else if (diff == 2 && jokerCount > 0) {
							boolean firstJokerOnCurrent = (jokers.size() == 0) ? false
							    : (jokers.get(0).getPossibleTiles().isEmpty()) ? true
							        : (currTile.getRank()
							            .getValue() == (jokers.get(0).getPossibleTiles().get(0).getRank().getValue() - 1)
							            && currTile.getColour() == (jokers.get(0).getPossibleTiles().get(0).getColour())
							            && jokers.get(0).getValue() + 1 == nextTile.getValue()) ? true : false;
							boolean secondJokerOnCurrent = (jokers.size() == 1) ? false
							    : (jokers.get(1).getPossibleTiles().isEmpty()) ? true
							        : (currTile.getRank()
							            .getValue() == (jokers.get(1).getPossibleTiles().get(0).getRank().getValue() - 1)
							            && currTile.getColour() == (jokers.get(1).getPossibleTiles().get(0).getColour())
							            && jokers.get(1).getValue() + 1 == nextTile.getValue()) ? true : false;
							// IF both can play on first card
							if (firstJokerOnCurrent || secondJokerOnCurrent) {
								// Get the one with lowest possible values size
								int indexOfLowest = -1;
								if (firstJokerOnCurrent && !secondJokerOnCurrent)
									indexOfLowest = 0;
								else if (!firstJokerOnCurrent && secondJokerOnCurrent)
									indexOfLowest = 1;
								else {

									int other = -1;
									if (jokers.get(0).getPossibleTiles().size() <= jokers.get(1).getPossibleTiles().size()) {
										indexOfLowest = 0;
										other = 1;
									} else {
										indexOfLowest = 1;
										other = 0;

									}
									// If we can't play because it's already played switch over to the other one
									if (indexOfLowest == jokerUsedIndex) {
										indexOfLowest = other;
									}
								}

								if (indexOfLowest != jokerUsedIndex) {
									// Set it up
									meld.addTile(jokers.get(indexOfLowest));
									meld.addTile(nextTile);
									jokerUsed++;
								}
							}
						} else {
							jokerUsed = 0;
							jokerUsedIndex = -1;
							meld = new Meld();
							meld.addTile(nextTile);
							continue;
							
						}
					} else {
						meld = new Meld();
						meld.addTile(nextTile);
						continue;
					}
				}

				meldList.add(new Meld(meld.tiles.toArray(new Tile[0])));
			}

		}
		List<Meld> meldListToReturn = new ArrayList<Meld>();
		for (Meld m : meldList) {
			Tile currTile = m.getTiles().get(0);
			Tile lastTile = m.getTiles().get(m.getTiles().size() - 1);
			// Loop over all jokers
			for (Joker j : jokers) {
				// If joker is already presnt in meld ignore
				if (m.getTiles().contains(j)) {
					continue;
				}

				boolean jokerOnFirst = (jokers.size() == 0) ? false
				    : (j.getPossibleTiles().isEmpty()) ? true
				        : (currTile.getRank().getValue() == (j.getValue() + 1)
				            && currTile.getColour() == (j.getPossibleTiles().get(0).getColour())
				            && currTile.getValue() > 1) ? true : false;
				boolean jokerOnLast = (jokers.size() == 0) ? false
				    : (j.getPossibleTiles().isEmpty()) ? true
				        : (lastTile.getRank().getValue() == (j.getPossibleTiles().get(0).getRank().getValue() - 1)
				            && lastTile.getColour() == (j.getPossibleTiles().get(0).getColour())
				            && lastTile.getValue() != 13) ? true : false;
				if (jokerOnFirst && jokerOnLast) {

					Meld m2 = new Meld(m.tiles.toArray(new Tile[0]));
					m2.tiles.add(0, j);
					m.addTile(j);
					if (m2.tiles.size() >= 3)
						meldListToReturn.add(m2);
				} else if (jokerOnFirst) {
					m.tiles.add(0, j);
				} else if (jokerOnLast) {
					m.addTile(j);
				}
			}
			if (m.getTiles().size() >= 3)
				meldListToReturn.add(new Meld(m.tiles.toArray(new Tile[0])));
		}
		for (int i = 0; i < jokers.size(); ++i) {
			jokers.get(i).setPossibleTiles(possibleTiles.get(0));
		}
		return meldListToReturn;
	}
	

	/*
	 * finding set-type melds in the rack first gets rid of duplicate objects (so if
	 * there are two R5's) then compares them to other colours and returns a list of
	 * same values
	 */
	public static List<Meld> getSetMelds(List<Tile> tileList) {

		List<Tile> tilesToCheck = new ArrayList<Tile>(
		    tileList.stream().filter(p -> !(p instanceof Joker)).collect(Collectors.toList()));
		List<Tile> playerTiles = new ArrayList<Tile>(
		    tileList.stream().filter(p -> !p.getPlayedOnTable()).collect(Collectors.toList()));
		List<Joker> jokers = new ArrayList<Joker>(
		    tileList.stream().filter(p -> (p instanceof Joker)).map(p -> (Joker) p).collect(Collectors.toList()));

		List<List<Tile>> possibleTiles = new ArrayList<List<Tile>>();

		for (int i = 0; i < jokers.size(); ++i) {
			List<Tile> secondLevelArrayList = new ArrayList<Tile>(jokers.get(i).getPossibleTiles());
			possibleTiles.add(secondLevelArrayList);
		}
		for (Tile t : playerTiles) {
			for (Joker j : jokers) {
				if (new ArrayList<String>(j.getPossibleTiles().stream().map(p -> p.toString()).collect(Collectors.toList()))
				    .contains(t.toString())) {
					j.setPossibleTilesToAll();
				}
			}
		}
		// Initialize array list of 13, with 2 lists each of tiles
		List<List<List<Tile>>> collectedSets = new ArrayList<List<List<Tile>>>();


		for (int i = 0; i < 13; ++i) {
			List<List<Tile>> secondLevelArrayList = new ArrayList<List<Tile>>();
			collectedSets.add(secondLevelArrayList);
			for (int j = 0; j < 2; ++j) {
				secondLevelArrayList.add(new ArrayList<Tile>());
			}
		}
		// Loop over all the tiles

		for (int i = 0; i < tilesToCheck.size(); i++) {
			Tile currTile = tilesToCheck.get(i);

			// We use the value -1 as the respective index in the collectedSets
			// Ex: If currTile is O4, then we would use collectedSets[3] to store all 4's
			int index = currTile.getValue() - 1;
			boolean containColor = false;
			// Check if the current tile's color is already in the first list
			for (Tile tile : collectedSets.get(index).get(0)) {
				if (tile.isSameColour(currTile)) {
					containColor = true;
				}
			}

			// If the color is in the first list then add it to the second list
			if (containColor) {
				collectedSets.get(index).get(1).add(currTile);
			}
			// Otherwise we add it to the first list
			else {
				collectedSets.get(index).get(0).add(currTile);
			}
		}

		List<Meld> setList = new ArrayList<Meld>();
		Meld meld = null;
		// Loop over all the collected sets and add all of size => 3 to setList
		for (int i = 0; i < 13; ++i) {
			for (int j = 0; j < 2; ++j) {

				// Loop over all jokers
				for (Joker joker : jokers) {
					List<Tile> possibleTilesOfJoker = joker.getPossibleTiles();
					if (collectedSets.get(i).get(j).size() < 4) {
						// If the joker is empty then add it to current
						if (possibleTilesOfJoker.isEmpty())
							collectedSets.get(i).get(j).add(joker);
						else {
							// Otherwise loop over all possible values and see if they form a set
							boolean inserted = false;

							for (Tile tileToCheck : possibleTilesOfJoker) {
								List<Tile> tempTiles = new ArrayList<Tile>(collectedSets.get(i).get(j));
								tempTiles.add(tileToCheck);
								if (Meld.checkMeldType(tempTiles) == Meld.MeldType.SET && !inserted) {
									collectedSets.get(i).get(j).add(joker);
									inserted = true;
								}
							}
						}
					}
				}

				if (collectedSets.get(i).get(j).size() >= 3 && collectedSets.get(i).get(j).size() <= 4) {
					meld = new Meld();
					meld.tiles = collectedSets.get(i).get(j);
					setList.add(meld);
				}
			}
		}


		for (int i = 0; i < jokers.size(); ++i) {
			jokers.get(i).setPossibleTiles(possibleTiles.get(0));
		}
		return setList;
	}

	static public MeldType checkMeldType(List<Tile> newTiles) {
		MeldType newMeldType = MeldType.INVALID;
		List<Tile> tilesToCheck = new ArrayList<Tile>(
		    newTiles.stream().filter(p -> !(p instanceof Joker)).collect(Collectors.toList()));
		//
		if (tilesToCheck.size() == 1) {
			return MeldType.RUN;
		}
		Map<Colours, List<Tile>> tilesByColour = tilesToCheck.stream().collect(Collectors.groupingBy(Tile::getColour));

		// a meld is a set iff:
		// - There is only one tile per colour
		// - Each tile of each colour is equal

		if (tilesByColour.keySet().size() == tilesToCheck.size() && checkEqualRanks(tilesToCheck)) {
			newMeldType = MeldType.SET;
		}
		// a meld is a run iff:
		// - the tiles are of only one colour
		// - the tiles are in a sequence
		else if (checkSequence(newTiles)) {
			newMeldType = MeldType.RUN;
		}

		return newMeldType;
	}

	// Uses Java streams to check if each tile has the same rank.
	static protected boolean checkEqualRanks(List<Tile> tiles) {
		return tiles.stream().filter(tile -> tile.getRank().equals(tiles.get(0).getRank())).count() == tiles.size();
	}

	// sorts tiles by colour and then rank, and uses Tile::isRunOn() to check if the
	// next tile's value is 1 greater of 1 less than.
	// Tile::isRunOn() returns false if the colour are not equal.
	static protected boolean checkSequence(List<Tile> tiles) {
		boolean check = true;

		List<Tile> tempTiles = new ArrayList<Tile>(tiles);
		Collections.sort(tempTiles);
		int incorrect = 0;
		for (int i = 0; i < tempTiles.size() - 1; i++) {
			if (!(tempTiles.get(i + 1) instanceof Joker)) {
				if (!tempTiles.get(i).isRunOn(tempTiles.get(i + 1))) {
					incorrect++;
				}
			}
		}
		Predicate<Tile> predicate = s -> s instanceof Joker;

		if (tempTiles.stream().filter(predicate).count() < incorrect) {
			check = false;
		}
		return check;
	}

	/**
	 * @return the meldType
	 */
	public MeldType getMeldType() {
		return meldType;
	}

	@Override
	public String toString() {
		String a = "";
		for (Tile s : this.tiles) {
			a += s.toString() + " ";
		}
		return a;
	}

}
