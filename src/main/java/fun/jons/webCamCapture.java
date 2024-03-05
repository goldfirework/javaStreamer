package fun.jons;

import org.bytedeco.javacv.*;
import org.bytedeco.opencv.global.opencv_imgcodecs;
import org.bytedeco.opencv.opencv_core.IplImage;

public class webCamCapture {
    public void captureVideo() {

        try {
            // Tar i bruk kameraet
            FrameGrabber grabber = new OpenCVFrameGrabber(0);
            grabber.start();

            // Tar et bildet fra kameraet
            Frame frame = grabber.grab();
            OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();
            IplImage img = converter.convert(frame);

            // Lagrer bildet
            // OBS! Vil jeg lagre mer enn bare første bilde som ble tatt må navn endres for vært.
            opencv_imgcodecs.cvSaveImage("bilde.jpg", img);

            // Åpner ett vindu for visning av kameraet
            CanvasFrame canvas = new CanvasFrame("Web Cam");

            while (true) {
                // Tar nytt bilde
                frame = grabber.grab();

                // Viser siste bildet
                canvas.showImage(frame);
            }


        } catch (FrameGrabber.Exception e) {
            System.out.println("Error: " + e.toString());
        }


    }
}
