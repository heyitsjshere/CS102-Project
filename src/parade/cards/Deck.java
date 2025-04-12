package parade.cards;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Represents a full deck of cards used in the Parade game.
 * <p>
 * A Parade deck contains 66 unique cards: one for each number (0–10) in all six {@link Colour}s.
 * The deck can be shuffled, drawn from, and reset.
 * </p>
 *
 * <p><strong>Example usage:</strong></p>
 * <pre>{@code
 * Deck deck = new Deck();
 * Card drawnCard = deck.drawCard();
 * System.out.println("Drawn Card: " + drawnCard);
 * }</pre>
 *
 * @author G3T7
 * @version 1.0
 */
public class Deck {

    /** The shuffled list of cards currently in the deck. */
    private ArrayList<Card> cardsInDeck;

    /**
     * A static reference deck containing all 66 cards (unshuffled).
     * <p>
     * Includes cards numbered 0–10 for each {@link Colour}.
     * </p>
     */
    private static final ArrayList<Card> DECK_REFERENCE = new ArrayList<Card>() {{
        for (Colour c : Colour.values()) {
            for (int i = 0; i <= 10; i++) {
                add(new Card(i, c));
            }
        }
    }};

    /**
     * Constructs a new deck and shuffles it.
     */
    public Deck() {
        cardsInDeck = new ArrayList<>();
        cardsInDeck.addAll(DECK_REFERENCE);
        Collections.shuffle(cardsInDeck);
    }

    /**
     * Returns the unshuffled reference deck (for testing or debugging).
     *
     * @return the original unshuffled list of all {@link Card}s.
     */
    public ArrayList<Card> getDeckReference() {
        return DECK_REFERENCE;
    }

    /**
     * Returns the current list of cards in the deck (shuffled).
     *
     * @return the current deck as an {@link ArrayList} of {@link Card}s.
     */
    public ArrayList<Card> getCurrentDeck() {
        return this.cardsInDeck;
    }

    /**
     * Draws and removes the top card from the deck.
     *
     * @return the drawn {@link Card}, or {@code null} if the deck is empty.
     */
    public Card drawCard() {
        if (cardsInDeck.isEmpty()) {
            return null;
        }
        return this.cardsInDeck.remove(0);
    }

    /**
     * Returns the number of cards remaining in the deck.
     *
     * @return the deck size.
     */
    public int getSize() {
        return cardsInDeck.size();
    }

    /**
     * Resets the deck to its full set of 66 cards and reshuffles it.
     */
    public void resetDeck() {
        this.cardsInDeck.clear();
        this.cardsInDeck = new ArrayList<>();
        this.cardsInDeck.addAll(DECK_REFERENCE);
        Collections.shuffle(cardsInDeck);
    }
}