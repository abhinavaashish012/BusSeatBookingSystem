package com;
//import java.util.*;
public class Bus
{
    private final int seats=45;
    //private String bookingId;
    private final int seatCost=200;
    //Map<String,String> bookList= new HashMap<>();

    public synchronized String viewSeats()
    {
        return "Remaining Seats : " + this.seats;
    }

    public synchronized int getRemSeats()
    {
        return this.seats;
    }

    public synchronized int getSeatCost()
    {
        return this.seatCost;
    }

    public synchronized String getSeatsToBook()
    {
        return "Enter the number of seats to book : ";
    }

    public synchronized String takeConfirmation(int s)
    {
        return "Payable amount : "+ s*this.seatCost+"\n\nPress 'Y' to confirm : ";
    }
}
