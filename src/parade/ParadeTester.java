package parade;

// import parade.*;
import java.util.ArrayList;
import java.util.Scanner;

import parade.enums.Colour;
import parade.exceptions.EndGameException;

/**
 * A class to test the functionality of the Parade game.
 * <p>
 * This class initializes players, a deck, and a parade, and simulates a turn where a player selects 
 * a card, adds it to the parade, and collects any removable cards.
 * </p>
 */

public class ParadeTester {
    /**
     * The main method to test the Parade game mechanics.
     * <p>
     * Initializes a deck, players, and the parade, then simulates a player's turn where they:
     * <ul>
     *     <li>Select a card</li>
     *     <li>Identify removable and collectible cards</li>
     *     <li>Collect applicable cards</li>
     *     <li>Play their selected card</li>
     *     <li>Draw a new card</li>
     * </ul>
     * The method also prints the game state before and after the player's turn.
     * </p>
     *
     * @param args command-line arguments (not used)
     */


    public static void main (String[] args) {
        Deck d = new Deck();
        Parade par = new Parade(d);
        PlayerList playerList = new PlayerList(d);
        Boolean endGame = false;

        int turn = -1;
        // while (d.getSize() > 0) { // changed end-game logic from true to <<<
        // while (true) { // use true so can display error message
        while(playerList.getPlayer(++turn).getHandSize() == 5) { // will keep playing
            // ++turn;
            Player curPlayer = playerList.getPlayer(turn);
            try {
                System.out.println("\n\n||   Turn " + (turn+1) + "   ||   " + curPlayer.getName());
                System.out.println("Parade: " + par.getParade());
    
                // now the player picks one card
                Card pickedCard = curPlayer.chooseCard();
                System.out.println("Player has played: " + pickedCard);

                // play card (officially add it to the parade and remove it from the player's hand)
                par.addCard(curPlayer.playCard(pickedCard)); 

                // collect cards
                ArrayList<Card> toCollect = par.getCollectibleCards(pickedCard);
                curPlayer.collectCard(toCollect, endGame); // will throw end game exception if player has collected all 6 and !endgame
                System.out.println("player should collect: " + toCollect);
                System.out.println("Player's Collection: " + curPlayer.getCollectedCards());    
                
                curPlayer.addCard(d.drawCard(), endGame); // will throw end game exception if no more in the deck

            } catch (EndGameException e) {
                System.out.println(e.getMessage());
                if (e.getMessage().toLowerCase().contains("deck")) { // because of empty deck
                    System.out.println("Everyone else has one last turn before the game ends.");
                    // curPlayer has 4 cards and nothing to draw
                    // already played their last turn

                } else { // because player has collected all
                    System.out.println("Everyone has one last turn before the game ends.");
                    try {
                        curPlayer.addCard(d.drawCard(), endGame); 
                        // player still has to draw card because rules state that
                        // "she finishes her turn as before"
                    } catch (EndGameException ee){
                        System.out.println(ee.getMessage()); 
                        // this means that on the same turn that the player collects all 6 colours
                        // the deck also fully depletes
                    }
                }

                endGame = true;

            }
        }

        // game ends
        // discard 2 cards from hand
        System.out.printf("\n\nThe game is over.\n" +
                            "Each player will now discard 2 cards.\n" + 
                            "The remaining cards will be added to your collection.\n");
        try {
            for (Player p : playerList.getPlayerList()){
                System.out.println("\n\n||   Please select 2 cards to discard.   ||   " + p.getName());
                // pick 1st card to discard
                Card discard1 = p.chooseCard();
                p.playCard(discard1); // remove card from hand
                // pick 2nd card to discard
                System.out.println();
                Card discard2 = p.chooseCard();
                p.playCard(discard2);
    
                p.collectCard(p.getHand(), false);
            }
        } catch (EndGameException e){
            // just to handle the exception, but should never be thrown
        }
    

        for (Player p: playerList.getPlayerList()) {
            System.out.println(p.getCollectedCards());
        }
        System.out.println();
        

        // calculate scores
        ScoreCalculator scoreCalc = new ScoreCalculator(playerList);
        ArrayList<Player> winners2 = scoreCalc.findWinners();
        int minScore2 = scoreCalc.getMinScore();
        

        System.out.println("\n=== WINNNER(s) ====");
        if (winners2.size() == 1) {
            System.out.println("Player " + (playerList.getPlayerList().indexOf(winners2.get(0)) + 1) + " WINS with " + minScore2 + " points!");
        } else {
            System.out.print("It's a TIE between Players "); 
            for (Player p : winners2) {
                System.out.print(playerList.getPlayerList().indexOf(p) + 1 + " ");
            }
            System.out.println("with " + minScore2 + " points!");
        }

        System.out.println("=== ALL SCORES ====");
        scoreCalc.printLosers();
    }
}