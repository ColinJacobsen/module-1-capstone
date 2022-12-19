package com.techelevator.view;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Scanner;

public class Menu {

    private PrintWriter out;
    private Scanner in;

    public Menu(InputStream input, OutputStream output) {
        this.out = new PrintWriter(output);
        this.in = new Scanner(input);
    }

    public Object getChoiceFromOptions(Object[] options) {
        Object choice = null;
        while (choice == null) {
            displayMenuOptions(options);
            choice = getChoiceFromUserInput(options);
        }
        return choice;
    }

    private Object getChoiceFromUserInput(Object[] options) {
        Object choice = null;
        String userInput = in.nextLine();
        try {
            int selectedOption = Integer.parseInt(userInput);
            if (selectedOption > 0 && selectedOption <= options.length) {
                choice = options[selectedOption - 1];
            }
        } catch (NumberFormatException e) {
            // eat the exception, an error message will be displayed below since choice will be null
        }
        if (choice == null) {
            out.println(System.lineSeparator() + "*** " + userInput + " is not a valid option ***" + System.lineSeparator());
        }
        return choice;
    }

    public void displayMenuOptions(Object[] options) {
        out.println();

        for (int i = 0; i < options.length; i++) {
            if (i < 3) {
                int optionNum = i + 1;
                out.println(optionNum + ") " + options[i]);
            }
        }
        out.print(System.lineSeparator() + "Please choose an option >>> ");
        out.flush();
    }

    public double getDepositAmount() {
        while (true) {
            double x = Double.parseDouble(in.nextLine());
            double y = Math.floor(x);
            if (x <= 0) {
                System.out.println("Please enter a positive number.\n");
            } else if (x != y) {
                System.out.println("Please enter whole dollar value\n");
            } else {
                return x;
            }
        }
    }

    public String getLocation(Map<String, Product> map) {
        while (true) {
            String input = in.nextLine().toUpperCase();
            if (map.containsKey(input)) {
                return input;
            } else {
                System.out.println("Invalid input, please try another code");
            }
        }
    }

}
