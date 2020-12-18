package utils;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class FileIoUtilsTest {
    private static final Logger log = LoggerFactory.getLogger(FileIoUtilsTest.class);

    @Test
    void loadFileFromClasspath() throws Exception {
        byte[] body = FileUtils.loadFileFromClasspath("./templates/index.html");
        log.debug("file : {}", new String(body));
    }

    @Test
    public void guessContentType() {
        assertThat(FileUtils.guessContentType("./templates/index.html"))
                .isEqualTo("text/html; charset=utf-8");
    }

}
