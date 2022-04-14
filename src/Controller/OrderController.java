package Controller;

import entities.Order;
import entities.Item;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import Enums.OrderStatus;

import java.util.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class OrderController implements Serializable {

    ArrayList<Order> orderList;
    // Map<String, ArrayList<Order>> allOrders;
    private static OrderController instance = null;
    SimpleDateFormat formatter;
    Scanner sc;

    // constructor
    private OrderController() {
        orderList = new ArrayList<Order>();
        sc = new Scanner(System.in);
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
    public Order checkExistence(int orderID) { // by orderID eg. 02021
        for (Order order : orderList) {
            if (order.getOrderID() == orderID)
                return order;
        }
        return null;
    }

    public void create() {
        Order order = new Order();

        System.out.println("Please enter your Room ID.");
        String roomID = sc.nextLine();
        order.setRoomID(roomID);

        System.out.println("Please enter the Order ID.");
        int orderID = sc.nextInt();
        sc.nextLine();
        order.setOrderID(orderID);

        addItemtoOrder(order);

        Calendar c = Calendar.getInstance();
        String date = formatter.format(c.getTime());
        order.setDate(date);

        System.out.println("Please enter remarks: ");
        String remarks = sc.next();
        order.setRemarks(remarks);

        System.out.println("Order " + orderID + " has been confirmed. ");

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
    public void update(Object entities) {

        Order order = (Order) entities;

        if (order.getOrderStatus() == OrderStatus.CONFIRM) {
            System.out.println("Choose either to (1)add or (2)remove item from order.");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    addItemtoOrder(order);
                    break;
                case 2:
                    deleteItemfromOrder(order);
                default:
                    break;
            }
        } else {
            System.out.println("Order has been delivered, no change can be make.");
            System.out.println("Create a new order: ");
            create();
        }

        storeData();
    }

    private void addItemtoOrder(Order order) {
        ArrayList<Item> listOfFood = new ArrayList<Item>();

        // print out catalog
        Menu.getInstance().printMenu();

        System.out.println("Please enter the itemID of the item you wish to order.");
        String itemID = sc.nextLine();
        Item itemToAdd = Menu.getInstance().checkExistance(itemID);

        System.out.println("Please enter the quantity of item for " + itemID);
        int quantityOfItem = sc.nextInt();
        sc.nextLine();
        for (int i = 0; i < quantityOfItem; i++)
            listOfFood.add(itemToAdd);

        order.setListOfFood(listOfFood);
    }

    private void deleteItemfromOrder(Order order) {
        System.out.println("Enter ItemID for item to be removed from order: ");
        String itemID = sc.nextLine();

        ArrayList<Item> listOfFood = order.getListOfFood();
        for (Item item : listOfFood) {
            if (item.getID().equals(itemID)) {
                listOfFood.remove(item);
            }
        }

    }

    // public void updateStatus(int orderID, String newOrderStatus) {
    // Order order = checkExistence(orderID);
    // order.setOrderStatus(newOrderStatus);
    // }

    public void read(Order order) { // read a single order
        order.viewOrder();
    }

    public ArrayList<Order> retrieveOrdersOfRoom(String roomID) { // by roomID
        ArrayList<Order> retrieveOL = new ArrayList<Order>();
        for (Order order : orderList) {
            if (order.getRoomID().equals(roomID))
                retrieveOL.add(order);
        }
        if (retrieveOL.size() > 0)
            return retrieveOL;
        else
            return null;
    }

    public float checkOutProcedures() { // include calculating order price

        float totalPrice = 0;

        System.out.println("Enter RoomID that is checking out: ");
        String roomID = sc.nextLine();

        ArrayList<Order> orders = retrieveOrdersOfRoom(roomID);

        for (Order order : orders) {
            for (Item food : order.getListOfFood()) {
                totalPrice += food.getPrice();
            }
        }
        System.out.println("Price of Room Service is " + totalPrice);
        return totalPrice;
    }

    public void storeData() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("Order.ser"));
            out.writeInt(orderList.size());
            for (Order order : orderList)
                out.writeObject(order);
            System.out.printf("%s \n\n--Entries Saved.--\n", orderList.toString().replace("[", "").replace("]", ""));
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

}
