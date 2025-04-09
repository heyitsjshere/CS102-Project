package util;

/**
 * Signals that the Parade game should end due to specific conditions.
 * <p>
 * This exception is thrown when either:
 * <ul>
 * <li>A player collects all six colors of cards.</li>
 * <li>The deck is depleted and no more cards can be drawn.</li>
 * </ul>
 * This exception allows the main game loop to transition into the endgame
 * phase.
 * 
 * <p>
 * <strong>Usage:</strong> Thrown and caught within the game logic to gracefully
 * handle endgame scenarios.
 * </p>
 * 
 * @see java.lang.Exception
 * @author G3T7
 * @version 1.0
 */
public class EndGameException extends java.lang.Exception {

    /**
     * Constructs a new {@code EndGameException} with no detail message.
     */
    public EndGameException() {
    }

    /**
     * Constructs a new {@code EndGameException} with the specified detail message.
     *
     * @param s the message describing the reason for the exception
     */
    public EndGameException(String s) {
        super(s);
    }
}