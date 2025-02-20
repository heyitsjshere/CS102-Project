package parade;

import java.util.ArrayList;
import java.util.Scanner;



public class ParadeTester {

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
        Card selectedCard = p.getHand().get(selectedNum+1);

        sc.close();
        
        System.out.println("-----------");
        System.out.println("You have selected Option " + selectedNum + ": " + selectedCard);

        return selectedCard;
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
