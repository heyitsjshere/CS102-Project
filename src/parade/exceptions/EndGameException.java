package parade.exceptions;

/**
 * Exception thrown when the Parade game ends due to specific conditions.
 * <p>
 * This exception is triggered when a player collects all six colors 
 * or when the deck runs out of cards.
 */
public class EndGameException extends java.lang.Exception {
    
    /**
     * Constructs a new EndGameException with no message.
     */
    public EndGameException() {}

    /**
     * Constructs a new EndGameException with a specified message.
     *
     * @param s the message describing the reason for the exception
     */
    public EndGameException(String s) {
        super(s);
    } 
}