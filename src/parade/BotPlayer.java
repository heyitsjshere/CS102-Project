package parade;

import java.util.Random;

/**
 * Represents a bot player in the Parade game.
 * <p>
 * A {@code BotPlayer} is a non-human player that makes automated moves.
 * This bot uses a simple random selection strategy to choose cards from its hand.
 * It inherits from the {@link Player} class and overrides the {@code chooseCard()} method.
 * </p>
 *
 * <p><strong>Example usage:</strong></p>
 * <pre>{@code
 * BotPlayer bot = new BotPlayer("Bot 1");
 * Card selectedCard = bot.chooseCard();
 * System.out.println("Bot chose: " + selectedCard);
 * }</pre>
 *
 * @author G3T7
 * @version 1.0
 * @see Player
 */
public class BotPlayer extends Player {

    /**
     * Constructs a new {@code BotPlayer} with the specified name.
     *
     * @param name the name of the bot player
     */
    public BotPlayer(String name) {
        super(name);
    }

    /**
     * Selects a random card from the bot's hand.
     * <p>
     * The method randomly selects an index between 0 and the hand size,
     * and returns the card at that index.
     * </p>
     *
     * @return the randomly selected {@link Card} from the bot's hand
     */
    @Override
    public Card chooseCard() {
        Random random = new Random();
        return this.getHand().get(random.nextInt(this.getHandSize()));
    }
}