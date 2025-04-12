package parade.game;

/**
 * Provides the main entry point and user interface for launching the Parade game.
 * <p>
 * This class is responsible for:
 * <ul>
 *   <li>Clearing the terminal for a clean interface</li>
 *   <li>Displaying a colourful ASCII-art banner to welcome players</li>
 *   <li>Printing a short intro message with animated delays for better user experience</li>
 *   <li>Starting the game by invoking the {@link Game} logic</li>
 * </ul>
 *
 * <p><strong>Usage:</strong></p>
 * <pre>
 * java parade.GameDisplay
 * </pre>
 * 
 * @author G3T7
 * @version 1.1
 */
public class GameDisplay {

    /**
     * Default constructor.
     */
    public GameDisplay() {
        // No initialization needed
    }

    /**
     * Main method: Entry point for launching the Parade game.
     * <p>
     * Clears the terminal, shows a welcome banner, prints a short intro,
     * and initiates the game setup process via {@code start()}.
     * </p>
     *
     * @param args Command-line arguments (not used)
     */
    public static void main(String[] args) {
        printWelcomeBanner();

        Game.delayMessageWithDots("\n✨ Prepare your top hats and marching shoes");
        Game.delayMessage("🎭 The Parade is about to begin!\n");

        start();
    }

    /**
     * Starts the Parade game by launching the main game loop in {@link Game}.
     */
    public static void start() {
        new Game();
    }

    /**
     * Clears the console using ANSI escape codes.
     * <p>
     * Moves the cursor to the top left and clears all text.
     * </p>
     */
    public static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /**
     * Prints a colourful ASCII-art banner to welcome players to the Parade game.
     * <p>
     * Clears console before printing the banner
     * Uses ANSI escape codes to apply colour to each line of the banner.
     * The message is styled for visual appeal and thematically matches the game.
     * Clears terminal before printing the banner
     * </p>
     */
    public static void printWelcomeBanner() {
        clearConsole(); 
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
    
    /**
     * Prints a colourful ASCII-art banner to indicate to players that game has ended
     * <p>
     * Uses ANSI escape codes to apply colour to each line of the banner.
     * The message is styled for visual appeal and thematically matches the game.
     * Clears terminal before printing the banner
     * </p>
     */
    public static void printExitBanner() {
        String reset = "\u001B[0m";
        String yellow = "\u001B[33m";
        String cyan = "\u001B[36m";
        String purple = "\u001B[35m";
        String green = "\u001B[32m";

        System.out.println();
        System.out.println(yellow +
        "                                                                                                                       ,---. ");
        System.out.println(cyan +
        ",--------.,--.                     ,--.             ,---.                      ,--.                 ,--.               |   | ");
        System.out.println(purple +
        "'--.  .--'|  ,---.  ,--,--.,--,--, |  |,-.  ,---.  /  .-' ,---. ,--.--.  ,---. |  | ,--,--.,--. ,--.`--',--,--,  ,---. |  .' ");
        System.out.println(green +
        "   |  |   |  .-.  |' ,-.  ||      \\|     / (  .-'  |  `-,| .-. ||  .--' | .-. ||  |' ,-.  | \\  '  / ,--.|      \\| .-. ||  |  ");
        System.out.println(cyan +
        "   |  |   |  | |  |\\ '-'  ||  ||  ||  \\  \\ .-'  `) |  .-'' '-' '|  |    | '-' '|  |\\ '-'  |  \\   '  |  ||  ||  |' '-' '`--'  ");
        System.out.println(purple +
        "   `--'   `--' `--' `--`--'`--''--'`--'`--'`----'  `--'   `---' `--'    |  |-' `--' `--`--'.-'  /   `--'`--''--'.`-  / .--.  ");
        System.out.println(yellow +
        "                                                                        `--'               `---'                `---'  '--'  ");
        System.out.println(reset);
    }
}

