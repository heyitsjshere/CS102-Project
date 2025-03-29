package parade;

import parade.enums.Colour;
import parade.exceptions.EndGameException;

import java.util.ArrayList;
import java.util.EnumMap;

/**
 * Represents a player in the Parade game.
 * <p>
 * This abstract class defines common behaviors for both human and bot players.
 * Each player has a hand of cards, a collection of acquired cards, and the 
 * ability to play, collect, and manage cards according to the game rules.
 * </p>
 *
 * @author G3T7
 * @version 1.0
 */
public abstract class Player {

    /** The player's current hand of cards. */
    private ArrayList<Card> hand; 

    /** The collection of cards the player has acquired, grouped by color. */
    private EnumMap<Colour, ArrayList<Card>> collectedCards; 

    /** The player's name. */
    private String name;

    /** Number of wins this player has */
    private int wins = 0;


    /**
     * Constructs a player with an empty hand and collection.
     *
     * @param name The name of the player.
     */
    public Player(String name) { 
        this.hand = new ArrayList<>();
        this.collectedCards = new EnumMap<>(Colour.class);
        this.name = name;
    }

    /**
     * Retrieves the name of the player.
     *
     * @return The player's name.
     */
    public String getName(){
        return this.name;
    }

    /**
     * Retrieves the player's current hand of cards.
     *
     * @return The player's hand as an {@link ArrayList} of {@link Card} objects.
     */
    public ArrayList<Card> getHand() {
        return this.hand;
    }

    /**
     * Retrieves the number of cards in the player's hand.
     *
     * @return The number of cards in hand.
     */
    public int getHandSize() {
        return this.hand.size();
    }

    public int getWins() {
        return wins;
    }

    /**
     * Retrieves the cards the player has collected during the game.
     *
     * @return An {@link EnumMap} of collected cards, grouped by {@link Colour}.
     */
    public EnumMap<Colour, ArrayList<Card>> getCollectedCards() {
        return this.collectedCards;
    }

    /**
     * Retrieves all collected cards of a specific color.
     *
     * @param colour The color of cards to retrieve.
     * @return An {@link ArrayList} of {@link Card} objects of the specified color,
     *         or {@code null} if no cards of that color have been collected.
     */
    public ArrayList<Card> getCollectedCardsWithColour(Colour colour) {
        return collectedCards.getOrDefault(colour, null);
    }

    /**
     * Allows the player to choose a card to play.
     * <p>
     * This method is implemented differently for human and bot players.
     * </p>
     *
     * @return The chosen {@link Card} to be played.
     */
    public abstract Card chooseCard();

    /**
     * Removes a card from the player's hand and returns it to be played.
     *
     * @param c The card to be played.
     * @return The played {@link Card}.
     */
    public Card playCard(Card c) {
        this.hand.remove(c);
        return c; // Return card so it can be added to the parade.
    }

    /**
     * Adds a card to the player's hand.
     *
     * @param c The card to be added.
     * @throws EndGameException If there are no more cards in the deck.
     */
    public void addCard(Card c) throws EndGameException {
        addCard(c, false); // Assume the game is not in the end phase.
    }

    /**
     * Adds a card to the player's hand, considering the endgame state.
     *
     * @param c The card to be added.
     * @param endGame Indicates whether the game is in the final phase.
     * @throws EndGameException If there are no more cards in the deck.
     */
    public void addCard(Card c, Boolean endGame) throws EndGameException {
        if (c != null && !endGame) {
            this.hand.add(c); // Add as normal.
        }

        if (c == null && !endGame) {
            throw new EndGameException("There are no more cards in the deck.");
        }
        
        // In endgame, players should not draw additional cards.
    }
    
    /**
     * Collects a list of cards and adds them to the player's collection.
     * <p>
     * If a player collects at least one card of all six colors, the game ends.
     * </p>
     *
     * @param cards The list of cards to be collected.
     * @throws EndGameException If the player collects all six colors.
     */
    public void collectCard(ArrayList<Card> cards) throws EndGameException {
        collectCard(cards, false); // Assume the game is not in the end phase.
    }

    /**
     * Collects a list of cards and adds them to the player's collection, considering the endgame state.
     *
     * @param cards The list of cards to be collected.
     * @param endGame Indicates whether the game is in the final phase.
     * @throws EndGameException If the player collects all six colors.
     */
    public void collectCard(ArrayList<Card> cards, boolean endGame) throws EndGameException {
        for (Card c : cards) {
            Colour curColour = c.getCardColour();

            if (!collectedCards.containsKey(curColour)) {
                collectedCards.put(curColour, new ArrayList<>()); // Initialize list for new color.
            }

            collectedCards.get(curColour).add(c); // Add card to list for that color.
        }

        if (!endGame && collectedCards.size() == 6) {
            throw new EndGameException("Player has collected all 6 colors!");
        }
    }

    public void clearHand() {
        hand.clear();
    }

    public void clearCollectedCards() {
        collectedCards.clear();
    }

    public void incrementWins() {
        ++wins;
    }
}