package parade;

import java.util.ArrayList;
import parade.enums.*;

/**
 * Represents the parade in the Parade game.
 * <p>
 * The parade consists of a list of cards in play. Players can add cards to the parade,
 * and certain cards may be removed based on game rules.
 * </p>
 */

public class Parade {

    /** The list of cards currently in the parade. */
    private ArrayList<Card> curParade; 

    /**
     * Constructs a Parade with an initial list of cards.
     *
     * @param initialParade the initial state of the parade. Should pass in an ArrayList of 6 cards
     */
    public Parade(Deck d) {
        this.curParade = new ArrayList<Card>();
        for (int i = 0; i < 6; i++) curParade.add(d.drawCard());
    }

    /**
     * Returns the current list of cards in the parade.
     *
     * @return the list of cards in the parade
     */
    public ArrayList<Card> getParade() {
        return this.curParade;
    }

    /**
     * Determines which cards are removable based on the given card.
     * <p>
     * This method returns a list of cards that are considered removable based
     * on the given card's number. It does not actually remove them.
     * </p>
     *
     * @param p the card being added to the parade
     * @return a list of removable cards
     */
    private ArrayList<Card> getRemoveable(Card p) { // all cards that are removable, but not necessarily removed
        int numRemovable = curParade.size() - p.getCardNum();
        ArrayList<Card> toReturn = new ArrayList<>();

        if (numRemovable >= 1) { // more cards in parade than the card number
            // means that there is something in the removable section
            toReturn.addAll(this.curParade.subList(0, numRemovable));
        } 
        return toReturn; 
    }

    /**
     * Determines which cards should be collected by the player.
     * <p>
     * A player collects cards from the removable stack if:
     * <ul>
     *   <li>The card has the same color as the added card.</li>
     *   <li>The card has a number less than or equal to the added card.</li>
     * </ul>
     * The method removes the collectible cards from the parade and returns them.
     * </p>
     *
     * @param p the card being added to the parade
     * @return a list of collectible cards that the player must take
     */
    public ArrayList<Card> getCollectibleCards(Card p) {
        ArrayList<Card> toReturn = new ArrayList<Card>();

        int cardNum = p.getCardNum();
        Colour cardColour = p.getCardColour();
         
        for (Card c: getRemoveable(p)) { // for each card in the removable stack
            if (c.getCardColour().equals(cardColour) || // if card colour is equal
                c.getCardNum() <= cardNum) { // or if card number is equal
                    this.curParade.remove(c); // remove the card from the parade
                    toReturn.add(c); // add that card to the list we are about to hand over to the player
            }
        }

        return toReturn; 
    }
    
     /**
     * Adds a new card to the parade.
     *
     * @param c the card to be added to the parade
     */
    public void addCard(Card c) {
        this.curParade.add(c);
    }

    // public String toString() {
    //     String toReturn = "";
    //     for (Card c : curParade) {
    //         toReturn += c.toString(); // c also has a toString?? will it work??
    //     }

    //     return toReturn;
    // }



    
}
