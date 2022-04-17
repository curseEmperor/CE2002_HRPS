package Entity;

import java.util.ArrayList;
import java.text.SimpleDateFormat;

import Enums.OrderStatus;

public class Order extends Entities {
    private String orderID;
    private String roomID;
    private OrderStatus orderStatus; // confirmed, preparing, delivered
    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
    private String date;
    private ArrayList<Item> listOfFood;
    private String remarks; // less oil, less salt

    public Order(String roomID) {
        this.roomID = roomID;
    }

    public int size() {
        return this.listOfFood.size();
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    public String getRoomID() {
        return roomID;
    }

    public void setOrderStatus(OrderStatus status) {
        this.orderStatus = status;
    }

    public OrderStatus getOrderStatus() {
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

    public float viewOrder() {

        sortOrder();

        float cost = 0;

        System.out.println();
        System.out.println("==============================");
        System.out.println("Items ordered for Room Service");
        System.out.println("==============================");
        System.out.println("Order ID: " + orderID);
        Item temp = null;
        int count = 0;
        for (Item food : listOfFood) {
            if (temp == food) {
                count++;
            } else {
                if (count != 0)
                    System.out.println("\t" + count);
                temp = food;
                System.out.print(food);
                count = 1;
            }
            cost += food.getPrice();
        }
        System.out.println("\t" + count);
        System.out.println("Total cost: $" + cost);
        System.out.printf("Status:\t");
        switch (orderStatus) {
            case CONFIRM:
                System.out.println("Confirmed");
                break;
            case PREPARING:
                System.out.println("Preparing");
                break;
            case DELIVERED:
                System.out.println("Delivered");
                break;
            case PAID:
                System.out.println("Paid");
                break;
        }
        System.out.println();
        return cost;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        String newLine = System.getProperty("line.separator");

        result.append(this.getClass().getName() + newLine);

        result.append("Date: " + this.date + newLine);
        result.append("orderID: " + this.orderID + "\tStatus: " + this.orderStatus + newLine);
        result.append("For Room: " + this.roomID + newLine);
        result.append("Remarks: " + this.remarks + newLine);

        return result.toString();
    }

    public void sortOrder() {
        int a, b;
        Item temp;
        for (a = 0; a < listOfFood.size(); a++) {
            for (b = a; b > 0; b--) {
                temp = listOfFood.get(b);
                if (temp.getID().compareTo(listOfFood.get(b - 1).getID()) > 0)
                    break;
                else {
                    listOfFood.set(b, listOfFood.get(b - 1));
                    listOfFood.set(b - 1, temp);
                }
            }
        }
    }

}