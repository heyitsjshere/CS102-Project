package parade;

import java.util.ArrayList;
import parade.exceptions.EndGameException;

/**
 * Simulates and manages the flow of a Parade game session.
 * <p>
 * This class initializes the deck, player list, and parade, then controls
 * the game loop — managing player turns, card play, endgame detection, 
 * post-game scoring, and replay logic.
 * </p>
 * 
 * <p><strong>Features:</strong></p>
 * <ul>
 *   <li>Handles initialization of a new game or reuse of previous players</li>
 *   <li>Controls player turns, card play, and collection logic</li>
 *   <li>Handles endgame conditions (deck exhaustion or all six colours collected)</li>
 *   <li>Manages post-game discard, scoring, and win tracking</li>
 *   <li>Prompts user to replay or restart with new players</li>
 * </ul>
 * 
 * <p><strong>Example usage:</strong></p>
 * <pre>
 * new ParadeTester();  // Starts the game loop
 * </pre>
 * 
 * @author G3T7
 * @version 1.0
 */
public class ParadeTester {
    /**
     * Constructs a new ParadeTester instance and starts the game loop.
     */
    public ParadeTester() {
        runGameLoop();
    }

    /**
     * Runs the main game loop.
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

    public void runGameLoop(boolean hasTimeLimit) {
        boolean playMoreGames = true;
        PlayerList playerList = null;
        Deck d = null;
    
        while (playMoreGames) {
            d = new Deck();
            Parade par = new Parade(d);
    
            if (playerList == null || !askSamePlayers()) {
                playerList = new PlayerList(d);
            } else {
                d = new Deck(); // reset deck size before restarting game
                resetGame(playerList, d);
                System.out.println("Continuing game with the same players...\n\n");
            }
    
            playerList.displayPlayerProfiles();
            boolean endGame = false;
            boolean continueGame = true;
            int turn = 0;
    
            System.out.println(hasTimeLimit ? "Time Limit round started" : "Classic round started");
    
            while (continueGame) {
                turn++;
                System.out.println("\n\n==== ROUND " + turn + " ====");
    
                for (int i = 0; i < playerList.getNumberOfPlayers(); i++) {
                    Player curPlayer = playerList.getPlayer(i);
                    try {
                        System.out.println("\n||  " + curPlayer.getName() + "'s turn  ||");
                        System.out.println("Parade: " + par.getParade()  + "\u001B[31m<==\u001B[0m Card inserted here");
    
                        if (curPlayer instanceof BotPlayer) {
                            System.out.println(curPlayer.getName() + " is selecting their cards...");
                            Thread.sleep(2000);
                            System.out.println("Selection complete.");
                        }
    
                        Card pickedCard = curPlayer.chooseCard();
                        System.out.println("Player has played: " + pickedCard);
    
                        ArrayList<Card> toCollect = par.getCollectibleCards(pickedCard);
                        curPlayer.collectCard(toCollect, endGame);
    
                        System.out.println("Player should collect: " + toCollect);
                        System.out.println("Player's Collection: " + curPlayer.getCollectedCards());
    
                        par.addCard(curPlayer.playCard(pickedCard));
                        curPlayer.addCard(d.drawCard(), endGame);
    
                    } catch (EndGameException e) {
                        System.out.println(e.getMessage());
                        if (e.getMessage().toLowerCase().contains("deck")) {
                            System.out.println("Everyone else has one last turn before the game ends.");
                        } else {
                            System.out.println("\n\n" + curPlayer.getName() + " has collected all 6 colours!");
                            System.out.println("Everyone has one last turn before the game ends.");
                            try {
                                curPlayer.addCard(d.drawCard(), endGame);
                            } catch (EndGameException ee) {
                                System.out.println(ee.getMessage());
                            }
                        }
                        endGame = true;
    
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException ex) {
                            Thread.currentThread().interrupt();
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
    
                for (int i = 0; i < playerList.getNumberOfPlayers(); i++) {
                    if (playerList.getPlayer(i).getHandSize() != 5) {
                        continueGame = false;
                        break;
                    }
                }
            }
    
            // Post-game: discard + scoring
            System.out.printf("\n\n🎉 The game is over! 🎉\n" +
            "🃏 It's time to discard and score!" +
            "\n Each player will discard 2 cards.\n" +
            "The remaining cards will be added to your collection.\n\n");
            delayMessageWithDots("\n\n🕑 Now preparing for final collection phase");

            try {
                for (Player curPlayer : playerList.getPlayerList()){
                    // pause thread during bot's term
                    System.out.println("\n\n||   Please select 2 cards to discard.   ||   " + curPlayer.getName());

                    if (curPlayer instanceof BotPlayer){
                        delayMessageWithDots(curPlayer.getName() + " is selecting their cards");
                        System.out.println("Selection complete.");
                    }
    
                    // pick 1st card to discard
                    Card discard1 = curPlayer.chooseCard();
                    curPlayer.playCard(discard1); // remove card from hand
                    System.out.println();
    
                    // pick 2nd card to discard
                    Card discard2 = curPlayer.chooseCard();
                    curPlayer.playCard(discard2);
                    
                    // add remaining hand cards to collection
                    curPlayer.collectCard(curPlayer.getHand(), false);
                }
            } catch (EndGameException e){
                // just to handle exceptions, but should never be thrown in this try block
            }
    
            System.out.println("\nFinal hands and collections:");
        
            // Display final hand and collection for each player
            for (Player p: playerList.getPlayerList()) {
                System.out.println(p.getName() + "\t Hand: " + p.getHand());
                p.printCollectedCards(true);
                System.out.println();
            }        
    
            // Calculate final scores
            ScoreCalculator scoreCalc = new ScoreCalculator(playerList);
            ArrayList<Player> winners = scoreCalc.findWinners();
            int minScore = scoreCalc.getMinScore();
    
            // Print winner(s)
            scoreCalc.printWinners(winners, minScore);
            // Print all scores
            scoreCalc.printAllScores();
            
            // Add to tally of number of games won for each player
            for (Player p : winners) {
                p.incrementWins();
            }
            // Print tally for number of wins
            System.out.println("=== Total WINS ===");
            for (Player p : playerList.getPlayerList()) {
                System.out.println(p.getName() + " has " + p.getWins() + (p.getWins() == 1 ? " win." : " wins."));
            }
    
            // Prompt user for if they want to play again
            playMoreGames = askToPlayAgain();
            if (!playMoreGames) {
                System.out.println("Thanks for playing!");
            }
        } while (playMoreGames);
    }

    /**
     * Resets the players' hands and collections and deals new cards from a new deck.
     *
     * @param playerList the current list of players
     * @param deck       the new deck to draw from
     */
    private static void resetGame(PlayerList playerList, Deck deck) {
        // Reset the player hands
        for (Player player : playerList.getPlayerList()) {
            player.clearHand();
            player.clearCollectedCards();
        }
        playerList.setDeck(deck);      // Update deck reference
        deck.resetDeck();              // Reset and shuffle deck
        playerList.dealInitialCards(); // Deal from new deck
    }

    /**
     * Prompts the user to decide whether to play another game.
     *
     * @return {@code true} if the player chooses to play again, {@code false} otherwise
     */
    private boolean askToPlayAgain() {
        UserInput input = new UserInput();
        String playAgainChoice;
        while (true) {
            playAgainChoice = input.getString("\n\nDo you want to play again? (Y/N): ").toLowerCase();
            if (playAgainChoice.equals("y") || playAgainChoice.equals("n")) break;
            System.out.println("Invalid input. Please enter 'y' for Yes or 'n' for No.");
        }
        return playAgainChoice.equals("y");
    }
    
    /**
     * Prompts the user to decide whether to reuse the same players.
     *
     * @return {@code true} if the user wants to keep the same players, {@code false} otherwise
     */
    private boolean askSamePlayers() {
        UserInput input = new UserInput();
        String choice;
        while (true) {
            choice = input.getString("Do you want to play with the same players? (Y/N): ").toLowerCase();
            if (choice.equals("y") || choice.equals("n")) break;
            System.out.println("Invalid input. Please enter 'y' for Yes or 'n' for No.");
        }
        return choice.equals("y");
    }

    public static void delayMessageWithDots(String message) {
        System.out.print(message);
        try {
            for (int i = 0; i < 4; i++) {
                Thread.sleep(700); // brief pause for user experience
                System.out.print(".");  // print loading dots every 0.5s in the same line
                System.out.flush();       // to immediately display each dot in buffer
            }
            System.out.println();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}