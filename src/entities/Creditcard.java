package Entities;

import java.io.Serializable;

public class Creditcard implements Serializable
{
    private String creditcardNum;
    private String creditcardType;
    private int creditcardExp;
    private int creditcardCVV;
    private String guestID;

    //Class Constructor
    public Creditcard(String creditcardNum, String creditcardType, int creditcardExp, int creditcardCVV, String guestID)
    {
		this.creditcardNum = creditcardNum;
		this.creditcardType = creditcardType;
		this.creditcardExp = creditcardExp;
		this.creditcardCVV = creditcardCVV;
		this.guestID = guestID;
	}

//////////////////////////////////////////////////Getters & Setters///////////////////////////////////////////////////////////////
    public String getcreditcardNum()
    {
		return creditcardNum;
	}
	
	public void setcreditcardNum(String creditcardNum)
    {
		this.creditcardNum = creditcardNum;
	}

	public String getcreditcardType()
    {
		return creditcardType;
	}

	public void setcreditcardType(String creditcardType)
    {
		this.creditcardType = creditcardType;
	}

	public int getcreditcardExp()
    {
		return creditcardExp;
	}

	public void setcreditcardExp(int creditcardExp)
    {
		this.creditcardExp = creditcardExp;
	}

	public int getcreditcardCVV()
    {
		return creditcardCVV;
	}

	public void setcreditcardCVV(int creditcardCVV)
    {
		this.creditcardCVV = creditcardCVV;
	}
	
	public String getguestID()
    {
		return guestID;
	}

	public void setguestID(String guestID) {
		this.guestID = guestID;
	}
}