package Controller;
import java.util.*;

import Database.SerializeDB;
import entities.Creditcard;

public class CreditcardController
{
	private List<Creditcard> creditList;
	
    //Class Constructor
	private CreditcardController()
	{
        this.creditList = new ArrayList<Creditcard>();
	}

    //Load Creditcard List from .dat file
    public boolean load()
    {
        List list;
        try
        {
            //read from serialized file 
            list = (ArrayList)SerializeDB.readSerializedObject("Creditcards.dat");
            for (int i = 0; i<list.size(); i++)
            {
                this.creditList.add((Creditcard)list.get(i));
            }
            return true;
        }
        catch (Exception e)
        {
            System.out.println("Exception >> " + e.getMessage());
            return false;
        }
    }

    //Get Creditcard list
    public List<Creditcard> allCreditcards()
    {
        return this.creditList;
    }

    //Add credit card into CreditcardDB
    public void createCreditcard(String guestID)
    {
        String creditcardNum = "";
        String creditcardType = "";
        int creditcardExp = 0;
        int creditcardCVV = 0;
        int choice = 0;
        Scanner sc = new Scanner(System.in);
        Creditcard credit;

        while (choice != 5)
        {
            System.out.println("guestID: " + guestID);
            System.out.println("Credit Card Number: " + creditcardNum);
            System.out.println("Credit Card Type: " + creditcardType);
            System.out.println("Credit Card Expiry: " + creditcardExp);
            System.out.println("Credit Card CVV: " + creditcardCVV);
            System.out.println("-------------------------------------------");
            System.out.println("1. Update Credit Card Number");
            System.out.println("2. Update Credit Card Type");
            System.out.println("3. Update Credit Card Expiry(MMYY)");
            System.out.println("4. Update Credit Card CVV");
            System.out.println("5. Confirm");

            switch (choice)
            {
                //Get Updated Card Number
                case 1:
                    System.out.println("Credit Card Number: ");
                    creditcardNum = sc.nextLine();
                    break;
                
                //Get Updated Card Type
                case 2:
                    System.out.println("Credit Card Type: ");
                    System.out.println("1. Visa");
                    System.out.println("2. Mastercard");
                    System.out.println("3. Amex");
                    int cardChoice = sc.nextInt();
                    do
                    {
                        switch(cardChoice)
                        {
                            case 1:
                                creditcardType = "Visa";
                                break;
                            
                            case 2:
                                creditcardType = "Mastercard";
                                break;

                            case 3:
                                creditcardType = "Amex";
                                break;
                            
                            default:
                                System.out.println("Please enter a valid choice.");
                        }
                    } while (cardChoice != 1 && cardChoice != 2 && cardChoice !=3);

                //Get Updated Card Expiry
                case 3:
                    System.out.println("Credit Card Expiry(MMYY): ");
                    creditcardExp = sc.nextInt();
                    break;

                //Get Updated Card CVV
                case 4:
                    System.out.println("Credit Card CVV: ");
                    creditcardCVV = sc.nextInt();
                    break;
                
                //Confirm
                case 5:
                    if (creditcardNum != "" && creditcardType != "" && creditcardExp != 0 && creditcardCVV != 0)
                    {
                        credit = new Creditcard(creditcardNum, creditcardType, creditcardExp, creditcardCVV, guestID);
                        if (this.addCreditcard(credit))
                        {
                            System.out.println("Registration Successful.");
                        }
                        else
                        {
                            System.out.println("Credit Card Already Registered ");
                        }
                    }
                    else
                    {
                        System.out.println("Missing Information!");
                        choice = 0;
                    }
                    break;
                
                //Catch invalid choice
                default:
                    System.out.println("Invalid Choice.");
                    break;
            }
        }
        sc.close();
    }

    //Add Credit Card to Credit Card List
    //Check if student is in list before adding
    public boolean addCreditcard(Creditcard credit)
    {
        if (this.isRegistered(credit))
        {
            return false;
        }
        this.creditList.add(credit);
        SerializeDB.writeSerializedObject("Creditcards.dat", creditList);
        return true;
    }

    //Remove Student from Student List
    //Check if card is in list before removing
    public void removeCard(Creditcard credit)
    {
        if (this.isRegistered(credit))
        {
            this.creditList.remove(credit);
        }
        else 
        {
            System.out.println("Card does not exist.");
        }
    }

    //Check if card is registered based on guestID
    public boolean isRegistered(Creditcard credit)
    {
        return creditList.stream()
                .filter(c -> credit.getguestID().equals(c.getguestID()))
                .findFirst()
                .orElse(null)
                .equals(null);
    }

    //Get string of all creditcard
    public String toString()
    {
        String n = "";

        for (int i = 0; i<this.creditList.size(); i++)
        {
            System.out.format("%s %s %s %s %s", this.creditList.get(i).getguestID(), this.creditList.get(i).getcreditcardNum(), this.creditList.get(i).getcreditcardType(), this.creditList.get(i).getcreditcardExp(), this.creditList.get(i).getcreditcardCVV());
        }
        return n;
    }

}