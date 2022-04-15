package entities;

import java.io.Serializable;

import Enums.CreditcardType;

public class Guest implements Serializable {

    private String guestID;
    private String guestName;
    private String address;
    private String contact;
    private String country;
    private char gender;
    private String nationality;
    private CreditcardType card;

    public Guest(String guestID, String guestName, String address, String contact, String country, char gender,
            String nationality, CreditcardType card) {
        this.guestID = guestID;
        this.guestName = guestName;
        this.address = address;
        this.contact = contact;
        this.country = country;
        this.gender = gender;
        this.nationality = nationality;
        this.card = card;
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

    public CreditcardType getCard() {
        return card;
    }

    public void setCard(CreditcardType card) {
        this.card = card;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        String newLine = System.getProperty("line.separator");

        result.append(this.getClass().getName() + newLine);

        result.append("GuestID: " + this.guestID + newLine);
        result.append("GuestName: " + this.guestName + newLine);
        result.append("Address: " + this.address + newLine);
        result.append("Contact: " + this.contact + newLine);
        result.append("Country: " + this.country + newLine);
        result.append("Gender: " + this.gender + newLine);
        result.append("Nationality: " + this.nationality + newLine);
        result.append("Creditcard Type: " + this.card + newLine);

        return result.toString();
    }

}
