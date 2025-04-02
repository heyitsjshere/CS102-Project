package parade;

import java.util.ArrayList;
import parade.enums.Colour;

/**
 * Manages the state of the parade in the Parade game.
 * <p>
 * The parade is a central row of cards that players interact with by adding
 * cards to it and collecting from it based on specific rules. This class handles:
 * <ul>
 *   <li>Initializing the parade with cards</li>
 *   <li>Tracking current parade state</li>
 *   <li>Determining which cards are removable or collectible</li>
 *   <li>Modifying the parade when new cards are played</li>
 * </ul>
 *
 * <p><strong>Example usage:</strong></p>
 * <pre>{@code
 * Deck deck = new Deck();
 * Parade parade = new Parade(deck);
 * Card playerCard = new Card(3, Colour.BLUE);
 * ArrayList<Card> collected = parade.getCollectibleCards(playerCard);
 * parade.addCard(playerCard);
 * }</pre>
 * 
 * @author G3T7
 * @version 1.0
 */
public class Parade {

    /** The list of cards currently in the parade. */
    private ArrayList<Card> curParade;

    /** The number of cards initially placed in the parade. */
    private static final int INITIAL_PARADE_SIZE = 6;

    /**
     * Constructs a new Parade and populates it with initial cards drawn from the deck.
     *
     * @param d the {@link Deck} from which the parade is initialized
     */
    public Parade(Deck d) {
        this.curParade = new ArrayList<Card>();
        for (int i = 0; i < INITIAL_PARADE_SIZE; i++)
            curParade.add(d.drawCard());
    }

    /**
     * Returns the current list of cards in the parade.
     *
     * @return an {@link ArrayList} of {@link Card} objects currently in the parade
     */
    public ArrayList<Card> getParade() {
        return this.curParade;
    }

    /**
     * Determines which cards are in the "removable zone" based on the played card's number.
     * <p>
     * These cards are eligible to be collected if they meet additional conditions.
     * This method does not remove them from the parade.
     * </p>
     *
     * @param p the card the player is about to play
     * @return a list of cards in the removable zone
     */
    private ArrayList<Card> getRemoveableCard(Card p) { // all cards that are removable, but not necessarily removed
        int numRemovable = curParade.size() - p.getCardNum() - 1;
        ArrayList<Card> toReturn = new ArrayList<>();

        if (numRemovable >= 1) {
            toReturn.addAll(this.curParade.subList(0, numRemovable));
        }
        return toReturn;
    }

    /**
     * Determines which cards should be collected based on the card being played.
     * <p>
     * A player must collect removable cards if:
     * <ul>
     *   <li>The card has the same color as the played card</li>
     *   <li>OR its number is less than or equal to the played card's number</li>
     * </ul>
     * The method removes collectible cards from the parade and returns them.
     *
     * @param p the card being added to the parade
     * @return a list of {@link Card} objects that the player must collect
     */
    public ArrayList<Card> getCollectibleCards(Card p) {
        ArrayList<Card> toReturn = new ArrayList<Card>();

        int cardNum = p.getCardNum();
        Colour cardColour = p.getCardColour();

        for (Card c : getRemoveableCard(p)) {
            if (c.getCardColour().equals(cardColour) || c.getCardNum() <= cardNum) {
                this.curParade.remove(c);
                toReturn.add(c);
            }
        }

        return toReturn;
    }

    /**
     * Adds a new card to the end of the parade.
     *
     * @param c the {@link Card} to be added
     */
    public void addCard(Card c) {
        this.curParade.add(c);
    }
}