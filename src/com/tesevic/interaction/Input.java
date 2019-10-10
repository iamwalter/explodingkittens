package com.tesevic.interaction;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Input {
    private static Scanner scanner;

    static {
        scanner = new Scanner(System.in);
    }

    public static int getInput() {
        int input = -1;
        boolean valid = false;

        while (!valid) {
            System.out.print("> ");
            try {
                input = scanner.nextInt();
                valid = true;
            } catch (InputMismatchException e) {
                System.out.println("Please enter a number.");
                scanner.nextLine(); // reset to next line.
            }
        }

        return input;
    }
}
