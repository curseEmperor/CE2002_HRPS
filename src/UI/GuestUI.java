package UI;

import Controller.GuestController;
import Enums.CreditcardType;
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
        System.out.println("Enter Creditcard Type (VISA/MASTERCARD): ");
        CreditcardType card = CreditcardType.valueOf(getUserString());

        Guest rawGuest = new Guest(guestID, guestName, address, contact, country,
                gender, nationality, card);
        GuestController.getInstance().create(rawGuest);

        System.out.println("Guest created and added to guest list");
    }

    public void readOneDets() {

        System.out.println("Enter your GuestID: ");
        String guestID = getUserString();

        Guest guestRead = GuestController.getInstance().checkExistence(guestID);
        if (guestRead != null) {
            System.out.println(guestRead);
        } else {
            System.out.println("Guest does not exist");
        }

    }

    public void update() {
        System.out.println("Enter ur GuestID: ");
        String guestID = getUserString();

        Guest toBeUpdated = GuestController.getInstance().checkExistence(guestID);
        if (toBeUpdated == null) {
            System.out.println("Guest does not exist");
            return;
        } else {
            System.out.println("What do you want to update?");
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
        System.out.println("Enter your GuestID: ");
        String guestID = getUserString();

        Guest toBeDeleted = GuestController.getInstance().checkExistence(guestID);
        if (toBeDeleted == null) {
            System.out.println("Guest does not exist.");
        } else {
            GuestController.getInstance().delete(toBeDeleted);
            System.out.println("Guest " + guestID + " is removed.");
        }
    }

}
