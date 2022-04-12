package Controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Enums.BedTypes;
import Enums.RoomStatus;
import Enums.RoomTypes;
import Enums.RoomView;
import entities.Room;

public class RoomController implements IStorage, IController {
    private static RoomController instance = null;

    private ArrayList<Room> roomList;

    private RoomController() {
        roomList = new ArrayList<>();
    }

    public static RoomController getInstance() {
        if (instance == null) {
            instance = new RoomController();
        }
        return instance;
    }

    public void initHotel() {
        Room r1 = new Room("06-01", 100.21, RoomTypes.DELUXE, BedTypes.SINGLE, true, RoomView.NIL, true);
        Room r2 = new Room("02-01", 200.21, RoomTypes.DELUXE, BedTypes.DOUBLE, true, RoomView.CITY, true);
        Room r3 = new Room("03-01", 400.21, RoomTypes.SUITE, BedTypes.QUEEN, true, RoomView.POOL, true);
        Room r4 = new Room("04-01", 500.21, RoomTypes.DELUXE, BedTypes.KING, true, RoomView.CITY, true);
        Room r5 = new Room("05-01", 600.21, RoomTypes.DELUXE, BedTypes.DOUBLE, true, RoomView.NIL, true);

        roomList.add(r1);
        roomList.add(r2);
        roomList.add(r3);
        roomList.add(r4);
        roomList.add(r5);

        storeData();
    }

    public Room checkExistence(String roomID) {
        for (Room room : roomList) {
            if (room.getRoomID().equals(roomID)) {
                return room;
            }
        }
        return null;
    }

    public void create(Object entities) {
        Room newRoom = (Room) entities;
        roomList.add(newRoom);

        storeData();
    }

    public void read() {
    }

    public void delete(Object entities) {
        Room toBeDeleted = (Room) entities;
        roomList.remove(toBeDeleted);
    }

    public void update(Object entities, int choice, String value) {
        Room tobeUpdated = (Room) entities;

        switch (choice) {
            case 1:
                try {
                    double price = Double.parseDouble(value);
                    tobeUpdated.setRoomPrice(price);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                break;
            case 3:
                if (value.equals("T")) {
                    tobeUpdated.setWiFi(true);
                } else {
                    tobeUpdated.setWiFi(false);
                }
                break;
            case 4:
                tobeUpdated.setRoomStatus(generateStatus(value));
                break;
        }
    }

    public Map<RoomTypes, List<Room>> generateOccupancyReport() {
        Map<RoomTypes, List<Room>> report = new HashMap<>();
        ArrayList<Room> vacantRooms = new ArrayList<>();

        ArrayList<Room> singleType = new ArrayList<Room>();
        ArrayList<Room> doubleType = new ArrayList<Room>();
        ArrayList<Room> deluxeType = new ArrayList<Room>();
        ArrayList<Room> suiteType = new ArrayList<Room>();

        HashMap<RoomStatus, List<Room>> roomByStatus = (HashMap<RoomStatus, List<Room>>) splitRoomByStatus();
        vacantRooms = (ArrayList<Room>) roomByStatus.get(RoomStatus.VACANT);

        for (Room room : vacantRooms) {
            if (room.getRoomType() == RoomTypes.SINGLE) { // single
                singleType.add(room);
            }
            if (room.getRoomType() == RoomTypes.DOUBLE) { // double
                doubleType.add(room);
            }
            if (room.getRoomType() == RoomTypes.DELUXE) { // deluxe
                deluxeType.add(room);
            }
            if (room.getRoomType() == RoomTypes.SUITE) { // suite
                suiteType.add(room);
            }
        }

        report.put(RoomTypes.SINGLE, singleType);
        report.put(RoomTypes.DOUBLE, doubleType);
        report.put(RoomTypes.DELUXE, deluxeType);
        report.put(RoomTypes.SUITE, suiteType);

        return report;
    }

    public Map<RoomStatus, List<Room>> splitRoomByStatus() {
        Map<RoomStatus, List<Room>> roomByStatus = new HashMap<>();

        ArrayList<Room> vacantRooms = new ArrayList<Room>();
        ArrayList<Room> occupiedRooms = new ArrayList<Room>();
        ArrayList<Room> reservedRooms = new ArrayList<Room>();
        ArrayList<Room> maintainRooms = new ArrayList<Room>();

        for (Room room : roomList) {
            if (room.getRoomStatus() == RoomStatus.VACANT) { // vacant
                vacantRooms.add(room);
            }
            if (room.getRoomStatus() == RoomStatus.OCCUPIED) { // vacant
                occupiedRooms.add(room);
            }
            if (room.getRoomStatus() == RoomStatus.RESERVED) { // vacant
                reservedRooms.add(room);
            }
            if (room.getRoomStatus() == RoomStatus.MAINTAINENCE) { // vacant
                maintainRooms.add(room);
            }
        }

        roomByStatus.put(RoomStatus.VACANT, vacantRooms);
        roomByStatus.put(RoomStatus.OCCUPIED, occupiedRooms);
        roomByStatus.put(RoomStatus.RESERVED, reservedRooms);
        roomByStatus.put(RoomStatus.MAINTAINENCE, maintainRooms);

        return roomByStatus;

    }

    public void storeData() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("Room.ser"));
            out.writeInt(roomList.size());
            for (Room room : roomList)
                out.writeObject(room);
            System.out.printf("RoomController: %s Entries Saved.\n", roomList);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadData() {
        ObjectInputStream ois;
        try {
            ois = new ObjectInputStream(new FileInputStream("Room.ser"));

            int noOfOrdRecords = ois.readInt();
            System.out.println("RoomController: " + noOfOrdRecords + " Entries Loaded");
            for (int i = 0; i < noOfOrdRecords; i++) {
                roomList.add((Room) ois.readObject());
            }

        } catch (IOException | ClassNotFoundException e1) {
            e1.printStackTrace();
        }
    }

    private RoomStatus generateStatus(String value) {
        int choice = Integer.parseInt(value);
        switch (choice) {
            case 1:
                return RoomStatus.VACANT;
            case 2:
                return RoomStatus.OCCUPIED;
            case 3:
                return RoomStatus.MAINTAINENCE;
            case 4:
                return RoomStatus.RESERVED;
            default:
                return RoomStatus.MAINTAINENCE;

        }
    }
}
