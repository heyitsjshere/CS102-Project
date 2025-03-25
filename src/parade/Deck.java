package parade;

import parade.enums.*;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Represents a deck of cards for the Parade game.
 * <p>
 * The deck consists of a fixed set of 66 cards, each having a color 
 * and a number. The deck is initialized in a shuffled order.
 * </p>
 *
 * <p>Example usage:</p>
 * <pre>
 *     Deck deck = new Deck();
 *     Card drawnCard = deck.drawCard();
 *     System.out.println("Drawn Card: " + drawnCard);
 * </pre>
 *
 * @author G3T7
 * @version 1.0
 */
public class Deck {

    /** The list of cards currently in the deck. */
    private ArrayList<Card> cardsInDeck;

    /**
     * A static reference deck containing all possible cards.
     * <p>
     * This deck consists of cards with numbers ranging from 0 to 10 
     * for each color in the {@link Colour} enum.
     * </p>
     */
    private static final ArrayList<Card> DECK_REFERENCE = new ArrayList<Card>() {{
        for (Colour c : Colour.values()) {  // For each color
            for (int i = 0; i <= 10; i++) { // Cards numbered 0-10
                add(new Card(i, c));  // Create and add the card
            }
        }
    }};

    /**
     * Constructs a new shuffled deck.
     * <p>
     * The deck is initialized with a shuffled version of {@code DECK_REFERENCE}.
     * </p>
     */
    public Deck() {
        cardsInDeck = new ArrayList<>();
        cardsInDeck.addAll(DECK_REFERENCE);
        Collections.shuffle(cardsInDeck);
    }

    /**
     * Returns the reference deck containing all possible cards.
     * <p>
     * This method is mainly used for testing purposes.
     * </p>
     *
     * @return the reference deck as an unshuffled list of {@link Card} objects.
     */
    public ArrayList<Card> getDeckReference() {
        return DECK_REFERENCE;
    }

    /**
     * Returns the current state of the deck.
     * <p>
     * This method provides access to the shuffled deck, primarily for testing.
     * </p>
     *
     * @return the list of {@link Card} objects in the current deck.
     */
    public ArrayList<Card> getCurrentDeck() {
        return this.cardsInDeck;
    }

    /**
     * Draws a card from the top of the deck.
     * <p>
     * Removes and returns the first card in the deck. If the deck is empty, returns {@code null}.
     * </p>
     *
     * @return the drawn {@link Card}, or {@code null} if the deck is empty.
     */
    public Card drawCard() {
        if (cardsInDeck.isEmpty()) {
            return null; // No more cards in the deck
        }
        return this.cardsInDeck.removeFirst(); // Draw from the top
    }

    /**
     * Returns the number of cards remaining in the deck.
     *
     * @return the number of cards left in the deck.
     */
    public int getSize() {
        return cardsInDeck.size();
    }
}