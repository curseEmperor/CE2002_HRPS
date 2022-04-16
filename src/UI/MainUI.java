package UI;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;

import Controller.GuestController;
import Controller.IStorage;
import Controller.Menu;
import Controller.OrderController;
import Controller.ReservationController;
import Controller.RoomController;

public class MainUI extends StandardUI {
    int choice, qSize;
    Scanner sc;
    ArrayList<IStorage> DB = new ArrayList<>();

    public MainUI() {
        DB.add(ReservationController.getInstance());
        DB.add(RoomController.getInstance());
        DB.add(OrderController.getInstance());
        DB.add(Menu.getInstance());
        DB.add(GuestController.getInstance());

        sc = new Scanner(System.in);
    }

    public void run() throws ParseException {
        setUp();

        mainMenu();

        System.out.println("Exiting hotel mainUI! Have a nice day!");
        windDown();
        System.exit(0);
    }

    private void setUp() {
        for (IStorage con : DB) {
            con.loadData();
        }
    }

    private void windDown() {
        for (IStorage con : DB) {
            System.out.println(con.getClass().getName());
            con.storeData();
        }
    }

    public int showSelection() {

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

        return 7;
    }

    public void mainMenu() {
        do {
            qSize = showSelection();
            choice = getUserChoice(qSize);

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
                    OrderUI.getInstance().mainMenu();
                    break;
                case 6:
                    CheckInOutUI.getInstance().mainMenu();
                    break;
                default:
                    break;
            }

        } while (choice < qSize);
    }
}
