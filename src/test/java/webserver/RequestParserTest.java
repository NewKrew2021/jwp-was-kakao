package webserver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.StringReader;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestParserTest {

    private BufferedReader bufferedReader;

    @Nested
    @DisplayName("페이지를 로드한다")
    class PageLoad {
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
    }
}
