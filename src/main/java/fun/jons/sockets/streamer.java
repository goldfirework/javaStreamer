package fun.jons.sockets;

import java.io.*;
import java.net.*;

public class streamer {

    public void streamData(String adress, int port, byte[] data) {
        try{
            // Etablerer tilkobling
            Socket s=new Socket(adress,port);

            // Gjør klar for å sende data. Opretter datastrømmen
            DataOutputStream dout=new DataOutputStream(s.getOutputStream());

            // Skriver data som skal sendes
            //dout.writeUTF(data);
            dout.write(data);

            // Sender dataen
            dout.flush();

            // Lukker datastrømmen
            dout.close();

            // Lukker tilkoblingen
            s.close();
        }catch(Exception e) { //Errorhåndtering
            System.out.println(e);
        }
    }
}
