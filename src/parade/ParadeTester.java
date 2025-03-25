package parade;

// import parade.*;
import java.util.ArrayList;
import parade.enums.Colour;
import parade.exceptions.EndGameException;

/**
 * A class to test the Parade game.
 * <p>
 * This class initializes a deck, players, and the parade, then simulates a turn-based game.
 * </p>
 *
 * @author G3T7
 * @version 1.0
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
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        Deck d = new Deck();
        Parade par = new Parade(d);
        PlayerList playerList = new PlayerList(d);
        boolean endGame = false;

        int turn = -1;

        /**
         * Simulates the game loop.
         * <p>
         * Each player takes turns playing a card until the conditions for ending the game are met.
         * Players will:
         * <ul>
         *     <li>Pick a card from their hand</li>
         *     <li>Play the selected card</li>
         *     <li>Collect applicable cards</li>
         *     <li>Draw a new card</li>
         * </ul>
         * If the deck runs out or a player collects all six colors, the game enters its final round.
         * </p>
         */
        while (playerList.getPlayer(++turn).getHandSize() == 5) { 
            Player curPlayer = playerList.getPlayer(turn);
            try {
                System.out.println("\n\n||   Turn " + (turn + 1) + "   ||   " + curPlayer.getName());
                System.out.println("Parade: " + par.getParade());

                if (curPlayer instanceof BotPlayer){
                    System.out.println(curPlayer.getName() + " is selecting their cards...");
                    Thread.sleep(2000);
                    System.out.println("Selection complete.");
                }

                // Player picks a card
                Card pickedCard = curPlayer.chooseCard();
                System.out.println("Player has played: " + pickedCard);

                // Collect cards based on game rules
                ArrayList<Card> toCollect = par.getCollectibleCards(pickedCard);
                curPlayer.collectCard(toCollect, endGame); 
                System.out.println("Player should collect: " + toCollect);
                System.out.println("Player's Collection: " + curPlayer.getCollectedCards());

                // Play card (add it to the parade and remove from the player's hand)
                par.addCard(curPlayer.playCard(pickedCard));

                // Player draws a new card (throws exception if deck is empty)
                curPlayer.addCard(d.drawCard(), endGame);

            } catch (EndGameException e) {
                /**
                 * Handles endgame conditions.
                 * <p>
                 * If the deck runs out or a player collects all six colors, the game enters its final phase,
                 * where each remaining player gets one last turn.
                 * </p>
                 */
                System.out.println(e.getMessage());
                if (e.getMessage().toLowerCase().contains("deck")) {
                    // Deck is empty
                    System.out.println("Everyone else has one last turn before the game ends.");
                } else {
                    // Player has collected all six colors
                    System.out.println("Everyone has one last turn before the game ends.");
                    try {
                        curPlayer.addCard(d.drawCard(), endGame); 
                    } catch (EndGameException ee) {
                        System.out.println(ee.getMessage()); 
                    }
                }
                endGame = true;
            } catch (InterruptedException e){
                
            }
        }

        // After main gameplay, each player discards 2 cards from hand
        System.out.printf("\n\nThe game is over.\n" +
                            "Each player will now discard 2 cards.\n" + 
                            "The remaining cards will be added to your collection.\n");
        try {
            for (Player p : playerList.getPlayerList()){
                // pause thread during bot's term
                System.out.println("\n\n||   Please select 2 cards to discard.   ||   " + p.getName());
                if (p instanceof BotPlayer){
                    System.out.println(p.getName() + " is selecting their cards...");
                    Thread.sleep(2000);
                    System.out.print("Selection complete.");
                }

                // pick 1st card to discard
                Card discard1 = p.chooseCard();
                p.playCard(discard1); // remove card from hand
                System.out.println();

                // pick 2nd card to discard
                Card discard2 = p.chooseCard();
                p.playCard(discard2);
                
                // add remaining hand cards to collection
                p.collectCard(p.getHand(), false);
            }
        } catch (EndGameException e){
            // just to handle the exception, but should never be thrown
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        System.out.println();
        System.out.println();
    
        // Display final hand and collection for each player
        for (Player p: playerList.getPlayerList()) {
            System.out.println(
                p.getName() + 
                "\t Hand: " + p.getHand()
            );
            System.out.println("\t Collection: ");
            for (Colour c : p.getCollectedCards().keySet()) {
                System.out.print("\t\t");
                for (Card card : p.getCollectedCardsWithColour(c)){
                    System.out.print(card + " ");
                }
                System.out.println();
            }
            System.out.println();
        }        

        // Calculate scores
        ScoreCalculator scoreCalc = new ScoreCalculator(playerList);
        ArrayList<Player> winners = scoreCalc.findWinners();
        int minScore = scoreCalc.getMinScore();

        System.out.println("\n=== WINNNER(s) ====");
        if (winners.size() == 1) {

            System.out.println(winners.get(0).getName() + " WINS with " + minScore + " points!");
        } else {
            System.out.print("It's a TIE between Players "); 
            for (Player p : winners) {
                System.out.print(p.getName());

            }
            System.out.println("with " + minScore + " points!");
        }

        /**
         * Displays the final scores of all players.
         */
        System.out.println("=== ALL SCORES ====");
        scoreCalc.printLosers();
    }
}