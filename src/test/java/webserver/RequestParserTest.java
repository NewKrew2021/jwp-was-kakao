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

    @DisplayName("메소드를 파싱한다")
    @Test
    void parseMethod() {
        HttpRequest httpRequest = requestParser.parse();
        assertThat(httpRequest.getMethod()).isEqualTo("GET");
    }

    @DisplayName("RequestURI 를 파싱한다")
    @Test
    void parseRequestURI() {
        HttpRequest httpRequest = requestParser.parse();
        assertThat(httpRequest.getRequestURI()).isEqualTo("/index.html");
    }

    @DisplayName("프로토콜을 를 파싱한다")
    @Test
    void parseProtocol() {
        HttpRequest httpRequest = requestParser.parse();
        assertThat(httpRequest.getProtocol()).isEqualTo("HTTP/1.1");
    }

    private static class RequestParser {
        private final BufferedReader bufferedReader;

        public RequestParser(BufferedReader bufferedReader) {
            this.bufferedReader = bufferedReader;
        }

        public HttpRequest parse() {
            String requestLine = nextLine();
            String[] requestLineSplit = requestLine.split(" ");
            HttpRequest httpRequest = new HttpRequest();
            httpRequest.setMethod(requestLineSplit[0]);
            httpRequest.setRequestURI(requestLineSplit[1]);
            httpRequest.setProtocol(requestLineSplit[2]);
            return httpRequest;
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
        private String method;
        private String requestURI;
        private String protocol;

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }

        public String getRequestURI() {
            return requestURI;
        }

        public void setRequestURI(String requestURI) {
            this.requestURI = requestURI;
        }

        public String getProtocol() {
            return protocol;
        }

        public void setProtocol(String protocol) {
            this.protocol = protocol;
        }
    }
}
