package UI;

import java.util.Scanner;

import Controller.OrderController;
import Controller.RoomController;
import entities.Order;

public class OrderUI extends StandardUI {
    private static OrderUI instance = null;
    Scanner sc;
    int choice, qSize;

    private OrderUI() {
        super();
        sc = new Scanner(System.in);
    }

    public static OrderUI getInstance() {
        if (instance == null) {
            instance = new OrderUI();
        }
        return instance;
    }

    public int showSelection() {
        System.out.println(" Order/Room Service options avaiable: ");
        System.out.println("1) Create");
        System.out.println("2) Read");
        System.out.println("3) Update");
        System.out.println("4) Delete");
        System.out.println("5) Return to MainUI");

        return 5;
    }

    public void mainMenu() {
        do {
            qSize = showSelection();
            choice = getUserChoice(qSize);

            switch (choice) {
                case 1:
                    create();
                    break;
                case 2:
                    readOneDets();
                    break;
                case 3:
                    update();
                    break;
                case 4:
                    delete();
                    break;
                case 5:
                    break;
            }
        } while (choice < qSize);

    }

    public void create() {

        System.out.println("Please enter your Room ID:");
        String roomID = sc.nextLine();
        while (RoomController.getInstance().checkExistence(roomID) == null) {
            System.out.println("Please enter valid Room ID:");
            roomID = sc.nextLine();
        }

        Order order = new Order(roomID);
        OrderController.getInstance().create(order);
    }

    public void readOneDets() {
        System.out.println("Enter the OrderID: ");
        String orderID = sc.nextLine();

        Order order = OrderController.getInstance().checkExistence(orderID);
        if (order == null) {
            System.out.println("order not found.");
            return;
        }

        OrderController.getInstance().read(order);
    }

    public void update() {
        System.out.println("Enter the OrderID to be updated: ");
        String orderID = sc.nextLine();

        Order order = OrderController.getInstance().checkExistence(orderID);
        if (order == null) {
            System.out.println("order not found.");
            return;
        }

        System.out.println("Choose either to \n(1)add item\n(2)remove item \n(3)update status");
        int choice = sc.nextInt();
        sc.nextLine();

        String value = null;
        if (choice == 3) {
            System.out.println("Change Order status to:");
            System.out.println("1) Preparing");
            System.out.println("2) Delivered");
            System.out.println("3) Paid");
            value = sc.nextLine();
        }
        OrderController.getInstance().update(order, choice, value);
    }

    public void delete() {
        System.out.println("Enter the OrderID to be deleted: ");
        String orderID = sc.nextLine();

        Order order = OrderController.getInstance().checkExistence(orderID);
        if (order == null) {
            System.out.println("Order not found");
            return;
        }

        OrderController.getInstance().delete(order);
    }

}
