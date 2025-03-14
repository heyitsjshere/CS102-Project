package parade;

// import parade.*;
import java.util.ArrayList;
import java.util.Scanner;

import parade.enums.Colour;
import parade.exceptions.EndGameException;

/**
 * A class to test the functionality of the Parade game.
 * <p>
 * This class initializes players, a deck, and a parade, and simulates a turn where a player selects 
 * a card, adds it to the parade, and collects any removable cards.
 * </p>
 */

public class ParadeTester {

    /**
     * Prompts the user to select a card from their hand.
     * <p>
     * Displays available options and retrieves the user's choice. The selected card is returned.
     * </p>
     *
     * @param p the player whose hand will be displayed for selection
     * @return the {@link Card} selected by the user
     */
    public static Card getUserInput(Player p, Scanner sc) {
        System.out.println("PICK A CARD");
        System.out.println("-----------");
        int i = 1;
        for (Card c: p.getHand()) {
            System.out.println("Option " + i + ": " + c);
            i++;
        }
        
        int selectedNum = -1;
        while (true) {
            System.out.print("Selection:  Option ");
            if (sc.hasNextInt()) {
                selectedNum = sc.nextInt();
                sc.nextLine(); // ignore the newline after nextInt()
                if (selectedNum >= 1 && selectedNum <= p.getHand().size()) { // selected card must be within number of cards in the hand
                    break;
                }
            }
            sc.nextLine(); // to clear invalid input
            System.out.println("Invalid selection! Please choose a number between 1 and " + p.getHand().size());
        }
        
        Card selectedCard = p.getHand().get(selectedNum - 1);
        System.out.println("-----------");
        System.out.println("You have selected Option " + selectedNum + ": " + selectedCard);
        return selectedCard;
    }

    // this method will be called at the end of the game and calculate the scores of each player, followed by listing out the winner(s)
    public static void calculateScores(PlayerList playerList) {
        System.out.println("\n=== FINAL SCORES ====");

        int minScore = Integer.MAX_VALUE; // track the minimum score
        ArrayList<Player> winners = new ArrayList<Player>();

        for (Player p : playerList.getPlayerList()) {
            int totalScore = 0;
            System.out.println("\nPlayer " + (playerList.getPlayerList().indexOf(p) + 1) + "'s Collection: " + p.getCollectedCards());

            for (Colour c : Colour.values()) {
                ArrayList<Card> cards = p.getCollectedCards().get(c);
                if (cards != null) {
                    totalScore += cards.stream().mapToInt(Card::getCardNum).sum();
                } 
            }

            System.out.println("Total Score: " + totalScore);

            // Check if this player has the lowest score
            if (totalScore < minScore) {
                minScore = totalScore;
                winners.clear(); // reset winners list with this new lowest score
                winners.add(p); 
            } else if (totalScore == minScore) { 
                winners.add(p); // add to winners list if there is a tie
            }
        }

        // Announce the winners
        System.out.println("\n=== WINNNER(s) ====");
        if (winners.size() == 1) {
            System.out.println("Player " + (playerList.getPlayerList().indexOf(winners.get(0)) + 1) + " WINS with " + minScore + " points!");
        } else {
            System.out.print("It's a TIE between Players "); 
            for (Player p : winners) {
                System.out.println(playerList.getPlayerList().indexOf(p) + 1 + " ");
            }
            System.out.println("with " + minScore + " points!");
        }

    }


    /**
     * The main method to test the Parade game mechanics.
     * <p>
     * Initializes a deck, players, and the parade, then simulates a player's turn where they:
     * <ul>
     *     <li>Select a card</li>
     *     <li>Identify removable and collectible cards</li>
     *     <li>Collect applicable cards</li>
     *     <li>Play their selected card</li>
     *     <li>Draw a new card</li>
     * </ul>
     * The method also prints the game state before and after the player's turn.
     * </p>
     *
     * @param args command-line arguments (not used)
     */
    public static void main (String[] args) {
        Scanner sc = new Scanner(System.in);

        Deck d = new Deck();
        System.out.println("Size of deck: " + d.getSize()); // 56

        Parade par = new Parade(d);
        System.out.println("Size of deck (after parade): " + d.getSize()); // 56

        PlayerList playerList = new PlayerList(d);

     
        System.out.println("Size of deck (after initialisation): " + d.getSize()); // 56
        for (int i = 0 ; i < playerList.getPlayerList().size() ; i++) { // show hand of all the players
            System.out.println(playerList.getPlayer(i));
        }

        int turn = -1;
        while (d.getSize() > 0) { // changed end-game logic from true to <<<
            ++turn;
            Player curPlayer = playerList.getPlayer(turn);
            try {
                System.out.println("\n\n||   Turn " + (turn+1) + "   ||    Player " + (playerList.getPlayerList().indexOf(curPlayer) + 1));
                System.out.println("Parade: " + par.getParade());
    
                // now the player picks one card, card is removed from player's hand
                Card pickedCard;
                if (curPlayer instanceof BotPlayer) {
                    pickedCard = ((BotPlayer) curPlayer).chooseCard(); // bot will randomly pick a card
                } else {
                    pickedCard = getUserInput(curPlayer, sc);
                }
                // Card pickedCard = curPlayer.getHand().get(0); // test
    
                System.out.println("Removable: " + par.getRemoveable(pickedCard));
                ArrayList<Card> toCollect = par.getCollectibleCards(pickedCard);
                curPlayer.collectCard(toCollect);
                System.out.println("player should collect: " + toCollect);
    
                par.addCard(curPlayer.playCard(pickedCard)); 
                curPlayer.addCard(d.drawCard());
                System.out.println("Player's Hand: " + curPlayer.getHand());
                System.out.println("Player's Collection: " + curPlayer.getCollectedCards());
                System.out.println("Parade: " + par.getParade());
    
                System.out.println("Size of deck: " + d.getSize()); // 56

            } catch (EndGameException e) {
                System.out.println("\n=== GAME OVER! ====");
                System.out.println("Player " + (playerList.getPlayerList().indexOf(curPlayer) + 1) + " has won after collecting 6 cards of the same colou!");
                // e.printStackTrace();
                break; // exit loop so game can end
            }
        }

        calculateScores(playerList);
        // TO-DO: add end game things
        // tip: comment on          Line 107 = getUserInput(curPlayer, sc); 
        //      and replace it with Line 108 = curPlayer.getHand().get(0);
        sc.close();
    }
}