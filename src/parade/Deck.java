package parade;

import parade.enums.*;
import parade.Card; // TODO! package this better

import java.util.ArrayList;
import java.util.Collections;

public class Deck {

    private ArrayList<Card> cardsInDeck;

    // deck of card is standard so a static array suffices
    // generating each time is possible but why would u do that
    private static final ArrayList<Card> DECK_REFERENCE = new ArrayList<Card>() {{
        for (Colour c : Colour.values()) {  // for each colour
            for (int i = 0; i <= 10; i++) { // for range 0-10 (inclusive)
                add(new Card(i, c));  // create the card!
            }
        }
    }};

    public Deck() {
        // should create an instance of a deck that can be shuffled and all that
        this.cardsInDeck = generateShuffledDeck(); // create an array of cards in a shuffled order
    }

    private ArrayList<Card> generateShuffledDeck() {
        cardsInDeck = new ArrayList<Card>();
        // cardsInDeck.add(new Card(1, Colour.YELLOW));
        cardsInDeck.addAll(DECK_REFERENCE);
        Collections.shuffle(cardsInDeck);
        return this.cardsInDeck;
    }

    public ArrayList<Card> getDeckReference() {
        // used for testing only
        return DECK_REFERENCE;
    }

    public ArrayList<Card> getCurrentDeck() {
        // used for testing only, will not need to see entire deck at once
        return this.cardsInDeck;
    }

    public Card drawCard(){
        // draw card from the top of the deck
        return this.cardsInDeck.removeFirst();
    }

    public int getSize() {
        return cardsInDeck.size();
    }


    public static void main(String[] args) {
        Deck d = new Deck();
        // for (Card c : d.getCurrentDeck()) {
        //     System.out.println(c);
        // }
        System.out.println(d.drawCard());
        System.out.println("Size of deck: " + d.getSize());

        System.out.println(d.drawCard());
        System.out.println(d.drawCard());
        System.out.println(d.drawCard());
        System.out.println("Size of deck: " + d.getSize());

    } 

    



}
