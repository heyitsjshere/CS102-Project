package parade;

import java.util.ArrayList;

import parade.exceptions.EndGameException;

public class SingleGame {
    private Parade par;
    private Deck d; 
    private boolean endGame;
    private PlayerList playerList;
    private int turn;

    public SingleGame(PlayerList playerList){
        this.d = new Deck();
        this.par = new Parade(d);
        this.endGame = false;
        this.playerList = playerList;
        this.turn = 0;

        startGame();
    }

    public void startGame() {
        
        int round = 1;

        while (playerList.getPlayer(turn).getHandSize() == 5) { 
                Player curPlayer = playerList.getPlayer(turn);
                try {
                    // Display round number before first turn of that round
                    round = turn/playerList.getNumberOfPlayers() + 1;
                    if (turn++ % playerList.getNumberOfPlayers() == 0) {
                        System.out.println("\n\n==== ROUND " + round + " ====");
                    }


                    System.out.println("\n||  " + curPlayer.getName() + "'s turn  ||");
                    System.out.println("Parade: " + par.getParade() + "\u001B[36m <==\u001B[0m Card inserted here\n");

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
    }
}
