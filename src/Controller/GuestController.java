package Controller;

import java.util.ArrayList;
import java.util.Date;

import Database.SerializeDB;
import entities.Guest;
import entities.Creditcard;

public class GuestController extends SerializeDB implements IController, IStorage {
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

    public void updateCreditcard(Object entities, String cardNumber, Date expiryDate, int CVC, int type,
            String cardName) {
        Guest toBeUpdated = (Guest) entities;
        toBeUpdated.setCard(new Creditcard(cardNumber, expiryDate, CVC, type, cardName));
    }

    public void storeData() {
        super.storeData("Guest.ser", guestList);

    }

    public void loadData() {
        super.loadData("Guest.ser");
    }

}
