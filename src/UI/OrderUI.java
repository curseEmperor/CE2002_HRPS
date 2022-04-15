package UI;

import java.util.Scanner;

import Controller.Menu;
import Controller.OrderController;
import Controller.RoomController;
import entities.Item;
import entities.Order;
import Enums.RoomStatus;

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
    	String roomID = checkRoom();
    	if (roomID == null) return;
        
        Order order = new Order(roomID);
        OrderController.getInstance().create(order);
        addItemtoOrder(order);
        System.out.println("Please enter remarks: ");
        String remarks = sc.nextLine();
        OrderController.getInstance().update(order, 2, remarks);
        System.out.println("Order " + order.getOrderID() + " has been confirmed. ");
    }

    public void readOneDets() {
        System.out.println("Enter the OrderID: ");
        String orderID = sc.nextLine();

        Order order = OrderController.getInstance().checkExistence(orderID);
        if (order == null) {
            System.out.println("Order not found.");
            return;
        }

        OrderController.getInstance().read(order);
    }

    public void update() {
        System.out.println("Enter the OrderID to be updated: ");
        String orderID = sc.nextLine();

        Order order = OrderController.getInstance().checkExistence(orderID);
        if (order == null) {
            System.out.println("Order not found.");
            return;
        }

        System.out.println(
        		"1) Room ID\n"
        		+ "2) Remarks\n"
        		+ "3) Order Status\n"
        		+ "4) Add Item(s)\n"
        		+ "5) Remove Item\n"
        		+ "What would you like to update?");
        int choice = getUserChoice(5);

        String value = null;
        if (choice <= 3) {
        	switch (choice) {
		        case 1: //roomID
		        	value = checkRoom();
		        	if (value == null) return;
		        	break;
		        case 2: //remarks
		        	System.out.println("Please enter remarks: ");
		        	value = getUserString();
		        	break;
		        case 3: //order status
		        	System.out.println("Change Order status to:");
		            System.out.println("1) Confirmed");
		            System.out.println("2) Preparing");
		            System.out.println("3) Delivered");
		            System.out.println("4) Paid");
		            value = String.valueOf(getUserChoice(4));
		        	break;
	        }
        	OrderController.getInstance().update(order, choice, value);
        }
        if (choice == 4) {
        	addItemtoOrder(order);
        }
        if (choice ==5) {
        	deleteItemfromOrder(order);
        }
        
    }
    
    public String checkRoom() {
    	//Check for valid room
        System.out.println("Please enter your Room ID:");
        String roomID = sc.nextLine();
        while (RoomController.getInstance().checkExistence(roomID) == null) {
            System.out.println("Please enter valid Room ID:");
            roomID = sc.nextLine();
        }
        
        //Check if room is occupied
        if (RoomController.getInstance().checkExistence(roomID).getRoomStatus() != RoomStatus.OCCUPIED) {
	        System.out.println("Room has no guest");
	        return null;
        }
    	return roomID;
    }

    public void delete() {
        System.out.println("Enter the OrderID to be deleted: ");
        String orderID = getUserString();

        Order order = OrderController.getInstance().checkExistence(orderID);
        if (order == null) {
            System.out.println("Order not found");
            return;
        }

        OrderController.getInstance().delete(order);
    }
    
    private void addItemtoOrder(Order order) {
        while (true) {
            Menu.getInstance().printMenu();
            System.out.println("Please enter the itemID of the item you wish to order:");
            String itemID = getUserString();
            Item itemToAdd = Menu.getInstance().checkExistance(itemID);
            while (itemToAdd==null) {
            	System.out.println("Please valid itemID:");
                itemID = getUserString();
                itemToAdd = Menu.getInstance().checkExistance(itemID);
            }

            System.out.format("Please enter the quantity of item for %s(Up to 50) ", itemID);
            int quantityOfItem = getUserChoice(50);
            for (int i = 0; i < quantityOfItem; i++)
                OrderController.getInstance().addItemtoOrder(order, itemToAdd);
            System.out.println("Item added to order " + order.getOrderID());

            System.out.println("Any additional items? (Y/N)");
            String select = getUserYN();
            if (select.compareTo("N") == 0) {
                break;
            }
        }
    }
    
    private void deleteItemfromOrder(Order order) {
        System.out.println("Enter ItemID for item to be removed from order: ");
        String itemID = getUserString();
        Item itemToDelete = Menu.getInstance().checkExistance(itemID);
        while (itemToDelete==null) {
        	System.out.println("Please valid itemID:");
            itemID = getUserString();
            itemToDelete = Menu.getInstance().checkExistance(itemID);
        }
        OrderController.getInstance().deleteItemfromOrder(order, itemToDelete);
    }

}
