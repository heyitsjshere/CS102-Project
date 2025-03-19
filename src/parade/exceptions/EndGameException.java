package parade.exceptions;

/**
 * Exception class to indicate that the game has ended due to specific conditions.
 * <p>
 * This exception is thrown when the game-ending criteria are met, such as:
 * <ul>
 *   <li>A player collects at least one card of all six colors.</li>
 *   <li>The deck is depleted, preventing further draws.</li>
 * </ul>
 * </p>
 *
 * <p>Example usage:</p>
 * <pre>
 *     if (player.hasAllColours()) {
 *         throw new EndGameException("Player has collected all six colors!");
 *     }
 * </pre>
 *
 * @author CS102G3T7
 * @version 1.0
 */
public class EndGameException extends java.lang.Exception {

    /**
     * Constructs a new {@code EndGameException} with no detail message.
     */
    public EndGameException() {
        super();
    }

    /**
     * Constructs a new {@code EndGameException} with a specified detail message.
     *
     * @param message The detail message describing the reason for the exception.
     */
    public EndGameException(String message) {
        super(message);
    }
}