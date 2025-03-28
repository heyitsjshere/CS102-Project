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
            System.out.printf("\n\nThe game is over.\n" +
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
    
                    p.collectCard(p.getHand(), false);
                }
            } catch (EndGameException | InterruptedException e) {
                e.printStackTrace();
            }
    
            System.out.println();
            System.out.println();
    
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

            for (Player p : winners) { // increment wins for ALL winners
                p.incrementWins();
            }
    
            System.out.println("=== ALL SCORES ===");
            scoreCalc.printLosers();
    
            System.out.println("=== Total WINS ===");
            for (Player p : playerList.getPlayerList()) {
                System.out.println(p.getName() + " has " + p.getWins() + " win.");
            }
    
            playMoreGames = askToPlayAgain();
            if (!playMoreGames) {
                System.out.println("Thanks for playing!");
            }
        }
    }

    private static void resetGame(PlayerList playerList, Deck deck) {
        // Reset the player hands
        for (Player player : playerList.getPlayerList()) {
            player.clearHand();
            player.clearCollectedCards();
        }

        playerList.setDeck(deck);     // Update deck reference
        deck.resetDeck();             // Reset card order
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