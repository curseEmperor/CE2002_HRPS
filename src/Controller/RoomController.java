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
        Room r1 = new Room("02-01", 100.21, RoomTypes.SINGLE, BedTypes.SINGLE, false, RoomView.NIL, false);
        Room r2 = new Room("02-02", 125.21, RoomTypes.SINGLE, BedTypes.SINGLE, false, RoomView.NIL, true);
        Room r3 = new Room("02-03", 125.21, RoomTypes.SINGLE, BedTypes.SINGLE, true, RoomView.NIL, false);
        Room r4 = new Room("02-04", 150.21, RoomTypes.SINGLE, BedTypes.SINGLE, true, RoomView.NIL, true);
        Room r5 = new Room("02-05", 200.21, RoomTypes.DOUBLE, BedTypes.DOUBLE, false, RoomView.NIL, false);
        Room r6 = new Room("02-06", 225.21, RoomTypes.DOUBLE, BedTypes.DOUBLE, true, RoomView.NIL, false);
        Room r7 = new Room("02-07", 225.21, RoomTypes.DOUBLE, BedTypes.QUEEN, false, RoomView.NIL, false);
        Room r8 = new Room("02-08", 250.21, RoomTypes.DOUBLE, BedTypes.QUEEN, true, RoomView.NIL, false);
        Room r9 = new Room("03-01", 300.21, RoomTypes.DOUBLE, BedTypes.KING, true, RoomView.NIL, false);
        Room r10 = new Room("03-02", 350.21, RoomTypes.DOUBLE, BedTypes.KING, true, RoomView.NIL, true);
        Room r11 = new Room("03-03", 400.21, RoomTypes.DELUXE, BedTypes.DOUBLE, true, RoomView.NIL, false);
        Room r12 = new Room("03-04", 400.21, RoomTypes.DELUXE, BedTypes.DOUBLE, false, RoomView.NIL, true);
        Room r13 = new Room("03-05", 450.21, RoomTypes.DELUXE, BedTypes.QUEEN, true, RoomView.NIL, false);
        Room r14 = new Room("03-06", 450.21, RoomTypes.DELUXE, BedTypes.QUEEN, false, RoomView.NIL, true);
        Room r15 = new Room("03-07", 500.21, RoomTypes.DELUXE, BedTypes.KING, true, RoomView.NIL, true);
        Room r16 = new Room("03-08", 500.21, RoomTypes.DELUXE, BedTypes.KING, true, RoomView.NIL, true);
        Room r17 = new Room("04-01", 150.21, RoomTypes.SINGLE, BedTypes.SINGLE, false, RoomView.POOL, false);
        Room r18 = new Room("04-02", 175.21, RoomTypes.SINGLE, BedTypes.SINGLE, false, RoomView.POOL, true);
        Room r19 = new Room("04-03", 175.21, RoomTypes.SINGLE, BedTypes.SINGLE, true, RoomView.POOL, false);
        Room r20 = new Room("04-04", 200.21, RoomTypes.SINGLE, BedTypes.SINGLE, true, RoomView.POOL, true);
        Room r21 = new Room("04-05", 350.21, RoomTypes.DOUBLE, BedTypes.DOUBLE, true, RoomView.POOL, true);
        Room r22 = new Room("04-06", 350.21, RoomTypes.DOUBLE, BedTypes.QUEEN, true, RoomView.POOL, true);
        Room r23 = new Room("04-07", 400.21, RoomTypes.DOUBLE, BedTypes.KING, true, RoomView.POOL, true);
        Room r24 = new Room("04-08", 450.21, RoomTypes.DELUXE, BedTypes.DOUBLE, true, RoomView.POOL, true);
        Room r25 = new Room("05-01", 500.21, RoomTypes.DELUXE, BedTypes.QUEEN, true, RoomView.POOL, true);
        Room r26 = new Room("05-02", 500.21, RoomTypes.DELUXE, BedTypes.KING, true, RoomView.POOL, false);
        Room r27 = new Room("05-03", 550.21, RoomTypes.DELUXE, BedTypes.KING, true, RoomView.POOL, true);
        Room r28 = new Room("05-04", 550.21, RoomTypes.SUITE, BedTypes.DOUBLE, true, RoomView.POOL, false);
        Room r29 = new Room("05-05", 600.21, RoomTypes.SUITE, BedTypes.DOUBLE, true, RoomView.POOL, true);
        Room r30 = new Room("05-06", 600.21, RoomTypes.SUITE, BedTypes.QUEEN, true, RoomView.POOL, true);
        Room r31 = new Room("05-07", 550.21, RoomTypes.SUITE, BedTypes.KING, true, RoomView.POOL, false);
        Room r32 = new Room("05-08", 600.21, RoomTypes.SUITE, BedTypes.KING, true, RoomView.POOL, true);
        Room r33 = new Room("06-01", 150.21, RoomTypes.SINGLE, BedTypes.SINGLE, false, RoomView.CITY, false);
        Room r34 = new Room("06-02", 175.21, RoomTypes.SINGLE, BedTypes.SINGLE, false, RoomView.CITY, true);
        Room r35 = new Room("06-03", 175.21, RoomTypes.SINGLE, BedTypes.SINGLE, true, RoomView.CITY, false);
        Room r36 = new Room("06-04", 200.21, RoomTypes.SINGLE, BedTypes.SINGLE, true, RoomView.CITY, true);
        Room r37 = new Room("06-05", 325.21, RoomTypes.DOUBLE, BedTypes.DOUBLE, true, RoomView.CITY, false);
        Room r38 = new Room("06-06", 350.21, RoomTypes.DOUBLE, BedTypes.DOUBLE, true, RoomView.CITY, true);
        Room r39 = new Room("06-07", 350.21, RoomTypes.DOUBLE, BedTypes.QUEEN, true, RoomView.CITY, true);
        Room r40 = new Room("06-08", 400.21, RoomTypes.DOUBLE, BedTypes.KING, true, RoomView.CITY, true);
        Room r41 = new Room("07-01", 450.21, RoomTypes.DELUXE, BedTypes.DOUBLE, true, RoomView.CITY, true);
        Room r42 = new Room("07-02", 500.21, RoomTypes.DELUXE, BedTypes.QUEEN, true, RoomView.CITY, true);
        Room r43 = new Room("07-03", 500.21, RoomTypes.DELUXE, BedTypes.KING, false, RoomView.CITY, true);
        Room r44 = new Room("07-04", 550.21, RoomTypes.DELUXE, BedTypes.KING, true, RoomView.CITY, true);
        Room r45 = new Room("07-05", 600.21, RoomTypes.SUITE, BedTypes.DOUBLE, true, RoomView.CITY, true);
        Room r46 = new Room("07-06", 550.21, RoomTypes.SUITE, BedTypes.QUEEN, true, RoomView.CITY, false);
        Room r47 = new Room("07-07", 600.21, RoomTypes.SUITE, BedTypes.QUEEN, true, RoomView.CITY, true);
        Room r48 = new Room("07-08", 600.21, RoomTypes.SUITE, BedTypes.KING, true, RoomView.CITY, true);

        roomList.add(r1);
        roomList.add(r2);
        roomList.add(r3);
        roomList.add(r4);
        roomList.add(r5);
        roomList.add(r6);
        roomList.add(r7);
        roomList.add(r8);
        roomList.add(r9);
        roomList.add(r10);
        roomList.add(r11);
        roomList.add(r12);
        roomList.add(r13);
        roomList.add(r14);
        roomList.add(r15);
        roomList.add(r16);
        roomList.add(r17);
        roomList.add(r18);
        roomList.add(r19);
        roomList.add(r20);
        roomList.add(r21);
        roomList.add(r22);
        roomList.add(r23);
        roomList.add(r24);
        roomList.add(r25);
        roomList.add(r26);
        roomList.add(r27);
        roomList.add(r28);
        roomList.add(r29);
        roomList.add(r30);
        roomList.add(r31);
        roomList.add(r32);
        roomList.add(r33);
        roomList.add(r34);
        roomList.add(r35);
        roomList.add(r36);
        roomList.add(r37);
        roomList.add(r38);
        roomList.add(r39);
        roomList.add(r40);
        roomList.add(r41);
        roomList.add(r42);
        roomList.add(r43);
        roomList.add(r44);
        roomList.add(r45);
        roomList.add(r46);
        roomList.add(r47);
        roomList.add(r48);
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
    
    public Map<RoomTypes, List<Room>> splitRoomByType() {
    	Map<RoomTypes, List<Room>> roomByType = new HashMap<>();

        ArrayList<Room> singleType = new ArrayList<Room>();
        ArrayList<Room> doubleType = new ArrayList<Room>();
        ArrayList<Room> deluxeType = new ArrayList<Room>();
        ArrayList<Room> suiteType = new ArrayList<Room>();

        for (Room room : roomList) {
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

        roomByType.put(RoomTypes.SINGLE, singleType);
        roomByType.put(RoomTypes.DOUBLE, doubleType);
        roomByType.put(RoomTypes.DELUXE, deluxeType);
        roomByType.put(RoomTypes.SUITE, suiteType);

        return roomByType;
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
            System.out.printf("%s \n\n--Entries Saved.--\n", roomList.toString().replace("[","").replace("]",""));
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
