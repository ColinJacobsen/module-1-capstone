package com.techelevator.view;

import com.techelevator.VendingMachineCLI;

import java.io.*;
import java.text.Format;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class VendingMachineLog {

    static PrintWriter writer;


    public static void logPurchase(Product product, Double balance) {
        LocalDateTime dateTime = LocalDateTime.now();
        Format dateFormat = new SimpleDateFormat("MM/dd/yy");
        String strDate = dateFormat.format(new Date());
        Date date = new Date();
        String strDateFormat = "HH:mm:ss a";
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
        NumberFormat dollarFormat = NumberFormat.getCurrencyInstance();
        File log = new File("src/main/resources/Log.txt");


            try {

                FileWriter fileWriter = new FileWriter(log, true);

                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write(strDate + " " + sdf.format(date) + " " + product.getProductName() + " " + product.getSlotLocation() + " " + dollarFormat.format(product.getPrice()) + " " + dollarFormat.format(balance) + "\n");


                bufferedWriter.close();

            } catch (IOException e) {
                System.out.println("COULD NOT LOG!!");
            }
    }

    public static void logDeposit(String depositAmount, String balance) {
        LocalDateTime dateTime = LocalDateTime.now();
        Format dateFormat = new SimpleDateFormat("MM/dd/yy");
        String strDate = dateFormat.format(new Date());
        Date date = new Date();
        String strDateFormat = "HH:mm:ss a";
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);

        File log = new File("src/main/resources/Log.txt");


        try {

            FileWriter fileWriter = new FileWriter(log, true);

            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(strDate + " " + sdf.format(date) + " FEED MONEY: " + depositAmount + " " + balance + "\n");


            bufferedWriter.close();

        } catch (IOException e) {
            System.out.println("COULD NOT LOG!!");
        }
    }
    public static void logExit(String change, String balance){

        LocalDateTime dateTime = LocalDateTime.now();
        Format dateFormat = new SimpleDateFormat("MM/dd/yy");
        String strDate = dateFormat.format(new Date());
        Date date = new Date();
        String strDateFormat = "HH:mm:ss a";
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);

        File log = new File("src/main/resources/Log.txt");


        try {

            FileWriter fileWriter = new FileWriter(log, true);

            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(strDate + " " + sdf.format(date) + " GIVE CHANGE " + change + " " + balance + "\n");
            bufferedWriter.close();

        } catch (IOException e) {
            System.out.println("COULD NOT LOG!!");
        }
    }

}
