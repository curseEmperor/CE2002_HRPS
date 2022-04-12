package Controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

import entities.Creditcard;
import entities.Entities;
import entities.Guest;

public class GuestController implements Serializable {
    private static GuestController instance = null;
    Scanner sc;

    private ArrayList<Guest> guestList;

    private GuestController() {
        guestList = new ArrayList<>();
    }

    // single instance of GuestController
    public static GuestController getInstance() {
        if (instance == null) {
            instance = new GuestController();
        }
        return instance;
    }

    public Guest checkExistence(String guestID) {
        System.out.println("GuestController.checkExistence()");
        for (Guest guest : guestList) {
            if (guest.getID().equals(guestID)) {
                System.out.println(guestID);
                return guest;
            }
        }
        return null;
    }

    public void create(Object entities) {

        System.out.println("guest list current size: " + guestList.size());
        Guest guest = (Guest) entities; // downcast here
        guestList.add(guest);
        System.out.println("Guest added, size of guest list is " + guestList.size()); // can return guest to UI to print

        // If entity is not of concrete type Guest throw error
        // if (entities instanceof Guest) {
        // } else {
        // throw new ClassCastException("Object was not of type GUEST");
        // }

        // TODO: save data
    }

    public void read() {
        for (Guest guest : guestList) {
            System.out.println(guest);
        }
    }

    public void delete(Object entities) { // removeGuest

        if (entities instanceof Guest) {
            Guest toBeDeleted = (Guest) entities; // downcast here
            guestList.remove(toBeDeleted);
            System.out.println("Guest removed alr"); // can return guest to UI to print
        } else {
            throw new ClassCastException("Object was not of type GUEST");
        }

        // TODO: store data
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
            case 8: // card:
                // toBeUpdated.setCard(value);
                break;
            default:
                break;
        }

        System.out.println(toBeUpdated.toString());
        // store data
    }

    public void storeData() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("Guest.ser"));
            out.writeInt(guestList.size());
            for (Guest guest : guestList)
                out.writeObject(guest);
            System.out.printf("GuestController: %s Entries Saved.\n", guestList);
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
            // System.out.printf("GuestController: %s Entires loaded.\n", guestList);

            for (Guest guest : guestList) {
                System.out.println(guest);
            }

        } catch (IOException | ClassNotFoundException e1) {
            e1.printStackTrace();
        }
    }

}
