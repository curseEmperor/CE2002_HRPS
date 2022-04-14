package UI;

import java.text.ParseException;
import java.util.Scanner;

public class MainUI {
    int choice;

    public void run() throws ParseException {
        Scanner sc = new Scanner(System.in);

        do {
            System.out.println("======================");
            System.out.println("Welcome to Main Menu!");
            System.out.println("======================");
            System.out.println("1) For Guest options");
            System.out.println("2) For Reservation options");
            System.out.println("3) For Room options");
            System.out.println("4) For Menu options");
            System.out.println("5) For Room Service options");
            System.out.println("6) Check In / Check Out");
            System.out.println("7) Exit App");

            do {
                if (sc.hasNextInt()) {
                    choice = sc.nextInt();
                    if (choice <= 0 || choice > 7) {
                        System.out.println("Please input values between 1 to 7 only!");
                    } else {
                        break;
                    }
                } else {
                    System.out.println("Please input only integers!");
                    sc.next();
                }
            } while (choice <= 0 || choice > 7);

            switch (choice) {
                case 1:
                    GuestUI.getInstance().mainMenu();
                    break;
                case 2:
                    ReservationUI.getInstance().mainMenu();
                    break;
                case 3:
                    RoomUI.getInstance().mainMenu();
                    break;
                case 4:
                    MenuUI.getInstance().mainMenu();
                    break;
                case 5:
                    //RoomServiceUI.getInstance().mainMenu();
                    break;
                case 6:
                    //CheckInOutUI.getInstance().mainMenu();
                    break;
                default:
                    break;
            }

        } while (choice < 7);

        sc.close();
        System.out.println("Exiting hotel mainUI! Have a nice day!");
        System.exit(0);
    }
}
