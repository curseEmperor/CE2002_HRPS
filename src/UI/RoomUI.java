package UI;

import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import Controller.RoomController;
import Enums.BedTypes;
import Enums.RoomStatus;
import Enums.RoomTypes;
import Enums.RoomView;
import entities.Room;

public class RoomUI extends StandardUI implements ControllerUI {
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
        System.out.println("1) Add Room");
        System.out.println("2) View Room");
        System.out.println("3) Update Room Detail");
        System.out.println("4) Remove Room");
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
                    create();
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

    public void create() {

        System.out.println("Enter ur roomID: ");
        String roomID = getUserString();

        System.out.println("Enter Room Price: ");
        double roomPrice = sc.nextDouble();

        while (roomPrice < 0.0) {
            System.out.println("Enter Positive number: ");
            roomPrice = sc.nextDouble();
        }

        System.out.println("Choose Room Type: ");
        System.out.println("1) Single ");
        System.out.println("2) Double ");
        System.out.println("3) Deluxe ");
        System.out.println("4) Suite ");
        RoomTypes roomType = null;

        choice = getUserChoice(4);
        roomType = RoomController.getInstance().generateRoomType(String.valueOf(choice));

        System.out.println("Enter Bed Type: ");
        System.out.println("1) Single Bed");
        System.out.println("2) Double Bed");
        System.out.println("3) Queen Bed");
        System.out.println("4) King Bed");
        BedTypes bedType = null;

        choice = getUserChoice(4);
        bedType = RoomController.getInstance().generateBedType(String.valueOf(choice));

        System.out.println("Please select if this room is WiFi enabled: ");
        System.out.println("1) WiFi Enabled");
        System.out.println("2) Not WiFi Enabled");

        choice = getUserChoice(2);
        boolean WiFi = choice == 1 ? true : false;

        System.out.println("Please select the room's view: ");
        System.out.println("1) City View");
        System.out.println("2) Pool View");
        System.out.println("3) No View");
        RoomView view = null;

        choice = getUserChoice(3);
        view = RoomController.getInstance().generateView(String.valueOf(choice));

        System.out.println("Please select if smoking is allowed in this room: ");
        System.out.println("(1) Smoking Allowed");
        System.out.println("(2) Smoking Not Allowed");

        choice = getUserChoice(2);
        boolean smoke = choice == 1 ? true : false;

        Room rawRoom = new Room(roomID, roomPrice, roomType, bedType, WiFi, view, smoke);
        RoomController.getInstance().create(rawRoom);
    }

    public void readOneDets() {

        System.out.println("Enter ur roomID: ");
        String roomID = getUserString();

        Room roomRead = RoomController.getInstance().checkExistence(roomID);
        if (roomRead != null)
            System.out.println(roomRead);
        else
            System.out.println("Room does not exist!");

    }

    public void update() {
        System.out.println("Enter ur roomID: ");
        String roomID = getUserString();

        Room toBeUpdated = RoomController.getInstance().checkExistence(roomID);
        if (toBeUpdated == null) {
            System.out.println("Room does not exist");
        } else {
            // TODO: while loop
            System.out.println("What do u want to update: ");
            System.out.println("1) Price");
            System.out.println("2) Bed Type");
            System.out.println("3) WiFi enabled");
            System.out.println("4) Room Status");

            choice = getUserChoice(4);

            System.out.println("Enter the relevant details: ");
            String content = getUserString();

            RoomController.getInstance().update(toBeUpdated, choice, content);

            System.out.println(toBeUpdated);
        }
    }

    public void delete() {
        System.out.println("Enter ur roomID: ");
        String roomID = getUserString();

        Room toBeDeleted = RoomController.getInstance().checkExistence(roomID);
        if (toBeDeleted == null) {
            System.out.println("Room does not exist");
        } else {
            RoomController.getInstance().delete(toBeDeleted);
        }
    }

    public void viewOccupancyReport() {
        occupancyReport();
    }

    private void occupancyReport() {
        HashMap<RoomTypes, List<Room>> report;
        report = (HashMap<RoomTypes, List<Room>>) RoomController.getInstance().generateOccupancyReport();

        for (RoomTypes key : report.keySet()) {
            System.out.println(key + " :  Number : " + report.get(key).size());
            System.out.println("\tRooms : ");
            for (Room room : report.get(key)) {
                System.out.println(room.getRoomID());
            }
        }
    }

    private void showRoomByStatus() {
        HashMap<RoomStatus, List<Room>> roomByStatus;
        roomByStatus = (HashMap<RoomStatus, List<Room>>) RoomController.getInstance().splitRoomByStatus();

        for (RoomStatus key : roomByStatus.keySet()) {
            System.out.println(key + ": ");
            for (Room room : roomByStatus.get(key)) {
                System.out.println(room.getRoomID());
            }
        }
    }

}
