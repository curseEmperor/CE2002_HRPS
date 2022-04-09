package entities;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.io.Serializable;


public class Payment implements Serializable
{
    private int PaymentID;
    private int roomID;
    private ArrayList<Order> OrderList; //store all orderIDs for a roomID, should be from room
    private boolean paymentStatus; //paid or unpaid (true = paid, false = unpaid)
    private String date;
    private double Total = 0;
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");


    //Class Constructor
    public Payment(int PaymentID, int roomID)
    {
        this.PaymentID = PaymentID;
        this.OrderList = new ArrayList<Order>();  //create empty order list
        this.paymentStatus = false;
        this.roomID = roomID;
        Calendar c = Calendar.getInstance();
        String d = sdf.format(c.getTime());
        this.date = d;
        PaymentID ++;
    }

    ////////////////////////Setters and Getters///////////////////////////////
    public int getPaymentID()
    {
        return PaymentID;
    }

    public void setPaymentID(int PaymentID)
    {
        this.PaymentID = PaymentID;
    }

    public boolean getpaymentStatus()
    {
        return paymentStatus;
    }

    public void setPaymentStatus(boolean paymentStatus)
    {
        this.paymentStatus = paymentStatus;
    }

    public int getroomID()
    {
        return roomID;
    }

    public void setroomID(int roomID)
    {
        this.roomID = roomID;
    }

    public double getTotal()
    {
        return Total;
    }

    public void setTotal(double Total)
    {
        this.Total = Total;
    }

    public String getDate()
    {
        return this.date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public ArrayList<Order> getOrderList()
    {
        return this.OrderList;
    }  

    public double calcSubTotal()
    {
        double SubTotal = 0;
        if (getOrderList() != null)
        {
            for (int i=0; i< this.OrderList.size(); i++)
            {
                SubTotal += this.OrderList.get(i).calcOrderPrice();
            }
        }
        return SubTotal;
    }

    public double calcTotal()
    {
        double SubTotal = calcSubTotal();
        Total = SubTotal * 1.07 * 1.10;
        setTotal(Total);
        return Total;
    }

    public void printInvoice()
    {
        double SubTotal = calcSubTotal();
        double Total, GST, SvcCharge;
        Total = calcTotal();
        GST = SubTotal * 0.07;
        SvcCharge = SubTotal * 0.07 * 0.10;
        System.out.format("Subtotal: %f", SubTotal);
        System.out.format("GST: %f", GST);
        System.out.format("Service Charge: %f", SvcCharge);
        System.out.println("=================================================================================");
        System.out.format("Total: %f", Total);
    }

    public void printOrderList()
    {
        for (int i = 0; i<this.getOrderList().size(); i++)
        {
            this.OrderList.get(i).viewOrder();
        }
    }

    public void addOrder(Order order)
    {
        this.OrderList.add(order);
    }    
}
