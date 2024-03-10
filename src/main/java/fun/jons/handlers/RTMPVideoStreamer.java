package fun.jons.handlers;


import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_videoio.VideoCapture;

public class RTMPVideoStreamer {
    private String rtmpURL;
    private VideoCapture capture;
    private FFmpegFrameRecorder recorder;
    private int deviceNR;
    private OpenCVFrameConverter.ToMat converter;

    // Setter alle perametere
    public RTMPVideoStreamer(String rtmpURL, int deviceNR) {
        this.rtmpURL = rtmpURL;
        this.deviceNR = deviceNR;
        this.converter = new OpenCVFrameConverter.ToMat();
    }

    // Starter streamen
    public void streamToRTMP() throws Exception {
        // Tar i bruk kameraet
        capture = new VideoCapture(deviceNR);
        // Sjekker om det var velykka og gir error dersom det ikke er det.
        if (!capture.isOpened()) {
            throw new RuntimeException("ERROR: Kunne ikke åpne webcam med index: " + deviceNR );
        }

        // Forskjellige innstillinger for streamen, må fikle litt her.
        recorder = new FFmpegFrameRecorder(rtmpURL, 780, 420);
        recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264);
        recorder.setFormat("flv");
        recorder.setFrameRate(5);
        recorder.setVideoBitrate(5000);
        recorder.start();

        Mat frame = new Mat();

        // Sender ut bildene
        while (true) {
            capture.read(frame);
            recorder.record(converter.convert(frame));
        }
    }

    // Brukes for å stoppe streamen
    public void stopStreaming() throws Exception{

        // Sjekker om kameraet er i bruk og gir slipp
        if (capture != null) {
            capture.release();
        }

        // Sjekker om den sender ut noe, og stopper
        if (recorder != null) {
            recorder.stop();
            recorder.release();
        }
    }
}
