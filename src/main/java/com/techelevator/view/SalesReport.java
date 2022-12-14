package com.techelevator.view;

import com.techelevator.VendingMachineCLI;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class SalesReport {

    double totalSales = 0;

    static PrintWriter writer;

    static String isoDate = LocalDate.parse(LocalDate.now().toString()).format(DateTimeFormatter.ISO_DATE);

    public static void generateSalesReport(Map<String, Product> productMap) {
        try {
            if (writer == null) {
                writer = new PrintWriter("../../../resources/SalesReport " + isoDate);

            }

            for (Map.Entry<String, Product> product : productMap.entrySet()) {


            }


        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }


    }
}
