both console and GUI is supported
to start a console game: run Game.java in the main folder
to start a GUI game: run Rummy.java

known bugs with the game:
  The following are not errors with GUI but the backend (it shows up on the console as well):
    - there are instances where there are more jokers than allotted amount (greater than 2)
    - instances where melds get added to the table, and when next player's turn comes up, the table gets emptied out
    - joker gets added to an invalid meld sometimes (cards are not proper, or there are less than 3 cards)


title screen: select the amount of players from the drop down menu
- select with strategies to use for each player selected
- you have the option of game rigging by clicking the button beside strategy option and selecting its initial 14 cards
- "choose" button is file input, it starts the game with a preset rack for each player
- "rig each draw" checkbox makes it so that after every turn (assuming player skips their turn), you can select which card they draw from the stock
regardless of whether they're human or AI
- checking "testing mode" allows you to view every player's racks (regardless of whether human or AI), and it also shows everything in the console as well
- play button will start the game with the options you have selected (without "test mode" you can only see the human players' cards)
