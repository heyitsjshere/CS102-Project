package parade;

import java.util.ArrayList;
import java.util.Scanner;



public class ParadeTester {

    public static void getUserInput(Player p) {
        Scanner sc = new Scanner(System.in);

        System.out.println("PICK A CARD");
        System.out.println("-----------");
        int i = 1;
        for (Card c: p.getHand()) {
            System.out.println("Option " + i + ": " + c);
            i++;
        }

        do {
            System.out.printf("Selection: Option ");
            if (!sc.hasNextInt()) {
                System.out.println("That is not a valid option.");
                System.out.printf("Selection: Option ");
            }
        } while (!sc.hasNextInt());
        
        int selectedNum = sc.nextInt();
        Card selectedCard = p.playCard(selectedNum);
        System.out.println("\nYou have selected Option " + selectedNum + ": " + selectedCard);
    }

    
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

        System.out.println(p1.getHand());
        System.out.println(p1.getCollectedCards());

        System.out.println("Size of deck: " + d.getSize()); // 56

        // now p1 picks one card, card is removed from player's hand



        // card is added to the parade

        // get cards in removal mode

        // eligible cards are added to player's collection

        // player draws a card

        // next player's turn
        
    }
}
