package com.tesevic.interaction;

import com.tesevic.text.TextHolder;

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

    /**
     * Function to get the user input with a specified minimum and maximum value.
     * @param min minimum value
     * @param max maximum value
     * @return the user input with the value between (and including) the min and max value
     */
    public static int getInput(int min, int max) {
        boolean valid = false;
        int input = -1;

        while (!valid) {
            input = Input.getInput();

            valid = (input >= min && input <= max);

            if (!valid) System.out.println(TextHolder.INPUT_NOT_IN_RANGE);
        }

        return input;
    }
}
