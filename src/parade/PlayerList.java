package parade;

import java.util.*;

import parade.exceptions.EndGameException;

/**
 * Manages the list of players in the Parade game.
 * <p>
 * This class handles player initialization, shuffling, card distribution,
 * and maintaining player order during the game.
 * </p>
 * 
 * <p>
 * Supports up to 6 players total (human and bot combined). Each player
 * is dealt a hand of cards at the start of the game.
 * </p>
 * 
 * <p>
 * Example usage:
 * <pre>
 * PlayerList pl = new PlayerList(new Deck());
 * pl.getPlayer(0).getName(); // Access first player
 * </pre>
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

    /** Number of cards each player starts with. */
    private static final int INITIAL_HAND_SIZE = 5;

    /**
     * Constructs a new PlayerList and initializes the players.
     * <p>
     * Prompts the user to input the number of human and bot players.
     * Ensures names are unique and shuffles the final player order.
     * Each player is dealt an initial hand.
     * </p>
     *
     * @param d The deck of cards used in the game.
     */
    public PlayerList(Deck d) {
        UserInput input = new UserInput();
        ArrayList<Player> players = new ArrayList<>();

        // Get number of human players
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

        // Get bot players if space remains
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

        // Shuffle the player order before starting the game
        Collections.shuffle(players);

        // Deal initial hands
        dealInitialCards();
    }

    /**
     * Deals the initial set of cards to each player.
     * <p>
     * Each player receives {@code INITIAL_HAND_SIZE} cards.
     * If there are not enough cards, the program exits.
     * </p>
     */
    public void dealInitialCards() {
        for (int i = 0; i < INITIAL_HAND_SIZE; i++) {
            for (Player p : playerList) {
                try {
                    p.addCard(deck.drawCard());
                } catch (EndGameException e) {
                    System.out.println("There are not enough cards to start the game.");
                    System.exit(-1);
                }
            }
        }
    }

    /**
     * Retrieves the list of all players.
     *
     * @return An {@link ArrayList} of {@link Player} objects.
     */
    public ArrayList<Player> getPlayerList() {
        return this.playerList;
    }

    /**
     * Retrieves the number of players in the game.
     *
     * @return Total number of players.
     */
    public int getNumberOfPlayers() {
        return playerList.size();
    }

    /**
     * Retrieves a player by index, with wrapping.
     * <p>
     * If the index exceeds the number of players, it wraps around to ensure turn rotation.
     * </p>
     *
     * @param i Index (can be greater than total number of players).
     * @return The corresponding {@link Player}.
     */
    public Player getPlayer(int i) {
        return playerList.get(i % playerList.size());
    }

    /**
     * Displays player profiles before the game starts.
     * <p>
     * Includes a brief delay for dramatic effect.
     * </p>
     */
    public void displayPlayerProfiles() {
        System.out.println("\n==== Player Profile ====");
        for (int i = 0; i < playerList.size(); i++) {
            Player p = playerList.get(i);
            System.out.println("Player " + (i + 1) + ": " + p.getName());
        }
        System.out.println("=========================\n");
        System.out.println("The game will start now.\n");

        try {
            Thread.sleep(2000); // Pause for 2 seconds
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Updates the deck used by this player list.
     * <p>
     * Used for restarting a game with the same players.
     *
     * @param newDeck The new {@link Deck} to be used.
     */
    public void setDeck(Deck newDeck) {
        this.deck = newDeck;
    }
}