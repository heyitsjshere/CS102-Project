package util;

import java.util.Scanner;

/**
 * Provides utility methods for handling user input in the Parade game.
 * <p>
 * This class contains only static methods and maintains a single
 * {@link Scanner} instance
 * for the entire game session. It offers controlled methods for validating and
 * retrieving
 * user inputs, ensuring that numeric inputs fall within a specified range and
 * that string
 * inputs are properly trimmed.
 * </p>
 *
 * <p>
 * Example usage:
 * 
 * <pre>
 * int choice = UserInput.getUserInt("Choose (1-3): ", 1, 3, "Invalid input! Please enter a number between 1 and 3.");
 * String name = UserInput.getUserString("Enter your name: ");
 * </pre>
 *
 * At the end of the game, close the scanner to release system resources:
 * 
 * <pre>
 * UserInput.close();
 * </pre>
 *
 * @author G3T7
 * @version 1.1
 */
public class UserInput {

    /** Scanner object used for reading user input from the console. */
    private static final Scanner sc = new Scanner(System.in);

    /** Default constructor for UserInput used to call from other classes */
    public UserInput() {

    }

    /**
     * Prompts the user for an integer within a specified range.
     * <p>
     * Displays the given message and keeps prompting until a valid input is
     * entered.
     * Uses a default error message for invalid inputs.
     * </p>
     *
     * @param message The prompt message to show the user.
     * @param min     The minimum valid input (inclusive).
     * @param max     The maximum valid input (inclusive).
     * @return A valid integer between {@code min} and {@code max}.
     */
    public static int getUserInt(String message, int min, int max) {
        return UserInput.getUserInt(message, min, max, "Invalid Input! Please enter a number between %d and %d!%n");
    }

    /**
     * Prompts the user for an integer within a specified range using a custom error
     * message.
     * <p>
     * Keeps prompting the user until a valid integer within the specified range is
     * entered.
     * Will also keep prompting if user does not key in any valid string
     * </p>
     *
     * @param message      The message to prompt the user.
     * @param min          Minimum valid value (inclusive).
     * @param max          Maximum valid value (inclusive).
     * @param errorMessage The custom error message displayed on invalid input.
     * @return The validated integer entered by the user.
     */
    public static int getUserInt(String message, int min, int max, String errorMessage) {
        int userInt;

        while (true) {
            System.out.printf(message, min, max);
            String input = sc.nextLine().trim();

            if (input.isEmpty()) {
                System.out.printf(errorMessage, min, max);
                continue;
            }

            try {
                userInt = Integer.parseInt(input);

                if (userInt >= min && userInt <= max) {
                    return userInt;
                } else {
                    System.out.printf(errorMessage, min, max);
                }
            } catch (NumberFormatException e) {
                System.out.printf(errorMessage, min, max);
            }
        }
    }

    /**
     * Prompts the user for a line of text input.
     * <p>
     * Trims any leading or trailing whitespace from the input.
     * Repeats until user does not key in an empty string
     * </p>
     *
     * @param message The message to display.
     * @return A trimmed string entered by the user.
     */
    public String getString(String message) {
        String input;
        while (true) {
            System.out.print(message);
            input = sc.nextLine().trim();

            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("Name cannot be empty. Please enter a valid name.");
        }
    }

    /**
     * Closes the scanner used for input.
     */
    public static void close() {
        sc.close();
    }
}