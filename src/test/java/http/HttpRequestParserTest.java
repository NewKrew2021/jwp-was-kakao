package http;

import annotation.web.RequestMethod;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class HttpRequestParserTest {
    private HttpRequestParser parser = new HttpRequestParser("GET /user/create?userId=id&password=pw&name=jyp&email=jyp@email.com HTTP/1.1\n" +
            "Host: localhost:8080\n" +
            "Connection: keep-alive\n" +
            "Accept: */*\n" +
            "\n" +
            "userId=id&password=pw&name=jyp&email=jyp@email.com");

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

        assertThat(uri).isEqualTo("/user/create");
    }

    @DisplayName("올바른 params를 반환하는지 테스트")
    @ParameterizedTest
    @CsvSource({"userId,id", "password,pw", "name,jyp", "email,jyp@email.com"})
    void getParams(String key, String value) {
        Map<String, String> params = parser.getParams();

        assertThat(params.get(key)).isEqualTo(value);
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
    @ParameterizedTest
    @CsvSource({"userId,id", "password,pw", "name,jyp", "email,jyp@email.com"})
    void getBody(String key, String value) {
        Map<String, String> body = parser.getBody();

        assertThat(body.get(key)).isEqualTo(value);
    }
}