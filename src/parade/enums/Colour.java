package parade.enums;

/**
 * Represents the different colors available in the Parade game.
 */
public enum Colour {
    YELLOW("\u001B[33m"),
    RED("\u001B[31m"),
    GREEN("\u001B[32m"),
    BLACK("\u001B[30m"),
    PURPLE("\u001B[35m"),
    BLUE("\u001B[34m"); // ANSI code for blue

    private final String colorCode;

    Colour(String colorCode) {
        this.colorCode = colorCode;
    }

    public String getColorCode() {
        return colorCode;
    }
}
