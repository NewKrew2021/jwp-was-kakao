package webserver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestParserTest {

    private BufferedReader bufferedReader;

    @BeforeEach
    void setUp() {
        //@formatter:off
        bufferedReader = new BufferedReader(new StringReader(
                "GET /index.html HTTP/1.1\n"
                + "Host: localhost:8080\n"
                + "Connection: keep-alive\n"
                + "Accept: */*"));
        //@formatter:on
    }

    @DisplayName("HttpRequest 를 생성한다")
    @Test
    void createHttpRequest() {
        assertThat(new RequestParser(bufferedReader).parse()).isNotNull();
    }

    @DisplayName("메소드를 파싱한다")
    @Test
    void parseMethod() {
        HttpRequest httpRequest = new RequestParser(bufferedReader).parse();
        assertThat(httpRequest.getMethod()).isEqualTo("GET");
    }

    @DisplayName("RequestURI 를 파싱한다")
    @Test
    void parseRequestURI() {
        HttpRequest httpRequest = new RequestParser(bufferedReader).parse();
        assertThat(httpRequest.getRequestURI()).isEqualTo("/index.html");
    }

    @DisplayName("프로토콜을 를 파싱한다")
    @Test
    void parseProtocol() {
        HttpRequest httpRequest = new RequestParser(bufferedReader).parse();
        assertThat(httpRequest.getProtocol()).isEqualTo("HTTP/1.1");
    }

    private static class RequestParser {
        private final BufferedReader bufferedReader;

        public RequestParser(BufferedReader bufferedReader) {
            this.bufferedReader = bufferedReader;
        }

        public HttpRequest parse() {
            return parseRequestLine();
        }

        private HttpRequest parseRequestLine() {
            String requestLine = nextLine();
            String[] requestLineSplit = requestLine.split(" ");
            return new HttpRequest(requestLineSplit[0], requestLineSplit[1], requestLineSplit[2]);
        }

        private String nextLine() {
            try {
                return bufferedReader.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static class HttpRequest {
        private final String method;
        private final String requestURI;
        private final String protocol;

        public HttpRequest(String method, String requestURI, String protocol) {
            this.method = method;
            this.requestURI = requestURI;
            this.protocol = protocol;
        }

        public String getMethod() {
            return method;
        }

        public String getRequestURI() {
            return requestURI;
        }

        public String getProtocol() {
            return protocol;
        }
    }
}
