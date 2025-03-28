package parade;

import java.util.Random;
import java.util.Scanner;

public class MagicBoxGame {
    private static int points = 0;
    private static int level = 1;
    private static int xp = 0;
    private static Random rand = new Random();

    // Array of possible rewards the Magic Box can give
    private static String[] rewards = {
            "Congratulations! You found a special card! +50 points!",
            "You unlocked a new avatar!",
            "You earned 100 bonus points!",
            "You gained 1 extra XP for your next game!",
            "A magical friend joins your parade! +200 points!"
    };

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Magic Box Game!");

        // Game loop
        while (true) {
            // Game choices and points (simplified)
            System.out.println("Choose a card: 1) Ace of Spades  2) Queen of Hearts  3) King of Clubs");
            int choice = scanner.nextInt();

            // Card selection logic for points and XP
            if (choice == 1) {
                points += 10;
                xp += 20;
                System.out.println("Great choice! You've earned 10 points and 20 XP!");
            } else if (choice == 2) {
                points += 15;
                xp += 25;
                System.out.println("Nice! You earned 15 points and 25 XP!");
            } else {
                points += 5;
                xp += 10;
                System.out.println("Good try! You earned 5 points and 10 XP.");
            }

            // Level up system
            if (xp >= 100 * level) {
                level++;
                System.out.println("You've leveled up to Level " + level + "!");
            }

            // Ask if user wants to continue
            System.out.println("Do you want to continue playing? (y/n)");
            char continuePlaying = scanner.next().charAt(0);
            if (continuePlaying == 'n' || continuePlaying == 'N') {
                System.out.println(
                        "Thanks for playing! You finished with " + points + " points and reached Level " + level + ".");

                // Trigger Magic Box at the end of the game
                triggerMagicBox();

                break; // Exit the game loop
            }
        }

        scanner.close();
    }

    // Method to trigger the magic box and give a random reward
    private static void triggerMagicBox() {
        System.out.println("\n🎉 The Magic Box Appears! 🎉");
        System.out.println("Opening the Magic Box...");

        // Randomly select a reward from the array
        int rewardIndex = rand.nextInt(rewards.length); // Randomly select an index
        System.out.println("You got: " + rewards[rewardIndex]); // Display the reward

        // Optionally, you could add some delay or animation here (e.g., pauses to build
        // excitement).
    }
}
