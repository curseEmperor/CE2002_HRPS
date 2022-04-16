package UI;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;
import java.util.Scanner;

import Controller.GuestController;
import Controller.ReservationController;
import entities.Reservation;
import Enums.ReservationStatus;
import Enums.RoomTypes;
import Mediator.CheckInOut;

public class ReservationUI extends StandardUI implements ControllerUI {
    private static ReservationUI instance = null;
    Scanner sc;
    int choice, qSize;

    private ReservationUI() {
        sc = new Scanner(System.in);
    }

    public static ReservationUI getInstance() {
        if (instance == null) {
            instance = new ReservationUI();
        }
        return instance;
    }

    public int showSelection() {
        System.out.println("Reservation options avaiable: ");
        System.out.println("1) Add Reservations");
        System.out.println("2) View Reservations");
        System.out.println("3) Update Reservation Info");
        System.out.println("4) Cancel Reservation");
        System.out.println("5) Return to MainUI");

        return 5;
    }

    public void mainMenu() {
        do {
            qSize = showSelection();
            choice = getUserChoice(qSize);

            switch (choice) {
                case 1:
                    System.out.println("Are you a new Guest? (Y/N)");
                    String select = getUserYN();
                    switch (select) {
                    case "N":
                        create();
                        break;
                    case "Y":
                        System.out.println("Please create Guest account first.");
                        GuestUI.getInstance().create();
                        create();
                        break;
                    }
                    break;
                case 2:
                    readOneDets();
                    ReservationController.getInstance().read();
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

        System.out.println("Enter Guest ID: ");
        String guestID = getUserString();
        if (GuestController.getInstance().checkExistence(guestID)==null) {
        	System.out.println("Invalid Guest ID");
        	return;
        }

        // RoomID to be filled via checkin
        System.out.println("Enter Check-in day (dd/MM/yy): ");
        String checkInString = getUserString() + " 09:00 AM";
        Date checkInDate = dateValid(checkInString);
        Date today = new Date();
        while (checkInDate.before(removeTime(today))) {
        	System.out.println("Check-in day must not be before today");
        	System.out.println("Enter Check-in day (dd/MM/yy): ");
        	checkInString = getUserString() + " 09:00 AM";
            checkInDate = dateValid(checkInString);
        }

        if (ReservationController.getInstance().checkExistence(guestID + checkInDate) != null) {
            System.out.println("Reservation already exist!");
            return;
        }

        System.out.println("Enter Check-out day (dd/MM/yy):");
        String checkOutString = getUserString() + " 12:00 PM";
        Date checkOutDate = dateValid(checkOutString);
        while (checkOutDate.before(checkInDate)||checkOutDate.equals(checkInDate)) {
        	System.out.println("Check-out day must be after Check-in day");
        	System.out.println("Enter Check-out day (dd/MM/yy): ");
        	checkOutString = getUserString() + " 12:00 PM";
            checkOutDate = dateValid(checkOutString);
        }

        System.out.println("Enter number of children: ");
        int numOfChild = sc.nextInt();

        System.out.println("Enter number of adults: ");
        int numOfAdults = sc.nextInt();

        Reservation rawReservation = new Reservation(guestID, checkInDate,
                checkOutDate, numOfChild,
                numOfAdults);
        
        int checkAvailability;
        RoomTypes roomType = RoomTypes.SINGLE; //Pre-set as single to avoid errors
        while(true) {
	        System.out.println(
	        		"1) Single\n"
	        		+ "2) Double\n"
                    + "3) Standard\n"
	        		+ "4) Deluxe\n"
	        		+ "5) Suite\n"
	        		+ "6) Cancel create\n"
	        		+ "Select Room Type: ");
	        choice = getUserChoice(9);
	        switch (choice) {
	            case 1:
	                roomType = RoomTypes.SINGLE;
	                break;
	            case 2:
	                roomType = RoomTypes.DOUBLE;
	                break;
	            case 3:
	                roomType = RoomTypes.STANDARD;
	                break;
	            case 4:
	                roomType = RoomTypes.DELUXE;
	                break;
                case 5:
	                roomType = RoomTypes.SUITE;
	                break;
	            case 6:
	            	return;
	            default:
	            	break;
	        }
	        checkAvailability = CheckInOut.getInstance().numAvailability(rawReservation.getCheckIn(), roomType);
	        if (checkAvailability <= 0) {
	        	System.out.println("Room type not available!");
	        	System.out.println("Put on waitlist? (Y/N)");
	        	String select = getUserYN();
                if (select.compareTo("Y")==0) {
                	rawReservation.setReservationStatus(ReservationStatus.WAITLIST);
                	break;
                }
	        }
	        else {
	        	rawReservation.setReservationStatus(ReservationStatus.CONFIRM);
	        	break;
	        }
        }
        
        rawReservation.setRoomType(roomType);
        
        ReservationController.getInstance().create(rawReservation);

        System.out.println("=================================");
        System.out.println("      Reservation Details ");
        System.out.printf("=================================");
        System.out.println(rawReservation.toString().replace("[", "").replace("]", ""));
        System.out.println("=================================");
        System.out.println("Reservation " + rawReservation.getID() + " has been created");

    }


    public void readOneDets() {

        System.out.println("Enter ur Reservation ID: ");
        String reservationID = getUserString();

        Reservation reserveRead = ReservationController.getInstance().checkExistence(reservationID);
        if (reserveRead != null)
        {
            System.out.println("=================================");
            System.out.println("      Reservation Details ");
            System.out.printf("=================================");
            System.out.println(reserveRead.toString().replace("[", "").replace("]", ""));
            System.out.println("=================================");
        }
        else
            System.out.println("Reservation does not exist");

    }

    public void update() {
        System.out.println("Enter your Reservation ID: ");
        String reservationID = getUserString();

        Reservation toBeUpdated = ReservationController.getInstance().checkExistence(reservationID);
        if (toBeUpdated == null) {
            System.out.println("Reservation does not exist");
        } else {
            // TODO: do while loop
            System.out.println("What do u want to update?");
            System.out.println("1) Guest ID");
            System.out.println("2) Room ID");
            System.out.println("3) Check In Date (dd/MM/yy)");
            System.out.println("4) Check Out Date (dd/MM/yy)");
            System.out.println("5) Number of Child(s)");
            System.out.println("6) Number of Adult(s)");
            System.out.println("7) Reservation Status");
            System.out.println("8) Room Type");

            choice = getUserChoice(8);
            int selection;
            String content;
            if (choice == 7) {
            	System.out.println(
            			"1) Confirmed\n"
            			+ "2) Checked In\n"
            			+ "3) Expired\n"
            			+ "4) Completed\n"
            			+ "5) Waitlist\n"
            			+ "Select Status: ");
            	selection = getUserChoice(5);
            	content = String.valueOf(selection);
            }
            else if (choice == 8) {
                int checkAvailability;
                RoomTypes roomType = RoomTypes.SINGLE; //Pre-set as single to avoid errors
                while(true) {
        	        System.out.println(
        	        		"1) Single\n"
        	        		+ "2) Double\n"
        	        		+ "3) Deluxe\n"
        	        		+ "4) Suite\n"
        	        		+ "5) Cancel reservation\n"
        	        		+ "Select Room Type: ");
        	        selection = getUserChoice(5);
        	        switch (choice) {
        	            case 1:
        	                roomType = RoomTypes.SINGLE;
        	                break;
        	            case 2:
        	                roomType = RoomTypes.DOUBLE;
        	                break;
        	            case 3:
        	                roomType = RoomTypes.DELUXE;
        	                break;
        	            case 4:
        	                roomType = RoomTypes.SUITE;
        	                break;
        	            case 5:
        	            	return;
        	            default:
        	            	break;
        	        }
        	        checkAvailability = CheckInOut.getInstance().numAvailability(toBeUpdated.getCheckIn(), roomType);
        	        if (checkAvailability <= 0) {
        	        	System.out.println("Room type not available!");
        	        	System.out.println("Put on waitlist? (Y/N)");
        	        	String select = getUserYN();
                        if (select.compareTo("Y")==0) {
                        	ReservationController.getInstance().update(toBeUpdated, 7, "5");
                        	break;
                        }
        	        }
        	        else {
        	        	ReservationController.getInstance().update(toBeUpdated, 7,"1");
        	        	break;
        	        }
                }
                content = String.valueOf(selection);
            }
            else {
            	System.out.println("Enter the relevant details: ");
            	content = getUserString();
            }

            if (choice == 3)
            {
                content = content + " 09:00 AM";
            }
            if (choice == 4)
            {
                content = content + " 12:00 PM";
            }
            ReservationController.getInstance().update(toBeUpdated, choice, content);

            //System.out.println(toBeUpdated);
        }
    }

    public void delete() {
        System.out.println("Enter your Reservation ID: ");
        String reservationID = getUserString();

        Reservation toBeDeleted = ReservationController.getInstance().checkExistence(reservationID);
        if (toBeDeleted == null) {
            System.out.println("Reservation does not exist");
        } else {
            ReservationController.getInstance().delete(toBeDeleted);
	    System.out.println("Reservation Cancelled.");
        }
    }

    private Date dateValid(String dateInString) {
        SimpleDateFormat sdfrmt = new SimpleDateFormat("dd/MM/yy hh:mm a");
        int i = dateInString.length();
        String time = dateInString.substring(i-9,i);
        sdfrmt.setLenient(false);
        Date javaDate = null;
        while (javaDate == null) {
        	try {
	            javaDate = sdfrmt.parse(dateInString);
	        } catch (ParseException e) {
	            System.out.println(dateInString + " is Invalid Date format\nEnter date: ");
	            System.out.println("Enter date (dd/MM/yy): ");
	            dateInString = getUserString() + time;
	        }
        }
        
        return javaDate;
    }


    
    private Date removeTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

}
