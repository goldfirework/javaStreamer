package fun.jons.sockets;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class reciever {

    public void ServerSocket(int port) {
        try {
            // Oppretter tilkoblingen
            ServerSocket ss = new ServerSocket(port);

            System.out.println("Listening on port: " + port);


            while (true) {
                Socket socket = ss.accept(); // Etablerer tilkoblingen

                // Lagrer dataen som blir sendt.
                DataInputStream inputStream = new DataInputStream(socket.getInputStream());

                String message = inputStream.readUTF();

                System.out.println(port + " recieved: " + message);

            }

            // Slutter å lytte etter data.
            //ss.close();

        } catch (Exception e ) { // Errorhåntering
            System.out.println("Error i etableringen av Socket: " + e.toString());
        }
    }
}
