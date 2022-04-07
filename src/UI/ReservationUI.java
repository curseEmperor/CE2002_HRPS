package UI;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import Controller.ReservationController;
import entities.Reservation;

public class ReservationUI {
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
        System.out.println("1) Create");
        System.out.println("2) Read");
        System.out.println("3) Update");
        System.out.println("4) Delete");
        System.out.println("5) Check In");
        System.out.println("6) Check Out");
        System.out.println("7) Return to MainUI");

        return 7;
    }

    public void mainMenu() throws ParseException {
        do {
            qSize = showSelection();
            choice = getUserChoice(qSize);

            switch (choice) {
                case 1:
                    System.out.println("Are you a new Guest?");
                    System.out.println("Y/N");
                    String select = getUserString();
                    switch (select) {
                        case "N":
                            createNewReservation();
                            break;
                        case "Y":
                            System.out.println("Returning to Main Menu!");
                            return;
                    }
                    break;
                case 2:
                    readOneDets();
                    break;
                case 3:
                    update();
                    break;
                case 4:
                    delete();
                    break;
                case 5:
                    checkIn();
                    break;
                case 6:
                    checkOut();
                    break;
                case 7:
                    break;
            }

        } while (choice < qSize);
    }

    public void createNewReservation() throws ParseException {

        System.out.println("Enter Guest ID: ");
        String guestID = getUserString();

        System.out.println("Enter Room ID: ");
        String roomID = getUserString();

        System.out.println("Enter Check-in day: ");
        String checkInString = getUserString();
        Date checkInDate = new SimpleDateFormat("dd/MM/yyyy").parse(checkInString);

        if (ReservationController.getInstance().checkExistence(checkInString + roomID) != null) {
            System.out.println("Reservation already exist!");
            return;
        }

        System.out.println("Enter Check- out day:");
        String checkOutString = getUserString();
        Date checkOutDate = new SimpleDateFormat("dd/MM/yyyy").parse(checkOutString);

        System.out.println("enter number of children: ");
        int numOfChild = sc.nextInt();

        System.out.println("enter number of adults: ");
        int numOfAdults = sc.nextInt();
        // Reservation rawReservation = new Reservation("guestID", "01-01",
        // new SimpleDateFormat("dd/MM/yyyy").parse("15/10/1999"),
        // new SimpleDateFormat("dd/MM/yyyy").parse("17/10/1999"), 3,
        // 2);

        Reservation rawReservation = new Reservation(guestID, roomID, checkInDate,
                checkOutDate, numOfChild,
                numOfAdults);

        ReservationController.getInstance().create(rawReservation);

    }

    public void readOneDets() {

        System.out.println("Enter ur Reservation ID: ");
        String reservationID = getUserString();

        Reservation reserveRead = ReservationController.getInstance().checkExistence(reservationID);
        if (reserveRead != null)
            System.out.println(reserveRead);
        else
            System.out.println("Reservation does not exist");

    }

    public void update() throws ParseException {
        System.out.println("Enter your Reservation ID: ");
        String reservationID = getUserString();

        Reservation toBeUpdated = ReservationController.getInstance().checkExistence(reservationID);
        if (toBeUpdated == null) {
            System.out.println("Guest does not exist");
        } else {
            // TODO: do while loop
            System.out.println("What do u want to update");
            System.out.println("2) check out day (dd/MM/yy): ");
            System.out.println("3) child numbers");
            System.out.println("4) adult number");
            System.out.println("5) Reservation status");

            int choice = getUserChoice(5);

            System.out.println("Enter the relevant details:");
            String content = sc.nextLine();

            ReservationController.getInstance().update(toBeUpdated, choice, content);

            System.out.println(toBeUpdated);
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
        }
    }

    public void checkIn() throws ParseException {
        System.out.println("Enter your Reservation ID: ");
        String reservationID = getUserString();

        Reservation toBeUpdated = ReservationController.getInstance().checkExistence(reservationID);
        if (toBeUpdated == null) {
            System.out.println("Guest does not exist");
            return;
        }
        ReservationController.getInstance().update(toBeUpdated, 5, "Checked IN");
    }

    public void checkOut() throws ParseException {
        System.out.println("Enter your Reservation ID: ");
        String reservationID = getUserString();

        Reservation toBeCheckOut = ReservationController.getInstance().checkExistence(reservationID);
        if (toBeCheckOut == null) {
            System.out.println("reservation does not exist");
            return;
        }
        ReservationController.getInstance().update(toBeCheckOut, 5, "Checked Out");

        // ReservationController.getInstance().checkOut(toBeCheckOut);
        // GuestController.getInstance().checkOut();
        // RoomController.getInstance().checkOut();
    }

    private int getUserChoice(int n) {

        do {
            if (sc.hasNextInt()) {
                choice = sc.nextInt();
                sc.nextLine();
                if (choice <= 0 || choice > n)
                    System.out.println("Please input values between 1 to " + n + " only!");
                else
                    break;
            } else {
                System.out.println("Please input only integers!");
                sc.next();
            }
        } while (choice <= 0 || choice > n);

        return choice;
    }

    private String getUserString() {
        String input = sc.nextLine().toUpperCase();
        return input;
    }

}
