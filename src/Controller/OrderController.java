package Controller;

import entities.Order;
import entities.Item;

import java.text.SimpleDateFormat;

import Enums.OrderStatus;
import Enums.RoomStatus;

import java.util.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class OrderController implements IStorage {

    ArrayList<Order> orderList;
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
    public Order checkExistence(String orderID) { // by orderID eg. 02021
        for (Order order : orderList) {
            if (order.getOrderID().compareTo(orderID) == 0)
                return order;
        }
        return null;
    }

    public void create(Object entities) {
        Order order = (Order) entities;
        /*
         * if (RoomController.getInstance().checkExistence(roomID).getRoomStatus()!=
         * RoomStatus.OCCUPIED) {
         * System.out.println("Room has no guest");
         * return;
         * }
         */

        addItemtoOrder(order);

        System.out.println("Please enter remarks: ");
        String remarks = sc.nextLine();
        order.setRemarks(remarks);

        Calendar c = Calendar.getInstance();
        Date time = c.getTime();
        String date = formatter.format(time);
        order.setDate(date);
        SimpleDateFormat formatID = new SimpleDateFormat("ddMMyyhhmm");
        order.setOrderID(order.getRoomID() + formatID.format(time));

        order.setOrderStatus(OrderStatus.CONFIRM);

        orderList.add(order);

        System.out.println("Order " + order.getOrderID() + " has been confirmed. ");
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

        if (choice == 3) { // update status
            OrderStatus status = generateStatus(value);
            order.setOrderStatus(status);
        } else if (order.getOrderStatus() == OrderStatus.CONFIRM) {
            order.viewOrder();

            switch (choice) {
                case 1:
                    addItemtoOrder(order);
                    break;
                case 2:
                    deleteItemfromOrder(order);
                    if (order.getListOfFood().size() == 0) {
                        System.out.println("No items left... Deleting order...");
                        orderList.remove(order);
                    }
                    break;
                default:
                    break;
            }
        } else {
            System.out.println("Order is preparing or is delivered, no changes can be made.");

        }

        storeData();
    }

    private void addItemtoOrder(Order order) {
        ArrayList<Item> listOfFood = new ArrayList<Item>();

        // print out catalog
        while (true) {
            Menu.getInstance().printMenu();
            System.out.println("Please enter the itemID of the item you wish to order:");
            String itemID = sc.nextLine();
            Item itemToAdd = Menu.getInstance().checkExistance(itemID);

            System.out.println("Please enter the quantity of item for " + itemID);
            int quantityOfItem = sc.nextInt();
            sc.nextLine();
            for (int i = 0; i < quantityOfItem; i++)
                listOfFood.add(itemToAdd);

            System.out.println("Any additional items? (Y/N)");
            String select = sc.nextLine();
            while (!(select.compareToIgnoreCase("Y") == 0 || select.compareToIgnoreCase("N") == 0)) {
                System.out.println("Please enter only Y/N");
                System.out.println("Y/N? ");
                select = sc.nextLine();
            }
            if (select.compareTo("N") == 0) {
                break;
            }
        }

        if (order.getListOfFood() != null)
            for (Item itemToAdd : order.getListOfFood())
                listOfFood.add(itemToAdd);

        order.setListOfFood(listOfFood);
    }

    private void deleteItemfromOrder(Order order) {
        System.out.println("Enter ItemID for item to be removed from order: ");
        String itemID = sc.nextLine();

        ArrayList<Item> listOfFood = order.getListOfFood();
        for (Item item : listOfFood) {
            if (item.getID().compareTo(itemID) == 0) {
                listOfFood.remove(item);
                break;
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
            if (order.getRoomID().equals(roomID) && order.getOrderStatus() != OrderStatus.PAID)
                retrieveOL.add(order);
        }
        if (retrieveOL.size() > 0)
            return retrieveOL;
        else
            return null;
    }

    public void printRoomReceipt(String roomID) {
        ArrayList<Order> orders = retrieveOrdersOfRoom(roomID);
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

    private OrderStatus generateStatus(String value) {
        int choice = Integer.parseInt(value);
        switch (choice) {
            case 1:
                return OrderStatus.PREPARING;
            case 2:
                return OrderStatus.DELIVERED;
            case 3:
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

}
