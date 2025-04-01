package parade;

import java.util.*;

/**
 * Manages the list of players in the Parade game.
 * <p>
 * Handles:
 * <ul>
 *   <li>Player initialization (both human and bot)</li>
 *   <li>Enforcing name uniqueness and player limits</li>
 *   <li>Shuffling player order</li>
 *   <li>Managing player retrieval and display</li>
 * </ul>
 *
 * <p>
 * Supports up to 6 players total (human and bot combined).
 * </p>
 *
 * <p><strong>Example usage:</strong></p>
 * <pre>
 * PlayerList playerList = new PlayerList();
 * Player first = playerList.getPlayer(0);
 * </pre>
 * 
 * @author G3T7
 * @version 1.0
 */
public class PlayerList {

    /** The list of players in the game. */
    private ArrayList<Player> playerList;

    /** The deck from which players draw cards. */

    /** The maximum number of players allowed in the game. */
    private static final int MAX_PLAYER_NUM = 6;



    /**
     * Constructs a new {@code PlayerList} and initializes players.
     * <p>
     * Prompts the user to specify the number of human and bot players,
     * validates names for uniqueness, and shuffles player order.
     * </p>
     */
    public PlayerList() {
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

        // Shuffle the player order before starting the game
        Collections.shuffle(players);

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
     * Displays all player names and types (human or bot) with icons.
     * <p>
     * Includes a short delay message before the game starts.
     * </p>
     */
    public void displayPlayerProfiles() {
        System.out.println("\n==== Player Profile ====");
        for (int i = 0; i < playerList.size(); i++) {
            Player p = playerList.get(i);
            String icon = (p instanceof BotPlayer) ? "🤖" : "🧍";
            System.out.println("Player " + (i + 1) + ": " + icon + " " + p.getName());
        }
        System.out.println("========================\n");

        Game.delayMessage("The game will start now.\n");
    }
}