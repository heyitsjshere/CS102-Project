package parade;

import java.util.ArrayList;
import parade.enums.Colour;
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
     * Initializes game state, handles turn-by-turn logic, monitors for
     * endgame triggers, performs post-game scoring, and prompts for replay.
     * </p>
     */
    public void runGameLoop() {
        boolean playMoreGames = true;
        PlayerList playerList = null;
        Deck d = null;
    
        while (playMoreGames) {
            d = new Deck();
            Parade par = new Parade(d);
    
            if (playerList == null || !askSamePlayers()) {
                playerList = new PlayerList(d);
            } else {
                d = new Deck();
                resetGame(playerList, d);
                playerList.dealInitialCards(); // redeal cards
                System.out.println("Continuing game with the same players...\n\n");
            }

            playerList.displayPlayerProfiles();
            boolean endGame = false;
            boolean continueGame = true;
            int turn = 0;
    
            while (continueGame) {
                turn++;
                System.out.println("\n\n==== ROUND " + turn + " ====");
    
                for (int i = 0; i < playerList.getNumberOfPlayers(); i++) {
                    Player curPlayer = playerList.getPlayer(i);
                    try {
                        System.out.println("\n||  " + curPlayer.getName() + "'s turn  ||");
                        System.out.println("Parade: " + par.getParade());
    
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
                        System.out.println("\n🃏" + e.getMessage());
                        if (e.getMessage().toLowerCase().contains("deck")) {
                            System.out.println("💫 Final round initiated... everyone else has one last turn before the game ends.\n");
                        } else {
                            System.out.println("\n🎨 " + curPlayer.getName() + " has collected all 6 colours!");
                            System.out.println("💫 Final round triggered! Everyone else gets one last turn.\n");

                            try {
                                curPlayer.addCard(d.drawCard(), endGame);
                            } catch (EndGameException ee) {
                                System.out.println(ee.getMessage());
                            }
                        }
                        endGame = true;

                        try { // Add delay after message
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
            System.out.println("\n\n🕑 Preparing for final collection phase...");
            try {
                Thread.sleep(2000); // brief pause for user experience
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            System.out.printf("\n\n🎉 The game is over! 🎉.\n" +
                    "🃏 Time to discard and score..." +
                    "Each player will now discard 2 cards.\n" +
                    "The remaining cards will be added to your collection.\n");

            
    
            try {
                for (Player p : playerList.getPlayerList()) {
                    System.out.println("\n\n||   Please select 2 cards to discard.   ||   " + p.getName());
                    if (p instanceof BotPlayer) {
                        System.out.println(p.getName() + " is selecting their cards...");
                        Thread.sleep(2000);
                        System.out.print("Selection complete.");
                    }

                    Card discard1 = p.chooseCard();
                    p.playCard(discard1);
                    System.out.println();

                    Card discard2 = p.chooseCard();
                    p.playCard(discard2);

                    p.collectCard(p.getHand(), true); // Prevents further EndGameExceptions
                }
            } catch (EndGameException | InterruptedException e) {
                e.printStackTrace(); // Kept for safety 
            }

            System.out.println("\nFinal hands and collections:");
            for (Player p : playerList.getPlayerList()) {
                System.out.println(p.getName() + "\t Hand: " + p.getHand());
                System.out.println("\t Collection: ");
                for (Colour c : p.getCollectedCards().keySet()) {
                    System.out.print("\t\t");
                    for (Card card : p.getCollectedCardsWithColour(c)) {
                        System.out.print(card + " ");
                    }
                    System.out.println();
                }
                System.out.println();
            }

            ScoreCalculator scoreCalc = new ScoreCalculator(playerList);
            ArrayList<Player> winners = scoreCalc.findWinners();
            int minScore = scoreCalc.getMinScore();

            System.out.println("\n=== WINNER(S) ===");
            if (winners.size() == 1) {
                System.out.println(winners.get(0).getName() + " WINS with " + minScore + " points!");
            } else {
                System.out.print("It's a TIE between Players ");
                for (Player p : winners) {
                    System.out.print(p.getName() + " ");
                }
                System.out.println("with " + minScore + " points!");
            }

            for (Player p : winners) {
                p.incrementWins();
            }

            System.out.println("=== ALL SCORES ===");
            scoreCalc.printLosers();

            System.out.println("=== Total WINS ===");
            for (Player p : playerList.getPlayerList()) {
                System.out.println(p.getName() + " has " + p.getWins() + " win(s).");
            }

            playMoreGames = askToPlayAgain();
            if (!playMoreGames) {
                System.out.println("Thanks for playing!");
            }
        }
    }

    /**
     * Resets the players' hands and collections and deals new cards from a new deck.
     *
     * @param playerList the current list of players
     * @param deck       the new deck to draw from
     */
    private static void resetGame(PlayerList playerList, Deck deck) {
        for (Player player : playerList.getPlayerList()) {
            player.clearHand();
            player.clearCollectedCards();
        }
        playerList.setDeck(deck);
        deck.resetDeck();
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
            playAgainChoice = input.getString("\n\nDo you want to play again? (y/n): ").toLowerCase();
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
            choice = input.getString("Do you want to play with the same players? (y/n): ").toLowerCase();
            if (choice.equals("y") || choice.equals("n")) break;
            System.out.println("Invalid input. Please enter 'y' for Yes or 'n' for No.");
        }
        return choice.equals("y");
    }
}