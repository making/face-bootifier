package am.ik.demo.facebootifier;

import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_objdetect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.net.URL;
import java.util.function.BiConsumer;

import static org.bytedeco.javacpp.opencv_objdetect.CV_HAAR_DO_ROUGH_SEARCH;
import static org.bytedeco.javacpp.opencv_objdetect.CV_HAAR_FIND_BIGGEST_OBJECT;

public class FaceDetector {
    private static final Logger log = LoggerFactory.getLogger(FaceDetector.class);
    private final static File classifierFile;
    private final opencv_objdetect.CascadeClassifier classifier;

    static {
        try {
            URL url = new ClassPathResource("haarcascade_frontalface_alt.xml").getURL();
            classifierFile = Loader.extractResource(url, null, "classifier", ".xml");
            classifierFile.deleteOnExit();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public FaceDetector() {
        this.classifier = new opencv_objdetect.CascadeClassifier(
                classifierFile.getAbsolutePath());
    }

    public void detectFaces(opencv_core.Mat source,
                            BiConsumer<opencv_core.Mat, opencv_core.Rect> detectAction) {
        opencv_core.RectVector faces = new opencv_core.RectVector();
        classifier.detectMultiScale(source, faces, 1.1, 3,
                CV_HAAR_FIND_BIGGEST_OBJECT | CV_HAAR_DO_ROUGH_SEARCH, null, null);
        long numOfFaces = faces.size();
        log.info("Detect {} faces", numOfFaces);
        for (int i = 0; i < numOfFaces; i++) {
            opencv_core.Rect rect = faces.get(i);
            detectAction.accept(source, rect);
        }
    }
}
