package parade;

import parade.enums.*;

import java.util.ArrayList;
import java.util.EnumMap;

/**
 * Represents a player in the Parade game.
 * <p>
 * This is an abstract class that defines common attributes and behaviors 
 * for all players, including their hand of cards and collected cards.
 * </p>
 */
public abstract class Player {
    // change to abstract later
    
    // private String name; // honestly optional but i think can

     /** The player's current hand of cards. */
    private ArrayList<Card> hand; // current hand

    /** The collection of cards the player has acquired, grouped by color. */
    private EnumMap<Colour, ArrayList<Card>> collectedCards; 

    /**
     * Constructs a player with an initial hand of cards.
     *
     * @param initialHand the initial set of cards given to the player
     */
    public Player(ArrayList<Card> initialHand) {
        this.hand = initialHand;
        this.collectedCards = new EnumMap<>(Colour.class); // new empty enum map
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
    public EnumMap<Colour, ArrayList<Card>> getCollectedCards(){
        return this.collectedCards;
    }

    /**
     * Removes a card from the player's hand and plays it.
     * <p>
     * The selected card is removed from the player's hand and 
     * returned so that it can be added to the parade.
     * </p>
     *
     * @param c the card to be played
     * @return the played {@link Card}
     */

    public Card playCard (Card c) { // remove card from hand

        this.hand.remove(c);
        return c; // return card so it can be added to the parade

    } // depends on human or bot


    /**
     * Adds a card to the player's hand.
     *
     * @param c the card to be added
     */
    public void addCard (Card c) { // add card from deck
        this.hand.add(c);
    } 

    /**
     * Collects a list of cards and adds them to the player's collection.
     * <p>
     * Each collected card is stored in the {@code collectedCards} map based on its color.
     * </p>
     *
     * @param cards the list of cards to be collected
     */
    public void collectCard(ArrayList<Card> cards) { // add many cards to collection
        for (Card c : cards) {
            // add card to collection
            // but need to remove card from parade too!
            Colour curColour = c.getCardColour();

            if (!collectedCards.containsKey(curColour)) { // if first of that colour
                collectedCards.put(curColour, new ArrayList<Card>());  // add it to map and create list
            }
            
            collectedCards.get(curColour).add(c);
        }
    }

}
