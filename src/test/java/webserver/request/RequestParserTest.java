package webserver.request;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import webserver.Cookie;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class RequestParserTest {
    @Test
    void getRequest() {
        String input = "GET /user/create?userId=javajigi&password=password&name=JaeSung HTTP/1.1 \n" +
                "Host: localhost:8080 \n" +
                "Connection: keep-alive \n" +
                "Accept: */*\n";

        HttpRequest request = RequestParser.fromLines(getLines(input));

        assertThat(request.getProtocol()).isEqualTo(Protocol.HTTP);
        assertThat(request.getMethod()).isEqualTo(HttpMethod.GET);
        assertThat(request.getPath()).isEqualTo("/user/create");
        assertThat(request.getHeader().getHost()).isEqualTo("localhost:8080");
        assertThat(request.getHeader().getHeader("Connection")).isEqualTo("keep-alive");
        assertThat(request.getHeader().getHeader("Accept")).isEqualTo("*/*");
        assertThat(request.getParameter("userId")).isEqualTo("javajigi");
        assertThat(request.getParameter("password")).isEqualTo("password");
        assertThat(request.getParameter("name")).isEqualTo("JaeSung");
    }

    private List<String> getLines(String input) {
        return Arrays.asList(input.split("\n"));
    }

    @Test
    void postRequest() {
        String input = "POST /user/create?id=1 HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Content-Length: 46\n" +
                "Content-Type: application/x-www-form-urlencoded\n" +
                "Accept: */*\n";

        String body = "userId=javajigi&password=password&name=JaeSung";

        HttpRequest request = RequestParser.fromLines(getLines(input));
        request.setBodyParams(body);

        assertThat(request.getProtocol()).isEqualTo(Protocol.HTTP);
        assertThat(request.getMethod()).isEqualTo(HttpMethod.POST);
        assertThat(request.getPath()).isEqualTo("/user/create");
        assertThat(request.getHeader().getHost()).isEqualTo("localhost:8080");
        assertThat(request.getHeader().getContentLength()).isEqualTo(46);
        assertThat(request.getHeader().getHeader("Content-Type")).isEqualTo("application/x-www-form-urlencoded");
        assertThat(request.getHeader().getHeader("Connection")).isEqualTo("keep-alive");
        assertThat(request.getHeader().getHeader("Accept")).isEqualTo("*/*");
        assertThat(request.getParameter("id")).isEqualTo("1");
        assertThat(request.getParameter("userId")).isEqualTo("javajigi");
        assertThat(request.getParameter("password")).isEqualTo("password");
        assertThat(request.getParameter("name")).isEqualTo("JaeSung");
    }

    @Test
    void cookieRequest() {
        String input = "GET /index.html HTTP/1.1 \n" +
                "Host: localhost:8080 \n" +
                "Connection: keep-alive \n" +
                "Accept: */*\n" +
                "Cookie: logined=true\n";

        HttpRequest request = RequestParser.fromLines(getLines(input));

        assertThat(request.getProtocol()).isEqualTo(Protocol.HTTP);
        assertThat(request.getMethod()).isEqualTo(HttpMethod.GET);
        assertThat(request.getPath()).isEqualTo("/index.html");
        assertThat(request.getHeader().getHost()).isEqualTo("localhost:8080");
        assertThat(request.getHeader().getHeader("Connection")).isEqualTo("keep-alive");
        assertThat(request.getHeader().getHeader("Accept")).isEqualTo("*/*");
        assertThat(request.getHeader().getCookies()).containsExactly(new Cookie("logined", "true", null));
    }
}