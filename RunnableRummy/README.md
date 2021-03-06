*Clone* this repository and import to your IDE to get started.  

PlayableRummy

`PlayableRummy` is an interface for the game logic of a variation of the popular card game "rummy". Together with the unit tests <code>TestRummyCode</code>, an implementation of this interface is meant to be an exercise in object-oriented development. An implementation of this type should provide a constructor with the signature <code>Rummy(String... players)</code> where the elements of <code>players</code> are the names of the players.

The expected behavior of an implementation is outlined in the unit tests that have been provided. The methods of this interface are only concerned with gameplay. There are no methods for dealing with scores. The first player to discard their hand, consistent with the rules, wins the game. The possible  states of the game depend on the current player and the various phases of the play as described in the enum <code>Steps</code>.

Improper uses for this type are intended to be handled with unchecked exceptions as described in <code>RummyException</code>.

The accompanying unit tests assume certain conventions. A <b>non-standard</b> deck of 65 playing cards with 5 suits and 13 ranks is assumed. The 5 suits are Clubs, Diamonds, Hearts, Spades, and Moons. Individual cards are referenced by short 2-3 character strings. The first group of characters indicates rank while the last group indicates the suit. For example, the string <code>"3D"</code> would represent the 3 of Diamonds. The string<code>"10C"</code> represents the 10 of Clubs. The regular expression<code>"(A|2|3|4|5|6|7|8|9|10|J|Q|K)(C|D|H|S|M)"</code> defines valid card descriptions. To give some more examples, <code>"AM"</code> represents the Ace of Moons, <code>"2D"</code> represents the Two of Diamonds,<code>"QH"</code> represents the Queen of Hearts. When forming runs, an Ace can be treated as high or low. For example, <code>AC,2C,3C</code> and<code>JC,QC,KC,AC</code> are both to be understood as valid runs.

The order of the players should be consistent with the order seen in the array returned from <code>getPlayers()</code>. The first element of the array is the name of the first player, second of the second player and so on. Play progresses and cards are dealt in the corresponding order. Play always begins with the first player.

A new instance of this type should be in the WAITING step. The methods<code>rearrange</code> and <code>shuffle</code> are meant to allow the client to put the deck into a certain state before play begins. The method <code>initialDeal</code> deals cards to the players and moves the game to the first turn of the first player in the DRAW step. Play continues until a player discards or melds all of their cards. The game should go to the FINISHED step when that happens.

To have the current player declare rummy, call `declareRummy` during the MELD step.  The game moves to the RUMMY step.  In this step, the player puts down cards as normal and ends with the `finishMeld` method.  An exception should be thrown if the player declared rummy but was not able to put down and discard their hand.  Any cards that were put down are treated as normal melds, and the play proceeds to the discard phase.  

[Description of the standard rules of rummy](https://bicyclecards.com/how-to-play/rummy-rum/).  Note that the game we are implementing is a variation of the standard rules.   


