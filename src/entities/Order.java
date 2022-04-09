package Entity;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Order {
//	private int countID; // number of items 
	private int orderID;
	private int roomID;
	private String orderStatus; //confirmed, preparing, delivered
	SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
	private String date;
	private ArrayList<item> order = new ArrayList<item>();
	private String remarks; //less oil, less salt
//	Calendar c = Calendar.getInstance();
//	String d = sdf.format(c.getTime());
	
	public Order(int orderID, int roomID, String orderStatus, ArrayList<item> order, String remarks) {
		//this.countID = countID;
		this.orderID = orderID;
		this.roomID = roomID;
		this.orderStatus = orderStatus;
		this.order = order;
		Calendar c = Calendar.getInstance();
        String date = formatter.format(c.getTime());
        this.date = date;
		this.remarks = remarks;
		
	} // constructor
	
	public Order() {
		// TODO Auto-generated constructor stub
	}
	
	public int size() {
        return this.order.size();
    }

//	public void setcountID(int ID) {
//	        countID = ID;
//	}
//	 
//	public int getcountID() {
//        return countID;
//    }
    
    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }
    
    public int getOrderID() {
        return orderID;
    }
    
    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }
    
    public int getRoomID() {
        return roomID;
    }
    
    public void setOrderStatus(String status) {
        this.orderStatus = status;
    }
    
    public String getOrderStatus() {
        return orderStatus;
    }
    
    public void setDate(String date) {
        this.date = date;
    }
    
    public String getDate() {
        return date;
    }
    
    public ArrayList<item> getItems() {
        return order;
    }
    
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
    

    public String getRemarks() {
        return remarks;
    }
    
    public void addItem(item item) {
        this.order.add(item);
    }
    
    public boolean removeItem(item itemToRemove) {
        for (item it : order) {
            if (it.getID() == itemToRemove.getID()) {
                this.order.remove(it);
                return true;
            }
        }
        return false;
    }
   
	public double calcOrderPrice(ArrayList<item> order) {
		double orderPrice = 0;
		for (int i=0; i<order.size(); i++) {
			orderPrice += order.get(i).getPrice();
		}
		return orderPrice;
	}
	
	public void viewOrder() {
		System.out.println("ID   Room   Date                          Remarks                       Status   ");
        System.out.println(toString());
        System.out.println("=================================================================================");
        System.out.println("ID   Name                          Description                          Price(S$)");
        System.out.println("=================================================================================");    
		for (int i = 0; i < order.size(); i++) {
		      System.out.println(order.get(i).toString());
		    }
		System.out.println("=================================================================================");
	}
	
	public String toString() {

        return (String.format("%-5d%-7s%-30s%-30s%-10s", orderID, roomID, date, remarks, orderStatus));
    }
	
}

