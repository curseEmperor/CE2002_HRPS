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
        System.out.println("1) Add Guest");
        System.out.println("2) View Guest");
        System.out.println("3) Update Guest's details");
        System.out.println("4) Delete Guest");
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
        System.out.println("Enter GuestID (NRIC/Passport): ");
        String guestID = getUserString();

        if (GuestController.getInstance().checkExistence(guestID) != null) {
            System.out.println("Guest exist!");
            return;
            // return guestID;
        }
        System.out.println("Creating new Guest......");

        System.out.println("Enter Guest Name: ");
        String guestName = getUserString();
        System.out.println("Enter Address: ");
        String address = getUserString();
        System.out.println("Enter Contact: ");
        String contact = getUserString();
        System.out.println("Enter Country: ");
        String country = getUserString();
        System.out.println("Enter Gender: ");
        char gender = getUserString().charAt(0);
        System.out.println("Enter Nationality: ");
        String nationality = getUserString();

        // Guest rawGuest = new Guest(guestID, "guestName", "address", "contact",
        // "country", 'n', "nationality");

        Guest rawGuest = new Guest(guestID, guestName, address, contact, country,
                gender, nationality);
        GuestController.getInstance().create(rawGuest);
        System.out.println("========================");
        System.out.println("     Guest Details ");
        System.out.println("========================");
        System.out.println(rawGuest.toString().replace("[", "").replace("]", ""));
        System.out.println("========================");
        System.out.println("Guest created and added to guest list.");
        // return guestID;
    }

    public void readOneDets() {

        System.out.println("Enter your GuestID: ");
        String guestID = getUserString();

        Guest guestRead = GuestController.getInstance().checkExistence(guestID);
        if (guestRead != null) {
            System.out.println("========================");
            System.out.println("   Guest Details ");
            System.out.println("========================");
            System.out.println(guestRead);
            System.out.println("========================");
            // return guestID;
        } else {
            System.out.println("Guest does not exist.");
            // return null;
        }

    }

    public void update() {
        System.out.println("Enter ur GuestID: ");
        String guestID = getUserString();

        Guest toBeUpdated = GuestController.getInstance().checkExistence(guestID);
        if (toBeUpdated == null) {
            System.out.println("Guest does not exist.");
            return;
        } else {
            // TODO: do while loop
            System.out.println("What do you want to update?");
            System.out.println("1) GuestID");
            System.out.println("2) Guest Name");
            System.out.println("3) Address");
            System.out.println("4) Contact");
            System.out.println("5) Country");
            System.out.println("6) Gender");
            System.out.println("7) Nationality");
            //System.out.println("8) guestName");

            choice = getUserChoice(7);

            System.out.println("Enter the relevant details:");
            String content = getUserString();

            GuestController.getInstance().update(toBeUpdated, choice, content);

            System.out.println(toBeUpdated);
        }
    }

    public void delete() {
        System.out.println("Enter your GuestID: ");
        String guestID = getUserString();

        Guest toBeDeleted = GuestController.getInstance().checkExistence(guestID);
        if (toBeDeleted == null) {
            System.out.println("Guest does not exist.");
        } else {
            System.out.println("Deleting Guest " + guestID);
            // TODO: Confirmation check
            GuestController.getInstance().delete(toBeDeleted);
            System.out.println("Guest is removed.");
        }
    }

}
