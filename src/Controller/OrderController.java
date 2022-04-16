package Controller;

import entities.Order;
import entities.Item;

import java.text.SimpleDateFormat;

import Enums.OrderStatus;

import java.util.*;

import Database.SerializeDB;

public class OrderController extends SerializeDB implements IController {

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

        // storeData();
        storeData("Order.ser", orderList);
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
        super.storeData("Order.ser", orderList);
    }

    public void loadData() {
        super.loadData("Order.ser");
    }

}
