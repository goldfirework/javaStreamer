package fun.jons.handlers;

import org.apache.commons.io.FileUtils;
import org.bytedeco.javacv.*;
import org.bytedeco.opencv.global.opencv_imgcodecs;
import org.bytedeco.opencv.opencv_core.IplImage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Base64;

public class webCamCapture {
    public void captureVideo(int device, String adresse, int port) {

        try {
            // Tar i bruk kameraet
            FrameGrabber grabber = new OpenCVFrameGrabber(device);
            grabber.start();

            // Tar et bildet fra kameraet
            Frame frame = grabber.grab();
            OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();
            IplImage img = converter.convert(frame);

            // Lagrer bildet
            // OBS! Vil jeg lagre mer enn bare første bilde som ble tatt må navn endres for vært.
            opencv_imgcodecs.cvSaveImage("bilde.jpg", img);

            // Endre til true dersom du ønsker at programmet skal åpne et vindu for preview av kamera.
            boolean enableCamPreview = false;

            // Åpner ett vindu for visning av kameraet dersom enableCamPreview er sann
            CanvasFrame canvas = null;
            if (enableCamPreview) {
                canvas = new CanvasFrame("Cam preview");
            }

            while (true) {
                // Tar nytt bilde
                frame = grabber.grab();

                // Lagrer siste bildet
                IplImage img2 = converter.convert(frame);
                opencv_imgcodecs.cvSaveImage("bilde.jpg", img2);

                try {

                    // Gjør bildet om til bytes.
                    byte[] fileContent = FileUtils.readFileToByteArray(new File("bilde.jpg"));

                    // Sender bytes ut til serveren
                    new fun.jons.sockets.streamer().streamData(adresse, port, fileContent);

                } catch (Exception e) {
                    System.out.println("Error i konverteringen til Base64: \n" + e.toString());
                }

                // Viser siste bildet
                if (enableCamPreview) {
                    canvas.showImage(frame);
                }
            }


        } catch (FrameGrabber.Exception e) {
            System.out.println("Error: " + e.toString());
        }


    }
}
