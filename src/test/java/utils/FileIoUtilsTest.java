package utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;

public class FileIoUtilsTest {
    private static final Logger log = LoggerFactory.getLogger(FileIoUtilsTest.class);

    @Test
    void loadFileFromClasspath() throws Exception {
        byte[] body = FileIoUtils.loadFileFromClasspath("./templates/index.html");
        log.debug("file : {}", new String(body));
    }

    @ParameterizedTest
    @CsvSource({"html,template", "ico,template", "css,static",
            "js,static", "png,static", "ttf,static", "woff,static", "svg,static"})
    void getDirectory(String extension, String directory) {
        assertThat(FileIoUtils.getDirectoryPath(extension)).isEqualTo(directory);
    }
}
