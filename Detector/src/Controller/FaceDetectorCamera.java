package Controller;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.WritableImage;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.util.Date;

public class FaceDetectorCamera {
    VideoCapture cam;
    FaceDetector faceDetector;
    Mat matrix;

    public FaceDetectorCamera() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        cam = new VideoCapture(0);
        faceDetector = new FaceDetector();
    }

    public WritableImage captureFaceDetectedFrame() {
        WritableImage image = null;
        matrix = new Mat();
        MatOfRect faces = new MatOfRect();
        if (!cam.isOpened()) System.out.println("Camera Is Not Working");
        cam.read(matrix);//read frame
        faces = faceDetector.detectFace(matrix);
        image = addFaceRects(matrix, faces);
        return image;
    }

    private WritableImage addFaceRects(Mat matrix, MatOfRect faces) {
        WritableImage image;
        for (Rect r : faces.toArray()) {
            Imgproc.rectangle(matrix,
                    new Point(r.x, r.y),
                    new Point(r.x + r.width, r.y + r.height),
                    new Scalar(255, 255, 255));
        }
        BufferedImage i = new BufferedImage(matrix.width(), matrix.height(), BufferedImage.TYPE_3BYTE_BGR);
        WritableRaster raster = i.getRaster();
        DataBufferByte buf = (DataBufferByte) raster.getDataBuffer();
        byte[] data = buf.getData();
        matrix.get(0, 0, data);
        image = SwingFXUtils.toFXImage(i, null);
        return image;
    }

    public void close() {
        cam.release();
    }

    public void save() {
        Imgcodecs codec=new Imgcodecs();
        Date date = new Date();
        codec.imwrite(""+date.getTime(),matrix);
    }
}
