package parade;

import java.util.ArrayList;

import parade.exceptions.EndGameException;

public class SingleGame {
    private Parade par;
    private Deck d; 
    private boolean endGame;
    private PlayerList playerList;
    private int turn;

    /** Number of cards each player starts with. */
    private static final int INITIAL_HAND_SIZE = 5;

    public SingleGame(PlayerList playerList){
        this.d = new Deck();
        this.par = new Parade(d);
        this.endGame = false;
        this.playerList = playerList;
        this.turn = 0;

        dealInitialCards();
        // startGame();
    }

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

    private int getRound(){
        return turn/playerList.getNumberOfPlayers() + 1;
    }

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
                    
                    // Delay output for bot players
                    if (curPlayer instanceof BotPlayer){
                        ParadeTester.delayMessageWithDots(curPlayer.getName() + " is selecting their cards");
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
                        System.out.println("💫 Final round initiated... Everyone else has one last turn before the game ends.\n");
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
            "🃏 It's time to discard and score!" +
            "\n Each player will discard 2 cards.\n" +
            "The remaining cards will be added to your collection.\n\n");
            ParadeTester.delayMessageWithDots("\n\n🕑 Now preparing the for final collection phase");

            try {
                for (Player curPlayer : playerList.getPlayerList()){
                    // pause thread during bot's term
                    System.out.println("\n\n||   Please select 2 cards to discard.   ||   " + curPlayer.getName());

                    if (curPlayer instanceof BotPlayer){
                        ParadeTester.delayMessageWithDots(curPlayer.getName() + " is selecting their cards");
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
            System.out.println(p.getName() + "\t Hand: " + p.getHand());
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
