package com;

import java.net.*;
import java.io.*;
public class BusServer
{
    public static void main(String[] args)
    {
        Bus mybus = new Bus();
        try
        {
            ServerSocket busserver= new ServerSocket(9999);
            System.out.println("Waiting for clients to connect.....");
            Socket s = busserver.accept();

            DataOutputStream dout= new DataOutputStream(s.getOutputStream());
            dout.writeUTF(sendConnectMessage());

            DataInputStream dis = new DataInputStream(s.getInputStream());
            String str= dis.readUTF();
            System.out.println("Message from client is : "+str);

            if(str.equals("1"))
            {
               dout.writeUTF(mybus.viewSeats());
            }
            else if(str.equals("2")) {
                System.out.println("Sending message to get seats to book.....");
                dout.writeUTF(mybus.getSeatsToBook());
                int x = Integer.parseInt(dis.readUTF());//reading the seats entered
                if (x <= mybus.getRemSeats() && x>0) {
                    dout.writeUTF(mybus.takeConfirmation(x));
                    String cnf = dis.readUTF();
                    if (cnf.equalsIgnoreCase("Y")) {
                        dout.writeUTF("Booking Confirmed for " + s + " seats @ " + x * mybus.getSeatCost());
                    }
                }
                else
                {
                    if(x<=0)
                        System.out.println("Try again and enter a Valid response!!!");
                    System.out.println("Not enough Seats!!!");
                    dout.writeUTF("-1");
                }
            }
            busserver.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String sendConnectMessage()
    {
        return "\n\nConnected!!!!\nHere are the following options : " +
                "\n\n1. Check Availability" +
                "\n\n2. Book Seats " +
                "\n\nSelect the appropriate key..........\n\nYOUR RESPONSE : ";
    }
}
