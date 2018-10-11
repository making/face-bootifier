package am.ik.demo.facebootifier;

import org.bytedeco.javacpp.opencv_core;

import static org.bytedeco.javacpp.opencv_imgproc.*;

public class FaceTranslator {
    public static void bootify(opencv_core.Mat source, opencv_core.Rect r) {
        int x = r.x(), y = r.y(), w = r.width();
        int a = (int) (w / 2 * Math.cos(Math.PI / 3));
        int h = (int) (w * Math.sin(Math.PI / 3));

        opencv_core.Point points = new opencv_core.Point(6);
        int offset = (r.height() - h) / 2;
        points.position(0).x(x + a).y(y + offset);
        points.position(1).x(x + w - a).y(y + offset);
        points.position(2).x(x + w).y(y + h / 2 + offset);
        points.position(3).x(x + w - a).y(y + h + offset);
        points.position(4).x(x + a).y(y + h + offset);
        points.position(5).x(x).y(y + h / 2 + offset);

        fillConvexPoly(source, points.position(0), 6, opencv_core.Scalar.GREEN, CV_AA, 0);

        circle(source, new opencv_core.Point(x + w / 2, y + w / 2), w / 4,
                opencv_core.Scalar.WHITE, -1, CV_AA, 0);
        circle(source, new opencv_core.Point(x + w / 2, y + w / 2), w / 6,
                opencv_core.Scalar.GREEN, -1, CV_AA, 0);

        rectangle(source, new opencv_core.Point(x + w / 2 - w / 12, y + w / 5),
                new opencv_core.Point(x + w / 2 + w / 12, y + w / 2),
                opencv_core.Scalar.GREEN, -1, CV_AA, 0);
        rectangle(source, new opencv_core.Point(x + w / 2 - w / 20, y + w / 5),
                new opencv_core.Point(x + w / 2 + w / 20, y + w / 2),
                opencv_core.Scalar.WHITE, -1, CV_AA, 0);
    }
}
