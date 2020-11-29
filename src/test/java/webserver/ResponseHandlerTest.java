package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

public class ResponseHandlerTest {

    @DisplayName("URI to ViewPath 테스트")
    @ParameterizedTest
    @CsvSource(value = {
            "    /index.html | ./templates/index.html",
            "/css/styles.css | ./static/css/styles.css",
            " /js/scripts.js | ./static/js/scripts.js"
    }, delimiter = '|')
    void getViewPath(String request, String expectedPath) {
        String path = ResponseHandler.getViewPath(request);
        assertThat(path).isEqualTo(expectedPath);
    }
}
