package parade;

import java.util.Random;

/**
 * Represents a bot player in the Parade game.
 * <p>
 * This bot player automatically selects a card from its hand based on a simple random selection strategy.
 * It extends the {@link Player} class and overrides the {@code chooseCard()} method to make an automated move.
 * </p>
 *
 * <p>Example usage:</p>
 * <pre>
 *     BotPlayer bot = new BotPlayer("Bot1");
 *     Card selectedCard = bot.chooseCard();
 *     System.out.println("Bot chose: " + selectedCard);
 * </pre>
 *
 * @author G3T7
 * @version 1.0
 */
public class BotPlayer extends Player {

    /**
     * Constructs a bot player with the given name.
     *
     * @param name The name of the bot player.
     */
    public BotPlayer(String name) {
        super(name);
    }

    /**
     * Selects a random card from the bot's hand.
     * <p>
     * This method generates a random index and picks a card from the bot's hand.
     * If the hand is empty, it returns {@code null}.
     * </p>
     *
     * @return the randomly selected {@link Card} from the bot's hand, or {@code null} if the hand is empty.
     */
    @Override
    public Card chooseCard() {
        Random random = new Random();
        // if (this.getHand().isEmpty()) {
        //     return null; // Handle edge case where bot has no cards
        // }
        return this.getHand().get(random.nextInt(this.getHandSize())); // Picks a valid index (0 to size-1)
    }
}