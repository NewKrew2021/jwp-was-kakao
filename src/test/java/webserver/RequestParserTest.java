package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.StringReader;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestParserTest {
    @Test
    void instantiation() {
        //@formatter:off
        BufferedReader bufferedReader = new BufferedReader(new StringReader(
                "GET /index.html HTTP/1.1\n"
                + "Host: localhost:8080\n"
                + "Connection: keep-alive\n"
                + "Accept: */*"));
        //@formatter:on
        new RequestParser(bufferedReader);
    }

    @DisplayName("HttpRequest 를 생성한다")
    @Test
    void createHttpRequest() {
        //@formatter:off
        BufferedReader bufferedReader = new BufferedReader(new StringReader(
                "GET /index.html HTTP/1.1\n"
                        + "Host: localhost:8080\n"
                        + "Connection: keep-alive\n"
                        + "Accept: */*"));
        //@formatter:on
        RequestParser requestParser = new RequestParser(bufferedReader);
        HttpRequest httpRequest = requestParser.parse();
        assertThat(httpRequest).isNotNull();
    }

    private static class RequestParser {
        public RequestParser(BufferedReader bufferedReader) {
        }
    }
}
