package UI;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import Controller.ReservationController;
import entities.Reservation;

public class ReservationUI implements StandardUI {
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
        System.out.println("5) Return to MainUI");

        return 5;
    }

    public void mainMenu() {
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
                            create();
                            break;
                        case "Y":
                            System.out.println("Returning to Main Menu, please create Guest account first.");
                            GuestUI.getInstance().creatNewGuest();
                            return;
                        default:
                            System.out.println("Please enter only Y/N");
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

        // RoomID to be filled via checkin

        System.out.println("Enter Check-in day: ");
        String checkInString = getUserString();
        Date checkInDate = dateValid(checkInString);

        if (ReservationController.getInstance().checkExistence(guestID + checkInDate) != null) {
            System.out.println("Reservation already exist!");
            return;
        }

        System.out.println("Enter Check-out day:");
        String checkOutString = getUserString();
        Date checkOutDate = dateValid(checkOutString);

        System.out.println("enter number of children: ");
        int numOfChild = sc.nextInt();

        System.out.println("enter number of adults: ");
        int numOfAdults = sc.nextInt();

        Reservation rawReservation = new Reservation(guestID, checkInDate,
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

    public void update() {
        System.out.println("Enter your Reservation ID: ");
        String reservationID = getUserString();

        Reservation toBeUpdated = ReservationController.getInstance().checkExistence(reservationID);
        if (toBeUpdated == null) {
            System.out.println("Reservation does not exist");
        } else {
            // TODO: do while loop
            System.out.println("What do u want to update");
            System.out.println("1) check out day (dd/MM/yy): ");
            System.out.println("2) child numbers");
            System.out.println("3) adult numbers");
            System.out.println("4) Reservation status: 1)confirm 2)checkin 3)expired 4)waitlist");

            choice = getUserChoice(4);

            System.out.println("Enter the relevant details:");
            String content = getUserString();

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

    private Date dateValid(String dateInString) {
        SimpleDateFormat sdfrmt = new SimpleDateFormat("dd/MM/yy");
        sdfrmt.setLenient(false);

        try {
            Date javaDate = sdfrmt.parse(dateInString);
            return javaDate;
        } catch (ParseException e) {
            System.out.println(dateInString + " is Invalid Date format");
            return null;
        }
    }

}
