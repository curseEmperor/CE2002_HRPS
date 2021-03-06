package entities;

import java.io.Serializable;

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
            String nationality) { // , CreditCard card
        this.guestID = guestID;
        this.guestName = guestName;
        this.address = address;
        this.contact = contact;
        this.country = country;
        this.gender = gender;
        this.nationality = nationality;
        this.card = null;
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

        result.append(this.getClass().getName() + newLine);

        result.append("guestID: " + this.guestID + newLine);
        result.append("guestName: " + this.guestName + newLine);
        result.append("address: " + this.address + newLine);
        result.append("contact: " + this.contact + newLine);
        result.append("country: " + this.country + newLine);
        result.append("gender: " + this.gender + newLine);
        result.append("nationality: " + this.nationality + newLine);

        return result.toString();
    }

}