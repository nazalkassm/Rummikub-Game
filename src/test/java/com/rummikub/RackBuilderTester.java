package com.rummikub;
 import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
 public class RackBuilderTester 
{
	public static void main(String[] args) throws Exception
	{
	
	Stock stock = new Stock(104,"false");
	Table table = new Table(stock);
	List<Player> players = new ArrayList<>();
	players.add(new Player("p0",new Strategy1()));
	players.add(new Player("p1",new Strategy1()));
	players.add(new Player("p2",new Strategy1()));
	players.add(new Player("p3",new Strategy1()));
	
    Rack player1_rack = new Rack();
    players.get(0).setPlayerRack(player1_rack);
//	Rack player2_rack = new Rack();
//	players.get(1).setPlayerRack(player1_rack);
//	Rack player3_rack = new Rack();
//	players.get(2).setPlayerRack(player1_rack);
	
	
	Print.print("First Test Scenario where no player plays");
	
	Rack no_meld_rack = new Rack();
	Rack one_meld_rack = new Rack();
	Rack two_meld_rack = new Rack();
	
	
	RackBuilder factory = new RackBuilder();
	factory.runAlgorithm(table, players);
	no_meld_rack = factory.createHand("no melds");
	one_meld_rack = factory.createHand("one melds");
	two_meld_rack = factory.createHand("two melds");
	
	no_meld_rack.sortRack();
	one_meld_rack.sortRack();
	two_meld_rack.sortRack();
	
	Print.printRacktoUser(no_meld_rack, true);
	Print.printRacktoUser(one_meld_rack, true);
	Print.printRacktoUser(two_meld_rack, true);
	
	Print.printMeldtoUser(Meld.getMeldsWithTable(no_meld_rack.getRackArray()), Collections.emptyList(), true);
	Print.printMeldtoUser(Meld.getMeldsWithTable(one_meld_rack.getRackArray()), Collections.emptyList(), true);
	Print.printMeldtoUser(Meld.getMeldsWithTable(two_meld_rack.getRackArray()), Collections.emptyList(), true);
 	////---------------------------------------------------------------------------------------------------------------------------------------------//////////////
	
	Print.print("Second Test Scenario where the table has tiles and a player has cards");
	
	List<Meld> changed_melds = new ArrayList<>();
	
	table.addPlayers(players.get(0));
	
	table.initPlayersTurn();
	
	List<Tile> rack_tiles = new ArrayList<>();
 	rack_tiles = players.get(0).getPlayerRack().getRackArray();
	
	stock.getStockArray().addAll(rack_tiles);
	
	players.get(0).rack.getRackArray().clear();
	
	players.get(0).setPlayerRack(one_meld_rack);
	
	List<Meld> melds_played = new ArrayList<>();
	
	//We force the player to play here not really playing just to show the functionality.
	
	melds_played = Meld.getMeldsWithTable(one_meld_rack.getRackArray());
	
	changed_melds = new ArrayList<>(Table.getDiffMelds(table.getAllMelds(), melds_played));
	Print.print("\nTable is: ");
	
	Print.printMeldtoUser(melds_played, changed_melds, true);
	
	table.updateMeldsOnTable(melds_played);
	
	
	///// Using the builder again, but this time with tiles in one of the player's rack and on the table. Proves it takes into account the table and players' racks.
	
	factory.runAlgorithm(table, players);
	
	no_meld_rack.sortRack();
	one_meld_rack.sortRack();
	two_meld_rack.sortRack();
	
	no_meld_rack = factory.createHand("no melds");
	one_meld_rack = factory.createHand("one melds");
	two_meld_rack = factory.createHand("two melds");
	
	
	Print.printRacktoUser(no_meld_rack, true);
	Print.printRacktoUser(one_meld_rack, true);
	Print.printRacktoUser(two_meld_rack, true);
	
	Print.printMeldtoUser(Meld.getMeldsWithTable(no_meld_rack.getRackArray()), Collections.emptyList(), true);
	Print.printMeldtoUser(Meld.getMeldsWithTable(one_meld_rack.getRackArray()), Collections.emptyList(), true);
	Print.printMeldtoUser(Meld.getMeldsWithTable(two_meld_rack.getRackArray()), Collections.emptyList(), true);
 	}
}