package parade;

import parade.enums.Colour;

public class ParadeMenu {
    public static void main(String[] args) {
        // String purple = Colour.PURPLE.getColorCode();
        // String yellow = Colour.YELLOW.getColorCode();
        // System.out.println(yellow + "=== " + purple + "WELCOME " + yellow + "TO " + purple + "THE " + yellow + "PARADE " + purple + "===\u001B[0m");
        System.out.println("SELECT GAME MODE:");
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
