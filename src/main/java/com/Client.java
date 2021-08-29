package com;
import java.net.*;
import java.io.*;


public class Client
{
    //private String name;

    public static void main(String[] args) {

        try
        {
            Socket s= new Socket("localhost",9999);
            DataInputStream dis= new DataInputStream(s.getInputStream());
            String connectMsg=dis.readUTF();
            System.out.println(connectMsg);

            DataOutputStream dout = new DataOutputStream(s.getOutputStream());

            BufferedReader brclient=new BufferedReader(new InputStreamReader(System.in));

            String resp=brclient.readLine();
            System.out.println("Response sent = " +resp);
            if(resp.equals("1")) {
                System.out.println("Obtaining response.........");
                dout.writeUTF(resp);
                String rcvdResponse= dis.readUTF();
                System.out.println(rcvdResponse);
            }
            else {
                dout.writeUTF(resp);
                String msgForSeatsToBook =  dis.readUTF();
                System.out.println(msgForSeatsToBook);
                String busSeats=brclient.readLine();
                dout.writeUTF(busSeats);//entering the seats to book
                String cnfCheck= dis.readUTF();
                if(!cnfCheck.equalsIgnoreCase("-1")) {
                    System.out.println(cnfCheck);
                    String cnf = brclient.readLine();
                    dout.writeUTF(cnf);//sending confirmation
                    if (cnf.equalsIgnoreCase("y")) {
                        String finalmsg = dis.readUTF();
                        System.out.println(finalmsg);
                        System.out.println(dis.readUTF());
                    }
                }
                else
                    System.out.println("Not enough Seats!!!");
            }
//            System.out.println("Ending connection from the client side...");
//            dout.flush();
//            dout.close();
//            s.close();
        }
        catch (Exception e)
        {
            System.out.println("Error Message : "+e);
        }
    }
}
