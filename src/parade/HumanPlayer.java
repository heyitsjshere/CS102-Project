package parade;

/**
 * Represents a human player in the Parade game.
 * <p>
 * This class extends {@link Player} and allows a human user to
 * manually select a card to play based on user input.
 * </p>
 *
 * <p>
 * Example usage:
 * </p>
 * 
 * <pre>
 * HumanPlayer player = new HumanPlayer("Alice");
 * Card selectedCard = player.chooseCard();
 * </pre>
 *
 * @author G3T7
 * @version 1.0
 */
public class HumanPlayer extends Player {

    /** The input handler for user interactions. */
    private UserInput input;

    /**
     * Constructs a human player with the specified name.
     *
     * @param name the name of the human player
     */
    public HumanPlayer(String name) {
        super(name);
        input = new UserInput();
    }

    /**
     * Allows the human player to manually choose a card from their hand.
     * <p>
     * The method displays the available cards, prompts the user for input,
     * and returns the selected card.
     * </p>
     *
     * @return the selected {@link Card} from the player's hand
     */
    @Override
    public Card chooseCard() {
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