package parade;

import java.util.ArrayList;
import parade.enums.*;

public class Parade {

    private ArrayList<Card> curParade; 

    
    public Parade(ArrayList<Card> initialParade) {
        this.curParade = initialParade;
    }

    public ArrayList<Card> getParade() {
        return this.curParade;
    }

    public ArrayList<Card> getRemoveable(Card p) { // all cards that are removable, but not necessarily removed
        
        // if doing animations, may need this (can show which is the removeable stack)
        // if not doing animations or not presenting this information, change to private

        int numRemovable = curParade.size() - p.getCardNum();
        ArrayList<Card> toReturn = new ArrayList<>();

        if (numRemovable >= 1) { // more cards in parade than the card number
            // means that there is something in the removable section
            toReturn.addAll(this.curParade.subList(0, numRemovable));
        } 
        return toReturn; 
    }

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
