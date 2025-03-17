package parade;

import parade.enums.*;

/**
 * Represents a card in the Parade game.
 * <p>
 * Each card has a numeric value and a color, defined by the {@link Colour}
 * enum.
 * This class provides methods to access the card's properties and a string
 * representation of the card.
 * </p>
 */

public class Card {
    private int num;
    private Colour colour;

    /**
     * Constructs a new card with the specified number and color.
     *
     * @param n the numeric value of the card
     * @param c the color of the card, from the {@link Colour} enum
     */
    public Card(int n, Colour c) {
        this.num = n;
        this.colour = c;
    }

    /**
     * Retrieves the numeric value of the card.
     *
     * @return the card's number
     */
    public int getCardNum() {
        return this.num;
    }

    /**
     * Retrieves the color of the card.
     *
     * @return the card's color as a {@link Colour}
     */
    public Colour getCardColour() {
        return this.colour;
    }

    /**
     * Returns a string representation of the card.
     * <p>
     * The format is "{Colour} {Number}", for example: "RED 5".
     * </p>
     *
     * @return a string describing the card's color and number
     */
    @Override
    public String toString() {
        return "" + this.colour + " " + this.num;
    }

}