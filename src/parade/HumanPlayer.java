package parade;

/**
 * Represents a human player in the Parade game.
 * <p>
 * This class extends {@link Player} and allows a human user to
 * manually select a card from their hand based on console input.
 * </p>
 *
 * <p><strong>Example usage:</strong></p>
 * <pre>{@code
 * HumanPlayer player = new HumanPlayer("Alice");
 * Card selectedCard = player.chooseCard();
 * }</pre>
 *
 * @author G3T7
 * @version 1.0
 */
public class HumanPlayer extends Player {

    /** Handles user input from the console. */
    private UserInput input;

    /**
     * Constructs a human player with the given name.
     *
     * @param name the name of the player
     */
    public HumanPlayer(String name) {
        super(name);
        input = new UserInput();
    }

    /**
     * Prompts the player to select a card from their hand.
     * <p>
     * Displays a numbered list of card options in the console and retrieves a valid selection
     * from the user based on numeric input.
     * </p>
     *
     * @return the {@link Card} selected by the player
     */
    @Override
    public Card chooseCard() {
        // to render emojis correctly
        System.setProperty("file.encoding", "UTF-8");

        System.out.println("PICK A CARD");
        System.out.println("-----------");

        int i = 1;
        for (Card c : super.getHand()) {
            System.out.println("Option " + i + ": " + c);
            i++;
        }
        
        int selectedNum = input.getUserInt("Selection: Option ", 1, super.getHandSize());

        return super.getHand().get(selectedNum - 1);
    }
}