package utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileIoUtilsTest {
    private static final Logger log = LoggerFactory.getLogger(FileIoUtilsTest.class);

    @DisplayName("templates/index.html를 가져온다")
    @Test
    void readFile1() throws Exception {
        byte[] body = FileIoUtils.readStaticHttpFile("/index.html");
        log.debug("file : {}", new String(body));
    }

    @DisplayName("static/css/styles.css를 가져온다")
    @Test
    void readFile2() throws Exception {
        byte[] body = FileIoUtils.readStaticHttpFile("/css/styles.css");
        log.debug("file : {}", new String(body));
    }

    @DisplayName("static/js/bootstrap.min.js를 가져온다")
    @Test
    void readFile3() throws Exception {
        byte[] body = FileIoUtils.readStaticHttpFile("/js/bootstrap.min.js");
        log.debug("file : {}", new String(body));
    }
}
