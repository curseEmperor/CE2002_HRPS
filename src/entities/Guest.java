package entities;

import java.io.Serializable;
import java.util.Date;
import Enums.CreditcardTypes;

public class Guest implements Serializable {

    private String guestID; // driver || passport licence
    private String guestName;
    private String address;
    private String contact;
    private String country;
    private char gender;
    private String nationality;
    private Creditcard card;

    public Guest(String guestID, String guestName, String address, String contact, String country, char gender,
            String nationality, String cardNumber, Date expDate, int CVC, int type, String cardName) { // , CreditCard card
        this.guestID = guestID;
        this.guestName = guestName;
        this.address = address;
        this.contact = contact;
        this.country = country;
        this.gender = gender;
        this.nationality = nationality;
        this.card = new Creditcard(cardNumber, expDate, CVC, type, cardName);
    }

    public String getID() {
        return this.guestID;
    }

    public void setGuestID(String guestID) {
        this.guestID = guestID;
    }

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public Creditcard getCard() {
        return card;
    }

    public void setCard(Creditcard card) {
        this.card = card;
    }
    
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        String newLine = System.getProperty("line.separator");

        //result.append(this.getClass().getName() + newLine);

        result.append("Guest ID: " + this.guestID + newLine);
        result.append("Guest Name: " + this.guestName + newLine);
        result.append("Address: " + this.address + newLine);
        result.append("Contact: " + this.contact + newLine);
        result.append("Country: " + this.country + newLine);
        result.append("Gender: " + this.gender + newLine);
        result.append("Nationality: " + this.nationality + newLine);
        result.append("Credit Card Details " + newLine + this.card + newLine);

        return result.toString();
    }

}
