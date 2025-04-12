package parade.cards;

/**
 * Enum representing the six distinct colours used in the Parade game.
 * <p>
 * Each colour is mapped to its corresponding ANSI escape code to enable
 * coloured output in the terminal during gameplay.
 * </p>
 *
 * <p><strong>Usage Example:</strong></p>
 * <pre>
 * Colour c = Colour.RED;
 * System.out.println(c.getColourCode() + "This is red text" + "\033[0m");
 * </pre>
 * 
 * <p><strong>Note:</strong> Use the {@code "\033[0m"} ANSI reset code after
 * printing coloured text to return to the default console coluor.</p>
 * 
 * @author G3T7
 * @version 1.0
 */
public enum Colour {

    /**
     * Yellow colour with ANSI code.
     */
    YELLOW("\u001B[33m"),

    /**
     * Red colour with ANSI code.
     */
    RED("\u001B[31m"),

    /**
     * Green colour with ANSI code.
     */
    GREEN("\u001B[32m"),

    /**
     * Grey colour with ANSI code.
     */
    GREY("\u001B[90m"),

    /**
     * Purple colour with ANSI code.
     */
    PURPLE("\u001B[35m"),

    /**
     * Blue colour with ANSI code.
     */
    BLUE("\u001B[34m");

    /**
     * ANSI escape code associated with this colour.
     */
    private final String colourCode;

    /**
     * Constructs a {@code Colour} enum with its associated ANSI escape code.
     *
     * @param colourCode The ANSI code used for terminal text formatting.
     */
    Colour(String colourCode) {
        this.colourCode = colourCode;
    }

    /**
     * Retrieves the ANSI escape code for this colour.
     *
     * @return A {@code String} representing the ANSI escape code.
     */
    public String getColourCode() {
        return colourCode;
    }
}