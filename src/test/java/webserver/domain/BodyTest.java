package webserver.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class BodyTest {
    @ParameterizedTest
    @CsvSource(value = {"/index.html:text/html", "/js/jquery-2.2.0.min.js:text/javascript", "/fonts/glyphicons-halflings-regular.woff:font/woff",
    "/css/bootstrap.min.css:text/css", "/favicon.ico:image/png", "/images/80-text.png:image/png", "/fonts/glyphicons-halflings-regular.svg:image/svg+xml",
    "/fonts/glyphicons-halflings-regular.eot:font/eot", "/fonts/glyphicons-halflings-regular.ttf:font/ttf", "/fonts/glyphicons-halflings-regular.woff2:font/woff2"},
            delimiter = ':')
    @DisplayName("콘텐츠 타입 테스트")
    void createTest(String input, String expected) {
        Body body = new Body(input);

        assertThat(body.getContentType()).isEqualTo(expected);
    }
}