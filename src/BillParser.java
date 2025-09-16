package Expense;

//BillParser.java
import java.util.*;

public class BillParser {

 public static List<BillItem> parseItems(String ocrText) {
     List<BillItem> items = new ArrayList<>();
     String[] lines = ocrText.split("\\r?\\n");

     for (String line : lines) {
         line = line.trim();
         if (line.isEmpty()) continue;

         // Example: "Milk 2 50" or "Bread 1 30"
         String[] parts = line.split(" ");
         if (parts.length >= 3) {
             try {
                 String name = parts[0];
                 int quantity = Integer.parseInt(parts[1]);
                 double price = Double.parseDouble(parts[2]);
                 items.add(new BillItem(name, quantity, price));
             } catch (NumberFormatException e) {
                 // skip invalid lines
             }
         }
     }
     return items;
 }
}
