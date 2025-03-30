package parade;

/**
 * Provides the main menu for starting the Parade game in different modes.
 */
public class ParadeMenu {

    /**
     * Default constructor.
     */
    public ParadeMenu() {
        
    }

    /**
     * Entry point for launching the Parade game.
     *
     * @param args command-line arguments (unused)
     */
    public static void main(String[] args) {
        System.out.println("=== SELECT GAME MODE ===");
        System.out.println("1. Classic");
        System.out.println("2. Time Limit");
        start();
    }

    /**
     * Prompts the user to select a game mode and starts the game.
     */
    public static void start() {
        UserInput input = new UserInput();
        int choice = input.getUserInt("Enter choice: ", 1, 2);
        System.out.println();
        switch (choice) {
            case 1:
                new ParadeTester(false);
                break;
            case 2:
                new ParadeTester(true);
        }
    }

}
