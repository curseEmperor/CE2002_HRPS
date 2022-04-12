package Controller;
import entities.Order;
import entities.Item;
import Controller.Menu;

import java.text.SimpleDateFormat;
import java.util.Calendar;


import java.util.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class OrderController implements Serializable {
	
	
	ArrayList<Order> orderList = new ArrayList<Order>();
	//private static final String filename = "Order.txt";
	private static OrderController instance = null;
	SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
	Scanner sc = new Scanner(System.in);
	
	//constructor
	public OrderController() {
		this.orderList = new ArrayList<Order>();
	} 
	
	//creating new instance of OrderController
    public static OrderController getInstance() {
        if (instance == null) {
            instance = new OrderController();
        }
        return instance;
    }
     
	public void createOrder() {
		Order order = new Order();
		orderList.add(order);
		System.out.println("Please enter your Room ID.");
		int roomid = sc.nextInt();
		order.setRoomID(roomid);
		
		System.out.println("Please enter the Order ID.");
		int orderid = sc.nextInt();
		order.setOrderID(orderid);
		
		//display menu

		System.out.println("Please enter the itemID of the item you wish to order.");
		int input = sc.nextInt();
		Menu menu = Menu.getInstance();
		Item itemToAdd = menu.getItem(input);
		System.out.printf("Please enter the quantity of item %d.\n", input);
		int quantityOfItem = sc.nextInt();
		for (int i=0; i<quantityOfItem; i++) order.addItem(itemToAdd);
		
		Calendar c = Calendar.getInstance();
        String date = formatter.format(c.getTime());
        order.setDate(date);
        
        System.out.println("Please enter remarks: ");
        String remarks = sc.next();
        order.setRemarks(remarks);
        
        order.setOrderStatus("Confirmed");
        System.out.println("Order %d has been confirmed: " + orderid);
        
        this.savetoDB();
	}
	
    // order can be cancelled as long as it has yet to be delivered
	public boolean cancelOrder(Order orderToCancel) {
        for (Order order : orderList) {
            if (order.getOrderID() == orderToCancel.getOrderID() && orderToCancel.getOrderStatus()!= "Delivered") {
                this.orderList.remove(order);
                return true;
            }
        }
        return false;
    }
	
	public Order retrieveOrder(int orderID) {	//by orderID
        for (Order order : orderList) {
            if (order.getOrderID() == orderID)
                return order;
        }
        return null;
    }
	
	public ArrayList<Order> retrieveOrderList(int roomID) { 		//by roomID
    	ArrayList<Order> retrieveOL = new ArrayList<Order>();
    	for (Order order : orderList) {
            if (order.getRoomID()==roomID)
                retrieveOL.add(order);
        }
    	if(retrieveOL.size() > 0) return retrieveOL;
    	else return null;
    }
	
	// if guest wants to "update an order", he/she can either choose to removeOrder then createOrder or updateItemInOrder
	
	public void updateItemInOrder(int orderID, Item item) {
		Order order = retrieveOrder(orderID);
        ArrayList<Item> items = order.getItems();
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getID() == item.getID()) {
                items.set(i, item);
                System.out.println(String.format("Order %d was updated.", order.getOrderID()));
            }
        }
        
        this.savetoDB();
    }
		
	public void updateStatus(int orderID, String newOrderStatus) {
		Order order = retrieveOrder(orderID);
		order.setOrderStatus(newOrderStatus);
	}
	
	public void displayOrder(int orderID) {
		Order order = retrieveOrder(orderID);
		order.viewOrder();
	}
	
	public void savetoDB() {
    	try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("Order.ser"));
            out.writeInt(orderList.size());
            for (Order order: orderList)
                out.writeObject(order);
            System.out.printf("OrderController: %s Entries Saved.\n", orderList);
            out.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }	
	
	public void loadinDB() {
        try {
        	ObjectInputStream ois = new ObjectInputStream(new FileInputStream("Order.ser"));

            int noOfRecords = ois.readInt();
            System.out.println("OrderController: " + noOfRecords + " Entries Loaded");
            for (int i = 0; i < noOfRecords; i++) {
                orderList.add((Order) ois.readObject());
            }
            for (Order order : orderList) {
                System.out.println(order);
            }
            ois.close();
        } catch (IOException | ClassNotFoundException e2) {
            e2.printStackTrace();
        }
    }	

}
