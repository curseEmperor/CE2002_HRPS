package entities;

import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Order {
    private int orderID;
    private String roomID;
    private String orderStatus; // confirmed, preparing, delivered
    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
    private String date;
    private ArrayList<Item> listOfFood;
    private String remarks; // less oil, less salt

    public Order(int orderID, String roomID, String orderStatus, ArrayList<Item> order, String remarks) {
        this.orderID = orderID;
        this.roomID = roomID;
        this.orderStatus = orderStatus;
        this.listOfFood = null;
        Calendar c = Calendar.getInstance();
        String date = formatter.format(c.getTime());
        this.date = date;
        this.remarks = remarks;

    } // constructor

    public Order() {
    }

    public int size() {
        return this.listOfFood.size();
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    public String getRoomID() {
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

    public ArrayList<Item> getListOfFood() {
        return listOfFood;
    }

    public void setListOfFood(ArrayList<Item> listOfFood) {
        this.listOfFood = listOfFood;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getRemarks() {
        return remarks;
    }

    // public void addItem(Item item) {
    // this.order.add(item);
    // }

    // public boolean removeItem(Item itemToRemove) {
    // for (Item it : order) {
    // if (it.getID() == itemToRemove.getID()) {
    // this.order.remove(it);
    // return true;
    // }
    // }
    // return false;
    // }

    // public double calcOrderPrice() {
    // double orderPrice = 0;
    // for (int i = 0; i < order.size(); i++) {
    // orderPrice += order.get(i).getPrice();
    // }
    // return orderPrice;
    // }

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
