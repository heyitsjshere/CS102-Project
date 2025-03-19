package parade;

import java.util.Scanner;

public class UserInput {
    Scanner sc;

    public UserInput(){
        sc = new Scanner(System.in);
    }

    public int getUserInt(String message, int min, int max){ 
        // if no error message given, have default error message
        return getUserInt(message, min, max, "Invalid Input! Please enter a number between %d and %d!%n");
    }

    public int getUserInt(String message, int min, int max, String errorMessage){ 
        int userInt = 0;

        while (true) {
            System.out.printf(message, min, max);
        
            if (sc.hasNextInt()) {
                userInt = sc.nextInt();
                sc.nextLine();
                if (userInt >= min && userInt <= max) {
                    return userInt;
                }
            } else {
                sc.next();
            }
            System.out.printf(errorMessage, min, max);
        }
    }

    public String getString(String message){
        System.out.print(message);
        String output = sc.nextLine().strip();
        return output;
    }
    

    // public String getString(String message, String errorMessage){
    //     String output = "";

    //     while (true) {
    //         System.out.print(message);
        
    //         if (sc.hasNextLine()) {
    //             output = sc.nextLine();
    //             if (!output.isEmpty()) {
    //                 return output;
    //             }
    //         } else {
    //             sc.nextLine();
    //         }
    //         System.out.print(errorMessage);
    //     }
    // }
}
