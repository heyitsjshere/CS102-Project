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
        clearConsole();
        printWelcomeBanner();
        start();
    }

    /**
     * Player will initaliate the start of a game
     */
    public static void start() {
        new ParadeTester();
    }

    public static void clearConsole() {
        // ANSI escape code to clear screen and move cursor to top left
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void printWelcomeBanner() {
        String reset = "\u001B[0m";
        String yellow = "\u001B[33m";
        String cyan = "\u001B[36m";
        String purple = "\u001B[35m";
        String green = "\u001B[32m";
    
        System.out.println();
        System.out.println(yellow +
            "                                                                                                               ,---. ");
        System.out.println(cyan +
            ",--.   ,--.      ,--.                               ,--.          ,------.                          ,--.       |   | ");
        System.out.println(purple +
            "|  |   |  |,---. |  |,---. ,---. ,--,--,--.,---.  ,-'  '-. ,---.  |  .--. ' ,--,--.,--.--.,--,--. ,-|  |,---.  |  .' ");
        System.out.println(green +
            "|  |.'.|  | .-. :|  | .--'| .-. ||        | .-. : '-.  .-'| .-. | |  '--' |' ,-.  ||  .--' ,-.  |' .-. | .-. : |  |  ");
        System.out.println(yellow +
            "|   ,'.   \\   --.|  \\ `--.' '-' '|  |  |  \\   --.   |  |  ' '-' ' |  | --' \\ '-'  ||  |  \\ '-'  |\\ `-' \\   --. `--'  ");
        System.out.println(cyan +
            "'--'   '--'`----'`--'`---' `---' `--`--`--'`----'   `--'   `---'  `--'      `--`--'`--'   `--`--' `---' `----' .--.  ");
        System.out.println(purple +
            "                                                                                                               '--'  ");
        System.out.println(reset);
    }
}
