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
        	//Check expiration of reservation
            if (reservation.getReservationStatus()!=ReservationStatus.COMPLETED
            		&& reservation.getReservationStatus()!=ReservationStatus.EXPIRED
            		&& thisDate.compareTo(reservation.getCheckIn()) > 0) {
                System.out.println("Current date is past check in time");
                reservation.setReservationStatus(ReservationStatus.EXPIRED);
            }
            
            //Check waitlist for confirmation
            if (reservation.getReservationStatus()==ReservationStatus.WAITLIST) {
            	if (CheckInOut.getInstance().numAvailability(reservation.getCheckIn(), reservation.getRoomType()) > 0)
            		reservation.setReservationStatus(ReservationStatus.CONFIRM);
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
        //newReservation.setReservationStatus(ReservationStatus.CONFIRM);

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
        for (Reservation reservation : reservationList) {
        	//Check waitlist for confirmation
            if (reservation.getReservationStatus()==ReservationStatus.WAITLIST) {
            	if (CheckInOut.getInstance().numAvailability(reservation.getCheckIn(), reservation.getRoomType()) > 0)
            		reservation.setReservationStatus(ReservationStatus.CONFIRM);
            }
        }
        storeData();
    }

    public void update(Object entities, int choice, String value) {
        Reservation toBeUpdated = (Reservation) entities;
        Date date;
        switch (choice) {
        case 1: //guestID
        	toBeUpdated.setGuestID(value);
        	break;
        case 2: //roomID
        	toBeUpdated.setRoomID(value);
        	break;
        case 3: //checkIn Date
        	try {
                date = new SimpleDateFormat("dd/MM/yy").parse(value);
                System.out.println(value + "\t" + date);

                toBeUpdated.setCheckIn(date);

            } catch (Exception e) {
                e.printStackTrace();
            }
        	break;
        case 4: //checkOut Date
        	try {
                date = new SimpleDateFormat("dd/MM/yy").parse(value);
                System.out.println(value + "\t" + date);

                toBeUpdated.setCheckOut(date);

            } catch (Exception e) {
                e.printStackTrace();
            }
        	break;
        case 5: //childNo
        	try {
                int numOfChild = Integer.parseInt(value);
                toBeUpdated.setChildNo(numOfChild);
            } catch (Exception e) {
                e.printStackTrace();
            }
        	break;
        case 6: //adultNo
        	try {
                int numOfAdults = Integer.parseInt(value);
                toBeUpdated.setAdultNo(numOfAdults);
            } catch (Exception e) {
                e.printStackTrace();
            }
        	break;
        case 7: //reservationStatus
        	toBeUpdated.setReservationStatus(generateStatus(value));
        	break;
        case 8: //roomType
        	RoomTypes roomType = RoomTypes.SINGLE;
        	switch (value.charAt(0)) {
            case '1':
                roomType = RoomTypes.SINGLE;
                break;
            case '2':
                roomType = RoomTypes.DOUBLE;
                break;
            case '3':
                roomType = RoomTypes.DELUXE;
                break;
            case '4':
                roomType = RoomTypes.SUITE;
                break;
        	}
        	toBeUpdated.setRoomType(roomType);
        	break;
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
            System.out.println("==========================");
            System.out.println("   Reservation Details: ");
            System.out.println("==========================");
            System.out.println(reservationList.toString().replace("[","").replace("]",""));
            System.out.println("========================");
            System.out.printf("Entries Saved!");
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
            case 5:
                return ReservationStatus.WAITLIST;
            default:
            	return null;
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
