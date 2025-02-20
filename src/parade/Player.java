package parade;

import parade.enums.*;

import java.util.ArrayList;
import java.util.EnumMap;

public abstract class Player {
    // change to abstract later
    
    private String name; // honestly optional but i think can

    private ArrayList<Card> hand; // current hand
    private EnumMap<Colour, Card> collectedCards; 
    
    public Player(ArrayList<Card> initialHand) {
        this.hand = initialHand;
        this.collectedCards = new EnumMap<>(Colour.class); // new empty enum map
    }

    public ArrayList<Card> getHand() {
        return this.hand;
    }

    public EnumMap<Colour, Card> getCollectedCards(){
        return this.collectedCards;
    }

    public Card playCard (Card c) { // remove card from hand

        this.hand.remove(c);
        return c; // return card so it can be added to the parade

    } // depends on human or bot

    public void addCard (Card c) { // add card from deck
        this.hand.add(c);
    } 

    public void collectCard(ArrayList<Card> cards) { // add one card to collection
        for (Card c : cards) {
            // add card to collection
            // but need to remove card from parade too!
            collectedCards.put(c.getCardColour(), c);
        }
    }



}
