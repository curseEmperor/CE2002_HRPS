package Entity;

public class Room {
	private int roomID; //4 digit room number. first 2 digits represent level, last 2 digits are room number
	private int guestID;
	private double roomPrice;
	private int roomType; //single, double, deluxe, suite
	private int bedType;	//single, double, queen, king
	private boolean WiFi;
	private int view;
	private boolean smoke;
	private String roomStatus; //Vacant, Occupied, Reserved, Under Maintenance
	
	public Room() {} // constructor
	
	public Room(int roomID, int guestID, double roomPrice, int roomType, int bedType, boolean WiFi, int view, boolean smoke, String roomStatus) {
		this.roomID = roomID;
		this.guestID = guestID;
		this.roomPrice = roomPrice;
		this.roomType = roomType;
		this.bedType = bedType;
		this.WiFi = WiFi;
		this.view = view;
		this.smoke = smoke;
		this.roomStatus = roomStatus;
	} // constructor
	
	public void setRoomID(int roomID) {
		this.roomID = roomID;
	}
	
	public int getRoomID() {
		return roomID;
	}
	
	public void setGuestID(int guestID) {
		this.guestID = guestID;
	}
	
	public int getGuestID() {
		return guestID;
	}
	
	public void setRoomPrice(double roomPrice) {
		this.roomPrice = roomPrice;
	}
	
	public double getRoomPrice() {
		return roomPrice;
	}

	public void setRoomType(int roomType) {
		this.roomType = roomType;
	}
	
	public int getRoomType() {
		return roomType;
	}
	
	public void setBedType(int bedType) {
		this.bedType = bedType;
	}
	
	public int getBedType() {
		return bedType;
	}

	public void setWiFi(boolean WiFi) {
		this.WiFi = WiFi;
	}
	
	public boolean getWiFi() {
		return WiFi;
	}
	
	public void setView(int view) {
		this.view = view;
	}

	public int getView() {
		return view;
	}

	public void setSmoke(boolean smoke) {
		this.smoke = smoke;
	}

	public boolean getSmoke() {
		return smoke;
	}
	
	public void setRoomStatus(String roomStatus) {
		this.roomStatus = roomStatus;
	}
	
	public String getRoomStatus() {
		return roomStatus;
	}
	
}