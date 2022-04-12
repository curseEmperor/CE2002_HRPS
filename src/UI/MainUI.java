package UI;

import java.text.ParseException;
import java.util.Scanner;

import Controller.MainController;

public class MainUI {
    MainController mainControl;
    int choice;

    public void run() throws ParseException {
        mainControl = new MainController();
        Scanner sc = new Scanner(System.in);

        do {
            System.out.println("Welcome: now in MainUI");
            System.out.println("1) For Guest options");
            System.out.println("2) For Reservation options");
            System.out.println("3) For Room options");
            System.out.println("4) For Menu options");
            System.out.println("5) For Room Service options");
            System.out.println("6) Exit App");

            do {
                if (sc.hasNextInt()) {
                    choice = sc.nextInt();
                    if (choice <= 0 || choice > 6) {
                        System.out.println("Please input values between 1 to 6 only!");
                    } else {
                        break;
                    }
                } else {
                    System.out.println("Please input only integers!");
                    sc.next();
                }
            } while (choice <= 0 || choice > 6);

            switch (choice) {
                case 1:
                    GuestUI.getInstance().mainMenu();
                    break;
                case 2:
                    ReservationUI.getInstance().mainMenu();
                    break;
                case 3:
                    RoomUI.getInstance().mainMenu();
                case 4:
                    // MenuUI.getInstance().mainMenu();
                case 6:
                    break;
            }

        } while (choice < 6);

        sc.close();
        System.out.println("Exiting hotel mainUI! Have a nice day!");
        System.exit(0);
    }
}
