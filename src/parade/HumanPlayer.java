package parade;

public class HumanPlayer extends Player {

    private UserInput input;
    private static final int HAND_SIZE = 5;

    public HumanPlayer() {
        super();
        input = new UserInput();
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
