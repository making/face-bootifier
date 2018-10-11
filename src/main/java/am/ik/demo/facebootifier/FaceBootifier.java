package am.ik.demo.facebootifier;

import org.bytedeco.javacpp.opencv_core;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Base64;
import java.util.function.Function;

import static org.bytedeco.javacpp.opencv_imgcodecs.imread;
import static org.bytedeco.javacpp.opencv_imgcodecs.imwrite;

public class FaceBootifier implements Function<String, byte[]> {
    @Override
    public byte[] apply(String s) {
        try {
            byte[] decode = Base64.getDecoder().decode(s);
            try (ByteArrayInputStream stream = new ByteArrayInputStream(decode)) {
                Path input = Files.createTempFile("input-", ".png");
                Path output = Files.createTempFile("output-", ".png");
                input.toFile().deleteOnExit();
                output.toFile().deleteOnExit();
                Files.copy(stream, input, StandardCopyOption.REPLACE_EXISTING);
                FaceDetector faceDetector = new FaceDetector();
                opencv_core.Mat source = imread(input.toAbsolutePath().toString());
                faceDetector.detectFaces(source, FaceTranslator::bootify);
                imwrite(output.toFile().getAbsolutePath(), source);
                byte[] translated = Base64.getEncoder()
                        .encode(Files.readAllBytes(output));
                Files.deleteIfExists(input);
                Files.deleteIfExists(output);
                return translated;
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
