package UI;

import java.util.Scanner;

import Controller.OrderController;
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
        OrderController.getInstance().create();
    }

    public void readOneDets() {
        System.out.println("Enter the OrderID: ");
        int orderID = sc.nextInt();
        sc.nextLine();

        Order order = OrderController.getInstance().checkExistence(orderID);
        if (order == null) {
            System.out.println("order not found.");
            return;
        }

        OrderController.getInstance().read(order);
    }

    public void update() {
        System.out.println("Enter the OrderID to be updated: ");
        int orderID = sc.nextInt();

        Order order = OrderController.getInstance().checkExistence(orderID);
        if (order == null) {
            System.out.println("order not found.");
            return;
        }

        OrderController.getInstance().update(order);
    }

    public void delete() {
        System.out.println("Enter the OrderID to be deleted: ");
        int orderID = sc.nextInt();

        Order order = OrderController.getInstance().checkExistence(orderID);
        if (order == null) {
            System.out.println("Order not found");
            return;
        }

        OrderController.getInstance().delete(order);
    }

}
