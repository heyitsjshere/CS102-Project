package parade.game;

import java.util.ArrayList;

import parade.players.Player;
import parade.players.PlayerList;
import util.UserInput;

/**
 * Controls the overall Parade game loop and session management.
 * <p>
 * The {@code Game} class handles the entire flow of a Parade session —
 * from player initialization to turn management, endgame logic,
 * scoring, and replay handling.
 * </p>
 * 
 * <p>
 * <strong>Main Responsibilities:</strong>
 * </p>
 * <ul>
 * <li>Handles setup and reuse of players</li>
 * <li>Manages full-game progression across multiple rounds</li>
 * <li>Detects endgame conditions (deck exhausted or 6 colors collected)</li>
 * <li>Triggers scoring and displays winners</li>
 * <li>Supports replaying with same or new players</li>
 * </ul>
 * 
 * <p>
 * <strong>Example usage:</strong>
 * </p>
 * 
 * <pre>{@code
 * new Game(); // Starts the Parade game loop
 * }</pre>
 * 
 * @author G3T7
 * @version 1.1
 */
public class Game {
    /**
     * Constructs a new ParadeTester instance and starts the game loop.
     */
    public Game() {
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

        do { // Run game at least once

            // Either reuse players or create new ones
            if (playerList == null || !askSamePlayers()) {
                playerList = new PlayerList();
            } else {
                resetGame(playerList);
                delayMessageWithDots("Continuing game with the same players");
            }

            playerList.displayPlayerProfiles();

            // Start main gameplay for each turn
            SingleGame game = new SingleGame(playerList);
            ArrayList<Player> winners = game.run();

            System.out.println("\nFinal hands and collections:");

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
                GameDisplay.printExitBanner();
                UserInput.close();
            }
        } while (playMoreGames);
    }

    /**
     * Resets the players' hands and collections and deals new cards from a new
     * deck.
     *
     * @param playerList the current list of players
     * @param deck       the new deck to draw from
     */
    private static void resetGame(PlayerList playerList) {
        // Reset the player hands
        for (Player player : playerList.getPlayerList()) {
            player.clearHand();
            player.clearCollectedCards();
        }
    }

    /**
     * Prompts the user to decide whether to play another game.
     *
     * @return {@code true} if the player chooses to play again, {@code false}
     *         otherwise
     */
    private boolean askToPlayAgain() {
        UserInput input = new UserInput();
        String playAgainChoice;
        while (true) {
            playAgainChoice = input.getString("\n\nDo you want to play again? (Y/N): ").toLowerCase();
            if (playAgainChoice.equals("y") || playAgainChoice.equals("n"))
                break;
            System.out.println("Invalid input. Please enter 'y' for Yes or 'n' for No.");
        }
        return playAgainChoice.equals("y");
    }

    /**
     * Prompts the user to decide whether to reuse the same players.
     *
     * @return {@code true} if the user wants to keep the same players,
     *         {@code false} otherwise
     */
    private boolean askSamePlayers() {
        UserInput input = new UserInput();
        String choice;
        while (true) {
            choice = input.getString("Do you want to play with the same players? (Y/N): ").toLowerCase();
            if (choice.equals("y") || choice.equals("n"))
                break;
            System.out.println("Invalid input. Please enter 'y' for Yes or 'n' for No.");
        }
        return choice.equals("y");
    }

    /**
     * Prints a message to the console and pauses for 2 seconds.
     * <p>
     * This method is used to introduce a brief delay after displaying important
     * messages
     * for better pacing and readability during gameplay.
     * </p>
     *
     * @param message The message to display before pausing.
     */
    public static void delayMessage(String message) {
        System.out.println(message);
        try {
            Thread.sleep(2000); // default pause
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Prints a message followed by three animated dots with delays between each
     * dot.
     * <p>
     * This method is used to simulate a "loading" or "thinking" animation,
     * typically when a bot is making a decision or the game is transitioning.
     * </p>
     *
     * @param message The message to display before the animated dots.
     */
    public static void delayMessageWithDots(String message) {
        System.out.print(message);
        try {
            for (int i = 0; i < 3; i++) {
                Thread.sleep(700); // fixed delay between each dot
                System.out.print(".");
                System.out.flush();
            }
            System.out.println(); // new line after dots
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}