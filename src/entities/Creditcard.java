package entities;

import Enums.CreditcardTypes;

import java.util.Date;
import java.text.SimpleDateFormat;

public class Creditcard {
	private String cardID;
	private Date expiryDate;
	private int CVC;
	private CreditcardTypes cardType;
	
	public Creditcard() {
		
	}
	
	public String getCardID() {
		return cardID;
	}
	
	public void setCardID(String cardID) {
		this.cardID = cardID;
	}
	
	public Date getExpiryDate() {
		return expiryDate;
	}
	
	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}
	
	public int getCVC() {
		return CVC;
	}
	
	public void setCVC(int CVC) {
		this.CVC = CVC;
	}
	
	public CreditcardTypes getCreditcardType() {
		return cardType;
	}
	
	public void setCreditcardType(CreditcardTypes cardType) {
		this.cardType = cardType;
	}
	
	@Override
	public String toString() {
        StringBuilder result = new StringBuilder();
        String newLine = System.getProperty("line.separator");
        SimpleDateFormat formatter = new SimpleDateFormat("MM/yy");

        result.append(this.getClass().getName() + newLine);

        result.append("cardID: " + this.cardID + newLine);
        result.append("expiryDate: " + formatter.format(this.expiryDate) + newLine);
        result.append("CVC: " + this.CVC + newLine);
        result.append("Creditcard Type: " + this.cardType.toString() + newLine);
        
        return result.toString();
    }
}
