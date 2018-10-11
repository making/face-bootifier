package am.ik.demo.facebootifier;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FaceBootifierApplication {

    @Bean
    public FaceBootifier bootifier() {
        return new FaceBootifier();
    }

    public static void main(String[] args) {
        SpringApplication.run(FaceBootifierApplication.class, args);
    }
}
