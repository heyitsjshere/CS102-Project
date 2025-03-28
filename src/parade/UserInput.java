package parade;

import java.util.Scanner;

/**
 * Handles user input for the Parade game.
 * <p>
 * This class provides methods to safely retrieve user input, ensuring that
 * integers fall within
 * a specified range and that string inputs are properly captured.
 * </p>
 *
 * @author G3T7
 * @version 1.0
 */
public class UserInput {

    /** Scanner object used for reading user input. */
    private Scanner sc;

    /**
     * Constructs a new {@code UserInput} object and initializes the
     * {@code Scanner}.
     */
    public UserInput() {
        sc = new Scanner(System.in);
    }

    /**
     * Prompts the user for an integer within a specified range.
     * <p>
     * If the user provides an invalid input, a default error message is displayed
     * and the prompt is repeated.
     * </p>
     *
     * @param message The prompt message to display to the user.
     * @param min     The minimum allowable value (inclusive).
     * @param max     The maximum allowable value (inclusive).
     * @return The validated integer input from the user.
     */
    public int getUserInt(String message, int min, int max) {
        return getUserInt(message, min, max, "Invalid Input! Please enter a number between %d and %d!%n");
    }

    /**
     * Prompts the user for an integer within a specified range.
     * <p>
     * If the user provides an invalid input, the provided custom error message is
     * displayed, and the prompt is repeated.
     * </p>
     *
     * @param message      The prompt message to display to the user.
     * @param min          The minimum allowable value (inclusive).
     * @param max          The maximum allowable value (inclusive).
     * @param errorMessage The custom error message to display for invalid input.
     * @return The validated integer input from the user.
     */
    public int getUserInt(String message, int min, int max, String errorMessage) {
        int userInt = 0;

        while (true) {
            System.out.printf(message, min, max);

            if (sc.hasNextInt()) {
                userInt = sc.nextInt();
                sc.nextLine(); // Consume newline to avoid input issues
                if (userInt >= min && userInt <= max) {
                    return userInt;
                }
            } else {
                sc.next(); // Clear invalid input
            }
            System.out.printf(errorMessage, min, max);
        }
    }

    /**
     * Prompts the user for a string input.
     * <p>
     * The input is stripped of leading and trailing spaces.
     * </p>
     *
     * @param message The prompt message to display to the user.
     * @return The user-provided string input.
     */
    public String getString(String message) {
        System.out.print(message);
        return sc.nextLine().strip();
    }
}