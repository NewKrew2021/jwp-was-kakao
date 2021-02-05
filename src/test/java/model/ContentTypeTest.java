package model;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import utils.FileIoUtils;

import static org.assertj.core.api.Assertions.assertThat;

class ContentTypeTest {
    /**
     *  HTML("html", "text/html"),
     *     JS("js", "application/javascript"),
     *     CSS("css", "text/css"),
     *     ICO("ico", "image/png"),
     *     PNG("png", "image/png"),
     *     JPG("jpg", "image/jpg"),
     *     JPEG("jpeg", "image/jpeg"),
     *     SVG("svg", "image/svg+xml"),
     *     EOT("eot", "font/eot"),
     *     TTF("ttf", "font/ttf"),
     *     WOFF("woff", "font/woff"),
     *     WOFF2("woff2", "font/woff2");
     */

    @ParameterizedTest
    @CsvSource({"HTML,text/html", "JS,application/javascript", "CSS,text/css",
            "ICO,image/png", "PNG,image/png", "EOT,font/eot", "WOFF,font/woff", "SVG,image/svg+xml"})
    void getDirectory(String extension, String mimetype) {
        assertThat(ContentType.valueOf(extension).getMimetype()).isEqualTo(mimetype);
    }
}