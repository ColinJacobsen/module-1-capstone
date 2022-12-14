package com.techelevator;

import com.techelevator.view.Menu;
import com.techelevator.view.Product;

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

	private static final String[] CUSTOMER_OPTIONS = {CUSTOMER_OPTION_FEED_MONEY, CUSTOMER_OPTION_SELECT_PRODUCT, CUSTOMER_OPTION_END_TRANSACTION};

	private static File inventoryFile = new File("vendingmachine.csv");

	private double balance = 0.0;

	NumberFormat dollarFormat = NumberFormat.getCurrencyInstance();

	private Menu menu;

	Map<String, Product> productMap = new TreeMap<>();

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
					System.out.println(product.getKey() + ": " + product.getValue().getProductName() + " " + dollarFormat.format(product.getValue().getPrice()) + " " + product.getValue().getQuantity() + " in stock");
				}

			} else if (choice.equals(MAIN_MENU_OPTION_PURCHASE)) {
				// do purchase

				//BOOKMARK **********************************************************

				while (true) {

					System.out.println("Current Money Provided: " + dollarFormat.format(balance));
					String customerChoice = (String) menu.getChoiceFromOptions(CUSTOMER_OPTIONS);
					if (customerChoice.equals(CUSTOMER_OPTION_FEED_MONEY)) {
						System.out.println("How many whole dollars did you deposit?");
						balance += menu.getDepositAmount();

					} else if (customerChoice.equals(CUSTOMER_OPTION_SELECT_PRODUCT)) {
						for (Map.Entry<String, Product> product : productMap.entrySet()) {
							System.out.println(product.getKey() + ": " + product.getValue().getProductName() + " " + dollarFormat.format(product.getValue().getPrice()) + " " + product.getValue().getQuantity() + " in stock");
						}
						System.out.println("Type the code for your item to select it for purchase (case sensitive):");
						String purchaseLocation = menu.getLocation();
						if (productMap.containsKey(purchaseLocation)) {
							balance -= productMap.get(purchaseLocation).getPrice();
							System.out.println(productMap.get(purchaseLocation).getProductName() + " " + productMap.get(purchaseLocation).getPrice() + " " + balance);
							if(productMap.get(purchaseLocation).getType().equalsIgnoreCase("candy")){
									System.out.println("Munch Munch, Yum!\"");
								} else if (productMap.get(purchaseLocation).getType().equalsIgnoreCase("chips")){
									System.out.println("Crunch Crunch, Yum!\"");
							} else if (productMap.get(purchaseLocation).getType().equalsIgnoreCase("soda")){
								System.out.println("Glug Glug, Yum!\"");
							} else if (productMap.get(purchaseLocation).getType().equalsIgnoreCase("gum")){
								System.out.println("Chew Chew, Yum!\"");
							}

							int currentQuantity = productMap.get(purchaseLocation).getQuantity();

							productMap.get(purchaseLocation).setQuantity(currentQuantity -= 1);

							//NEED TO TEST AFTER FIXING CONSOLE LOG

						}
					}
				}
			}
		}
		}
		public static void main (String[]args){
			Menu menu = new Menu(System.in, System.out);
			VendingMachineCLI cli = new VendingMachineCLI(menu);
			cli.run();

		}

	}