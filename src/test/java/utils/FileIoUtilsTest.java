package utils;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

public class FileIoUtilsTest {
    private static final Logger log = LoggerFactory.getLogger(FileIoUtilsTest.class);

    @Test
    void byteArrayAndString() {
        String body = FileIoUtils.loadFileFromClasspath("./templates/index.html");
        String body2 = FileIoUtils.loadFileFromClasspath("./templates/index.html", StandardCharsets.UTF_8);

        assertThat(body.getBytes(StandardCharsets.UTF_8)).isEqualTo(body2.getBytes(StandardCharsets.UTF_8));
    }

    @Test
    void byteArrayAndString2() {
        String body = FileIoUtils.loadFileFromClasspath("./static/fonts/glyphicons-halflings-regular.ttf", StandardCharsets.ISO_8859_1);
        assertThat(body.getBytes()).isNotEqualTo(body.getBytes(StandardCharsets.ISO_8859_1));
    }
}
