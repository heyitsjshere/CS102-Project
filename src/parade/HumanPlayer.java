package parade;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.Random;
import java.util.Timer;

/**
 * Represents a human player in the Parade game.
 * <p>
 * This class extends {@link Player} and allows a human user to 
 * manually select a card to play based on user input.
 * </p>
 *
 * <p>Example usage:</p>
 * <pre>
 *     HumanPlayer player = new HumanPlayer("Alice");
 *     Card selectedCard = player.chooseCard();
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

    public Card chooseCardTimed() {
        System.out.println("PICK A CARD");
        System.out.println("-----------");

        int i = 1;
        for (Card c : super.getHand()) {
            System.out.println("Option " + i + ": " + c);
            i++;
        }

        // Create a countdown timer with a 5-second limit
        CountdownTimer timer = new CountdownTimer();
        int selectedNum = input.getUserIntWithTimeout("Selection: Option ", 1, super.getHandSize(), 5000, timer);

        // If no valid selection was made (timeout or invalid input), return a random card
        if (selectedNum == -1) {
            Random random = new Random();
            return super.getHand().get(random.nextInt(super.getHandSize()));
        }
        return super.getHand().get(selectedNum - 1);
    }

    // public Card chooseCardTimed() {
    //     System.out.println("PICK A CARD (You have 5 seconds!)");
    //     System.out.println("-----------");

    //     int i = 1;
    //     for (Card c : super.getHand()) {
    //         System.out.println("Option " + i + ": " + c);
    //         i++;
    //     }

    //     Integer selectedNum = input.getUserIntWithTimeout("Selection: Option ", 1, super.getHandSize(), 5000);

    //     if (selectedNum != null) {
    //         return super.getHand().get(selectedNum - 1);
    //     } else {
    //         // If time expired or invalid input
    //         System.out.println("Selecting a random card for you...");
    //         return super.getHand().get(new Random().nextInt(super.getHandSize()));
    //     }
    // }

}