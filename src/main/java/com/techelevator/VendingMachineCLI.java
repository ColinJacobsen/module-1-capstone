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
    private static final String[] MAIN_MENU_OPTIONS = {MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE};

    private static final String CUSTOMER_OPTION_FEED_MONEY = "Feed Money?";

    private static final String CUSTOMER_OPTION_SELECT_PRODUCT = "Select Product";

    private static final String CUSTOMER_OPTION_END_TRANSACTION = "End Transaction";

    private static final String GENERATE_SALES_REPORT = null;

    private static final String[] CUSTOMER_OPTIONS = {CUSTOMER_OPTION_FEED_MONEY, CUSTOMER_OPTION_SELECT_PRODUCT, CUSTOMER_OPTION_END_TRANSACTION, GENERATE_SALES_REPORT};

    private static File inventoryFile = new File("vendingmachine.csv");

    private double balance = 0.0;

    private static final double QUARTER_VALUE = .25;

    private static final double DIME_VALUE = .10;

    private static final double NICKEL_VALUE = .05;

    private static int numberOfQuarters;

    private static int numberOfDimes;

    private static int numberOfNickels;

    NumberFormat dollarFormat = NumberFormat.getCurrencyInstance();

    private Menu menu;

    Map<String, Product> productMap = new TreeMap<>();

    public Map<String, Product> getProductMap() {
        return productMap;
    }

    public void setProductMap(Map<String, Product> productMap) {
        this.productMap = productMap;
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

    public void run() {

        try (Scanner inventoryScanner = new Scanner(inventoryFile)) {
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
                        System.out.println(product.getKey() + ": " + product.getValue().getProductName() + " " + dollarFormat.format(product.getValue().getPrice()) + " " + product.getValue().getQuantity() + " in stock");
                    } else {
                        System.out.println(product.getValue().getProductName() + ": SOLD OUT");

                    }

                }
            } else if (choice.equals(MAIN_MENU_OPTION_PURCHASE)) {
                // do purchase


                while (true) {

                    System.out.println("Current Money Provided: " + dollarFormat.format(balance));
                    String customerChoice = (String) menu.getChoiceFromOptions(CUSTOMER_OPTIONS);
                    if (customerChoice.equals(CUSTOMER_OPTION_FEED_MONEY)) {
                        System.out.println("How many whole dollars did you deposit?");
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

                            System.out.println("Type the code for your item to select it for purchase (case sensitive):");
                            String purchaseLocation = menu.getLocation();
                            if (productMap.get(purchaseLocation).getQuantity() > 0) {

                                if (balance >= productMap.get(purchaseLocation).getPrice()) {
                                    if (productMap.containsKey(purchaseLocation)) {
                                        balance -= productMap.get(purchaseLocation).getPrice();
                                        System.out.println("You purchased " + productMap.get(purchaseLocation).getProductName() + " for " + dollarFormat.format(productMap.get(purchaseLocation).getPrice()) + ". Your remaining balance is: " + dollarFormat.format(balance));
                                        if (productMap.get(purchaseLocation).getType().equalsIgnoreCase("candy")) {
                                            System.out.println("Munch Munch, Yum!");
                                        } else if (productMap.get(purchaseLocation).getType().equalsIgnoreCase("chips")) {
                                            System.out.println("Crunch Crunch, Yum!");
                                        } else if (productMap.get(purchaseLocation).getType().equalsIgnoreCase("soda")) {
                                            System.out.println("Glug Glug, Yum!");
                                        } else if (productMap.get(purchaseLocation).getType().equalsIgnoreCase("gum")) {
                                            System.out.println("Chew Chew, Yum!");
                                        }

                                        int currentQuantity = productMap.get(purchaseLocation).getQuantity();

                                        productMap.get(purchaseLocation).setQuantity(currentQuantity -= 1);

                                        VendingMachineLog.logPurchase(productMap.get(purchaseLocation), balance);

                                    }
                                } else System.out.println("Insufficient balance, please insert more funds");
                            } else System.out.println("Item is sold out");
                        }
                    else if(customerChoice.equals(CUSTOMER_OPTION_END_TRANSACTION)){
                        double oldBalance = balance;
                        numberOfQuarters = (int) (balance/QUARTER_VALUE);
                        numberOfDimes = (int)((balance-(numberOfQuarters*QUARTER_VALUE))/DIME_VALUE);
                        numberOfNickels = (int)((balance-((numberOfQuarters*QUARTER_VALUE)+(numberOfDimes*DIME_VALUE)))/NICKEL_VALUE);
                        System.out.println("Your change is: " + dollarFormat.format(balance));
                        System.out.println("You should receive " + numberOfQuarters + " quarters, " + numberOfDimes + " dimes, and " + numberOfNickels + " nickels.");
                        balance = 0;
                        VendingMachineLog.logExit(dollarFormat.format(oldBalance), dollarFormat.format(balance));

                        break;
                    }
                    else if (customerChoice.equals(GENERATE_SALES_REPORT)){
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

}