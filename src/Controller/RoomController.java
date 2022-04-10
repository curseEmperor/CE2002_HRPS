// TODO: edit check in to allow for both reservation and walk ins
// TODO: print invoice w checkout

package Controller;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;

import Entity.Room;
import java.io.Serializable;

public class RoomController implements Serializable {
	Scanner sc = new Scanner(System.in);
	private ArrayList<Room> rooms = new ArrayList<Room>();
	
	
	public void createRoom() {  //roomID, guestID, roomPrice, roomType, bedType, WiFi, view, smoke, roomStatus
		Room room = new Room();
		rooms.add(room);
		
		do {
			// roomID
			System.out.println("Enter roomID: ");
	        int roomID = sc.nextInt();

	        while (this.roomExists(roomID))//room already exists
	        {
	            System.err.println("Room "+ roomID + " already exists. Please enter another roomID again: ");
	            roomID = sc.nextInt();
	        }
	        room.setRoomID(roomID);
	       
	        // roomPrice  
	        System.out.println("Enter Room Price: ");
	        double roomPrice = sc.nextDouble();

	        while (roomPrice < 0.0)
	        {
	            System.err.println("Please enter a positive room rate: ");
	            roomPrice = sc.nextDouble();
	        }
	        room.setRoomPrice(roomPrice);
	        
	        //roomType
	        System.out.println("Enter Room Type: ");
	        System.out.println("(1) Single ");
	        System.out.println("(2) Double ");
	        System.out.println("(3) Deluxe ");
	        System.out.println("(4) Suite ");
	        int roomType = sc.nextInt();

	        while (roomType != 1 && roomType != 2 && roomType != 3 && roomType != 4)
	        {
	            System.err.println("Please select a valid room type: ");
	            System.out.println("(1) Single ");
	            System.out.println("(2) Double ");
	            System.out.println("(3) Deluxe ");
	            System.out.println("(4) Suite ");
	            roomType = sc.nextInt();
	        }
	        room.setRoomType(roomType);
	        
	        //bedType
	        System.out.println("Enter Bed Type: ");
	        System.out.println("(1) Single Bed");
	        System.out.println("(2) Double Bed");
	        System.out.println("(3) Queen Bed");
	        System.out.println("(4) King Bed");
	        int bedType = sc.nextInt();

	        while (bedType != 1 && bedType != 2 && bedType != 3 && bedType != 4)
	        {
	            System.err.println("Please select a valid bed type: ");
	            System.out.println("(1) Single Bed");
	            System.out.println("(2) Double Bed");
	            System.out.println("(3) Queen Be");
	            System.out.println("(4) King Bed ");
	            bedType = sc.nextInt();
	        }
	        room.setBedType(bedType);
	        
	        //WIFI
	        System.out.println("Please select if this room is WiFi enabled: ");
	        System.out.println("(1) WiFi Enabled");
	        System.out.println("(2) Not WiFi Enabled");
	        int choiceW = sc.nextInt();

	        while (choiceW != 1 && choiceW !=2)
	        {
	            System.err.println("Please enter a valid choice: ");
	            System.out.println("(1) WiFi Enabled");
	            System.out.println("(2) Not WiFi Enabled");
	            choiceW = sc.nextInt();
	        }
	        if (choiceW == 1) room.setWiFi(true);
	        else room.setWiFi(false);
	        
	        //View
	        System.out.println("Please select the room's view: ");
	        System.out.println("(1) City View");
	        System.out.println("(2) Pool View");
	        System.out.println("(3) No View");
	        int choiceV = sc.nextInt();

	        while (choiceV != 1 && choiceV !=2 && choiceV != 3)
	        {
	            System.err.println("Please enter a valid choice: ");
	            System.out.println("(1) City View ");
	            System.out.println("(2) Pool View ");
	            System.out.println("(3) No View");
	            choiceV = sc.nextInt();
	        }
	        room.setView(choiceV);
	        
	        //Smoking
	        System.out.println("Please select if smoking is allowed in this room: ");
	        System.out.println("(1) Smoking Allowed");
	        System.out.println("(2) Smoking Not Allowed");
	        int choiceS = sc.nextInt();

	        while (choiceS != 1 && choiceS !=2)
	        {
	            System.err.println("Please enter a valid choice: ");
	            System.out.println("(1) Smoking Allowed");
	            System.out.println("(2) Smoking Not Allowed");
	            choiceS = sc.nextInt();
	        }
	        if (choiceS == 1) room.setSmoke(true);
	        else room.setSmoke(false);
	       
	        //roomStatus
	        System.out.println("Please select a valid room status: ");
	        System.out.println("Vacant");
	        System.out.println("Occupied");
	        System.out.println("Reserved");
	        System.out.println("Under Maintenance");
	        String roomStatus = sc.next();
	        while (!roomStatus.equals("Vacant") && !roomStatus.equals("Occupied") && !roomStatus.equals("Reserved") && !roomStatus.equals("Under Maintenance")) {
	        	System.err.println("Please select a valid room status: ");
	            System.out.println("Vacant");
	            System.out.println("Occupied");
	            System.out.println("Reserved");
	            System.out.println("Under Maintenance");
	           roomStatus = sc.next();
	        }
	        room.setRoomStatus(roomStatus); 
			
		} while(rooms.size()<=48);
		   
	}
	
//	public String formatRoomID(int roomID) {
//	    StringBuilder sb = new StringBuilder(roomID);
//	    sb.insert(2, "-");
//	    return sb.toString();
//	}
	
	public boolean roomExists(int roomID) { //searchByRoom
		for (int i=0; i<rooms.size(); i++) {
			if((rooms.get(i)).getRoomID() == roomID) return true;
		}
		return false;
	}
	
	public Room retrieveRoom(int roomID) {	//by roomID
        for (Room room : rooms) {
            if (room.getRoomID() == roomID)
                return room;
        }
        return null;
    }
	
	// TODO: edit check in to allow for both reservation and walk ins
	// reserved will become occupied for reservations
	// walk ins need to skim thru available rooms and check if they fit room detail requirements by guest
	public void checkIn(int roomID, int guestID) {
		if (roomExists(roomID)) {
			Room room = retrieveRoom(roomID);
			room.setGuestID(guestID);
			room.setRoomStatus("Occupied");
			System.out.printf("Room %d has been assigned to guest %d", roomID, guestID);
		}
		else System.out.println("Check-In unsuccessful");
	} 
	
	// TODO: print invoice w checkout
	public boolean checkOut(int roomID) {
		if (roomExists(roomID)) {
			Room room = retrieveRoom(roomID);
			if (room.getRoomStatus().equals("Occupied")){
				room.setGuestID(0);
				room.setRoomStatus("Vacant");
				return true;
			}	
		}
		return false;
	}
	
	// Room is available only if status is "Vacant". Else unavailable ("Occupied", "Reserved", "Under Maintenance") 
	public String checkAvailability(int roomID) {
		if (roomExists(roomID)) {
			Room room = retrieveRoom(roomID);
			if (room.getRoomStatus().equals("Vacant")) return ("Room is available");
			else return ("Room is not available");
		}
		return ("Room does not exist");
	}
	
	public void printRoomDetails() {
		int roomID;
		do {
			System.out.println("Please enter the roomID: ");
			roomID = sc.nextInt();
		} while(!roomExists(roomID)); 
		
		System.out.println("\n==================================================");
		System.out.println(" Room Details ");
		System.out.println("==================================================");
		System.out.printf("%-8s %-13s %-18s %-11s %-19s %-10s %-11s %-12s %-13s %-10s", "Room ID", "Guest ID", "Room Price", "Room Type", "Bed Type","WiFi Enabled","Smoking Status", "View", "Room Status");
	    System.out.println();
	    Room room = retrieveRoom(roomID);
	    System.out.printf("%-8s %-13s %-18s %-11s %-19s %-10s %-11s %-12s %-13s %-10s", room.getRoomID(), room.getGuestID(), room.getRoomPrice(), room.getRoomType(), room.getBedType(), room.getWiFi(), room.getSmoke(), room.getView(), room.getRoomStatus());
		System.out.println("");
	}
	
//////////////////////////////////////////////// UPDATE ROOM DETAILS //////////////////////////////////////////////// 
	
	// can only update 1.roomPrice, 2.roomType, 3.bedType, 4.WiFi, 5.Smoke & 6.roomStatus ((how to change view??)
	
	public void updateRoomDetails(int roomID) {
		
		if (roomExists(roomID)) {
			System.out.println("Please select the option you would like to update: ");
			System.out.println("(1) Room Price");
			System.out.println("(2) Room Type");
			System.out.println("(3) Bed Type");
			System.out.println("(4) WiFi Availability");
			System.out.println("(5) Smoking Availability");
			System.out.println("(6) Room Status");
			
			int updateChoice = sc.nextInt();
			
			switch(updateChoice) {
				case 1://roomPrice
					System.out.println("Enter new Room Price: ");
			        double roomPrice = sc.nextDouble();
			        updateRoomPrice(roomID, roomPrice);
				case 2: //roomType
					System.out.println("Enter new Room Type: ");
			        System.out.println("(1) Single ");
			        System.out.println("(2) Double ");
			        System.out.println("(3) Deluxe ");
			        System.out.println("(4) Suite ");
			        int roomType = sc.nextInt();
			        updateRoomType(roomID, roomType);
				case 3: //bedType
					System.out.println("Enter new Bed Type: ");
			        System.out.println("(1) Single Bed");
			        System.out.println("(2) Double Bed");
			        System.out.println("(3) Queen Bed");
			        System.out.println("(4) King Bed");
			        int bedType = sc.nextInt();
			        updateBedType(roomID, bedType);
				case 4: //WiFi - if need to update its just true-->false or false-->true
					updateWiFi(roomID);
				case 5: //Smoke - if need to update its just true-->false or false-->true
					updateSmoke(roomID);
				case 6: //roomStatus
					System.out.println("Please select a valid new room status: ");
			        System.out.println("Vacant");
			        System.out.println("Occupied");
			        System.out.println("Reserved");
			        System.out.println("Under Maintenance");
			        String newRoomStatus = sc.next();
			        updateRoomStatus(roomID, newRoomStatus);
			}
		}
		else System.out.println("Room does not exist. Please try again.");
	}
	
	// or can update directly without menu being displayed
	public void updateRoomPrice(int roomID, double roomPrice) {
		while (roomPrice < 0.0) {
			System.err.println("Please enter a positive room rate: ");
	        roomPrice = sc.nextDouble();
		}
		Room room = retrieveRoom(roomID);
		room.setRoomPrice(roomPrice);
		System.out.printf("Room %d's price has been updated!\n", roomID);
	}	
	
	public void updateRoomType(int roomID, int roomType) {
		 while (roomType != 1 && roomType != 2 && roomType != 3 && roomType != 4){
	            System.err.println("Please select a valid room type: ");
	            System.out.println("(1) Single ");
	            System.out.println("(2) Double ");
	            System.out.println("(3) Deluxe ");
	            System.out.println("(4) Suite ");
	            roomType = sc.nextInt();
	     }
		Room room = retrieveRoom(roomID);
		room.setRoomType(roomType);
		System.out.printf("Room %d's type has been updated!\n", roomID);
	}
	
	public void updateBedType(int roomID, int bedType) {
		while (bedType != 1 && bedType != 2 && bedType != 3 && bedType != 4)
        {
            System.err.println("Please select a valid bed type: ");
            System.out.println("(1) Single Bed");
            System.out.println("(2) Double Bed");
            System.out.println("(3) Queen Be");
            System.out.println("(4) King Bed ");
            bedType = sc.nextInt();
        }
		Room room = retrieveRoom(roomID);
		room.setBedType(bedType);
		System.out.printf("Room %d's bed type has been updated!\n", roomID);
	}
	
	public void updateWiFi(int roomID) {
		Room room = retrieveRoom(roomID);
		if (room.getWiFi() == true) room.setWiFi(false);
		else room.setWiFi(true);
		System.out.printf("Room %d's WiFi availability has been updated!\n", roomID);
	}
	
	public void updateSmoke(int roomID) {
		Room room = retrieveRoom(roomID);
		if (room.getSmoke() == true) room.setSmoke(false);
		else room.setSmoke(true);
		System.out.printf("Room %d's smoking availability has been updated!\n", roomID);
	}
	
	public void updateRoomStatus(int roomID, String newRoomStatus) {
		while (!newRoomStatus.equals("Vacant") && !newRoomStatus.equals("Occupied") && !newRoomStatus.equals("Reserved") && !newRoomStatus.equals("Under Maintenance")) {
        	System.err.println("Please select a valid room status: ");
            System.out.println("Vacant");
            System.out.println("Occupied");
            System.out.println("Reserved");
            System.out.println("Under Maintenance");
            newRoomStatus = sc.next();
        }		
		Room room = retrieveRoom(roomID);
		room.setRoomStatus(newRoomStatus);
		System.out.printf("Room %d's status has been updated!\n", roomID);
	}
	
//////////////////////////////////////////////// END OF UPDATE //////////////////////////////////////////////// 

	
	
//////////////////////////////////////////////// ROOM STATISTIC REPORT //////////////////////////////////////////////// 	
	
	// a. room type occupancy rate (room status = vacant) - (room type, number and list the room number)
	public void printeOccupancyRate(){
		ArrayList<Room> roomList = new ArrayList<Room>();
		//String vacantString = "";
		int vacantSingle = 0;
		int vacantDouble = 0;
		int vacantDeluxe = 0; //single double deluxe suite
		int vacantSuite = 0;
		int singleTotal = 0;
		int doubleTotal = 0;
		int deluxeTotal = 0;
		int suiteTotal = 0;
		System.out.println("\n==================================================");
		System.out.println(" Occupancy Rate ");
		System.out.println("==================================================");
		for (int i = 0; i < rooms.size(); i++) {
			Room room = rooms.get(i);
					
			if(room.getRoomType() == 1) { //single
				singleTotal += 1;
				if(room.getRoomStatus().contentEquals("Vacant")) {
					roomList.add(room);
					vacantSingle += 1;
				}
			}
			if(room.getRoomType() == 2) { //double
				doubleTotal += 1;
				if(room.getRoomStatus().contentEquals("Vacant")) {
					roomList.add(room);
					vacantDouble += 1;
				}
			}
			if(room.getRoomType() == 3) { //deluxe
				deluxeTotal += 1;
				if(room.getRoomStatus().contentEquals("Vacant")) {
					roomList.add(room);
					vacantDeluxe += 1;
				}
			}
			if(room.getRoomType() == 4) { //suite
				suiteTotal += 1;
				if(room.getRoomStatus().contentEquals("Vacant")) {
					roomList.add(room);
					vacantSuite += 1;
				}
			}
		}

		System.out.println("\nSingle Room: " + "Number : " + vacantSingle + " out of " + singleTotal);
		formatPrintRoomsA(roomList, 1);
		System.out.println("\nDouble Room: " + vacantDouble + " out of " + doubleTotal);
		formatPrintRoomsA(roomList, 2);
		System.out.println("\nDeluxe Room: " + vacantDeluxe + " out of " + deluxeTotal);
		formatPrintRoomsA(roomList, 3);
		System.out.println("\nVIP Suite: " + vacantSuite + " out of " + suiteTotal);
		formatPrintRoomsA(roomList, 4);
	}
	
	public static void formatPrintRoomsA(ArrayList<Room> roomList, int roomType) {
		System.out.print("\t\tRooms: ");
		ArrayList<String> roomTypeList = new ArrayList<String>();
		for(int i = 0; i < roomList.size(); i++) {
			if(roomList.get(i).getRoomType() == roomType) {
				roomTypeList.add(Integer.toString(roomList.get(i).getRoomID()));
			}
		}
		String toPrint = String.join(", ", roomTypeList);
		System.out.println(toPrint);
	}
	
	// b. room status (status type and list the room number)
	public void printAllByRoomStatus() {
		ArrayList<Room> roomListVacant = new ArrayList<Room>();
		ArrayList<Room> roomListOccupied = new ArrayList<Room>();
		ArrayList<Room> roomListReserved = new ArrayList<Room>();
		ArrayList<Room> roomListUnderMaintenance = new ArrayList<Room>();
		System.out.println("\n==================================================");
		System.out.println(" Room Status ");
		System.out.println("==================================================");
		for (int i = 0; i < rooms.size(); i++) {
			Room room = rooms.get(i);
					
			if(room.getRoomStatus().equals("Vacant")) { //vacant
				roomListVacant.add(room);
			}
			if(room.getRoomStatus().equals("Occupied")) { //vacant
				roomListOccupied.add(room);
			}
			if(room.getRoomStatus().equals("Reserved")) { //vacant
				roomListReserved.add(room);
			}
			if(room.getRoomStatus().equals("Under Maintenance")) { //vacant
				roomListUnderMaintenance.add(room);
			}
		}	
		System.out.println("\nVacant   			: ");
		formatPrintRoomsB(roomListVacant);
		System.out.println("\nOccupied 			:");
		formatPrintRoomsB(roomListOccupied);
		System.out.println("\nReserved 			:");
		formatPrintRoomsB(roomListReserved);
		System.out.println("\nUnder Maintenance :");
		formatPrintRoomsB(roomListUnderMaintenance);
	}
	
	public void printbyRoomStatus(String roomStatus) { //given a room status, print all rooms w that status
		ArrayList<Room> roomList = new ArrayList<Room>();
		System.out.println("\n==================================================");
		System.out.println(" Room Status :" + roomStatus);
		System.out.println("==================================================");
		for (int i = 0; i < rooms.size(); i++) {
			Room room = rooms.get(i);	
			if(room.getRoomStatus().equals(roomStatus)) { //Vacant, Occupied, Reserved, Under Maintenance
				roomList.add(room);
			}
		}	
		System.out.println("\n" + roomStatus);
		formatPrintRoomsB(roomList);
	}
	
	public static void formatPrintRoomsB(ArrayList<Room> roomList) {
		System.out.print("\t\tRooms: ");
		ArrayList<String> roomTypeList = new ArrayList<String>();
		for(int i = 0; i < roomList.size(); i++) {
			roomTypeList.add(Integer.toString(roomList.get(i).getRoomID()));
		}
		String toPrint = String.join(", ", roomTypeList);
		System.out.println(toPrint);
	}
	
//////////////////////////////////////////////// END OF ROOM STATISTIC REPORT //////////////////////////////////////////////// 	
	
	public void savetoDB() {
    	try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("Room.ser"));
            out.writeInt(rooms.size());
            for (Room room: rooms)
                out.writeObject(room);
            System.out.printf("RoomController: %s Entries Saved.\n", rooms);
            out.close();
        } catch (IOException e3) {
            e3.printStackTrace();
        }
    }	
	
	public void loadinDB() {
        try {
        	ObjectInputStream ois = new ObjectInputStream(new FileInputStream("Room.ser"));

            int noOfRecords = ois.readInt();
            System.out.println("RoomController: " + noOfRecords + " Entries Loaded");
            for (int i = 0; i < noOfRecords; i++) {
                rooms.add((Room) ois.readObject());
            }
            for (Room room : rooms) {
                System.out.println(room);
            }
            ois.close();
        } catch (IOException | ClassNotFoundException e4) {
            e4.printStackTrace();
        }
    }	
}
