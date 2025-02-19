package parade;

import java.util.ArrayList;

public class HumanPlayer extends Player {

    public HumanPlayer(ArrayList<Card> initialHand) {
        super(initialHand);
    }

    public Card playCard(int i){
        return super.getHand().get(i);

        // return new Card(1, Colour.YELLOW);
    }


    
}
