package parade;

import java.util.Scanner;

public class HumanPlayer extends Player {

    private UserInput input;
    private static final int HAND_SIZE = 5;

    public HumanPlayer() {
        super();
        input = new UserInput();
    }

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

    @Override
    public Card chooseCard() {
        System.out.println("PICK A CARD");
        System.out.println("-----------");
        int i = 1;
        for (Card c: super.getHand()) {
            System.out.println("Option " + i + ": " + c);
            i++;
        }

        int selectedNum = input.getUserInt("Selection: Option ", 1, HAND_SIZE);

        return super.getHand().get(selectedNum);
    }

    
}
