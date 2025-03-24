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
    BLUE("\u001B[34m");

    private final String ansiCode;
    private static final String RESET = "\u001B[0m";

    Colour(String ansiCode) {
        this.ansiCode = ansiCode;
    }

    public String colorize(String text) {
        return ansiCode + text + RESET;
    }

    @Override
    public String toString() {
        return ansiCode + name() + RESET;
    }
}
