package parade.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import parade.cards.Card;
import parade.cards.Colour;
import parade.players.Player;
import parade.players.PlayerList;

/**
 * Calculates and manages the scores of players in the Parade game.
 * <p>
 * Scoring is determined by evaluating each player's collected cards. For each u:
 * <ul>
 *     <li>The player(s) with the most cards of that colour only count the number of cards.</li>
 *     <li>Other players sum the values of the cards in that colour.</li>
 *     <li>In 2-player games, the majority rule requires a margin of 2 to apply.</li>
 * </ul>
 *
 * <p>
 * Example usage:
 * <pre>
 * ScoreCalculator sc = new ScoreCalculator(playerList);
 * sc.printLosers();
 * ArrayList&lt;Player&gt; winners = sc.findWinners();
 * </pre>
 * 
 * @author G3T7
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
            for (Player p : playerList) {
                put(p, 0);
            }
        }};

        calculateScores();
    }

    /**
     * Calculates scores for each player.
     * <p>
     * For each colour:
     * <ul>
     *     <li>Players with the maximum count of that colour receive a score equal to the number of cards.</li>
     *     <li>All others receive the total sum of card values for that colour.</li>
     * </ul>
     * </p>
     */
    private void calculateScores(){
        for (Colour colour : Colour.values()) { 
            ArrayList<Player> maxPlayers = findMaxPlayers(colour);

            for (Player p : playerList) {
                int currentScore = scoreTracker.get(p);
                int toAdd = 0;

                if (maxPlayers.contains(p)) {
                    toAdd = p.getCollectedCards().get(colour).size();
                } else if (p.getCollectedCardsWithColour(colour) != null) {
                    for (Card c : p.getCollectedCardsWithColour(colour)) {
                        toAdd += c.getCardNum();
                    }
                }

                scoreTracker.put(p, currentScore + toAdd);
            }
        }
    }

    /**
     * Finds the players who have the most cards of a given colour.
     * <p>
     * In 3+ player games, multiple players can tie for max.
     * In 2-player games, a majority requires a lead of 2 cards or more.
     * </p>
     *
     * @param colour The {@link Colour} to evaluate.
     * @return A list of {@link Player}s with the most cards in the given colour.
     */
    private ArrayList<Player> findMaxPlayers(Colour colour){
        ArrayList<Player> maxPlayers = new ArrayList<>();
        int maxCount = 0;

        for (Player p : playerList){
            if (!p.getCollectedCards().containsKey(colour)) continue;

            int count = p.getCollectedCards().get(colour).size();

            if (playerList.size() > 2) {
                if (count > maxCount) {
                    maxCount = count;
                    maxPlayers.clear();
                    maxPlayers.add(p);
                } else if (count == maxCount) {
                    maxPlayers.add(p);
                }
            } else {
                // Two-player logic
                if (count > maxCount + 1) {
                    maxCount = count;
                    maxPlayers.clear();
                    maxPlayers.add(p);
                } else if (count == maxCount + 1) {
                    maxPlayers.clear(); // No majority
                }
            }
        }

        return maxPlayers;
    }

    /**
     * Finds the player(s) with the lowest total score.
     *
     * @return A list of winning {@link Player}s.
     */
    public ArrayList<Player> findWinners(){
        ArrayList<Player> winners = new ArrayList<>();
        int minScore = getMinScore();

        for (Player p : scoreTracker.keySet()) {
            if (scoreTracker.get(p) == minScore) {
                winners.add(p);
            }
        }
        return winners;
    }

    /**
     * Prints the winner(s) of the game based on the lowest score.
     * <p>
     * In case of a tie, all tied players are displayed.
     * </p>
     */
    public void printWinners() {
        ArrayList<Player> winners = findWinners();
        int minScore = getMinScore();
        System.out.println("\n=== WINNNER(s) ===");
        if (winners.size() == 1) {
            System.out.println(winners.get(0).getName() + " WINS with " + minScore + " points!");
        } else {
            System.out.print("It's a TIE between Players ");
            int size = winners.size();
            for (int i = 0; i < size; i++) {
                System.out.print(winners.get(i).getName());
                
                if (i < size - 2) { // If there are more than two remaining, add a comma
                    System.out.print(", ");
                } else if (i == size - 2) { // Before the last name, add "and"
                    System.out.print(" and ");
                }
            }
        }
        System.out.println();
    }

    /**
     * Retrieves the lowest score achieved by any player.
     *
     * @return The minimum score.
     */
    private int getMinScore(){
        return Collections.min(scoreTracker.values());
    }

    /**
     * Prints out the score of each player.
     * <p>
     * Useful for post-game summary.
     * </p>
     */
    public void printAllScores(){
        System.out.println("=== ALL SCORES ===");
        for (Player p : scoreTracker.keySet()) {
            System.out.println(p.getName() + " has a score of " + scoreTracker.get(p) + ".");
        }
        System.out.println();
    }
}