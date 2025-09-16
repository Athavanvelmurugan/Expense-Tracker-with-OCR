package Expense;

//Bill.java
import java.util.Date;
import java.util.List;

public class Bill {
	private int id;
	private String vendor;
	private Date billDate;
	private double total;
	private String imagePath;
	private List<BillItem> items;

	public int getId() { return id; }
	public void setId(int id) { this.id = id; }
	public String getVendor() { return vendor; }
	public void setVendor(String vendor) { this.vendor = vendor; }
	public Date getBillDate() { return billDate; }
	public void setBillDate(Date billDate) { this.billDate = billDate; }
	public double getTotal() { return total; }
	public void setTotal(double total) { this.total = total; }
	public String getImagePath() { return imagePath; }
	public void setImagePath(String imagePath) { this.imagePath = imagePath; }
	public List<BillItem> getItems() { return items; }
	public void setItems(List<BillItem> items) { this.items = items; }
}

