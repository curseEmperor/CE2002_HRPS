package UI;

import java.util.Scanner;

import Controller.CheckInOut;

public class CheckInOutUI extends StandardUI{
	private static CheckInOutUI instance = null;

    private CheckInOutUI() {
        sc = new Scanner(System.in);
    }

    public static CheckInOutUI getInstance() {
        if (instance == null) instance = new CheckInOutUI();
        return instance;
    }
    
    public int showSelection() {
    	System.out.println(" Check In/Out options avaiable: ");
        System.out.println("1) Check In");
        System.out.println("2) Check Out");
        System.out.println("3) Return to MainUI");

        return 3;
    }
    
    public void mainMenu() {
    	do {
            qSize = showSelection();
            choice = getUserChoice(qSize);
            String ID;
            switch (choice) {
                case 1: //checkIn
                    System.out.println("Enter reservation ID: ");
                    ID = getUserString();
                    CheckInOut.getInstance().checkIn(ID);
                    break;
                case 2: //checkOut
                	System.out.println("Enter reservation ID: ");
                    ID = getUserString();
                    System.out.println("Room Discount (%): ");
                    float roomDiscount = (float)getUserChoice(100) / 100;
                    System.out.println("Order Discount (%): ");
                    float orderDiscount = (float)getUserChoice(100) / 100;
                    CheckInOut.getInstance().checkOut(ID, roomDiscount, orderDiscount);
                    break;
                case 3:
                	return;
            }
        } while (choice < qSize);
    }
}