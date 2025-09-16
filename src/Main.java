package Expense;

//Main.java
import java.util.*;
import java.util.stream.*;

public class Main {
 public static void main(String[] args) {
	 String imagePath = "C:\\Users\\Athavan\\Downloads\\aimage.jpg";

   //  String imagePath = "\C:\\Users\\Athavan\\Downloads\\aimage.png.jpg\"; // put your receipt image here

     // Step 1: Extract text from image
     String ocrText = OCRUtil.extractText(imagePath);
     System.out.println("OCR Extracted Text:\n" + ocrText);

     // Step 2: Parse items
     List<BillItem> items = BillParser.parseItems(ocrText);

     // Step 3: Calculate total
     double total = items.stream().mapToDouble(i -> i.getPrice() * i.getQuantity()).sum();

     // Step 4: Create Bill object
     Bill bill = new Bill();
     bill.setVendor("SuperMart");
     bill.setBillDate(new Date());
     bill.setItems(items);
     bill.setTotal(total);
     bill.setImagePath(imagePath);

     // Step 5: Save to database
     BillService.addBill(bill);

     // Step 6: View all bills
     BillService.viewBills();
 }
}
