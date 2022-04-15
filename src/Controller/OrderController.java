package Controller;

import entities.Order;
import entities.Item;
import entities.Node;

import java.text.SimpleDateFormat;

import Enums.OrderStatus;
import Enums.PriceFilterType;

import java.util.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class OrderController implements IController, ICheckOut {

    ArrayList<Order> orderList;
    private static OrderController instance = null;
    SimpleDateFormat formatter;

    // constructor
    private OrderController() {
        orderList = new ArrayList<Order>();
        formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
    }

    // creating new instance of OrderController
    public static OrderController getInstance() {
        if (instance == null) {
            instance = new OrderController();
        }
        return instance;
    }

    // check existence
    public Order checkExistence(String orderID) { // by orderID eg. 02021
        for (Order order : orderList) {
            if (order.getOrderID().compareTo(orderID) == 0)
                return order;
        }
        return null;
    }

    public void create(Object entities) {
        Order order = (Order) entities;

        Calendar c = Calendar.getInstance();
        Date time = c.getTime();
        String date = formatter.format(time);
        order.setDate(date);
        SimpleDateFormat formatID = new SimpleDateFormat("ddMMyyhhmm");
        order.setOrderID(order.getRoomID() + formatID.format(time));

        order.setOrderStatus(OrderStatus.CONFIRM);
        order.setListOfFood(new ArrayList<Item>());

        orderList.add(order);

        storeData();
    }

    // order can be cancelled as long as it has yet to be delivered
    // delete() - to delete the whole order from stockpile of orders
    public void delete(Object entities) {

        Order toBeDeleted = (Order) entities;

        if (toBeDeleted.getOrderStatus() != OrderStatus.DELIVERED) {
            orderList.remove(toBeDeleted);
            System.out.println("Order is deleted.");
            return;
        }

        System.out.println("Order can't be deleted as it is delivered.");
    }

    // if guest wants to "update an order",
    // he can only add new item
    public void update(Object entities, int choice, String value) {

        Order order = (Order) entities;

        switch (choice) {
            case 1: // roomID
                order.setRoomID(value);
                break;
            case 2: // remarks
                order.setRemarks(value);
                break;
            case 3: // orderStatus
                OrderStatus status = generateStatus(value);
                order.setOrderStatus(status);
                break;
        }

        storeData();
    }

    public void addItemtoOrder(Order order, Item itemToAdd) {
        if (order.getOrderStatus() != OrderStatus.CONFIRM) {
            System.out.println("Order is preparing or is delivered, no changes can be made.");
            return;
        }
        order.getListOfFood().add(itemToAdd);
        storeData();
    }

    public void deleteItemfromOrder(Order order, Item itemToDelete) {
        if (order.getOrderStatus() != OrderStatus.CONFIRM) {
            System.out.println("Order is preparing or is delivered, no changes can be made.");
            return;
        }
        if (order.getListOfFood().remove(itemToDelete)) {
            System.out.println("Item removed from order " + order.getOrderID());
            if (order.getListOfFood().size() == 0) {
                System.out.println("No items left... Deleting order...");
                orderList.remove(order);
            }
        }
        storeData();
    }

    public void read() {
        for (Order order : orderList) {
            System.out.println(order.getOrderID());
        }
    }

    public void read(Order order) { // read a single order
        order.viewOrder();
    }

    public ArrayList<Order> retrieveOrdersOfRoom(String roomID) { // by roomID
        ArrayList<Order> retrieveOL = new ArrayList<Order>();
        for (Order order : orderList) {
            if (order.getRoomID().compareTo(roomID) == 0 && order.getOrderStatus() != OrderStatus.PAID)
                retrieveOL.add(order);
        }
        if (retrieveOL.size() > 0)
            return retrieveOL;
        else
            return null;
    }

    private OrderStatus generateStatus(String value) {
        int choice = Integer.parseInt(value);
        switch (choice) {
            case 1:
                return OrderStatus.CONFIRM;
            case 2:
                return OrderStatus.PREPARING;
            case 3:
                return OrderStatus.DELIVERED;
            case 4:
                return OrderStatus.PAID;
            default:
                return null;
        }
    }

    public void storeData() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("Order.ser"));
            out.writeInt(orderList.size());
            for (Order order : orderList)
                out.writeObject(order);
            // System.out.printf("%s \n\n--Entries Saved.--\n",
            // orderList.toString().replace("[", "").replace("]", ""));
            System.out.println("--Entries Saved--\n");
            out.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public void loadData() {
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

    @Override
    public Node checkOutPro(String roomID) {

        Node node;
        double total = 0;
        ArrayList<Order> toBeCalc = retrieveOrdersOfRoom(roomID);

        if (toBeCalc.size() == 0) {
            node = new Node(PriceFilterType.ADDER, 0);
        } else {
            for (Order order : toBeCalc) {
                order.setOrderStatus(OrderStatus.PAID);
                for (Item item : order.getListOfFood()) {
                    total += item.getPrice();
                }
            }

            node = new Node(PriceFilterType.ADDER, total);
        }

        System.out.println("Total cost of Room Service Order: " + total);
        return node;
    }

}
