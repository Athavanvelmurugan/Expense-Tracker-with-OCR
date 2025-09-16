package Expense;

//BillService.java
import java.sql.*;
import java.util.List;

public class BillService {

 public static void addBill(Bill bill) {
     String insertBill = "INSERT INTO bills (vendor, bill_date, total, image_path) VALUES (?, ?, ?, ?)";
     String insertItem = "INSERT INTO bill_items (bill_id, item_name, quantity, price) VALUES (?, ?, ?, ?)";

     try (Connection conn = DBConnection.getConnection();
          PreparedStatement billStmt = conn.prepareStatement(insertBill, Statement.RETURN_GENERATED_KEYS)) {

         billStmt.setString(1, bill.getVendor());
         billStmt.setDate(2, new java.sql.Date(bill.getBillDate().getTime()));
         billStmt.setDouble(3, bill.getTotal());
         billStmt.setString(4, bill.getImagePath());
         billStmt.executeUpdate();

         ResultSet rs = billStmt.getGeneratedKeys();
         int billId = 0;
         if (rs.next()) billId = rs.getInt(1);

         try (PreparedStatement itemStmt = conn.prepareStatement(insertItem)) {
             for (BillItem item : bill.getItems()) {
                 itemStmt.setInt(1, billId);
                 itemStmt.setString(2, item.getName());
                 itemStmt.setInt(3, item.getQuantity());
                 itemStmt.setDouble(4, item.getPrice());
                 itemStmt.addBatch();
             }
             itemStmt.executeBatch();
         }

         System.out.println("Bill added successfully with ID: " + billId);
     } catch (SQLException e) {
         e.printStackTrace();
     }
 }

 public static void viewBills() {
     String query = "SELECT * FROM bills";
     try (Connection conn = DBConnection.getConnection();
          Statement stmt = conn.createStatement();
          ResultSet rs = stmt.executeQuery(query)) {

         while (rs.next()) {
             int billId = rs.getInt("id");
             System.out.println("Bill ID: " + billId);
             System.out.println("Vendor: " + rs.getString("vendor"));
             System.out.println("Date: " + rs.getDate("bill_date"));
             System.out.println("Total: " + rs.getDouble("total"));
             System.out.println("Image Path: " + rs.getString("image_path"));
             System.out.println("Items:");

             try (PreparedStatement itemStmt = conn.prepareStatement("SELECT * FROM bill_items WHERE bill_id=?")) {
                 itemStmt.setInt(1, billId);
                 ResultSet itemsRs = itemStmt.executeQuery();
                 while (itemsRs.next()) {
                     System.out.println("  - " + itemsRs.getString("item_name") + 
                                        " | Qty: " + itemsRs.getInt("quantity") + 
                                        " | Price: " + itemsRs.getDouble("price"));
                 }
             }
             System.out.println("-----------------------------");
         }
     } catch (SQLException e) {
         e.printStackTrace();
     }
 }
}
