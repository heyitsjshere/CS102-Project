package parade;

import java.util.ArrayList;

public class Game {

    // assume here game starts, everything tested here for now
    public static void main (String[] args) {
        ArrayList<Card> initialHand1 = new ArrayList<>();
        ArrayList<Card> initialHand2 = new ArrayList<>();

        Deck d = new Deck();
        for (int i = 0; i < 5; i++) {
            initialHand1.add(d.drawCard());
            initialHand2.add(d.drawCard());

        }
    
        Player p1 = new Player(initialHand1);
        Player p2 = new Player(initialHand2);

        System.out.println(p1.getHand());
        System.out.println(p1.getCollectedCards());

        System.out.println("Size of deck: " + d.getSize()); // 56

        // now p1 picks one card, card is removed from player's hand

        // card is added to the parade

        // get cards in removal mode

        // eligible cards are added to player's collection

        // player draws a card

        // next player's turn
        
    }
    
}
