package parade;

// import java.util.Scanner;

public class ParadeMenu {
    public static void main(String[] args) {
        System.out.println("=== SELECT GAME MODE ===");
        System.out.println("1. Classic");
        System.out.println("2. Time Limit");
        start();
    }

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
