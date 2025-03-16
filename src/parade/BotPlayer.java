package parade;

import java.util.Random;

/**
 * Represents a bot player in the Parade game.
 * <p>
 * The bot automatically selects a card to play based on a simple random selection strategy.
 * </p>
 */
public class BotPlayer extends Player {

    /**
     * Constructs a bot player.
     */
    public BotPlayer() {
        super();
    }

    /**
     * Selects a random card from the bot's hand.
     * <p>
     * This method randomly picks a card index within the bot's current hand size.
     * </p>
     *
     * @return the randomly selected {@link Card} to play
     */
    @Override
    public Card chooseCard() {
        Random random = new Random();
        if (this.getHand().isEmpty()) {
            return null; // Handle edge case where bot has no cards
        }
        return this.getHand().get(random.nextInt(this.getHandSize())); // Picks a valid index (0 to size-1)
    }
}