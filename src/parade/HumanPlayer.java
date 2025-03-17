package parade;

public class HumanPlayer extends Player {

    private UserInput input;

    public HumanPlayer() {
        super();
        input = new UserInput();
    }

    @Override
    public Card chooseCard() {
        System.out.println("PICK A CARD");
        System.out.println("-----------");
        int i = 1;
        for (Card c : super.getHand()) {
            System.out.println("Option " + i + ": " + c);
            i++;
        }

        int selectedNum = input.getUserInt("Selection: Option ", 1, super.getHandSize());

        return super.getHand().get(selectedNum - 1);
    }

}