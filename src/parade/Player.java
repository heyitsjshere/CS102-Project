package parade;

import parade.enums.*;

import java.util.ArrayList;
import java.util.EnumMap;

public class Player {
    // change to abstract later
    
    // what do players have? hand. collection. aaaa
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
    

    public static void main(String[] args) {
        ArrayList<Card> initialHand = new ArrayList<>();

        Deck d = new Deck();
        for (int i = 0; i < 5; i++) initialHand.add(d.drawCard());

        Player p1 = new Player(initialHand);
        System.out.println(p1.getHand());
        System.out.println(p1.getCollectedCards());
    }

    


}
