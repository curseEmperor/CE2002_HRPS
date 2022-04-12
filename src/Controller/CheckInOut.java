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
    
    public void checkOut() {
    	//Change reservation status
    	//Change room status
    	//Get payment details
    }
    
    public void checkIn(String reservationID) {
    	//Change reservation status
    	Reservation Res = ReservationController.getInstance().checkExistence(reservationID);
    	if (Res == null) {
    		System.out.println("Invalid Reservation ID");
    		return;
    	}
    	Date thisDate = new Date();
    	SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yy HH:mm:ss");
    	ReservationController.getInstance().update(Res, 6, formatter.format(thisDate));
    	//Assign room
    	//Change room status
    }
}
