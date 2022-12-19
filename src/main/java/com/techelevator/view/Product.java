package com.techelevator.view;
import com.techelevator.VendingMachineCLI;

import java.util.Map;

public class Product {
    private String slotLocation;
    private String productName;
    private double price;
    private String type;
    private int quantity = 5;

    //private Map<String,Double> products;


    public Product(String slotLocation, String productName, double price, String type) {

        this.slotLocation = slotLocation;
        this.productName = productName;
        this.price = price;
        this.type = type;

    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    //prints out the type when needed for the user experience
    public String getType() {

//        if()
//        if (type.equals("Candy")) {
//            System.out.println("Candy swirl! You are my world!");
//        } else if (type.equals("Chip")) {
//            System.out.println("Chip Chip, HOORAY!");
//        } else if (type.equals("Drink")){
//            System.out.println("You are Soda-lightful!");
//        } else if (type.equals("Gum")){
//            System.out.println("Thanks for chew-sing this vending machine!");
//        }

        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getSlotLocation() {
        return slotLocation;
    }

    public void setSlotLocation(String productLocation) {
        this.slotLocation = productLocation;
    }

<<<<<<< HEAD
   // public Map<String, Double> getProducts() {
   //     return products;
   // }

   // public void setProducts(Map<String, Double> products) {
    //    this.products = products;
   // }
=======
//    public Map<String, Double> getProducts() {
//        return products;
//    }
//
//    public void setProducts(Map<String, Double> products) {
//        this.products = products;
//    }
>>>>>>> 57a35ce2c98151280bb1688b6a6d6981dc0a8bbe

}