package Mediator;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import Controller.OrderController;
import Controller.ReservationController;
import Controller.RoomController;
import Entity.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import Enums.RoomTypes;
import Enums.ReservationStatus;

import java.util.List;
import java.util.Map;

public class CheckInOut {
	private static CheckInOut instance = null;

	private CheckInOut() {
	}

	public static CheckInOut getInstance() {
		if (instance == null)
			instance = new CheckInOut();
		return instance;
	}

	public void checkOut(String roomID, float roomDiscount, float orderDiscount) {
		// Check validity of check-out
		Reservation reservation = ReservationController.getInstance().getCheckInReservation(roomID);
		if (validCheckOut(reservation) == false)
			return;

		// Change reservation status to completed
		ReservationController.getInstance().update(reservation, 7, "4");

		// Change room status to vacant and clear guestID
		Room room = RoomController.getInstance().checkExistence(reservation.getRoomID());
		RoomController.getInstance().update(room, 2, null);
		RoomController.getInstance().update(room, 9, "1");

		// Check-out confirmation
		System.out.println("Check-Out Successful");
		System.out.println("Reservation ID: " + reservation.getID());
		System.out.println("Assigned Room: " + reservation.getRoomID());

		// Get payment details (Room payment, order payments)
		printReceipt(reservation, roomDiscount, orderDiscount);
	}

	public void checkIn(String reservationID) {
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy hh:mm a");
		Reservation reservation = ReservationController.getInstance().checkExistence(reservationID);
		if (validCheckIn(reservation) == false)
			return;

		// Assign room
		Room room = assignRoom(reservation);
		if (room == null)
			return;
		ReservationController.getInstance().update(reservation, 2, room.getRoomID());

		// Change reservation status to checked-in
		ReservationController.getInstance().update(reservation, 7, "2");

		// set check in date to NOW
		ReservationController.getInstance().update(reservation, 3, sdf.format(now));

		// Change room status to occupied and set guestID
		RoomController.getInstance().update(room, 2, reservation.getGuestID());
		RoomController.getInstance().update(room, 9, "2");

		// Check-In confirmation
		System.out.println("Check-In Successful");
		System.out.println("Reservation ID: " + reservation.getID());
		System.out.println("Assigned Room: " + room.getRoomID());
	}

	public int numAvailability(Date dateCheck, RoomTypes roomType) {
		// No of room type >= Count date is between checkin to checkout date
		Map<RoomTypes, List<Room>> roomList = RoomController.getInstance().splitRoomByType();
		Map<ReservationStatus, List<Reservation>> reservationList = ReservationController.getInstance()
				.splitReservationByStatus();
		int reservationCount = 0;
		for (Reservation reservation : reservationList.get(ReservationStatus.CONFIRM)) {
			if (reservation.getRoomType() == roomType) {
				if (dateCheck.equals(reservation.getCheckIn()))
					reservationCount++;
				else if (dateCheck.after(reservation.getCheckIn()) && dateCheck.before(reservation.getCheckOut()))
					reservationCount++;
			}
		}
		for (Reservation reservation : reservationList.get(ReservationStatus.CHECKIN)) {
			if (reservation.getRoomType() == roomType) {
				if (dateCheck.before(reservation.getCheckOut()))
					reservationCount++;
			}
		}

		// Rooms are available if number of room type is larger than number of guesting
		// using the room type at date
		return roomList.get(roomType).size() - reservationCount;
	}

	public void printReceipt(Reservation reservation, float roomDiscount, float orderDiscount) {
		String roomID = reservation.getRoomID();

		// Retrieve orders from room
		ArrayList<Order> roomOrders = OrderController.getInstance().retrieveOrdersOfRoom(roomID);

		// Get price of room
		double roomPrice = RoomController.getInstance().checkExistence(roomID).getRoomPrice();

		// Calculate number of weekends and weekdays
		Date checkIn = removeTime(reservation.getCheckIn());
		Date checkOut = removeTime(reservation.getCheckOut());
		long days = TimeUnit.DAYS.convert(checkOut.getTime() - checkIn.getTime(), TimeUnit.MILLISECONDS);
		long weekends = countWeekends(reservation.getCheckIn(), reservation.getCheckOut());
		long weekdays = days - weekends;

		// Calculate room cost (Weekends cost 10% more)
		double roomCost = roomPrice * weekdays + roomPrice * weekends * 1.1;
		double discountRoomCost = roomCost * (1 - roomDiscount);

		// Calculate total order costs and view receipt
		double orderCost = 0;
		if (roomOrders != null)
			for (Order order : roomOrders)
				orderCost += order.viewOrder();
		double discountOrderCost = orderCost * (1 - orderDiscount);

		// Calculate subTotal and total cost
		double subTotal = discountRoomCost + discountOrderCost;
		double GST = 0.07;
		double serviceCharge = 0.10;
		double total = subTotal * (1 + GST + serviceCharge);

		// Print receipt
		Date thisDate = new Date();
		System.out.println();
		System.out.println("============================");
		System.out.println("    Outstanding Payments    ");
		System.out.println("============================");
		System.out.println(thisDate);
		System.out.println("Room");
		System.out.println("  - Weekdays: " + weekdays);
		System.out.println("  - Weekends: " + weekends);
		System.out.println("  - Discount: " + roomDiscount * 100 + "%");
		System.out.printf("  - Total cost: $%.2f\n", discountRoomCost);
		System.out.println("Room Service");
		System.out.println("  - Discount: " + orderDiscount * 100 + "%");
		System.out.printf("  - Total cost: $%.2f\n", discountOrderCost);
		System.out.printf("SubTotal : $%.2f", subTotal);
		System.out.println();
		System.out.printf("GST      : $%.2f", subTotal * GST);
		System.out.println();
		System.out.printf("Service  : $%.2f", subTotal * serviceCharge);
		System.out.println();
		System.out.printf("Total    : $%.2f", total);
		System.out.println();
		System.out.println("============================");
		System.out.println();
	}

	public void payment(String ID) {
		Reservation reservation = ReservationController.getInstance().checkExistence(ID);
		if (reservation == null) {
			System.out.println("Invalid Reservation ID");
			return;
		}
		ArrayList<Order> roomOrders = OrderController.getInstance().retrieveOrdersOfRoom(reservation.getRoomID());
		// update orders to paid
		if (roomOrders != null)
			for (Order order : roomOrders)
				OrderController.getInstance().update(order, 3, "4");
	}

	public void setReservationCreditcard(String ID, String cardNumber, Date expiryDate, int CVV, int type,
			String cardName) {
		Reservation reservation = ReservationController.getInstance().checkExistence(ID);
		if (reservation == null)
			return;
		ReservationController.getInstance().updateCreditcard(reservation, cardNumber, expiryDate, CVV, type, cardName);
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

	private long countWeekends(Date start, Date end) {
		long days = TimeUnit.DAYS.convert(end.getTime() - start.getTime(), TimeUnit.MILLISECONDS);
		long weekends = 2 * (int) (days / 7);
		days = days - 7 * (int) (days / 7);
		Calendar c = Calendar.getInstance();
		c.setTime(start);
		while (days > 0) {
			int day = c.get(Calendar.DAY_OF_WEEK);
			if (day == Calendar.FRIDAY || day == Calendar.SATURDAY)
				weekends++;
			days--;
			c.add(Calendar.DATE, 1);
		}
		return weekends;
	}

	private boolean validCheckIn(Reservation reservation) {
		if (reservation == null) {
			System.out.println("Invalid Reservation ID");
			return false;
		}
		Date thisDate = new Date();
		thisDate = removeTime(thisDate);
		// System.out.println("today: " + thisDate + "checkin: " +
		// removeTime(reservation.getCheckIn()));
		if (!thisDate.equals(removeTime(reservation.getCheckIn()))) {
			System.out.println("Check In date does not match");
			return false;
		}
		if (reservation.getReservationStatus() != ReservationStatus.CONFIRM) {
			System.out.println("Reservation cannot be checked-in");
			return false;
		}
		return true;
	}

	private boolean validCheckOut(Reservation reservation) {
		if (reservation == null) {
			System.out.println("No reservation found (Please enter room ID)");
			return false;
		}
		if (reservation.getReservationStatus() != ReservationStatus.CHECKIN) {
			System.out.println("Reservation cannot be checked-out");
			return false;
		}

		Date thisDate = new Date();
		thisDate = removeTime(thisDate);
		Date checkOut = removeTime(reservation.getCheckOut());
		if (!thisDate.equals(checkOut)) {
			System.out.println("Check Out date does not match");
			return false;
		}
		
		return true;
	}

	private Room assignRoom(Reservation reservation) {
		RoomTypes roomType = reservation.getRoomType();
		List<Room> vacantRooms = RoomController.getInstance().generateOccupancyReport().get(roomType);
		if (vacantRooms.size() == 0) { // if not rooms available
			System.out.println("Room is not ready");
			return null;
		}
		return vacantRooms.get(0);
	}
}
