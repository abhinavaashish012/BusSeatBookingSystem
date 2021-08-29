package com;

import java.net.*;
import java.io.*;
public class BusServer
{
    static int count=0;
    public static void main(String[] args)
    {
        ServerSocket server = null;
        Bus mybus = new Bus();
        try {
            server = new ServerSocket(9999);  // server is listening on port 9999
            server.setReuseAddress(true);
            System.out.println("Waiting for Clients to connect........");
            // running infinite loop to obtain client request
            while (true) {
                Socket client = server.accept();// socket object to receive incoming client requests
                System.out.println("New client connected"+ client.getInetAddress().getHostAddress());  // Displaying that new client is connected to server

                // create a new thread object
                ClientHandler clientSock = new ClientHandler(client,mybus);
                new Thread(clientSock).start(); // Thread will handle the client separately
                count++;
                System.out.println("Number of clients connected : "+ count);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (server != null) {
                try {
                    server.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }























//        Bus mybus = new Bus();
//        try
//        {
//            ServerSocket busserver= new ServerSocket(9999);
//            System.out.println("Waiting for clients to connect.....");
//            Socket s = busserver.accept();
//            System.out.println("Client Connected. "+s.getInetAddress().getHostAddress());
//            DataOutputStream dout= new DataOutputStream(s.getOutputStream());
//            dout.writeUTF(sendConnectMessage());
//
//            DataInputStream dis = new DataInputStream(s.getInputStream());
//            String str= dis.readUTF();
//            System.out.println("Message from client is : "+str);
//
//            if(str.equals("1"))
//            {
//               dout.writeUTF(mybus.viewSeats());
//            }
//            else if(str.equals("2")) {
//                System.out.println("Sending message to get seats to book.....");
//                dout.writeUTF(mybus.getSeatsToBook());
//                int x = Integer.parseInt(dis.readUTF());//reading the seats entered
//                if (x <= mybus.getRemSeats() && x>0) {
//                    dout.writeUTF(mybus.takeConfirmation(x));
//                    String cnf = dis.readUTF();
//                    if (cnf.equalsIgnoreCase("Y")) {
//                        dout.writeUTF("Booking Confirmed for " + s + " seats @ " + x * mybus.getSeatCost());
//                        mybus.setSeats(x);
//                        System.out.println(mybus.viewSeats());
//                        dout.writeUTF(mybus.viewSeats());
//                    }
//                }
//                else
//                {
//                    if(x<=0)
//                        System.out.println("Try again and enter a Valid response!!!");
//                    System.out.println("Not enough Seats!!!");
//                    dout.writeUTF("-1");
//                }
//            }
//            busserver.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    public static String sendConnectMessage()
    {
        return "\n\nConnected!!!!\nHere are the following options : " +
                "\n\n1. Check Availability" +
                "\n\n2. Book Seats " +
                "\n\nSelect the appropriate key..........\n\nYOUR RESPONSE : ";
    }
}
