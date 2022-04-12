package Controller;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import Enums.ReservationStatus;
import entities.Reservation;

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
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yy HH:mm:ss");

        System.out.println("Time now is" + formatter.format(thisDate));

        Reservation toBeReturned = null;
        // ArrayList<Reservation> toClear = new ArrayList<Reservation>();

        for (Reservation reservation : reservationList) {
            if (thisDate.compareTo(reservation.getCheckIn()) > 0) {
                System.out.println("Current date is past check in time");
                reservation.setReservationStatus(ReservationStatus.EXPIRED);
            }
            // long time_diff = thisDate.getTime() - reservation.getCheckIn().getTime();
            // System.out.println("time_diff = " + time_diff);

            // if (time_diff >= 10800 * 1000) // 3 hours
            // // toClear.add(reservation);
            // reservation.setReservationStatus("Expired");

            if (reservation.getID().equals(reservationID)) {
                System.out.println(reservation);
                toBeReturned = reservation;
            }
        }

        // for (Reservation res : toClear) {
        // reservationList.remove(res);
        // }
        // toClear.clear();

        return toBeReturned;
    }

    public void create(Object entities) {

        Reservation newReservation = (Reservation) entities;

        String checkInString = new SimpleDateFormat("ddMMyy").format(newReservation.getCheckIn());

        String reservationID = checkInString + newReservation.getRoomID();
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
            case 1:
                try {
                    date1 = new SimpleDateFormat("dd/MM/yy").parse(value);
                    System.out.println(value + "\t" + date1);

                    toBeUpdated.setCheckOut(date1);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                try {
                    int numOfChild = Integer.parseInt(value);
                    toBeUpdated.setChildNo(numOfChild);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 3:
                try {
                    int numOfAdults = Integer.parseInt(value);
                    toBeUpdated.setAdultNo(numOfAdults);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            case 4:
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
            System.out.printf("GuestController: %s Entries Saved.\n", reservationList);
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
}
