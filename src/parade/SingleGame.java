package parade;

import java.util.ArrayList;

import parade.enums.Colour;
import parade.exceptions.EndGameException;

/**
 * Manages a single full session of the Parade game.
 * <p>
 * This class handles the lifecycle of a single game round including:
 * <ul>
 *   <li>Initial card dealing</li>
 *   <li>Turn-by-turn game logic</li>
 *   <li>Endgame condition checks</li>
 *   <li>Final discard and scoring</li>
 *   <li>Displaying player results</li>
 * </ul>
 * 
 * <p>It integrates deck usage, parade updates, and player interactions, including
 * both human and bot players.</p>
 * 
 * @author G3T7
 * @version 1.0
 */
public class SingleGame {
    private Parade par;
    private Deck d; 
    private boolean endGame;
    private PlayerList playerList;
    private int turn;

    /** Number of cards each player starts with. */
    private static final int INITIAL_HAND_SIZE = 5;

    /**
     * Constructs a new {@code SingleGame} session and deals initial cards to all players.
     *
     * @param playerList The list of players participating in this game session.
     */
    public SingleGame(PlayerList playerList){
        this.d = new Deck();
        this.par = new Parade(d);
        this.endGame = false;
        this.playerList = playerList;
        this.turn = 0;

        dealInitialCards();
        // startGame();
    }

    /**
     * Deals the initial set of cards to each player.
     * <p>
     * Each player receives {@code INITIAL_HAND_SIZE} cards drawn from the deck.
     * Terminates the program if the deck runs out too early.
     * </p>
     */
    private void dealInitialCards() {
        for (int i = 0; i < INITIAL_HAND_SIZE; i++) {
            for (Player p : playerList.getPlayerList()) {
                try {
                    p.addCard(d.drawCard());
                } catch (EndGameException e) {
                    System.out.println("There are not enough cards to start the game.");
                    System.exit(-1);
                }
            }
        }
    }

    /**
     * Calculates the current round number based on the number of turns taken.
     *
     * @return The current round number.
     */
    private int getRound(){
        return turn/playerList.getNumberOfPlayers() + 1;
    }

    /**
     * Executes the full game loop for a single session.
     * <p>
     * This method handles:
     * <ul>
     *   <li>Player turns and card selection</li>
     *   <li>Adding cards to the parade</li>
     *   <li>Collecting based on game rules</li>
     *   <li>Drawing new cards</li>
     *   <li>Handling endgame triggers</li>
     *   <li>Final discard and scoring phase</li>
     * </ul>
     *
     * @return The list of {@link Player}s who won this game session.
     */
    public ArrayList<Player> run() {
        while (playerList.getPlayer(turn).getHandSize() == 5) { 
                Player curPlayer = playerList.getPlayer(turn);
                try {
                    // Display round number before first turn of that round
                    if (turn++ % playerList.getNumberOfPlayers() == 0) {
                        System.out.println("\n\n==== ROUND " + getRound() + " ====");
                    }

                    System.out.println("\n||  " + curPlayer.getName() + "'s turn  ||");

                    String bold = "\u001B[1m";
                    String reset = "\u001B[0m";
                    System.out.println("Parade: " + par.getParade() + " \u001B[36m<== " + bold + "Card inserted here" + reset);
                    
                    // Print only during main phase (before endgame triggered)
                    if (!endGame) {
                        System.out.println("\u001B[33mCards left in deck: " + d.getSize() + "\u001B[0m");
                    }

                    // If current player is human, print their collection
                    if (curPlayer instanceof HumanPlayer) {
                        System.out.println("\nYour current collection:");

                        boolean hasCollectedCards = false;
                        for (Colour c : Colour.values()) {
                            ArrayList<Card> cardsOfColour = curPlayer.getCollectedCardsWithColour(c);
                            if (cardsOfColour != null && !cardsOfColour.isEmpty()) {
                                hasCollectedCards = true;
                                break;
                            }
                        }

                        if (hasCollectedCards) {
                            curPlayer.printCollectedCards(false);
                        } else {
                            System.out.println("You have no cards in your collection yet.");
                        }
                    }

                    // Delay output for bot players
                    if (curPlayer instanceof BotPlayer){
                        Game.delayMessageWithDots(curPlayer.getName() + " is selecting their cards");
                        System.out.println("Selection complete.");
                    }
    
                    // Prompt player to pick a card, player chooses card
                    Card pickedCard = curPlayer.chooseCard();
                    System.out.println("\nPlayer has played: " + pickedCard);

                    // Play card (add it to the parade and remove from the player's hand)
                    par.addCard(curPlayer.playCard(pickedCard));
    
                    // Collect cards based on game rules
                    ArrayList<Card> toCollect = par.getCollectibleCards(pickedCard);
                    curPlayer.collectCard(toCollect, endGame); // endgame exception can be thrown here
                    if (toCollect.isEmpty()) { // should show explicitly if no cards are to be collected
                        System.out.println("Player should collect: [\u001B[3mNone\u001B[0m]"); // italic
                    } else {
                        System.out.println("Player should collect: " + toCollect);
                    }
                    curPlayer.printCollectedCards(false);
    
                    // Player draws a new card (throws exception if deck is empty)
                    curPlayer.addCard(d.drawCard(), endGame);
    
                } catch (EndGameException e) {
                    /**
                     * Handles endgame conditions.
                     * <p>
                     * If the deck runs out or a player collects all six colors, the game enters its final phase,
                     * where each remaining player gets one last turn.
                     * </p>
                     */
                    System.out.println(e.getMessage());
                    if (e.getMessage().toLowerCase().contains("deck")) {
                        // Deck is empty
                        System.out.println("💫 Final round initiated...\n");
                        Game.delayMessage("Everyone else has one last turn before the game ends.\n");
                    } else {
                        // Player has collected all 6 colors
                        System.out.println("\n🎨 " + curPlayer.getName() + " has collected all 6 colours!");
                        System.out.println("💫 Final round triggered! Everyone else gets one last turn.\n");

                        try {
                            curPlayer.addCard(d.drawCard(), endGame); 
                        } catch (EndGameException ee) {
                            System.out.println(ee.getMessage()); 
                        }
                    }
                    endGame = true;
                } 
            }

            // at this point, all players should have 4 cards left in their hand

            // Post-game: discard + scoring
            System.out.printf("\n\n🎉 The game is over! 🎉\n" +
            "🃏 It's time to discard and score!\n" +
            "Each player will discard 2 cards.\n" +
            "The remaining cards will be added to your collection.\n\n");
            Game.delayMessageWithDots("\n\n🕑 Now preparing the for final collection phase");

            try {
                for (Player curPlayer : playerList.getPlayerList()){
                    // pause thread during bot's term
                    System.out.println("\n\n||   Please select 2 cards to discard.   ||   " + curPlayer.getName());

                    if (curPlayer instanceof BotPlayer){
                        Game.delayMessageWithDots(curPlayer.getName() + " is selecting their cards");
                        System.out.println("Selection complete.");
                    }
    
                    // pick 1st card to discard
                    Card discard1 = curPlayer.chooseCard();
                    curPlayer.playCard(discard1); // remove card from hand
                    System.out.println();
    
                    // pick 2nd card to discard
                    Card discard2 = curPlayer.chooseCard();
                    curPlayer.playCard(discard2);
                    
                    // add remaining hand cards to collection
                    curPlayer.collectCard(curPlayer.getHand(), false);
                }
            } catch (EndGameException e){
                // just to handle exceptions, but should never be thrown in this try block
            }
    

        // Display final hand and collection for each player
        for (Player p: playerList.getPlayerList()) {
            System.out.print(p.getName() + "\nHand      : "); // to align with collection
            for (Card c : p.getHand()) {
                System.out.print(c + " ");
            }
            System.out.println();
            p.printCollectedCards(true);
            System.out.println();
        }        

        ScoreCalculator scoreCalc = new ScoreCalculator(playerList);
        ArrayList<Player> winners = scoreCalc.findWinners();

        // Print winner(s)
        scoreCalc.printWinners();
        // Print all scores
        scoreCalc.printAllScores();

        return winners;
    }
}