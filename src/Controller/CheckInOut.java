package Controller;

import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.ArrayList;

import Enums.RoomTypes;
import Enums.ReservationStatus;
import entities.*;
import java.util.List;
import java.util.Map;

public class CheckInOut {
	private static CheckInOut instance = null;
    Scanner sc;

    private CheckInOut() {
        sc = new Scanner(System.in);
    }

    public static CheckInOut getInstance() {
        if (instance == null) instance = new CheckInOut();
        return instance;
    }
    
    public void checkOut(String reservationID, float roomDiscount, float orderDiscount) {
    	//Change reservation status
    	Reservation reservation = ReservationController.getInstance().checkExistence(reservationID);
    	if (reservation == null) {
    		System.out.println("Invalid Reservation ID");
    		return;
    	}
    	ReservationController.getInstance().update(reservation, 7, "4");
    	
    	//Change room status
    	Room room = RoomController.getInstance().checkExistence(reservation.getRoomID());
    	RoomController.getInstance().update(room, 2, null);
    	RoomController.getInstance().update(room, 9, "1");
    	
    	//Get payment details (Room payment, order payments)
    	payment(reservation);
    }
    
    public void checkIn(String reservationID) {
    	try {
    	Reservation reservation = ReservationController.getInstance().checkExistence(reservationID);
    	if (reservation == null) {
    		System.out.println("Invalid Reservation ID");
    		return;
    	}
    	Date thisDate = new Date();
    	thisDate = removeTime(thisDate);
    	if (!thisDate.equals(reservation.getCheckIn())) {
    		System.out.println("Check In date does not match");
    		return;
    	}
    	if (reservation.getReservationStatus()!=ReservationStatus.CONFIRM) {
    		System.out.println("Reservation cannot be checked-in");
    		return;
    	}
    	
    	//Assign room
    	List<Room> vacantRooms = RoomController.getInstance().generateOccupancyReport().get(reservation.getRoomType());
    	if (vacantRooms.size()==0) {
    		System.out.println("Room is not ready");
    		return;
    	}
    	Room room = vacantRooms.get(0);
    	ReservationController.getInstance().update(reservation, 2, room.getRoomID());
    	
    	//Change reservation status
    	ReservationController.getInstance().update(reservation, 7, "2");
    	
    	//Change room status
    	RoomController.getInstance().update(room, 2, reservation.getGuestID());
    	RoomController.getInstance().update(room, 9, "2");
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    
    public int numAvailability(Date dateCheck, RoomTypes roomType) {
    	//No of room type >= Count date is between checkin to checkout date
    	Map<RoomTypes, List<Room>> roomList = RoomController.getInstance().splitRoomByType();
    	Map<ReservationStatus, List<Reservation>> reservationList = ReservationController.getInstance().splitReservationByStatus();
    	int reservationCount = 0;
    	for (Reservation reservation : reservationList.get(ReservationStatus.CONFIRM)) {
    		if (reservation.getRoomType() == roomType) {
	    		if (dateCheck.equals(reservation.getCheckIn())) reservationCount++;
	    		else if (dateCheck.after(reservation.getCheckIn()) && dateCheck.before(reservation.getCheckOut())) reservationCount++;
    		}
    	}
    	for (Reservation reservation : reservationList.get(ReservationStatus.CHECKIN)) {
    		if (reservation.getRoomType() == roomType) {
    			if (dateCheck.before(reservation.getCheckOut())) reservationCount++;
    		}
    	}
    	
    	//Rooms are available if number of room type is larger than number of guesting using the room type at date
    	return roomList.get(roomType).size() - reservationCount;
    }
    
    public void payment(Reservation reservation) {
    	ArrayList<Order> roomOrders = OrderController.getInstance().retrieveOrdersOfRoom(reservation.getRoomID());
    	double roomPrice = RoomController.getInstance().checkExistence(reservation.getRoomID()).getRoomPrice();
    	long days = TimeUnit.DAYS.convert(reservation.getCheckOut().getTime()-reservation.getCheckIn().getTime(), TimeUnit.MILLISECONDS);
    	double reservationCost = roomPrice*days;
    	System.out.println("Outstanding payments");
    	
    }
    
    private Date removeTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }
    
    private long countWeekends (Date start, Date end) {
    	long days = TimeUnit.DAYS.convert(end.getTime()-start.getTime(), TimeUnit.MILLISECONDS);
    	int weekends = 2*(int)(days/7);
    	days = days - (int)(days/7);
    	while (days > 0) {
    		if (start.getDay() == 6 || start.getDay() == 6) weekends++;
    		
    	}
    	return weekends;
    }
}
