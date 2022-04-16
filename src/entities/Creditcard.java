package entities;

import Enums.CreditcardTypes;

import java.util.Date;
import java.io.Serializable;
import java.text.SimpleDateFormat;

public class Creditcard extends Entities implements Serializable {
	private String cardNumber;
	private Date expiryDate;
	private int CVV;
	private CreditcardTypes cardType;
	private String registeredName;
	
	public Creditcard() {
		
	}
	
	public Creditcard(String cardNumber, Date expiryDate, int CVV, int type, String cardName) {
		this.cardNumber = cardNumber;
		this.expiryDate = expiryDate;
		this.CVV = CVV;
		switch (type) {
		case 1:
			cardType = CreditcardTypes.VISA;
			break;
		case 2:
			cardType = CreditcardTypes.MASTER;
			break;
		case 3:
			cardType = CreditcardTypes.AMEX;
			break;
		default:
			cardType = null;
			break;
		}
		this.registeredName = cardName;
	}
	
	public String getCardNumber() {
		return cardNumber;
	}
	
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	
	public Date getExpiryDate() {
		return expiryDate;
	}
	
	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}
	
	public int getCVV() {
		return CVV;
	}
	
	public void setCVV(int CVV) {
		this.CVV = CVV;
	}
	
	public CreditcardTypes getCreditcardType() {
		return cardType;
	}
	
	public void setCreditcardType(CreditcardTypes cardType) {
		this.cardType = cardType;
	}
	
	public String getRegisteredName() {
		return registeredName;
	}
	
	public void setRegisteredName(String name) {
		registeredName = name;
	}
	
	@Override
	public String toString() {
        StringBuilder result = new StringBuilder();
        String newLine = System.getProperty("line.separator");
        SimpleDateFormat formatter = new SimpleDateFormat("MM/yy");

        result.append(this.getClass().getName() + newLine);

        result.append("cardNumber: " + this.cardNumber + newLine);
        result.append("expiryDate: " + formatter.format(this.expiryDate) + newLine);
        result.append("CVV: " + String.format("%03d", this.CVV) + newLine);
        result.append("Creditcard Type: " + this.cardType.toString() + newLine);
        result.append("Registered Name: " + this.registeredName + newLine);
        
        return result.toString();
    }
}
