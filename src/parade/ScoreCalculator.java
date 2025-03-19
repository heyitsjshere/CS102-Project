package parade;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import parade.enums.Colour;

/**
 * Calculates and manages the scores of players in the Parade game.
 * <p>
 * The scoring is based on the number and value of collected cards.
 * Players with the maximum number of a specific color score only the number of cards,
 * while others score based on the sum of card values.
 * </p>
 *
 * @author Your Name
 * @version 1.0
 */
public class ScoreCalculator {

    /** List of players participating in the game. */
    private ArrayList<Player> playerList;

    /** A mapping of each player to their calculated score. */
    private HashMap<Player, Integer> scoreTracker;

    /**
     * Constructs a ScoreCalculator and initializes the score tracking.
     * <p>
     * This constructor retrieves the list of players from the given {@link PlayerList}
     * and initializes their scores to zero before calculating them.
     * </p>
     *
     * @param pl The {@link PlayerList} containing all players in the game.
     */
    public ScoreCalculator(PlayerList pl){
        this.playerList = pl.getPlayerList();
        scoreTracker = new HashMap<>() {{ 
            // Initialize score tracker with zero for all players
            for (Player p : playerList) {
                put(p, 0);
            }
        }};

        calculateScores();
    }

    /**
     * Calculates the scores for each player.
     * <p>
     * The method iterates through all colors and determines which players have the most cards 
     * of a given color. These players score based on the number of cards they hold. 
     * Other players score based on the sum of card values.
     * </p>
     */
    private void calculateScores(){
        for (Colour colour : Colour.values()) { 
            ArrayList<Player> maxPlayers = findMaxPlayers(colour); // Players with the most cards of this color
            
            for (Player p : playerList) {
                int curScore = scoreTracker.get(p); // Get current score before adding
                int toAdd = 0;

                if (maxPlayers.contains(p)) {
                    // Max players only count the number of cards, not their values
                    toAdd = p.getCollectedCards().get(colour).size();
                } else if (p.getCollectedCardsWithColour(colour) != null) { 
                    // Other players sum the values of the cards
                    for (Card c : p.getCollectedCardsWithColour(colour)) {
                        toAdd += c.getCardNum();
                    }
                }

                scoreTracker.put(p, toAdd + curScore);
            }
        }
    }

    /**
     * Finds the players with the highest count of a specific color.
     * <p>
     * If multiple players share the maximum count, they all qualify.
     * Special rules apply when there are only two players.
     * </p>
     *
     * @param colour The {@link Colour} to check.
     * @return A list of players with the most cards of the given color.
     */
    private ArrayList<Player> findMaxPlayers(Colour colour){
        ArrayList<Player> maxPlayers = new ArrayList<>();
        int maxCount = 0;

        for (Player p : playerList){
            if (!p.getCollectedCards().containsKey(colour)) {
                continue; // Skip players who do not have this color
            }

            int count = p.getCollectedCards().get(colour).size();

            if (playerList.size() > 2) { 
                // Standard rule: find the player(s) with the largest count
                if (count > maxCount) {
                    maxCount = count;
                    maxPlayers.clear();
                    maxPlayers.add(p);
                } else if (count == maxCount) {
                    maxPlayers.add(p);
                }
            } else if (playerList.size() == 2) {
                // Special rule: In a two-player game, majority must be at least two more
                if (maxCount - count < 2) {
                    maxPlayers.clear();
                }
                if (count > maxCount + 1) {
                    maxCount = count;
                    maxPlayers.clear();
                    maxPlayers.add(p);
                }
            }
        }

        return maxPlayers;
    }

    /**
     * Finds the winner(s) of the game.
     * <p>
     * The winner is the player (or players) with the lowest score.
     * </p>
     *
     * @return A list of players who achieved the lowest score.
     */
    public ArrayList<Player> findWinners(){
        calculateScores();
        ArrayList<Player> winners = new ArrayList<>();

        int minScore = Collections.min(scoreTracker.values()); // Lowest score in the game

        for (Player p : scoreTracker.keySet()) {
            if (scoreTracker.get(p) == minScore) {
                winners.add(p);
            }   
        }
        return winners;
    }

    /**
     * Retrieves the minimum score among all players.
     *
     * @return The lowest score in the game.
     */
    public int getMinScore(){
       return Collections.min(scoreTracker.values());
    }

    /**
     * Prints the scores of all players.
     * <p>
     * This method is used to display the final results at the end of the game.
     * </p>
     */
    public void printLosers(){
        for (Player p : scoreTracker.keySet()) {
            System.out.println("Player " + (playerList.indexOf(p) + 1) + " has a score of " + scoreTracker.get(p));
        }
    }

}