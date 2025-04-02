package parade;

import parade.enums.Colour;
import parade.exceptions.EndGameException;
import java.util.ArrayList;
import java.util.EnumMap;

/**
 * Abstract base class representing a player in the Parade game.
 * <p>
 * This class defines the shared functionality and attributes for both
 * {@link HumanPlayer} and {@link BotPlayer} classes.
 * It includes hand management, card collection, turn logic,
 * and endgame condition checking.
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

    /** Number of wins accumulated across multiple games. */
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
        this.wins = 0;
    }

    /**
     * Retrieves the name of the player.
     *
     * @return The player's name.
     */
    public String getName() {
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
     * Returns the number of cards in the player's hand.
     *
     * @return The hand size.
     */
    public int getHandSize() {
        return this.hand.size();
    }

    /**
     * Returns the number of wins this player has accumulated.
     *
     * @return Total wins.
     */
    public int getWins() {
        return wins;
    }

    /**
     * Retrieves the cards the player has collected during the game.
     *
     * @return A map of {@link Colour} to a list of {@link Card}s.
     */
    public EnumMap<Colour, ArrayList<Card>> getCollectedCards() {
        return this.collectedCards;
    }

    /**
     * Retrieves the collected cards of a specific color.
     *
     * @param colour The color to retrieve.
     * @return A list of cards of the given color, or {@code null} if none collected.
     */
    public ArrayList<Card> getCollectedCardsWithColour(Colour colour) {
        return collectedCards.getOrDefault(colour, null);
    }

    /**
     * Abstract method to choose a card from the player's hand.
     * <p>
     * Implemented differently by {@link HumanPlayer} and {@link BotPlayer}.
     *
     * @return The chosen card.
     */
    public abstract Card chooseCard();

    /**
     * Removes a card from the player's hand and returns it.
     *
     * @param c The card to play.
     * @return The played card.
     */
    public Card playCard(Card c) {
        this.hand.remove(c);
        return c;
    }

    /**
     * Adds a card to the player's hand.
     *
     * @param c The card to add.
     * @throws EndGameException If the card is null and the game has not entered the end phase.
     */
    public void addCard(Card c) throws EndGameException {
        addCard(c, false); // Default to non-endgame mode
    }

    /**
     * Adds a card to the player's hand, with logic for endgame mode.
     *
     * @param c       The card to add.
     * @param endGame Whether the game is in endgame mode.
     * @throws EndGameException If {@code c} is null and {@code endGame} is false.
     */
    public void addCard(Card c, Boolean endGame) throws EndGameException {
        if (c == null && !endGame) {
            throw new EndGameException("There are no more cards in the deck.");
        }

        if (c != null && !endGame) {
            this.hand.add(c);
        }
    }

    /**
     * Adds a set of cards to the player's collection.
     *
     * @param cards The cards to collect.
     * @throws EndGameException If this collection results in the player owning all six colors.
     */
    public void collectCard(ArrayList<Card> cards) throws EndGameException {
        collectCard(cards, false);
    }

    /**
     * Adds a set of cards to the player's collection, with logic for endgame.
     *
     * @param cards   The cards to collect.
     * @param endGame Whether the game is in endgame mode.
     * @throws EndGameException If the player collects all six colors before endgame.
     */
    public void collectCard(ArrayList<Card> cards, boolean endGame) throws EndGameException {
        for (Card c : cards) {
            Colour curColour = c.getCardColour();
            collectedCards.putIfAbsent(curColour, new ArrayList<>());
            collectedCards.get(curColour).add(c);
        }

        if (!endGame && collectedCards.size() == 6) {
            throw new EndGameException(this.name + " has collected all 6 colors!");
        }
    }

    /**
     * Prints the player's collected cards, grouped by color.
     * <p>
     * This method aligns the output for readability. On the first line, the label
     * "Collection:" is printed, and subsequent lines are indented to match.
     * </p>
     *
     * @param forFinalDisplay whether the print is for final scoring view
     */
    public void printCollectedCards(boolean forFinalDisplay) { // boolean is for the alignment of the texts
        String label = "Collection:";
        String indent = " ".repeat(label.length() + 1); // Align subsequent lines
    
        boolean firstLine = true;
        for (Colour c : Colour.values()) {
            ArrayList<Card> cardsOfColour = collectedCards.get(c);
            if (cardsOfColour != null && !cardsOfColour.isEmpty()) {
                // Print label on the first line, indent on the rest
                System.out.print(firstLine ? label + " " : indent);
                firstLine = false;
    
                for (Card card : cardsOfColour) {
                    System.out.print(card + " ");
                }
                System.out.println();
            }
        }
    }

    /**
     * Clears the player's hand.
     */
    public void clearHand() {
        hand.clear();
    }

    /**
     * Clears all collected cards from the player.
     */
    public void clearCollectedCards() {
        collectedCards.clear();
    }

    /**
     * Increments the player's win count by one.
     */
    public void incrementWins() {
        ++wins;
    }
}