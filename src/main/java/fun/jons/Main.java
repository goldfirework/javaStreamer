package fun.jons;

import fun.jons.handlers.webCamCapture;

public class Main {

    // Første argumentet må være device nummeret :)
    public static void main(String[] args) {
        //System.out.println("Hello world! " + args[0]);

        // Starter videocapture
        new webCamCapture().captureVideo(Integer.parseInt(args[0]));
    }
}