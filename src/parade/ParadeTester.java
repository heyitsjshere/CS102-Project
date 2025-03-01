package parade;

// import parade.*;
import java.util.ArrayList;
import java.util.Scanner;

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
        
        // System.out.println(p.getHand());
        System.out.printf("Selection: Option ");

        // System.out.println("Has next int = " + sc.hasNextInt());

        int selectedNum = sc.nextInt();
        // System.out.println("value of next is " + selectedNum);

        // System.out.println("selected num is " + selectedNum);

        Card selectedCard = p.getHand().get(selectedNum-1);
        // Card selectedCard = p.getHand().get(0);

        
        System.out.println("-----------");
        System.out.println("You have selected Option " + selectedNum + ": " + selectedCard);
        return selectedCard;
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

        Player p1 = new HumanPlayer();
        Player p2 = new HumanPlayer();
        Player p3 = new HumanPlayer();

        PlayerList playerList = new PlayerList(new ArrayList<Player>(){{
            add(p1);
            add(p2);
            add(p3);
        }}, par, d);

     
        System.out.println("Size of deck (after initialisation): " + d.getSize()); // 56
        System.out.println(p1.getHand());
        System.out.println(p2.getHand());
        System.out.println(p3.getHand());

        int turn = -1;
        while (true) {
            try {
                ++turn;
                System.out.println("\n\n||   Turn " + (turn+1) + "   ||    Player " + (turn%3+1));
                Player curPlayer = playerList.getPlayer(turn);
                System.out.println("Parade: " + par.getParade());
    
                // now the player picks one card, card is removed from player's hand
                Card pickedCard = getUserInput(curPlayer, sc); 
                // Card pickedCard = curPlayer.getHand().get(0);
    
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
                e.printStackTrace();
                break;
            }
        }

        // TO-DO: add end game things
        // tip: comment on          Line 107 = getUserInput(curPlayer, sc); 
        //      and replace it with Line 108 = curPlayer.getHand().get(0);


        

    }
}
