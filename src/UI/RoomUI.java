package UI;

import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import Controller.RoomController;
import Enums.RoomStatus;
import entities.Room;

public class RoomUI {
    private static RoomUI instance = null;
    Scanner sc;
    int choice, qSize;

    private RoomUI() {
        sc = new Scanner(System.in);
    }

    public static RoomUI getInstance() {
        if (instance == null) {
            instance = new RoomUI();
        }
        return instance;
    }

    public int showSelection() {
        System.out.println(" Room options avaiable: ");
        System.out.println("1) Create");
        System.out.println("2) Read");
        System.out.println("3) Update");
        System.out.println("4) Delete");
        System.out.println("5) Occupancy Report");
        System.out.println("6) Show room by status");
        System.out.println("7) INIT hotel......");
        System.out.println("8) Return to MainUI");

        return 8;
    }

    public void mainMenu() {
        do {
            qSize = showSelection();
            choice = getUserChoice(qSize);

            switch (choice) {
                case 1:
                    createNewRoom();
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
                    occupancyReport();
                    break;
                case 6:
                    showRoomByStatus();
                    break;
                case 7:
                    RoomController.getInstance().initHotel();
                    break;
                case 8:
                    break;
            }
        } while (choice < qSize);

    }

    public String createNewRoom() {

    }

    public String readOneDets() {

        System.out.println("Enter ur roomID: ");

    }

    public void update() {
        System.out.println("Enter ur roomID: ");
        String roomID = getUserString();

    }

    public void delete() {
        System.out.println("Enter ur roomID: ");
        String roomID = getUserString();

    }

    private void showRoomByStatus() {
        HashMap<RoomStatus, List<Room>> roomByStatus = new HashMap<>();

        roomByStatus = (HashMap) RoomController.getInstance().splitRoomByStatus();

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
