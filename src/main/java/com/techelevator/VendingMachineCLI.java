package com.techelevator;

import com.sun.source.tree.BreakTree;
import com.techelevator.view.*;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class VendingMachineCLI {


    //START SCREEN
    private static final String START_GUI = "Start GUI";
    private static final String START_COMMAND_LINE = "Start Command Line";

    private static final String[] START_MENU_OPTIONS = {START_GUI, START_COMMAND_LINE};

    //MAIN MENU

    private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
    private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";

    private static final String EXIT = "Exit";

    private static final String GENERATE_SALES_REPORT = "";
    private static final String[] MAIN_MENU_OPTIONS = {MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE, EXIT, GENERATE_SALES_REPORT};

    //PURCHASE MENU

    private static final String CUSTOMER_OPTION_FEED_MONEY = "Feed Money";
    private static final String CUSTOMER_OPTION_SELECT_PRODUCT = "Select Product";
    private static final String CUSTOMER_OPTION_END_TRANSACTION = "Finish Transaction";
    private static final String[] CUSTOMER_OPTIONS = new String[]{CUSTOMER_OPTION_FEED_MONEY, CUSTOMER_OPTION_SELECT_PRODUCT, CUSTOMER_OPTION_END_TRANSACTION};

    //private Customer customer;
    public boolean usingGUI = false;

    public boolean isUsingGUI() {
        return usingGUI;
    }

    public void setUsingGUI(boolean usingGUI) {
        this.usingGUI = usingGUI;
    }

    JFrameTest vendMachineJFrame;

    public JFrameTest getVendMachineJFrame() {
        return vendMachineJFrame;
    }

    public void setVendMachineJFrame(JFrameTest vendMachineJFrame) {
        this.vendMachineJFrame = vendMachineJFrame;
    }
//******************

    private double balance = 0.0;

    NumberFormat dollarFormat = NumberFormat.getCurrencyInstance();

    //TEST
    private String mainMenuInput;

    public String getMainMenuInput() {
        return mainMenuInput;
    }

    public void setMainMenuInput(String mainMenuInput) {
        this.mainMenuInput = mainMenuInput;
    }
    //TEST

    private Menu menu;


    public Map<String, Product> productMap = new TreeMap<>();

    public Map<String, Product> getProductMap() {
        return productMap;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public VendingMachineCLI(Menu menu) {
        this.menu = menu;
    }

    ////TEST CODE // THIS WORKED
    private Map<String, Product> createProductMap() {
        try (Scanner inventoryScanner = new Scanner(new File("vendingmachine.csv"))) {
            while (inventoryScanner.hasNextLine()) {
                String currentLine = inventoryScanner.nextLine();
                String[] splitString = currentLine.split("\\|");
                productMap.put(splitString[0], new Product(splitString[0], splitString[1], Double.parseDouble(splitString[2]), splitString[3]));
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return productMap;
    }

    private void displayStartScreen() {
        menu.getChoiceFromOptions(START_MENU_OPTIONS);


    }

    private void displayVendingMachineItems(Map<String, Product> productMap) {

        for (Map.Entry<String, Product> product : productMap.entrySet()) {
            if (product.getValue().getQuantity() > 0) {


                /********************************* USE PRINTF TO LINE UP SLOT LOCATIONS *********************************/


                System.out.print(product.getKey() + ": " + product.getValue().getProductName() + " " + dollarFormat.format(product.getValue().getPrice()) + " " + product.getValue().getQuantity() + " in stock");
            } else {
                System.out.print(product.getValue().getProductName() + ": SOLD OUT");

            }
            if (product.getKey().charAt(1) == '4') {
                System.out.println();
            } else {
                System.out.print(" | ");
            }
        }
    }


    public void run() {

        createProductMap();


        while (true) {
            String startChoice = (String) menu.getChoiceFromOptions(START_MENU_OPTIONS);
            if (startChoice.equals(START_GUI)) {
                usingGUI = true;
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        JFrameTest vendMachineGUI = new JFrameTest(productMap);
                        vendMachineGUI.show();
                    }

                });
            } else if (startChoice.equals(START_COMMAND_LINE)) {
                VendingMachineLog.logStart();
                while (true) {
                    String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);

                    if (choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {
                        // display vending machine items

                        //********MAKE THIS INTO METHOD

                        for (Map.Entry<String, Product> product : productMap.entrySet()) {
                            if (product.getValue().getQuantity() > 0) {


                                /********************************* USE PRINTF TO LINE UP SLOT LOCATIONS *********************************/


                                System.out.print(product.getKey() + ": " + product.getValue().getProductName() + " " + dollarFormat.format(product.getValue().getPrice()) + " " + product.getValue().getQuantity() + " in stock");
                            } else {
                                System.out.print(product.getValue().getProductName() + ": SOLD OUT");

                            }
                            if (product.getKey().charAt(1) == '4') {
                                System.out.println();
                            } else {
                                System.out.print(" | ");
                            }
                        }
                    } else if (choice.equals(MAIN_MENU_OPTION_PURCHASE)) {
                        // do purchase


                        while (true) {

                            System.out.println("\nCurrent Money Provided: " + dollarFormat.format(balance));
                            String customerChoice = (String) menu.getChoiceFromOptions(CUSTOMER_OPTIONS);
                            if (customerChoice.equals(CUSTOMER_OPTION_FEED_MONEY)) {
                                System.out.println("Deposit money. Dollar bills only.");
                                double depositAmount = menu.getDepositAmount();
                                balance += depositAmount;

                                VendingMachineLog.logDeposit(dollarFormat.format(depositAmount), dollarFormat.format(balance));

                            } else if (customerChoice.equals(CUSTOMER_OPTION_SELECT_PRODUCT)) {

                                //TURN THIS INTO METHOD
                                for (Map.Entry<String, Product> product : productMap.entrySet()) {
                                    if (product.getValue().getQuantity() > 0) {
                                        System.out.println(product.getKey() + ": " + product.getValue().getProductName() + " " + dollarFormat.format(product.getValue().getPrice()) + " " + product.getValue().getQuantity() + " in stock");
                                    } else {
                                        System.out.println(product.getValue().getProductName() + ": SOLD OUT");

                                    }
                                }

                                System.out.println("Type the code for your item to select it for purchase:");

                                //TURN THIS INTO A METHOD

                                String purchaseLocation = menu.getLocation(productMap);
                                if (productMap.get(purchaseLocation).getQuantity() > 0) {

                                    if (balance >= productMap.get(purchaseLocation).getPrice()) {
                                        if (productMap.containsKey(purchaseLocation)) {
                                            balance -= productMap.get(purchaseLocation).getPrice();
                                            System.out.println("You purchased " + productMap.get(purchaseLocation).getProductName() + " for " + dollarFormat.format(productMap.get(purchaseLocation).getPrice()) + ". Your remaining balance is: " + dollarFormat.format(balance));
                                            String type = productMap.get(purchaseLocation).getType();
                                            if(!usingGUI)
                                                if (type.equals("Candy")) {
                                                    System.out.println("Candy swirl! You are my world!");
                                                } else if (type.equals("Chip")) {
                                                    System.out.println("Chip Chip, HOORAY!");
                                                } else if (type.equals("Drink")) {
                                                    System.out.println("You are Soda-lightful!");
                                                } else if (type.equals("Gum")) {
                                                    System.out.println("Thanks for chew-sing this vending machine!");
                                                }


                                            int currentQuantity = productMap.get(purchaseLocation).getQuantity();

                                            productMap.get(purchaseLocation).setQuantity(currentQuantity - 1);

                                            VendingMachineLog.logPurchase(productMap.get(purchaseLocation), balance);

                                        }
                                    } else System.out.println("Insufficient balance, please insert more funds");
                                } else System.out.println("Item is sold out");
                            } else if (customerChoice.equals(CUSTOMER_OPTION_END_TRANSACTION)) {
                                double oldBalance = balance;

                                makeChange(balance);

                                balance = 0;
                                VendingMachineLog.logExit(dollarFormat.format(oldBalance), dollarFormat.format(balance));

                                break;
                            }

                        }
                    } else if (choice.equals(EXIT)) {
                        return;
                    } else if (choice.equals(GENERATE_SALES_REPORT)) {
                        SalesReport.generateSalesReport(productMap);
                    }
                }
            }
        }
    }

    public static void main(String[] args) {

        Menu menu = new Menu(System.in, System.out);
        VendingMachineCLI cli = new VendingMachineCLI(menu);
        cli.run();

    }

    public int[] makeChange(double balance) {

        int quarters = (int) ((balance * 100) / 25);
        int remainder = (int) ((balance * 100) % 25);

        int dimes = remainder / 10;
        remainder = remainder % 10;

        int nickels = remainder / 5;
        remainder = remainder % 5;

        if(!usingGUI) {

            System.out.println("From " + dollarFormat.format(balance) + " you get:");
            System.out.println(quarters + " quarters");
            System.out.println(dimes + " dimes");
            System.out.println(nickels + " nickels");
        }

        return new int[]{quarters, dimes, nickels};
    }

    public String formatMoney(double numberToBeConverted) {
        return dollarFormat.format(numberToBeConverted);
    }


}