package parade;
import parade.enums.*;

public class Card {
    private int num;
    private Colour colour;

    // constructor
    public Card (int n, Colour c) {
        this.num = n;
        this.colour = c;
    }

    public int getCardNum() {
        return this.num;
    }

    public Colour getCardColour() {
        return this.colour;
    }

    @Override
    public String toString() {
        return "" + this.colour + " " + this.num ;
    }


}