package http;

import annotation.web.RequestMethod;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class HttpRequestParserTest {
    private HttpRequestParser parser;

    @BeforeEach
    void setUp() {
        parser = new HttpRequestParser();
        parser.parse("GET /user/create?userId=id&password=pw&name=jyp&email=jyp@email.com HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Accept: */*");
    }

    @DisplayName("올바른 RequestMethod를 반환하는지 테스트")
    @Test
    void getRequestMethod() {
        RequestMethod method = parser.getRequestMethod();

        assertThat(method).isEqualTo(RequestMethod.GET);
    }

    @DisplayName("올바른 uri를 반환하는지 테스트")
    @Test
    void getUri() {
        String uri = parser.getUri();

        assertThat(uri).isEqualTo("/user/create?userId=id&password=pw&name=jyp&email=jyp@email.com");
    }

    @DisplayName("올바른 RequestHeaders를 반환하는지 테스트")
    @Test
    void getRequestHeaders() {
        HttpRequestHeaders headers = parser.getRequestHeaders();

        assertThat(headers.getHeader("Host")).isEqualTo("localhost:8080");
        assertThat(headers.getHeader("Connection")).isEqualTo("keep-alive");
        assertThat(headers.getHeader("Accept")).isEqualTo("*/*");
    }

    @DisplayName("올바른 body를 반환하는지 테스트")
    @Test
    void getBody() {
        parser.parse("POST /user/create HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Content-Length: 59\n" +
                "Content-Type: application/x-www-form-urlencoded\n" +
                "Accept: */*\n" +
                "\n" +
                "userId=id&password=pw&name=jyp&email=jyp@email.com");
        String body = parser.getBody();

        assertThat(body).isEqualTo("userId=id&password=pw&name=jyp&email=jyp@email.com");
    }
}