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
     * Default constructor for ParadeTester.
     */
    public ParadeTester() {
        // No initialization required
    }

    /**
     * The main method to test the Parade game mechanics.
     * <p>
     * Initializes a deck, players, and the parade, then simulates a player's turn
     * where they:
     * <ul>
     * <li>Select a card</li>
     * <li>Identify removable and collectible cards</li>
     * <li>Collect applicable cards</li>
     * <li>Play their selected card</li>
     * <li>Draw a new card</li>
     * </ul>
     * The method also prints the game state before and after the player's turn.
     *
     * @param args command-line arguments (not used)
     */

    public ParadeTester(boolean hasTimeLimit){
        runGameLoop(hasTimeLimit);
    }

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

    // Start game, run in loop so that same players can be reused
    public void runGameLoop(boolean hasTimeLimit){
        boolean playMoreGames = true;
        PlayerList playerList = null;
    
        do {
            boolean reusePlayers = playerList != null && askSamePlayers(); // if (condition), true, else false
            Deck d = new Deck();                  // Now created AFTER reuse decision
            Parade par = new Parade(d);
            boolean endGame = false;
            // boolean continueGame = true;
            int turn = -1;
            int round = 1;
    
            // if user wants to play with NEW players from previous round (if any)
            if (!reusePlayers) {
                playerList = new PlayerList(d);   // Add players and deal initial cards
            } else { // if user wants to play with SAME players
                resetGame(playerList, d);         // Reuses players, resets hands
                System.out.println("Continuing game with the same players...\n\n");
            }
            playerList.displayPlayerProfiles();
            System.out.println(hasTimeLimit ? "Time Limit round started" : "Classic round started");


            // Start main gameplay for each turn
            while (playerList.getPlayer(++turn).getHandSize() == 5) { 
                Player curPlayer = playerList.getPlayer(turn);
                try {
                    round = turn/playerList.getNumberOfPlayers() + 1;
                    // Display round number before first turn of that round
                    if (turn % playerList.getNumberOfPlayers() == 0) {
                        System.out.println("\n\n==== ROUND " + round + " ====");
                    }
                    System.out.println("\n\n||   Turn " + (turn + 1) + "   ||   " + curPlayer.getName());
                    System.out.println("Parade: " + par.getParade() + "\u001B[31m <==\u001B[0m Card inserted here");
    
                    // Delay output for bot players
                    if (curPlayer instanceof BotPlayer){
                        System.out.println(curPlayer.getName() + " is selecting their cards...");
                        Thread.sleep(20);
                        System.out.println("Selection complete.");
                    }
    
                    // Prompt player to pick a card, player chooses card
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
                        System.out.println("\n\n" + curPlayer.getName() + " has collected all 6 colours!");
                        System.out.println("Everyone has one last turn before the game ends.");
                        try {
                            curPlayer.addCard(d.drawCard(), endGame); 
                        } catch (EndGameException ee) {
                            System.out.println(ee.getMessage()); 
                        }
                    }
                    endGame = true;
    
                    // Delay before post-game steps
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }
    
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
                // just to handle the exception, but should never be thrown in this block
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
    
            // Calculate final scores
            ScoreCalculator scoreCalc = new ScoreCalculator(playerList);
            ArrayList<Player> winners = scoreCalc.findWinners();
            int minScore = scoreCalc.getMinScore();
    
            // Print winner(s))
            System.out.println("\n=== WINNNER(s) ===");
            if (winners.size() == 1) {
                System.out.println(winners.get(0).getName() + " WINS with " + minScore + " points!");
            } else {
                System.out.print("It's a TIE between Players ");
                for (Player p : winners) {
                    System.out.print(p.getName() + " ");
                }
                System.out.println("with " + minScore + " points!");
            }
            System.out.println();

            // Add to tally of number of games won for each player
            for (Player p : winners) {
                p.incrementWins();
            }
    
            // Print all scores and tally for number of wins
            System.out.println("=== ALL SCORES ===");
            scoreCalc.printLosers();

            System.out.println("=== Total WINS ===");
            for (Player p : playerList.getPlayerList()) {
                System.out.println(p.getName() + " has " + p.getWins() + " win.");
            }
    
            // Prompt user for if they want to play again
            playMoreGames = askToPlayAgain();
            if (!playMoreGames) {
                System.out.println("Thanks for playing!");
            }
        } while (playMoreGames);
    }

    
    private static void resetGame(PlayerList playerList, Deck deck) {
        // Reset the player hands
        for (Player player : playerList.getPlayerList()) {
            player.clearHand();
            player.clearCollectedCards();
        }

        playerList.setDeck(deck);      // Update deck reference
        deck.resetDeck();              // Reset card order
        playerList.dealInitialCards(); // Deal from the new deck
    }

    private boolean askToPlayAgain() {
        UserInput input = new UserInput();
        String playAgainChoice;
        while (true) {
            playAgainChoice = input.getString("\n\nDo you want to play again? (y/n): ").toLowerCase();
            if (playAgainChoice.equals("y") || playAgainChoice.equals("n")) break;
            System.out.println("Invalid input. Please enter 'y' for Yes or 'n' for No.");
        }
        return playAgainChoice.equals("y");
    }
    
    private boolean askSamePlayers() {
        UserInput input = new UserInput();
        String choice;
        while (true) {
            choice = input.getString("Do you want to play with the same players? (y/n): ").toLowerCase();
            if (choice.equals("y") || choice.equals("n")) break;
            System.out.println("Invalid input. Please enter 'y' for Yes or 'n' for No.");
        }
        return choice.equals("y");
    }
}