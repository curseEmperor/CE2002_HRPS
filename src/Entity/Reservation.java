package Entity;

import java.util.Date;
import java.text.SimpleDateFormat;

import Enums.ReservationStatus;
import Enums.RoomTypes;

public class Reservation extends Entities {
    private String reservationID;
    private String guestID;
    private String roomID;
    private Date checkIn;
    private Date checkOut;
    private int childNo;
    private int adultNo;
    private ReservationStatus reservationStatus;
    private RoomTypes roomType;
    private Creditcard card;

    public Reservation(String guestID, Date checkIn, Date checkOut, int childNo, int adultNo) {
        this.guestID = guestID;
        this.roomID = null;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.childNo = childNo;
        this.adultNo = adultNo;
        this.card = null;
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

    public Creditcard getCreditcard() {
        return card;
    }

    public void setCreditcard(Creditcard card) {
        this.card = card;
    }

    public void setCreditcard(String cardNumber, Date expDate, int CVV, int type, String cardName) {
        this.card = new Creditcard(cardNumber, expDate, CVV, type, cardName);
    }

    @Override
    /*
     * public String toString() {
     * StringBuilder result = new StringBuilder();
     * String newLine = System.getProperty("line.separator");
     * SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy hh:mm:ss a");
     * //result.append(this.getClass().getName() + " Object {" + newLine);
     * /*result.append("Reservation ID: " + this.reservationID + newLine);
     * result.append("Guest ID: " + this.guestID + newLine);
     * result.append("Room ID: " + this.roomID + newLine);
     * result.append("No. of Adult(s): " + this.adultNo + newLine);
     * result.append("No. of Child(ren): " + this.childNo + newLine);
     * result.append("Room Type: " + this.getRoomType() + newLine);
     * result.append("Check in Date: " + sdf.format(this.checkIn) + newLine);
     * result.append("Check out Date: " + sdf.format(this.checkOut) + newLine);
     * result.append("Status: " + this.reservationStatus);
     * if (this.getReservationStatus() != ReservationStatus.EXPIRED)
     * {
     * result.append("  " + this.reservationID + "          " + this.guestID +
     * "         " + this.roomID + "       " + this.roomType + "      " +
     * this.reservationStatus + "     " + sdf.format(this.checkIn) + "    " +
     * sdf.format(this.checkOut) + newLine);
     * }
     * 
     * //result.append("}");
     * 
     * return result.toString();
     * }
     */

    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy hh:mm:ss a");
        return String.format("%10s\t%10s\t%10s\t%10s\t%10s\t%10s\t%10s", this.reservationID, this.guestID, this.roomID,
                this.roomType, this.reservationStatus, sdf.format(this.checkIn), sdf.format(this.checkOut));

    }

}
