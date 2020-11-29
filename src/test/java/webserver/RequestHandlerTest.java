package webserver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.net.Socket;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestHandlerTest {

    private RequestHandler requestHandler;

    @BeforeEach
    public void setup() {
        requestHandler = new RequestHandler(new Socket());
    }

    @ParameterizedTest
    @CsvSource(value = {
            "    /index.html | ./templates/index.html",
            "/css/styles.css | ./static/css/styles.css",
            " /js/scripts.js | ./static/js/scripts.js"
    }, delimiter = '|')
    void tt(String request, String expectedPath) {
        String path = requestHandler.getPath(request);
        assertThat(path).isEqualTo(expectedPath);
    }
}
