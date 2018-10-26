package Controller;

import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.objdetect.CascadeClassifier;

class FaceDetector {
    Mat matrix;
    CascadeClassifier classifier;

    FaceDetector() {
        classifier = new CascadeClassifier("src/lbpcascade_frontalface.xml");
    }

    public MatOfRect detectFace(Mat matrix) {
        MatOfRect faces = new MatOfRect();
        this.matrix = matrix;
        classifier.detectMultiScale(matrix, faces);
        return faces;
    }
}