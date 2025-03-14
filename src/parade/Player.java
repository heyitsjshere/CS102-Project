package parade;

import parade.enums.Colour;
import parade.exceptions.EndGameException;

import java.util.ArrayList;
import java.util.EnumMap;

/**
 * Represents a player in the Parade game.
 * <p>
 * This abstract class defines common behaviors for both human and bot players.
 * </p>
 */
public abstract class Player {

    /** The player's current hand of cards. */
    private ArrayList<Card> hand; 

    /** The collection of cards the player has acquired, grouped by color. */
    private EnumMap<Colour, ArrayList<Card>> collectedCards; 

    /**
     * Constructs a player with an empty hand and collection.
     */
    public Player() { 
        this.hand = new ArrayList<>();
        this.collectedCards = new EnumMap<>(Colour.class);
    }

    /**
     * Returns the player's current hand of cards.
     *
     * @return the player's hand as an {@link ArrayList} of {@link Card} objects
     */
    public ArrayList<Card> getHand() {
        return this.hand;
    }

    /**
     * Returns the cards the player has collected during the game.
     *
     * @return an {@link EnumMap} of collected cards, grouped by {@link Colour}
     */
    public EnumMap<Colour, ArrayList<Card>> getCollectedCards() {
        return this.collectedCards;
    }

    public abstract Card chooseCard();
    /**
     * Removes a card from the player's hand and plays it.
     *
     * @param c the card to be played
     * @return the played {@link Card}
     */
    public Card playCard(Card c) {
        this.hand.remove(c);
        return c; 
    }

    /**
     * Adds a card to the player's hand.
     *
     * @param c the card to be added
     * @throws EndGameException if there are no more cards in the deck
     */
    public void addCard(Card c) throws EndGameException {
        if (c == null) throw new EndGameException("There are no more cards in the deck. ");
        this.hand.add(c);
    }

    /**
     * Collects a list of cards and adds them to the player's collection.
     * <p>
     * If a player collects at least one card of all six colors, the game ends.
     * </p>
     *
     * @param cards the list of cards to be collected
     * @throws EndGameException if the player collects all 6 colors
     */
    public void collectCard(ArrayList<Card> cards) throws EndGameException {
        collectCard(cards, false); // assume that it is not end game
    }

    public void collectCard(ArrayList<Card> cards, boolean endGame) throws EndGameException {
        for (Card c : cards) {
            Colour curColour = c.getCardColour();

            if (!collectedCards.containsKey(curColour)) {
                collectedCards.put(curColour, new ArrayList<>()); // Initialize list for new color
            }

            collectedCards.get(curColour).add(c);
        }

        if (!endGame) {
            if (collectedCards.size() == 6) {
                throw new EndGameException("Player has collected all 6 colors! ");
            }
        }
    }

}