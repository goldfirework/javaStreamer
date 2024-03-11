package fun.jons;

import fun.jons.handlers.RTMPVideoStreamer;
import fun.jons.handlers.webCamCapture;

public class Main {

    // Første argumentet må være device nummeret :)
    // Andre må være adressen
    // Tredje er porten
    public static void main(String[] args) {



        Thread rtmp = new Thread(() -> {

            System.out.println("Thread rtmp started.");

            try {
                RTMPVideoStreamer streamer = new RTMPVideoStreamer("rtmp://172.21.224.1:1935/live/obs", Integer.parseInt(args[0]));
                streamer.streamToRTMP();
            }catch (Exception e) {
                System.out.println("Fikk feil i å streame ut gitt.");
                System.out.println(e.toString());
            }
        });

        Thread reciever = new Thread(() -> {

            System.out.println("Thread reciever started.");
            new fun.jons.sockets.reciever().ServerSocket(Integer.parseInt(args[1]));
        });

        reciever.start();
        rtmp.start();


        //System.out.println("Hello world! " + args[0]);

        // Starter videocapture
        //new webCamCapture().captureVideo(Integer.parseInt(args[0]), args[1], Integer.parseInt(args[2]));

    }
}