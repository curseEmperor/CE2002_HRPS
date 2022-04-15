package Controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;

import entities.Guest;
import entities.Creditcard;

public class GuestController implements IController {
    private static GuestController instance = null;

    private ArrayList<Guest> guestList;

    private GuestController() {
        guestList = new ArrayList<>();
    }

    public static GuestController getInstance() {
        if (instance == null) {
            instance = new GuestController();
        }
        return instance;
    }

    public Guest checkExistence(String guestID) {
        System.out.println("Checking whether guestID exists...");
        for (Guest guest : guestList) {
            if (guest.getID().equals(guestID)) {
                System.out.println(guestID);
                return guest;
            }
        }
        return null;
    }

    public void create(Object entities) {

        Guest guest = (Guest) entities;
        guestList.add(guest);
        storeData();
    }

    public void read() {
        for (Guest guest : guestList) {
            System.out.println(guest.getID());
        }
    }

    public void delete(Object entities) {

        Guest toBeDeleted = (Guest) entities;
        guestList.remove(toBeDeleted);

        storeData();
    }

    public void update(Object entities, int choice, String value) {

        Guest toBeUpdated = (Guest) entities;
        switch (choice) {
            case 1: // guestID:
                toBeUpdated.setGuestID(value);
                break;
            case 2: // guestName:
                toBeUpdated.setGuestName(value);
                break;
            case 3: // address:
                toBeUpdated.setAddress(value);
                break;
            case 4: // contact:
                toBeUpdated.setContact(value);
                break;
            case 5: // country:
                toBeUpdated.setCountry(value);
                break;
            case 6: // gender:
                toBeUpdated.setGender(value.charAt(0));
                break;
            case 7: // nationality:
                toBeUpdated.setNationality(value);
                break;
            default:
                break;
        }

        System.out.println(toBeUpdated.toString());
        storeData();
    }
    
    public void updateCreditcard(Object entities, String cardNumber, Date expiryDate, int CVC, int type, String cardName) {
    	Guest toBeUpdated = (Guest) entities;
    	toBeUpdated.setCard(new Creditcard(cardNumber, expiryDate, CVC, type, cardName));
    }

    public void storeData() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("Guest.ser"));
            out.writeInt(guestList.size());
            for (Guest guest : guestList)
                out.writeObject(guest);
            System.out.println("Entries Saved!");
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadData() {
        // create an ObjectInputStream for the file we created before
        ObjectInputStream ois;
        try {
            ois = new ObjectInputStream(new FileInputStream("Guest.ser"));

            int noOfOrdRecords = ois.readInt();
            System.out.println("GuestController: " + noOfOrdRecords + " Entries Loaded");
            for (int i = 0; i < noOfOrdRecords; i++) {
                guestList.add((Guest) ois.readObject());
            }
            // System.out.printf("GuestController: %s Entries loaded.\n", guestList);

            for (Guest guest : guestList) {
                System.out.println(guest);
            }

        } catch (IOException | ClassNotFoundException e1) {
            e1.printStackTrace();
        }
    }
}
