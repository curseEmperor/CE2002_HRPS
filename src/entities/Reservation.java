package entities;

import java.io.Serializable;
import java.util.Date;

import Enums.ReservationStatus;
import Enums.RoomTypes;

public class Reservation implements Serializable {
    private String reservationID;
    private String guestID;
    private String roomID;
    private Date checkIn;
    private Date checkOut;
    private int childNo;
    private int adultNo;
    private ReservationStatus reservationStatus;
    private RoomTypes roomType;

    public Reservation(String guestID, Date checkIn, Date checkOut, int childNo, int adultNo) {
        this.guestID = guestID;
        this.roomID = null;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.childNo = childNo;
        this.adultNo = adultNo;
    }

    public String getID() {
        return reservationID;
    }

    public void setID(String reservationID) {
        this.reservationID = reservationID;
    }

    public String getGuestID() {
        return guestID;
    }

    public void setGuestID(String guestID) {
        this.guestID = guestID;
    }

    public String getRoomID() {
        return roomID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    public Date getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(Date checkIn) {
        this.checkIn = checkIn;
    }

    public Date getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(Date checkOut) {
        this.checkOut = checkOut;
    }

    public int getChildNo() {
        return childNo;
    }

    public void setChildNo(int childNo) {
        this.childNo = childNo;
    }

    public int getAdultNo() {
        return adultNo;
    }

    public void setAdultNo(int adultNo) {
        this.adultNo = adultNo;
    }

    public ReservationStatus getReservationStatus() {
        return reservationStatus;
    }

    public void setReservationStatus(ReservationStatus reservationStatus) {
        this.reservationStatus = reservationStatus;
    }
    
    public RoomTypes getRoomType() {
    	return roomType;
    }
    
    public void setRoomType(RoomTypes roomType) {
    	this.roomType = roomType;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        String newLine = System.getProperty("line.separator");

        result.append(this.getClass().getName() + " Object {" + newLine);

        result.append("reservationID" + this.reservationID + newLine);
        result.append("guestID" + this.guestID + newLine);
        result.append("RoomID" + this.roomID + newLine);
        result.append("adult number" + this.adultNo + newLine);
        result.append("child number" + this.childNo + newLine);
        result.append("check in" + this.checkIn + newLine);
        result.append("check out" + this.checkOut + newLine);
        result.append("status" + this.reservationStatus + newLine);

        result.append("}");

        return result.toString();
    }

}
