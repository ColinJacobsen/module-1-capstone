package com.techelevator.view;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class VendingMachineLog {


    static PrintWriter writer;

    public static void logPurchase(Product product, Double balance){
        LocalDateTime dateTime = LocalDateTime.now();
        try{
            if(writer == null){
                writer = new PrintWriter("src/main/resources/Log.txt");
            }
            Format dateFormat = new SimpleDateFormat("MM/dd/yy");
            String strDate = dateFormat.format(new Date());

            Date date = new Date();
            String strDateFormat = "HH:mm:ss a";
            SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
            System.out.println(sdf.format(date));

            writer.print(strDate + " " + sdf.format(date) + " " + product.getProductName() + " " + product.getProductLocation() + " " + product.getPrice() + " " + balance);


        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        writer.flush();

    }


    public static void logDeposit(String depositAmount, String balance){
        LocalDateTime dateTime = LocalDateTime.now();
        try{
            if(writer == null){
                writer = new PrintWriter("src/main/resources/Log.txt");
            }
            Format dateFormat = new SimpleDateFormat("MM/dd/yy");
            String strDate = dateFormat.format(new Date());

            Date date = new Date();
            String strDateFormat = "HH:mm a";
            SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
            System.out.println(sdf.format(date));

            writer.print(strDate + " " + sdf.format(date) + " FEED MONEY: " + depositAmount + " " + balance + "\n");


        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        writer.flush();

    }

    public static void logExit(String change, String balance){
        LocalDateTime dateTime = LocalDateTime.now();
        try{
            if(writer == null){
                writer = new PrintWriter("src/main/resources/Log.txt");
            }
            Format dateFormat = new SimpleDateFormat("MM/dd/yy");
            String strDate = dateFormat.format(new Date());

            Date date = new Date();
            String strDateFormat = "HH:mm a";
            SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
            System.out.println(sdf.format(date));

            writer.print(strDate + " " + sdf.format(date) + " GIVE CHANGE " + change + " " + balance + "\n");


        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        writer.flush();

    }




}
