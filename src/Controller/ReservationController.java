package Controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import entities.Reservation;

public class ReservationController {
    private static ReservationController instance = null;
    Scanner sc;

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
                reservation.setReservationStatus("Expired");
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
        newReservation.setReservationStatus("Reserved");

        reservationList.add(newReservation);
        System.out.println("Reservation ID generated: " + reservationID);

    }

    public void read() {
        for (Reservation reservation : reservationList) {
            System.out.println(reservation);
        }
    }

    public void delete(Object reservation) {
        reservationList.remove(reservation);
        System.out.println("reservation removed from list in reservation controller");
    }

    public void update(Object entities, int choice, String value) throws ParseException {
        Reservation toBeUpdated = (Reservation) entities;

        switch (choice) {
            case 1:
                toBeUpdated.setID(value);
                break;
            case 2:
                Date date1 = new SimpleDateFormat("dd/MM/yy").parse(value);
                System.out.println(value + "\t" + date1);

                toBeUpdated.setCheckOut(date1);
                break;
            case 3:
                int numOfChild = Integer.parseInt(value);
                toBeUpdated.setChildNo(numOfChild);
                break;
            case 4:
                int numOfAdults = Integer.parseInt(value);
                toBeUpdated.setAdultNo(numOfAdults);
                break;
            case 5:
                toBeUpdated.setReservationStatus(value);
                break;
            default:
                break;
        }

        System.out.println(toBeUpdated.toString());
        // store data

    }

    // public void checkOut(Reservation reservation) {
    // RoomController.getInstance().freeRoom();
    // PaymentController.getInstance().generateInvoice();
    // }

    public void saveData() {

    }

    public void loadData() {

    }

}
