package webserver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.StringReader;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestParserTest {

    private BufferedReader bufferedReader;
    private RequestParser requestParser;

    @BeforeEach
    void setUp() {
        //@formatter:off
        bufferedReader = new BufferedReader(new StringReader(
                "GET /index.html HTTP/1.1\n"
                + "Host: localhost:8080\n"
                + "Connection: keep-alive\n"
                + "Accept: */*"));
        //@formatter:on
        requestParser = new RequestParser(bufferedReader);
    }

    @DisplayName("HttpRequest 를 생성한다")
    @Test
    void createHttpRequest() {
        HttpRequest httpRequest = requestParser.parse();
        assertThat(httpRequest).isNotNull();
    }

    private static class RequestParser {
        public RequestParser(BufferedReader bufferedReader) {
        }

        public HttpRequest parse() {
            return new HttpRequest();
        }
    }

    private static class HttpRequest {
    }
}
