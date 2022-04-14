package UI;

import java.util.Scanner;

import Controller.GuestController;
import entities.Guest;

public class GuestUI extends StandardUI implements ControllerUI {
    private static GuestUI instance = null;

    private GuestUI() {
    	super();
    }

    public static GuestUI getInstance() {
        if (instance == null) {
            instance = new GuestUI();
        }
        return instance;
    }

    public int showSelection() {
        System.out.println(" Guest options avaiable: ");
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
        System.out.println("Enter ur GuestID: ");
        String guestID = getUserString();

        if (GuestController.getInstance().checkExistence(guestID) != null) {
            System.out.println("Guest exist!");
            // return guestID;
        }
        System.out.println("Creating new Guest..... ");

        System.out.println("Enter Guest Name: ");
        String guestName = getUserString();
        System.out.println("Enter address: ");
        String address = getUserString();
        System.out.println("Enter Contact: ");
        String contact = getUserString();
        System.out.println("Enter country: ");
        String country = getUserString();
        System.out.println("Enter Gender: ");
        char gender = getUserString().charAt(0);
        System.out.println("Enter nationality: ");
        String nationality = getUserString();

        // Guest rawGuest = new Guest(guestID, "guestName", "address", "contact",
        // "country", 'n', "nationality");

        Guest rawGuest = new Guest(guestID, guestName, address, contact, country,
                gender, nationality);
        GuestController.getInstance().create(rawGuest);

        System.out.println("Guest created and added to guest list");
        // return guestID;
    }

    public void readOneDets() {

        System.out.println("Enter ur GuestID: ");
        String guestID = getUserString();

        Guest guestRead = GuestController.getInstance().checkExistence(guestID);
        if (guestRead != null) {
            System.out.println(guestRead);
            // return guestID;
        } else {
            System.out.println("Guest does not exist");
            // return null;
        }

    }

    public void update() {
        System.out.println("Enter ur GuestID: ");
        String guestID = getUserString();

        Guest toBeUpdated = GuestController.getInstance().checkExistence(guestID);
        if (toBeUpdated == null) {
            System.out.println("Guest does not exist");
        } else {
            // TODO: do while loop
            System.out.println("What do u want to update");
            System.out.println("2) guestName");
            System.out.println("3) address");
            System.out.println("4) contact");
            System.out.println("5) contract");
            System.out.println("6) gender");
            System.out.println("7) nationality");
            System.out.println("8) guestName");

            choice = getUserChoice(8);

            System.out.println("Enter the relevant details:");
            String content = getUserString();

            GuestController.getInstance().update(toBeUpdated, choice, content);

            System.out.println(toBeUpdated);
        }
    }

    public void delete() {
        System.out.println("Enter ur GuestID: ");
        String guestID = getUserString();

        Guest toBeDeleted = GuestController.getInstance().checkExistence(guestID);
        if (toBeDeleted == null) {
            System.out.println("Guest does not exist");
        } else {
            System.out.println("do you confirm to delete Guest: " + guestID);
            // TODO: Confirmation check
            GuestController.getInstance().delete(toBeDeleted);

            System.out.println("guest is removed");
        }
    }

}
