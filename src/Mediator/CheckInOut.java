package Mediator;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import Controller.ICheckOut;
import Controller.OrderController;
import Controller.ReservationController;
import Controller.RoomController;

import java.util.ArrayList;

import Enums.RoomTypes;
import Enums.TaxFilterType;
import Enums.CreditcardType;
import Enums.PriceFilterType;
import Enums.ReservationStatus;
import entities.*;
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

	private ArrayList<ICheckOut> addCheckOut() {
		ArrayList<ICheckOut> checkOutCon = new ArrayList<>();

		checkOutCon.add(ReservationController.getInstance());
		checkOutCon.add(RoomController.getInstance());
		checkOutCon.add(OrderController.getInstance());

		return checkOutCon;
	}

	private ArrayList<IPriceFilter> addPriceFilters() {
		ArrayList<IPriceFilter> priceFilters = new ArrayList<>();
		DiscountFilter discount = new DiscountFilter(PriceFilterType.MULTIPLIER, CreditcardType.VISA);
		TaxFilter gst = new TaxFilter(PriceFilterType.MULTIPLIER, TaxFilterType.GST);
		TaxFilter serviceCharge = new TaxFilter(PriceFilterType.MULTIPLIER, TaxFilterType.SERVICE);

		priceFilters.add(discount);
		priceFilters.add(gst);
		priceFilters.add(serviceCharge);

		return priceFilters;
	}

	public void checkOut(String roomID) {

		ArrayList<ICheckOut> checkOutCon = addCheckOut();
		ArrayList<Node> paymentInfo = new ArrayList<>();
		ArrayList<IPriceFilter> priceFilters = addPriceFilters();
		double addPrice, totalPrice = 1, finalPrice;
		Date thisDate = new Date();

		System.out.println("=============================");
		System.out.println("  Information Of Hotel Stay  ");
		System.out.println("=============================");
		for (ICheckOut con : checkOutCon) {
			paymentInfo.add(con.checkOutPro(roomID));
		}

		System.out.println();
		System.out.println("-----Check-Out Successful-----");
		System.out.println();

		for (Node node : paymentInfo) {
			if (node.getType() == PriceFilterType.MULTIPLIER) {
				totalPrice *= node.getNum().doubleValue();
			} else {
				if (totalPrice == 1)
					totalPrice = 0;
				totalPrice += node.getNum().doubleValue();
			}
		}

		System.out.println();
		System.out.println("============================");
		System.out.println("    Outstanding Payments    ");
		System.out.println("============================");
		System.out.println(thisDate);
		System.out.println("Total Cost of Hotel Stay: " + totalPrice);

		finalPrice = totalPrice;
		for (IPriceFilter filter : priceFilters) {
			addPrice = filter.execute(totalPrice);
			System.out.printf("%20s  %.2f\n", filter.getDescription(), addPrice);
			finalPrice += addPrice;
		}

		System.out.println("Total Payable: " + finalPrice);
	}

	public void checkIn(String reservationID) {
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

		return roomList.get(roomType).size() - reservationCount;
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

	private boolean validCheckIn(Reservation reservation) {
		if (reservation == null) {
			System.out.println("Invalid Reservation ID");
			return false;
		}
		Date thisDate = new Date();
		thisDate = removeTime(thisDate);
		if (!thisDate.equals(reservation.getCheckIn())) {
			System.out.println("Check In date does not match");
			return false;
		}
		if (reservation.getReservationStatus() != ReservationStatus.CONFIRM) {
			System.out.println("Reservation cannot be checked-in");
			return false;
		}
		return true;
	}

	// private boolean validCheckOut(Reservation reservation) {
	// if (reservation == null) {
	// System.out.println("Invalid Reservation ID");
	// return false;
	// }
	// if (reservation.getReservationStatus() != ReservationStatus.CHECKIN) {
	// System.out.println("Reservation cannot be checked-out");
	// return false;
	// }
	// /*
	// * Date thisDate = new Date();
	// * thisDate = removeTime(thisDate);
	// * if (!thisDate.equals(reservation.getCheckOut())) {
	// * System.out.println("Check Out date does not match");
	// * return false;
	// * }
	// */
	// return true;
	// }

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
