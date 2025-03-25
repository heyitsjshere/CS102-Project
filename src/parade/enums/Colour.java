package parade.enums;

/**
 * Represents the different colors available in the Parade game.
 * <p>
 * Each color is associated with an ANSI escape code to enable colored text output in the console.
 * </p>
 *
 * <p>Example usage:</p>
 * <pre>
 *     Colour c = Colour.RED;
 *     System.out.println(c.getColorCode() + "This is red text" + "\033[0m");
 * </pre>
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
    BLUE("\u001B[34m"); // ANSI code for blue

    /**
     * ANSI escape code for displaying the color in the console.
     */
    private final String colorCode;

    /**
     * Constructs a {@code Colour} enum with its corresponding ANSI escape code.
     *
     * @param colorCode The ANSI escape code for the color.
     */
    Colour(String colorCode) {
        this.colorCode = colorCode;
    }

    /**
     * Returns the ANSI escape code associated with this color.
     *
     * @return A {@code String} representing the ANSI escape code.
     */
    public String getColorCode() {
        return colorCode;
    }
}