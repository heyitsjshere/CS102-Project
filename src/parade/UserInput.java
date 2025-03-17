package parade;

import java.util.Scanner;

public class UserInput {
    Scanner sc;

    public UserInput() {
        sc = new Scanner(System.in);
    }

    public int getUserInt(String message, int min, int max) {
        // if no error message given, have default error message
        return getUserInt(message, min, max, "Invalid Input! Please enter a number between %d and %d!%n");
    }

    public int getUserInt(String message, int min, int max, String errorMessage) {
        int userInt = 0;

        while (true) {
            System.out.printf(message, min, max);

            if (sc.hasNextInt()) {
                userInt = sc.nextInt();
                if (userInt >= min && userInt <= max) {
                    return userInt;
                }
            } else {
                sc.next();
            }
            System.out.printf(errorMessage, min, max);
        }

    }

}