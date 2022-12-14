package com.techelevator.view;

import java.util.Map;

    // will need a log class and exception log class
//    public enum ProductName {
//        potato_crisps, stackers, grain_waves,
//    }

    public class Product {

        private String productName;

        private String type;

        private double price;

        private String productLocation; // or a list?!? ProductLocation could be enum like ProductName

        private Map<String,Double> products;

        private int quantity = 5;


        public Product(String productLocation, String productName, double price, String type) {

            this.productLocation = productLocation;
            this.productName = productName;
            this.price = price;
            this.type = type;

        }

        // a display() method to list all items
        // a sales_report() method
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

        public String getType() {
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

        public String getProductLocation() {
            return productLocation;
        }

        public void setProductLocation(String productLocation) {
            this.productLocation = productLocation;
        }

        public Map<String, Double> getProducts() {
            return products;
        }

        public void setProducts(Map<String, Double> products) {
            this.products = products;
        }

    }
