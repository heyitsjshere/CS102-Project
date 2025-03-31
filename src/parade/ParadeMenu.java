package parade;

/**
 * Provides the main menu and entry point for starting the Parade game.
 * <p>
 * This class is responsible for displaying a welcome banner, clearing the console,
 * and initializing the game by launching the {@link ParadeTester}.
 * </p>
 *
 * <p><strong>Features:</strong></p>
 * <ul>
 *   <li>Clears the terminal screen using ANSI escape codes</li>
 *   <li>Displays a colorful ASCII welcome banner</li>
 *   <li>Starts the Parade game</li>
 * </ul>
 *
 * <p><strong>Example usage:</strong></p>
 * <pre>
 * java parade.ParadeMenu
 * </pre>
 *
 * @author G3T7
 * @version 1.0
 */
public class ParadeMenu {

    /**
     * Default constructor for ParadeMenu.
     */
    public ParadeMenu() {}

    /**
     * Entry point for launching the Parade game.
     * <p>
     * Clears the console, prints a welcome banner, and starts the game.
     * </p>
     *
     * @param args command-line arguments (unused)
     */
    public static void main(String[] args) {
        clearConsole();
        printWelcomeBanner();
        start();
    }

    /**
     * Starts the Parade game in classic mode.
     * <p>
     * This method creates a new {@link ParadeTester} instance to begin gameplay.
     * </p>
     */
    public static void start() {
        new ParadeTester();
    }

    /**
     * Clears the terminal screen using ANSI escape codes.
     * <p>
     * Works on most Unix-based terminals. May not work on Windows Command Prompt
     * unless ANSI support is enabled.
     * </p>
     */
    public static void clearConsole() {
        // ANSI escape code to clear screen and move cursor to top left
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /**
     * Prints a large, colorful ASCII banner for the Parade game.
     * <p>
     * Uses ANSI escape codes to color each line of the welcome message.
     * </p>
     */
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