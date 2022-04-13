package Controller;

import java.util.Date;
import java.util.Scanner;
import entities.*;
import java.util.Date;
import java.text.SimpleDateFormat;

public class CheckInOut {
	private static CheckInOut instance = null;
    Scanner sc;
    int CheckInOut;

    private CheckInOut() {
        sc = new Scanner(System.in);
    }

    public static CheckInOut getInstance() {
        if (instance == null) instance = new CheckInOut();
        return instance;
    }
    
    public void checkOut(String reservationIDs) {
    	//Change reservation status
    	//Change room status
    	//Get payment details
    }
    
    public void checkIn(String reservationID) {
    	try {
    	//Change reservation status
    	Reservation reservation = ReservationController.getInstance().checkExistence(reservationID);
    	if (reservation == null) {
    		System.out.println("Invalid Reservation ID");
    		return;
    	}
    	Date thisDate = new Date();
    	SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yy HH:mm:ss");
    	ReservationController.getInstance().update(reservation, 6, formatter.format(thisDate));
    	ReservationController.getInstance().update(reservation, 5, "Checked In");
    	//Assign room
    		//1. Get room type
    		//2. Check available rooms
    		//3. Assign roomID
    	//Change room status
    	Room room = RoomController.getInstance().checkExistence(reservation.getRoomID());
    	RoomController.getInstance().update(reservation, 5, reservation.getGuestID()); //TODO edit choice number
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    	}
    }
}
