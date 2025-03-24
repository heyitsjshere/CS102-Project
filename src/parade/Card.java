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

    public String displayCard() {
        String value;
        switch (num) {
            case 1:
                value = "A";
                break;
            case 11:
                value = "J";
                break;
            case 12:
                value = "Q";
                break;
            case 13:
                value = "K";
                break;
            default:
                value = String.valueOf(num);
                break;
        }

        String symbol;
        switch (colour) {
            case RED:
                symbol = "♥";
                break;
            case BLUE:
                symbol = "♣";
                break;
            case GREEN:
                symbol = "♠";
                break;
            case YELLOW:
                symbol = "♦";
                break;
            case PURPLE:
                symbol = "★";
                break;
            case BLACK:
                symbol = "⬥";
                break;
            default:
                symbol = "?";
                break;
        }

        // Padding to align properly (max 2-character values like "10")
        String topLeft = String.format("%-2s", value);
        String bottomRight = String.format("%2s", value);

        return "┌──────┐\n" +
                "│ " + topLeft + "    │\n" +
                "│      │\n" +
                "│  " + symbol + "   │\n" +
                "│      │\n" +
                "│    " + bottomRight + " │\n" +
                "└──────┘";
    }

}