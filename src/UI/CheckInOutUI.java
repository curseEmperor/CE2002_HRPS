package UI;

import java.util.Scanner;

import Mediator.CheckInOut;

public class CheckInOutUI extends StandardUI {
    private static CheckInOutUI instance = null;

    private CheckInOutUI() {
        sc = new Scanner(System.in);
    }

    public static CheckInOutUI getInstance() {
        if (instance == null)
            instance = new CheckInOutUI();
        return instance;
    }

    public int showSelection() {
        System.out.println(" Check In/Out options avaiable: ");
        System.out.println("1) Check In");
        System.out.println("2) Check Out");
        System.out.println("3) Make Payment");
        System.out.println("4) Return to MainUI");

        return 4;
    }

    public void mainMenu() {
        do {
            qSize = showSelection();
            choice = getUserChoice(qSize);
            switch (choice) {
                case 1: // checkIn
                    System.out.println("Enter reservation ID: ");
                    String ID = getUserString();
                    CheckInOut.getInstance().checkIn(ID);
                    break;
                case 2: // checkOut
                    checkOut();
                    break;
                case 3: // payment
                    payment();
                    break;
                case 4:
                    return;
            }
        } while (choice < qSize);
    }

    private void checkOut() {
        String ID, select;
        System.out.println("Enter room ID: ");
        ID = getUserString();

        float roomDiscount = 0;
        float orderDiscount = 0;

        // Check for room discounts
        // System.out.println("Any Room Discounts? (Y/N)");
        // select = getUserYN();
        // if (select.compareTo("Y") == 0) {
        // System.out.println("Room Discount (%): ");
        // roomDiscount = (float) getUserChoice(100) / 100;
        // }

        // // Check for order discounts
        // System.out.println("Any Order Discounts? (Y/N)");
        // select = getUserYN();
        // if (select.compareTo("Y") == 0) {
        // System.out.println("Order Discount (%): ");
        // orderDiscount = (float) getUserChoice(100) / 100;
        // }

        // Run checkout
        CheckInOut.getInstance().checkOut(ID);
    }

    private void payment() {
        String ID;
        System.out.println("Enter reservation ID: ");
        ID = getUserString();
        System.out.println("Payment by?");
        System.out.println("1) Cash");
        System.out.println("2) Credit/Debit Card");
        choice = getUserChoice(2);
        if (choice == 2) {
            System.out.println("Please enter Credit/Debit Card number:");
            getUserString();
        }
        CheckInOut.getInstance().payment(ID);
        System.out.println("Payment Completed");
    }
}