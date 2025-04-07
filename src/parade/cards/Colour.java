package parade.cards;

/**
 * Enum representing the six distinct colors used in the Parade game.
 * <p>
 * Each color is mapped to its corresponding ANSI escape code to enable
 * colored output in the terminal during gameplay.
 * </p>
 *
 * <p><strong>Usage Example:</strong></p>
 * <pre>
 * Colour c = Colour.RED;
 * System.out.println(c.getColorCode() + "This is red text" + "\033[0m");
 * </pre>
 * 
 * <p><strong>Note:</strong> Use the {@code "\033[0m"} ANSI reset code after
 * printing colored text to return to the default console color.</p>
 * 
 * @author G3T7
 * @version 1.0
 */
public enum Colour {

    /**
     * Yellow color with ANSI code.
     */
    YELLOW("\u001B[33m"),

    /**
     * Red color with ANSI code.
     */
    RED("\u001B[31m"),

    /**
     * Green color with ANSI code.
     */
    GREEN("\u001B[32m"),

    /**
     * Black color with ANSI code.
     */
    BLACK("\u001B[30m"),

    /**
     * Purple color with ANSI code.
     */
    PURPLE("\u001B[35m"),

    /**
     * Blue color with ANSI code.
     */
    BLUE("\u001B[34m");

    /**
     * ANSI escape code associated with this color.
     */
    private final String colorCode;

    /**
     * Constructs a {@code Colour} enum with its associated ANSI escape code.
     *
     * @param colorCode The ANSI code used for terminal text formatting.
     */
    Colour(String colorCode) {
        this.colorCode = colorCode;
    }

    /**
     * Retrieves the ANSI escape code for this color.
     *
     * @return A {@code String} representing the ANSI escape code.
     */
    public String getColorCode() {
        return colorCode;
    }
}