package parade;

import parade.enums.*;
// import parade.Card; // TODO! package this better

import java.util.ArrayList;
import java.util.Collections;

/**
 * Represents a deck of cards for the Parade game.
 * <p>
 * The deck consists of a fixed set of 66 cards, each having a color
 * and a number. The deck is initialized in a shuffled order.
 * </p>
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
    private static final ArrayList<Card> DECK_REFERENCE = new ArrayList<Card>() {
        {
            // deck of card is standard so a static array suffices
            // generating each time is possible but why would u do that
            for (Colour c : Colour.values()) { // for each colour
                for (int i = 0; i <= 10; i++) { // for range 0-10 (inclusive)
                    add(new Card(i, c)); // create the card!
                }
            }
        }
    };

    /**
     * Constructs a new shuffled deck.
     * <p>
     * The deck is initialized with a shuffled version of {@code DECK_REFERENCE}.
     * </p>
     */
    public Deck() {
        // should create an instance of a deck that can be shuffled and all that
        // this.cardsInDeck = generateShuffledDeck(); // create an array of cards in a
        // shuffled order
        cardsInDeck = new ArrayList<Card>();
        cardsInDeck.addAll(DECK_REFERENCE);
        Collections.shuffle(cardsInDeck);
    }

    /**
     * Generates a shuffled version of the reference deck.
     *
     * @return a shuffled list of cards representing the deck
     */
    // private ArrayList<Card> generateShuffledDeck() {
    // cardsInDeck = new ArrayList<Card>();
    // cardsInDeck.addAll(DECK_REFERENCE);
    // Collections.shuffle(cardsInDeck);
    // return this.cardsInDeck;
    // }

    /**
     * Returns the reference deck containing all possible cards.
     * <p>
     * This method is mainly used for testing purposes.
     * </p>
     *
     * @return the reference deck as an unshuffled list of cards
     */
    public ArrayList<Card> getDeckReference() {
        // used for testing only
        return DECK_REFERENCE;
    }

    /**
     * Returns the current state of the deck.
     * <p>
     * This method provides access to the shuffled deck, primarily for testing.
     * </p>
     *
     * @return the list of cards in the current deck
     */
    public ArrayList<Card> getCurrentDeck() {
        // used for testing only, will not need to see entire deck at once
        return this.cardsInDeck;
    }

    /**
     * Draws a card from the top of the deck.
     * <p>
     * Removes and returns the first card in the deck. If the deck is empty, returns
     * {@code null}.
     * </p>
     *
     * @return the drawn {@link Card}, or {@code null} if the deck is empty
     */
    public Card drawCard() {
        // if no more cards in deck
        if (cardsInDeck.size() == 0)
            return null;

        // else, return top card and remove
        return this.cardsInDeck.removeFirst();
    }

    /**
     * Returns the number of cards remaining in the deck.
     *
     * @return the size of the deck
     */
    public int getSize() {
        return cardsInDeck.size();
    }

    public void resetDeck() {
        // this.cardsInDeck = new ArrayList<Card>();

        this.cardsInDeck.clear();
        cardsInDeck = new ArrayList<Card>();
        cardsInDeck.addAll(DECK_REFERENCE);
        Collections.shuffle(cardsInDeck);

    }

}