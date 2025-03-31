package parade;

import java.util.*;

// import parade.enums.Colour;
import parade.exceptions.EndGameException;

/**
 * Manages the list of players in the Parade game.
 * <p>
 * This class handles player initialization, card distribution, and
 * maintaining player order during the game.
 * </p>
 *
 * @author G3T7
 * @version 1.0
 */
public class PlayerList {

    /** The list of players in the game. */
    private ArrayList<Player> playerList;

    /** The deck from which players draw cards. */
    private Deck deck;

    /** The maximum number of players allowed in the game. */
    private static final int MAX_PLAYER_NUM = 6;
    private static final int INITIAL_HAND_SIZE = 5;

    /**
     * Constructs a new PlayerList and initializes players.
     * <p>
     * This constructor prompts the user for the number of human and bot players,
     * assigns them names, shuffles the order shuffles the order of players and distributes initial hands.
     * There should be at least 1 human player, with a total of 2 players in each game. 
     * </p>
     *
     * @param d The deck of cards used in the game.
     */
    public PlayerList(Deck d) {
        UserInput input = new UserInput();
        ArrayList<Player> players = new ArrayList<>();
        
        // Get human players
        int numHumanPlayers = input.getUserInt("Enter number of Human Players (%d - %d): ", 1, MAX_PLAYER_NUM);
        for (int i = 0; i < numHumanPlayers; i++) {
            String name;
            while (true) {
                name = input.getString("Enter name for Human Player " + (i + 1) + ": ");
                boolean isDuplicate = false;
                for (Player p : players) {
                    if (p.getName().equalsIgnoreCase(name)) {
                        System.out.println("This name is already taken. Please choose a different name.");
                        isDuplicate = true;
                        break;
                    }
                }
                if (!isDuplicate) break;
            }
            players.add(new HumanPlayer(name));
        }

        // Get bot players only if space remains
        int numBotPlayers = 0;
        if (numHumanPlayers == MAX_PLAYER_NUM) {
            System.out.println("Since you have selected " + MAX_PLAYER_NUM + " human players, there is no space for bot players.");
        } else {
            int minNumBots = (numHumanPlayers == 1) ? 1 : 0;
            numBotPlayers = input.getUserInt("Enter number of Bot Players (%d - %d): ", minNumBots, MAX_PLAYER_NUM - numHumanPlayers);
            for (int i = 1; i <= numBotPlayers; i++) {
                players.add(new BotPlayer("Bot " + i));
            }
        }

        this.playerList = players;
        this.deck = d;

        // Shuffle order of players
        Collections.shuffle(players);

        // Deal cards to all players in the game
        dealInitialCards();
    }

    /**
     * Distributes the initial set of cards to each player.
     * <p>
     * Each player receives {@code HAND_SIZE} cards from the deck.
     * If the deck does not have enough cards to start the game, the program exits.
     * </p>
     */
    public void dealInitialCards() {
        for (int i = 0; i < INITIAL_HAND_SIZE; i++) {
            for (Player p : playerList) {
                try {
                    p.addCard(deck.drawCard());
                } catch (EndGameException e) {
                    System.out.println("There are not enough cards to start the game.");
                    System.exit(-1); // Exit if not enough cards are available
                }
            }
        }
    }

    /**
     * Retrieves the list of players in the game.
     *
     * @return An {@link ArrayList} of {@link Player} objects.
     */
    public ArrayList<Player> getPlayerList() {
        return this.playerList;
    }

    /**
     * Retrieves the total number of players in the game.
     *
     * @return The number of players.
     */
    public int getNumberOfPlayers() {
        return playerList.size();
    }

    /**
     * Retrieves a player based on their turn order.
     * <p>
     * The index is wrapped around if it exceeds the number of players, ensuring
     * continuous looping through the player list.
     * </p>
     *
     * @param i The index of the player (can be greater than the number of players).
     * @return The {@link Player} at the specified turn position.
     */
    public Player getPlayer(int i) {
        int size = playerList.size(); // Ensure index wraps around
        return playerList.get(i % size);
    }
    /**

    /**
     * Displays a list of all players in the game along with their assigned numbers.
     * <p>
     * This method prints a formatted list of players and introduces a 2-second
     * delay
     * after the display to simulate a pause before the game begins.
     * </p>
     *
     * <p>
     * Example output:
     * </p>
     * 
     * <pre>
     * ==== Player Profile ====
     * Player 1: Alice
     * Player 2: Bot 1
     * =========================
     * The game will start now.
     * </pre>
     *
     */
    public void displayPlayerProfiles() {
        System.out.println("\n==== Player Profile ====");
        for (int i = 0; i < playerList.size(); i++) {
            Player p = playerList.get(i);
            System.out.println("Player " + (i + 1) + ": " + p.getName());
        }
        System.out.println("=========================\n");

        System.out.println("The game will start now.\n\n");

        try { // delay by 2 seconds
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void setDeck(Deck newDeck) {
        this.deck = newDeck;
    }

}