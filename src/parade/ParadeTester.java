package parade;

import parade.*;
import java.util.ArrayList;
import java.util.Scanner;

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
    public static Card getUserInput(Player p) {
        Scanner sc = new Scanner(System.in);

        System.out.println("PICK A CARD");
        System.out.println("-----------");
        int i = 1;
        for (Card c: p.getHand()) {
            System.out.println("Option " + i + ": " + c);
            i++;
        }

        // do {
        //     System.out.printf("Selection: Option ");
        //     if (!sc.hasNextInt()) {
        //         System.out.println("That is not a valid option. Please enter an integer");
        //         System.out.printf("Selection: Option ");
        //     }
        // } while (sc.hasNextInt() == false); // need better handling but just assume user enters correct thing for now

        System.out.printf("Selection: Option ");
        int selectedNum = sc.nextInt();
        Card selectedCard = p.getHand().get(selectedNum-1);

        sc.close();
        
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
        ArrayList<Card> initialHand1 = new ArrayList<>();
        ArrayList<Card> initialHand2 = new ArrayList<>();

        Deck d = new Deck();
        for (int i = 0; i < 5; i++) {
            initialHand1.add(d.drawCard());
            initialHand2.add(d.drawCard());
        }
        Player p1 = new HumanPlayer(initialHand1);
        Player p2 = new HumanPlayer(initialHand2);

        Parade curParade = new Parade(new ArrayList<Card>(){{ // initialise parade with 6 cards
            for (int i = 0; i < 6; i++) add(d.drawCard());
        }});

        System.out.println("Player 1's Hand: " + p1.getHand());
        System.out.println("Player 1's Collection: " + p1.getCollectedCards());
        System.out.println("Parade: " + curParade.getParade());

        System.out.println("Size of deck: " + d.getSize()); // 56

        // now p1 picks one card, card is removed from player's hand
        Card pickedCard = getUserInput(p1); 

        System.out.println("Removable: " + curParade.getRemoveable(pickedCard));
        ArrayList<Card> toCollect = curParade.getCollectibleCards(pickedCard);
        p1.collectCard(toCollect);
        System.out.println("p1 should collect: " + toCollect);

        curParade.addCard(p1.playCard(pickedCard));
        p1.addCard(d.drawCard());
        System.out.println("Player 1's Hand: " + p1.getHand());
        System.out.println("Player 1's Collection: " + p1.getCollectedCards());
        System.out.println("Parade: " + curParade.getParade());

        System.out.println("Size of deck: " + d.getSize()); // 56
    }
}
