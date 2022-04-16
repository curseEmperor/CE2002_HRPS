package Entity;

import Enums.BedTypes;
import Enums.RoomStatus;
import Enums.RoomTypes;
import Enums.RoomView;

public class Room extends Entities {
	private String roomID; // 4 digit room number. first 2 digits represent level, last 2 digits are room
							// number
	private String guestID;
	private double roomPrice;
	private RoomTypes roomType; // single, double, deluxe, suite
	private BedTypes bedType; // single, double, queen, king
	private boolean WiFi;
	private RoomView view;
	private boolean smoke;
	private RoomStatus roomStatus; // Vacant, Occupied, Reserved, Under Maintenance

	public Room(String roomID, double roomPrice, RoomTypes roomType, BedTypes bedType, boolean WiFi,
			RoomView view,
			boolean smoke) {
		this.roomID = roomID;
		this.guestID = null;
		this.roomPrice = roomPrice;
		this.roomType = roomType;
		this.bedType = bedType;
		this.WiFi = WiFi;
		this.view = view;
		this.smoke = smoke;
		this.roomStatus = RoomStatus.VACANT;
	}

	public void setRoomID(String roomID) {
		this.roomID = roomID;
	}

	public String getRoomID() {
		return roomID;
	}

	public void setGuestID(String guestID) {
		this.guestID = guestID;
	}

	public String getGuestID() {
		return guestID;
	}

	public void setRoomPrice(double roomPrice) {
		this.roomPrice = roomPrice;
	}

	public double getRoomPrice() {
		return roomPrice;
	}

	public void setRoomType(RoomTypes roomType) {
		this.roomType = roomType;
	}

	public RoomTypes getRoomType() {
		return roomType;
	}

	public void setBedType(BedTypes bedType) {
		this.bedType = bedType;
	}

	public BedTypes getBedType() {
		return bedType;
	}

	public void setWiFi(boolean WiFi) {
		this.WiFi = WiFi;
	}

	public boolean getWiFi() {
		return WiFi;
	}

	public void setView(RoomView view) {
		this.view = view;
	}

	public RoomView getView() {
		return view;
	}

	public void setSmoke(boolean smoke) {
		this.smoke = smoke;
	}

	public boolean getSmoke() {
		return smoke;
	}

	public void setRoomStatus(RoomStatus roomStatus) {
		this.roomStatus = roomStatus;
	}

	public RoomStatus getRoomStatus() {
		return roomStatus;
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		String newLine = System.getProperty("line.separator");

		// result.append(this.getClass().getName() + newLine);

		result.append("Room ID: " + this.roomID + newLine);
		result.append("Guest ID: " + this.guestID + newLine);
		result.append("Room Price: $" + this.roomPrice + newLine);
		result.append("Room Type: " + this.roomType + newLine);
		result.append("Bed Type: " + this.bedType + newLine);
		String YN = (this.WiFi) ? "Y" : "N";
		result.append("WiFi: " + YN + newLine);
		result.append("View: " + this.view + newLine);
		YN = (this.smoke) ? "Y" : "N";
		result.append("Smoke: " + this.smoke + newLine);
		result.append("Room Status: " + this.roomStatus);

		return result.toString();
	}

}
