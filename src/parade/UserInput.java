package parade;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Handles user input for the Parade game.
 * <p>
 * This class provides methods to safely retrieve user input, ensuring that integers fall within 
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
     * Constructs a new {@code UserInput} object and initializes the {@code Scanner}.
     */
    public UserInput(){
        sc = new Scanner(System.in);
    }

    /**
     * Prompts the user for an integer within a specified range.
     * <p>
     * If the user provides an invalid input, a default error message is displayed and the prompt is repeated.
     * </p>
     *
     * @param message The prompt message to display to the user.
     * @param min The minimum allowable value (inclusive).
     * @param max The maximum allowable value (inclusive).
     * @return The validated integer input from the user.
     */
    public int getUserInt(String message, int min, int max){ 
        return getUserInt(message, min, max, "Invalid Input! Please enter a number between %d and %d!%n");
    }

    /**
     * Prompts the user for an integer within a specified range.
     * <p>
     * If the user provides an invalid input, the provided custom error message is displayed, and the prompt is repeated.
     * </p>
     *
     * @param message The prompt message to display to the user.
     * @param min The minimum allowable value (inclusive).
     * @param max The maximum allowable value (inclusive).
     * @param errorMessage The custom error message to display for invalid input.
     * @return The validated integer input from the user.
     */
    public int getUserInt(String message, int min, int max, String errorMessage){ 
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


    public int getUserIntWithTimeout(String message, int min, int max, int durationInMilliseconds, CountdownTimer timer) {
        AtomicInteger selectedNum = new AtomicInteger(-1); // Stores user's selection safely across threads

        // Start a new thread for user input
        Thread inputThread = new Thread(() -> {
            Scanner newsc = new Scanner(System.in);
            int userInt = 0;
            while (true) {
                // clear buffer
                // if (newsc.hasNextLine()) {
                //     newsc.nextLine(); // This will consume any extra newline or invalid characters left in the buffer
                // }
                System.out.printf(message, min, max);
                if (newsc.hasNextInt()) {
                    userInt = newsc.nextInt();
                    newsc.nextLine(); // Consume newline to avoid input issues

                    if (userInt >= min && userInt <= max) {
                        selectedNum.set(userInt);
                        break; // Stop input thread once valid input is received
                    }
                } else {
                    newsc.next(); // Discard invalid input
                }
                System.out.printf("Invalid input! Please enter a number between %d and %d.%n", min, max);
            }
            // newsc.close();
            // newsc = new Scanner(System.in);
        });

        // Start input thread
        inputThread.start();

        // Start the countdown timer
        timer.start(durationInMilliseconds);

        // Wait until either time runs out or the user makes a selection
        while (!timer.hasTimeExpired() && selectedNum.get() == -1) {
            // Do nothing, just waiting for either input or timeout
        }

        if (timer.hasTimeExpired()) {
            System.out.println("Randomising card...");
            // try {
            //     Thread.sleep(1000);
            // } catch (InterruptedException e){}
        }

        timer.stop();
        
        // Return selectedNum if within time and valid, else return -1 if no valid input was received
        return selectedNum.get() == -1 ? -1 : selectedNum.get(); 

    }


//     public Integer getUserIntWithTimeout(String message, int min, int max, long timeoutMillis) {
//     ExecutorService executor = Executors.newSingleThreadExecutor();

//     Future<Integer> future = executor.submit(() -> {
//         // Create a fresh Scanner inside the thread to avoid stale input state
//         Scanner threadScanner = new Scanner(System.in);
//         int userInt = 0;

//         while (true) {
//             System.out.printf(message, min, max);

//             if (threadScanner.hasNextInt()) {
//                 userInt = threadScanner.nextInt();
//                 threadScanner.nextLine(); // Consume newline
//                 if (userInt >= min && userInt <= max) {
//                     return userInt;
//                 }
//             } else {
//                 threadScanner.next(); // discard invalid
//             }
//             System.out.printf("Invalid Input! Please enter a number between %d and %d!%n", min, max);
//         }
//     });

//     try {
//         return future.get(timeoutMillis, TimeUnit.MILLISECONDS);
//     } catch (TimeoutException e) {
//         future.cancel(true); // interrupt the input thread
//         System.out.println("⏰ Time's up! You didn't respond in time.");
//     } catch (Exception e) {
//         e.printStackTrace();
//     } finally {
//         executor.shutdownNow(); // cleanup
//     }

//     return null;
// }

    // public Integer getUserIntWithTimeout(String message, int min, int max, long timeoutMillis) {
    //     ExecutorService executor = Executors.newSingleThreadExecutor();
    //     Future<Integer> future = executor.submit(() -> getUserInt(message, min, max));

    //     try {
    //         return future.get(timeoutMillis, TimeUnit.MILLISECONDS); // Wait for input or timeout
    //     } catch (TimeoutException e) {
    //         future.cancel(true); // Cancel input task
    //         System.out.println("Time's up! You didn't respond in time.");
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //     } finally {
    //         executor.shutdownNow();
            
    //     }

    //     return null; // No valid input
    // }

    /**
     * Prompts the user for a string input.
     * <p>
     * The input is stripped of leading and trailing spaces.
     * </p>
     *
     * @param message The prompt message to display to the user.
     * @return The user-provided string input.
     */
    public String getString(String message){
        System.out.print(message);
        return sc.nextLine().strip();
    }
}