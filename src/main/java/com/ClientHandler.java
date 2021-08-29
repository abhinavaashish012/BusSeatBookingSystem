package com;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.*;

import static com.BusServer.sendConnectMessage;

public class ClientHandler implements Runnable{
    private final Socket s;
    Bus mybus;
    public ClientHandler(Socket socket, Bus b)
    {
        this.s = socket;
        this.mybus=b;
    }
    @Override
    public synchronized void run()
    {
        try
        {
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
                        mybus.setSeats(x);
                        System.out.println(mybus.viewSeats());
                        dout.writeUTF(mybus.viewSeats());
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
            //busserver.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
