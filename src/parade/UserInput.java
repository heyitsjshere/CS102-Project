package parade;

import java.util.Scanner;

/**
 * Handles user input for the Parade game.
 * <p>
 * Provides utility methods for validating and retrieving user input
 * in a controlled and user-friendly manner. This class ensures that
 * numeric inputs fall within a specified range and that string inputs
 * are properly trimmed.
 * </p>
 *
 * <p>
 * Example usage:
 * <pre>
 * UserInput input = new UserInput();
 * int choice = input.getUserInt("Choose (1-3): ", 1, 3);
 * String name = input.getString("Enter your name: ");
 * </pre>
 *
 * @author G3T7
 * @version 1.0
 */
public class UserInput {

    /** Scanner object used for reading user input from the console. */
    private Scanner sc;

    /**
     * Constructs a new {@code UserInput} object and initializes the input scanner.
     */
    public UserInput() {
        sc = new Scanner(System.in);
    }

    /**
     * Prompts the user for an integer within a specified range.
     * <p>
     * Displays the given message and keeps prompting until a valid input is entered.
     * Uses a default error message for invalid inputs.
     * </p>
     *
     * @param message The prompt message to show the user.
     * @param min     The minimum valid input (inclusive).
     * @param max     The maximum valid input (inclusive).
     * @return A valid integer between {@code min} and {@code max}.
     */
    public int getUserInt(String message, int min, int max) {
        return getUserInt(message, min, max, "Invalid Input! Please enter a number between %d and %d!%n");
    }

    /**
     * Prompts the user for an integer within a specified range using a custom error message.
     * <p>
     * Keeps prompting the user until a valid integer within the specified range is entered.
     * </p>
     *
     * @param message      The message to prompt the user.
     * @param min          Minimum valid value (inclusive).
     * @param max          Maximum valid value (inclusive).
     * @param errorMessage The custom error message displayed on invalid input.
     * @return The validated integer entered by the user.
     */
    public int getUserInt(String message, int min, int max, String errorMessage) {
        int userInt;

        while (true) {
            System.out.printf(message, min, max);

            if (sc.hasNextInt()) {
                userInt = sc.nextInt();
                sc.nextLine(); // Consume trailing newline
                if (userInt >= min && userInt <= max) {
                    return userInt;
                }
            } else {
                sc.next(); // Discard invalid token
            }

            System.out.printf(errorMessage, min, max);
        }
    }

    /**
     * Prompts the user for a line of text input.
     * <p>
     * Trims any leading or trailing whitespace from the input.
     * </p>
     *
     * @param message The prompt to display.
     * @return A trimmed string entered by the user.
     */
    public String getString(String message) {
        System.out.print(message);
        return sc.nextLine().strip();
    }
}