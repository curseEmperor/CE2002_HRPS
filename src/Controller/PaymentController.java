package Controller;

import java.util.*;


import entities.Creditcard;
import entities.Guest;
import entities.Item;
import entities.Order;
import entities.Payment;

import Database.SerializeDB;

public class PaymentController
{
    private ArrayList<Payment> PaymentList;
    //Class constructor
    private PaymentController()
    {
        PaymentList = new ArrayList<>();
    }

    //Load Payment List from .dat file
    public boolean load()
    {
        List list;
        try
        {
            list = (ArrayList)SerializeDB.readSerializedObject("Payment.dat");
            for (int i = 0; i<list.size(); i++)
            {
                this.PaymentList.add((Payment)list.get(i));
            }
            return true;
        }
        catch (Exception e)
        {
            System.out.println("Exception >> " + e.getMessage());
            return false;
        }
    }

    //Get Payment list
    public List<Payment> allPayments()
    {
        return this.PaymentList;
    }

    //Add payment into PaymentDB
    public void addPayment(Payment payment)
    {
        this.PaymentList.add(payment);   
    }


    //Get paymentID by roomID
    public int retrievePaymentID(int roomID)
    {
        for (Payment payment:PaymentList)
        {
            if (payment.getroomID() == roomID)
            {
                return payment.getPaymentID();
            }
        }
        return 0;
    }

    public void printPayment(Payment payment)
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("--------------------------------------------------------------------------");
        System.out.format("RoomID: %d", payment.getroomID());
        System.out.format("PaymentID: %d", payment.getPaymentID());
        System.out.format("Date: %d", payment.getDate());
        System.out.println("--------------------------------------------------------------------------");
        payment.printOrderList();
        payment.printInvoice();
        System.out.println("Make payment? (Y/N): ");
        String choice = sc.nextLine();
        if (choice == "Y")
        {
            System.out.println("Retriving Credit card details...");
            System.out.println("Making payment...");
            System.out.println("Payment done!");
            payment.setPaymentStatus(true);
        }
        sc.close();
    }



}
