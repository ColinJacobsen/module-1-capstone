package com.techelevator;

import com.techelevator.view.Menu;
import com.techelevator.view.Product;
import com.techelevator.view.SalesReport;
import com.techelevator.view.VendingMachineLog;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class VendingMachineCLI {

    private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
    private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";

    private static final String EXIT = "Exit";
    private static final String GENERATE_SALES_REPORT = "";
    private static final String[] MAIN_MENU_OPTIONS = {MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE, EXIT, GENERATE_SALES_REPORT};

    private static final String CUSTOMER_OPTION_FEED_MONEY = "Feed Money";
    private static final String CUSTOMER_OPTION_SELECT_PRODUCT = "Select Product";
    private static final String CUSTOMER_OPTION_END_TRANSACTION = "Finish Transaction";
    private static final String[] CUSTOMER_OPTIONS = new String[]{CUSTOMER_OPTION_FEED_MONEY, CUSTOMER_OPTION_SELECT_PRODUCT, CUSTOMER_OPTION_END_TRANSACTION};

    private double balance = 0.0;

    NumberFormat dollarFormat = NumberFormat.getCurrencyInstance();

    private Menu menu;

    Map<String, Product> productMap = new TreeMap<>();


    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public VendingMachineCLI(Menu menu) {
        this.menu = menu;
    }

    public void run() {

        try (Scanner inventoryScanner = new Scanner(new File("vendingmachine.csv"))) {
            while (inventoryScanner.hasNextLine()) {
                String currentLine = inventoryScanner.nextLine();
                String[] splitString = currentLine.split("\\|");
                productMap.put(splitString[0], new Product(splitString[0], splitString[1], Double.parseDouble(splitString[2]), splitString[3]));
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        while (true) {
            String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);

            if (choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {
                // display vending machine items
                for (Map.Entry<String, Product> product : productMap.entrySet()) {
                    if (product.getValue().getQuantity() > 0) {
                        System.out.print(product.getKey() + ": " + product.getValue().getProductName() + " " + dollarFormat.format(product.getValue().getPrice()) + " " + product.getValue().getQuantity() + " in stock");
                    } else {
                        System.out.print(product.getValue().getProductName() + ": SOLD OUT");

                    }
                    if(product.getKey().charAt(1) == '4'){
                        System.out.println();
                    }
                    else{
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
                        for (Map.Entry<String, Product> product : productMap.entrySet()) {
                            if (product.getValue().getQuantity() > 0) {
                                System.out.println(product.getKey() + ": " + product.getValue().getProductName() + " " + dollarFormat.format(product.getValue().getPrice()) + " " + product.getValue().getQuantity() + " in stock");
                            } else {
                                System.out.println(product.getValue().getProductName() + ": SOLD OUT");

                            }
                        }

                        System.out.println("Type the code for your item to select it for purchase:");
                        String purchaseLocation = menu.getLocation(productMap);
                        if (productMap.get(purchaseLocation).getQuantity() > 0) {

                            if (balance >= productMap.get(purchaseLocation).getPrice()) {
                                if (productMap.containsKey(purchaseLocation)) {
                                    balance -= productMap.get(purchaseLocation).getPrice();
                                    System.out.println("You purchased " + productMap.get(purchaseLocation).getProductName() + " for " + dollarFormat.format(productMap.get(purchaseLocation).getPrice()) + ". Your remaining balance is: " + dollarFormat.format(balance));

                                    // getType() has if-else that prints sounds, gets type from the key location in Map
                                    productMap.get(purchaseLocation).getType();


                                    int currentQuantity = productMap.get(purchaseLocation).getQuantity();

                                    productMap.get(purchaseLocation).setQuantity(currentQuantity - 1);

                                    VendingMachineLog.logPurchase(productMap.get(purchaseLocation), balance);

                                }
                            } else System.out.println("Insufficient balance, please insert more funds");
                        } else System.out.println("Item is sold out");
                    }
                    else if(customerChoice.equals(CUSTOMER_OPTION_END_TRANSACTION)){double oldBalance = balance;

                        makeChange(balance);

                        balance = 0;
                        VendingMachineLog.logExit(dollarFormat.format(oldBalance), dollarFormat.format(balance));

                        break;
                    }

                }
            } else if (choice.equals(GENERATE_SALES_REPORT)){
                SalesReport.generateSalesReport(productMap);
            }
        }
    }

    public static void main(String[] args) {
        Menu menu = new Menu(System.in, System.out);
        VendingMachineCLI cli = new VendingMachineCLI(menu);
        cli.run();

    }

    public double makeChange(double balance){

        int quarters = (int) (balance / 25);
        int remainder = (int) (balance % 25);

        int dimes = remainder / 10;
        remainder = remainder % 10;

        int nickels = remainder / 5;
        remainder = remainder % 5;

        int cents = remainder;

        System.out.println("From " + dollarFormat.format(balance) + " cents you get");
        System.out.println(quarters + " quarters");
        System.out.println(dimes + " dimes");
        System.out.println(nickels + " nickels");
        System.out.println(cents + " cents");

        return balance;
    }

    public String formatMoney(double numberToBeConverted) {
        return dollarFormat.format(numberToBeConverted);
    }


}