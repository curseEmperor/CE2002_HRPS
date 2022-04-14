package Controller;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Enums.ReservationStatus;
import Enums.RoomTypes;
import entities.Reservation;
import entities.Room;

public class ReservationController implements IController, IStorage {
    private static ReservationController instance = null;

    private ArrayList<Reservation> reservationList;

    private ReservationController() {
        reservationList = new ArrayList<>();
    }

    public static ReservationController getInstance() {
        if (instance == null) {
            instance = new ReservationController();
        }
        return instance;
    }

    public Reservation checkExistence(String reservationID) {
        Date thisDate = new Date();
        // SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yy HH:mm:ss");
        // System.out.println("Time now is " + formatter.format(thisDate));

        Reservation toBeReturned = null;

        for (Reservation reservation : reservationList) {
            if (thisDate.compareTo(reservation.getCheckIn()) > 0) {
                System.out.println("Current date is past check in time");
                reservation.setReservationStatus(ReservationStatus.EXPIRED);
            }

            if (reservation.getID().equals(reservationID)) {
                toBeReturned = reservation;
            }
        }

        return toBeReturned;
    }

    public void create(Object entities) {

        Reservation newReservation = (Reservation) entities;

        String checkInString = new SimpleDateFormat("ddMMyy").format(newReservation.getCheckIn());

        String reservationID = checkInString + newReservation.getGuestID();
        newReservation.setID(reservationID);
        newReservation.setReservationStatus(ReservationStatus.CONFIRM);

        reservationList.add(newReservation);
        // System.out.println("Reservation ID generated: " + reservationID);

        storeData();
    }

    public void read() {
        for (Reservation reservation : reservationList) {
            System.out.println(reservation.getID());
            System.out.println();
        }
    }

    public void delete(Object entities) {

        Reservation toBeDeleted = (Reservation) entities;
        reservationList.remove(toBeDeleted);
        // System.out.println("reservation removed from list in reservation
        // controller");
        storeData();
    }

    public void update(Object entities, int choice, String value) {
        Reservation toBeUpdated = (Reservation) entities;
        Date date1;
        switch (choice) {
            case 1: // checkout date
                try {
                    date1 = new SimpleDateFormat("dd/MM/yy").parse(value);
                    System.out.println(value + "\t" + date1);

                    toBeUpdated.setCheckOut(date1);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 2: // num of children
                try {
                    int numOfChild = Integer.parseInt(value);
                    toBeUpdated.setChildNo(numOfChild);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 3: // num of adults
                try {
                    int numOfAdults = Integer.parseInt(value);
                    toBeUpdated.setAdultNo(numOfAdults);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            case 4: // reservation status
                toBeUpdated.setReservationStatus(generateStatus(value));
                break;
            // case 6:
            // try {
            // date1 = new SimpleDateFormat("dd/MM/yy").parse(value);
            // System.out.println(value + "\t" + date1);

            // toBeUpdated.setCheckIn(date1);
            // } catch (ParseException e) {
            // e.printStackTrace();
            // }
            // break;
            default:
                break;
        }

        System.out.println(toBeUpdated.toString());

        storeData();
    }

    public void storeData() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("Reservation.ser"));
            out.writeInt(reservationList.size());
            for (Reservation res : reservationList)
                out.writeObject(res);
            System.out.printf("%s \n\n--Entries Saved.--\n", reservationList.toString().replace("[","").replace("]",""));
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadData() {
        // create an ObjectInputStream for the file we created before
        ObjectInputStream ois;
        try {
            ois = new ObjectInputStream(new FileInputStream("Reservation.ser"));

            int noOfOrdRecords = ois.readInt();
            System.out.println("GuestController: " + noOfOrdRecords + " Entries Loaded");
            for (int i = 0; i < noOfOrdRecords; i++) {
                reservationList.add((Reservation) ois.readObject());
            }

            for (Reservation res : reservationList) {
                System.out.println(res.getID());
            }

        } catch (IOException | ClassNotFoundException e1) {
            e1.printStackTrace();
        }
    }

    private ReservationStatus generateStatus(String value) {
        int choice = Integer.parseInt(value);
        switch (choice) {
            case 1:
                return ReservationStatus.CONFIRM;
            case 2:
                return ReservationStatus.CHECKIN;
            case 3:
                return ReservationStatus.EXPIRED;
            case 4:
                return ReservationStatus.COMPLETED;
            default:
                return ReservationStatus.WAITLIST;
        }
    }
    
    public Map<ReservationStatus, List<Reservation>> splitReservationByStatus() {
    	Map<ReservationStatus, List<Reservation>> reservationByStatus = new HashMap<>();

        ArrayList<Reservation> confirmStatus = new ArrayList<Reservation>();
        ArrayList<Reservation> checkinStatus = new ArrayList<Reservation>();
        ArrayList<Reservation> expiredStatus = new ArrayList<Reservation>();
        ArrayList<Reservation> completedStatus = new ArrayList<Reservation>();
        ArrayList<Reservation> waitlistStatus = new ArrayList<Reservation>();

        for (Reservation reservation : reservationList) {
            if (reservation.getReservationStatus() == ReservationStatus.CONFIRM) { // single
            	confirmStatus.add(reservation);
            }
            if (reservation.getReservationStatus() == ReservationStatus.CHECKIN) { // single
            	checkinStatus.add(reservation);
            }
            if (reservation.getReservationStatus() == ReservationStatus.EXPIRED) { // single
            	expiredStatus.add(reservation);
            }
            if (reservation.getReservationStatus() == ReservationStatus.COMPLETED) { // single
            	completedStatus.add(reservation);
            }
            if (reservation.getReservationStatus() == ReservationStatus.WAITLIST) { // single
            	waitlistStatus.add(reservation);
            }
        }

        reservationByStatus.put(ReservationStatus.CONFIRM, confirmStatus);
        reservationByStatus.put(ReservationStatus.CHECKIN, checkinStatus);
        reservationByStatus.put(ReservationStatus.EXPIRED, expiredStatus);
        reservationByStatus.put(ReservationStatus.COMPLETED, completedStatus);
        reservationByStatus.put(ReservationStatus.WAITLIST, waitlistStatus);

        return reservationByStatus;
    }
}
