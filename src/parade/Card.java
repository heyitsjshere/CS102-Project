package parade;

import parade.enums.Colour;

/**
 * Represents a card in the Parade game.
 * <p>
 * Each card has a numeric value (from 0 to 10) and a color,
 * defined by the {@link Colour} enum.
 * </p>
 *
 * <p>
 * This class provides methods to access a card's number and color,
 * and formats the card nicely with ANSI color codes for terminal display.
 * </p>
 *
 * <p><strong>Example usage:</strong></p>
 * <pre>{@code
 * Card card = new Card(5, Colour.RED);
 * System.out.println(card); // Outputs: RED 5 (in red color)
 * }</pre>
 *
 * @author G3T7
 * @version 1.0
 */
public class Card {

    /** The numeric value of the card (range: 0 to 10). */
    private int num;

    /** The color of the card, represented by the {@link Colour} enum. */
    private Colour colour;

    /**
     * Constructs a new {@code Card} with the specified number and color.
     *
     * @param n the numeric value of the card
     * @param c the color of the card
     */
    public Card(int n, Colour c) {
        this.num = n;
        this.colour = c;
    }

    /**
     * Returns the numeric value of this card.
     *
     * @return the card's number
     */
    public int getCardNum() {
        return this.num;
    }

    /**
     * Returns the color of this card.
     *
     * @return the card's color
     */
    public Colour getCardColour() {
        return this.colour;
    }

    /**
     * Returns a formatted string representation of this card.
     * <p>
     * The format is "{Colour} {Number}" (e.g., "BLUE 7") with the appropriate
     * ANSI color applied for supported terminals.
     * </p>
     *
     * @return a color-formatted string representing the card
     */
    @Override
    public String toString() {
        return this.colour.getColorCode() + this.colour + " " + this.num + "\u001B[0m";
    }
}