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
        System.out.println("7) Return to MainUI");

        return 7;
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
                    break;
            }
        } while (choice < qSize);

    }

    public void create() {

        System.out.println("Enter Room ID: ");
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
        System.out.println("3) Standard ");
        System.out.println("4) Deluxe ");
        System.out.println("5) Suite ");
        RoomTypes roomType = null;

        choice = getUserChoice(5);
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

        System.out.println("Enter roomID: ");
        String roomID = getUserString();

        Room roomRead = RoomController.getInstance().checkExistence(roomID);
        if (roomRead != null)
        {
            System.out.println("\n==============");
            System.out.println(" Room Details");
            System.out.println("==============");
            System.out.println(roomRead);
        }
        else
            System.out.println("Room does not exist!");

    }

    public void update() {
        System.out.println("Enter roomID: ");
        String roomID = getUserString();

        Room toBeUpdated = RoomController.getInstance().checkExistence(roomID);
        if (toBeUpdated == null) {
            System.out.println("Room does not exist");
        } else {
            // TODO: while loop
            System.out.println("What do you want to update: ");
            System.out.println("1) Room ID"); //string
            System.out.println("2) Guest ID"); //string
            System.out.println("3) Room Price"); //float
            System.out.println("4) Room Type"); //enum
            System.out.println("5) Bed Type"); //enum
            System.out.println("6) WiFi Enabled (Y/N)"); // YN
            System.out.println("7) Room View"); //enum
            System.out.println("8) Smoking Room (Y/N)"); //enum
            System.out.println("9) Room Status"); //enum
            System.out.println("10) Cancel Update");

            choice = getUserChoice(10);
            if (choice==10) return;
            String content = null;
            int count;
            
            switch (choice) {
            case 4: //roomType
            	count = 0;
            	for (RoomTypes roomType : RoomTypes.values()) {
            		count++;
            		System.out.println(count + ") " + roomType.name());
            	}
            	System.out.println("Choose room type: ");
            	content = String.valueOf(getUserChoice(RoomTypes.values().length));
            	break;
            case 5: //bedType
            	count = 0;
            	for (BedTypes bedType : BedTypes.values()) {
            		count++;
            		System.out.println(count + ") " + bedType.name());
            	}
            	System.out.println("Choose bed type: ");
            	content = String.valueOf(getUserChoice(BedTypes.values().length));
            	break;
            case 6:
            	System.out.println("Is the room WiFi Enabled? (Y/N)?");
            	content = getUserYN();
            	break;
            case 7: //roomView
            	count = 0;
            	for (RoomView view : RoomView.values()) {
            		count++;
            		System.out.println(count + ") " + view.name());
            	}
            	System.out.println("Choose view: ");
            	content = String.valueOf(getUserChoice(RoomView.values().length));
            	break;
            case 8: //smoking
            	System.out.println("Is this a smoking room? (Y/N)?");
            	content = getUserYN();
            	break;
            case 9: //roomStatus
            	count = 0;
            	for (RoomStatus roomStatus : RoomStatus.values()) {
            		count++;
            		System.out.println(count + ") " + roomStatus.name());
            	}
            	System.out.println("Choose status: ");
            	content = String.valueOf(getUserChoice(RoomStatus.values().length)+1);
            	break;
            default:
            	System.out.println("Enter the relevant details: ");
            	content = getUserString();
            }
            	
            RoomController.getInstance().update(toBeUpdated, choice, content);

            System.out.println(toBeUpdated);
        }
    }

    public void delete() {
        System.out.println("Enter roomID: ");
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
            System.out.format("\n\033[1m===========%s==========\033[0m\n", key);
            System.out.println("Number: " + report.get(key).size());
            System.out.println("Rooms: ");
            for (Room room : report.get(key)) {
                System.out.println("\t   " + room.getRoomID());
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
