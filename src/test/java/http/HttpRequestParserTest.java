package http;

import annotation.web.RequestMethod;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpRequestParserTest {
    String headGet = "GET /index.html HTTP/1.1\n" +
            "Host: localhost:8080\n" +
            "Connection: keep-alive\n" +
            "Accept: */*";

    String headPost = "POST /user/create HTTP/1.1\n" +
            "Host: localhost:8080\n" +
            "Connection: keep-alive\n" +
            "Content-Length: 59\n" +
            "Content-Type: application/x-www-form-urlencoded\n" +
            "Accept: */*\n" +
            "\n" +
            "userId=wonsik&password=1234&name=wonsik&email=wonsik@kakaocorp.com";

    @DisplayName("request line을 잘 파싱하는지 테스트")
    @Test
    void testParseRequestLine() {
        HttpRequestParser parser = new HttpRequestParser();
        parser.parse(headGet);

        assertThat(parser.getRequestMethod()).isEqualTo(RequestMethod.GET);
        assertThat(parser.getUri()).isEqualTo("/index.html");

        parser.parse(headPost);

        assertThat(parser.getRequestMethod()).isEqualTo(RequestMethod.POST);
        assertThat(parser.getUri()).isEqualTo("/user/create");
    }

    @DisplayName("header를 잘 파싱하는지 테스트")
    @Test
    void testParseHeaders() {
        HttpRequestParser parser = new HttpRequestParser();
        parser.parse(headGet);
        HttpRequestHeaders headers = parser.getRequestHeaders();

        assertThat(headers.getHeader("Host")).isEqualTo("localhost:8080");
        assertThat(headers.getHeader("Connection")).isEqualTo("keep-alive");
    }

    @DisplayName("body를 잘 파싱하는지 테스트")
    @Test
    void testParseBody() {
        HttpRequestParser parser = new HttpRequestParser();
        parser.parse(headPost);

        assertThat(parser.getBody()).isEqualTo("userId=wonsik&password=1234&name=wonsik&email=wonsik@kakaocorp.com");
    }
}