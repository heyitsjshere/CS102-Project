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
 * @author Your Name
 * @version 1.0
 */
public class PlayerList {

    /** The list of players in the game. */
    private ArrayList<Player> playerList;

    /** The deck from which players draw cards. */
    private Deck deck;

    /** The maximum number of players allowed in the game. */
    private static final int MAX_PLAYER_NUM = 6;

    /** The number of cards each player starts with. */
    private static final int HAND_SIZE = 5;

    /**
     * Constructs a new PlayerList and initializes players.
     * <p>
     * This constructor prompts the user for the number of human and bot players,
     * assigns them names, and distributes initial hands.
     * </p>
     *
     * @param d The deck of cards used in the game.
     */
    public PlayerList(Deck d){
        UserInput input = new UserInput();
        ArrayList<Player> players = new ArrayList<>();

        Scanner sc = new Scanner(System.in);

        // Get human players
        int numHumanPlayers = input.getUserInt("Enter number of Human Players (%d - %d): ", 1, MAX_PLAYER_NUM);
        for (int i = 0; i < numHumanPlayers; i++) { 
            String name = input.getString("Enter name: ");
            players.add(new HumanPlayer(name));
        }

        // Determine minimum number of bot players if needed
        int minNumBots = (numHumanPlayers == 1) ? 1 : 0;
        int numBotPlayers = input.getUserInt("Enter number of Bot Players (%d - %d): ", minNumBots, MAX_PLAYER_NUM - numHumanPlayers);

        // Create bot players
        for (int i = 1; i < numBotPlayers + 1; i++) {
            players.add(new BotPlayer("Bot " + i));
        }

        this.playerList = players;
        this.deck = d;
        dealInitialCards();
    }

    /**
     * Distributes the initial set of cards to each player.
     * <p>
     * Each player receives {@code HAND_SIZE} cards from the deck.
     * If the deck does not have enough cards to start the game, the program exits.
     * </p>
     */
    private void dealInitialCards() {
        for (int i = 0; i < HAND_SIZE; i++) {
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
    public ArrayList<Player> getPlayerList(){
        return this.playerList;
    }

    /**
     * Retrieves the total number of players in the game.
     *
     * @return The number of players.
     */
    public int getNumberOfPlayers(){
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
    public Player getPlayer(int i){
        int size = playerList.size(); // Ensure index wraps around
        return playerList.get(i % size);
    }

}