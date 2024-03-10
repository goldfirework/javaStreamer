package fun.jons.handlers;


import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_videoio.VideoCapture;

public class RTMPVideoStreamer {
    private String rtmpURL;
    private FFmpegFrameGrabber grabber;
    private VideoCapture capture;
    private FFmpegFrameRecorder recorder;
    private int deviceNR;
    private OpenCVFrameConverter.ToMat converter;

    public RTMPVideoStreamer(String rtmpURL, int deviceNR) {
        this.rtmpURL = rtmpURL;
        this.deviceNR = deviceNR;
        this.converter = new OpenCVFrameConverter.ToMat();
    }

    public void streamToRTMP() throws Exception {
        capture = new VideoCapture(deviceNR);
        if (!capture.isOpened()) {
            throw new RuntimeException("ERROR: Kunne ikke åpne webcam med index: " + deviceNR );
        }

        recorder = new FFmpegFrameRecorder(rtmpURL, (int) capture.get(3), (int) capture.get(4));
        recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264);
        recorder.setFormat("mkv");
        recorder.setFrameRate(5);
        recorder.setVideoBitrate(5000);
        recorder.start();

        Mat frame = new Mat();
        while (true) {
            capture.read(frame);
            recorder.record(converter.convert(frame));
        }
    }

    public void stopStreaming() throws Exception{
        if (capture != null) {
            capture.release();
        }

        if (recorder != null) {
            recorder.stop();
            recorder.release();
        }
    }
}
