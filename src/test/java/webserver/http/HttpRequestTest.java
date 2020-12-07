package webserver.http;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpRequestTest {

    @Test
    public void parse_normalGet() throws Exception {
        String sample = "GET /index.html?test=123 HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Accept: */*\n";

        assertThat(HttpRequest.Parser.parse(new ByteArrayInputStream(sample.getBytes())))
                .satisfies(req -> {
                    assertThat(req.getMethod()).isEqualTo(HttpMethod.GET);
                    assertThat(req.getPath()).isEqualTo("/index.html");
                    assertThat(req.getParameter("test")).isEqualTo("123");
                    assertThat(req.getHeader("Host")).isEqualTo("localhost:8080");
                    assertThat(req.getHeader("Connection")).isEqualTo("keep-alive");
                    assertThat(req.getHeader("Accept")).isEqualTo("*/*");
                });
    }

    @Test
    public void parse_formGet() throws Exception {
        String sample = "GET /user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Accept: */*\n";

        assertThat(HttpRequest.Parser.parse(new ByteArrayInputStream(sample.getBytes())))
                .satisfies(req -> {
                    assertThat(req.getMethod()).isEqualTo(HttpMethod.GET);
                    assertThat(req.getPath()).isEqualTo("/user/create");

                    assertThat(req.getParameter("userId")).isEqualTo("javajigi");
                    assertThat(req.getParameter("password")).isEqualTo("password");
                    assertThat(req.getParameter("name")).isEqualTo("박재성");
                    assertThat(req.getParameter("email")).isEqualTo("javajigi@slipp.net");
                    assertThat(req.getHeader("Host")).isEqualTo("localhost:8080");
                    assertThat(req.getHeader("Connection")).isEqualTo("keep-alive");
                    assertThat(req.getHeader("Accept")).isEqualTo("*/*");
                });
    }

    @Test
    public void parse_formPost() throws Exception {
        String sample = "POST /user/create HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Content-Length: 93\n" +
                "Content-Type: application/x-www-form-urlencoded\n" +
                "Accept: */*\n" +
                "\n" +
                "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";

        assertThat(HttpRequest.Parser.parse(new ByteArrayInputStream(sample.getBytes())))
                .satisfies(req -> {
                    assertThat(req.getMethod()).isEqualTo(HttpMethod.POST);
                    assertThat(req.getPath()).isEqualTo("/user/create");

                    assertThat(req.getHeader("Host")).isEqualTo("localhost:8080");
                    assertThat(req.getHeader("Connection")).isEqualTo("keep-alive");
                    assertThat(req.getHeader("Content-Length")).isEqualTo("93");
                    assertThat(req.getHeader("Content-Type")).isEqualTo("application/x-www-form-urlencoded");
                    assertThat(req.getHeader("Accept")).isEqualTo("*/*");

                    assertThat(req.getParameter("userId")).isEqualTo("javajigi");
                    assertThat(req.getParameter("password")).isEqualTo("password");
                    assertThat(req.getParameter("name")).isEqualTo("박재성");
                    assertThat(req.getParameter("email")).isEqualTo("javajigi@slipp.net");
                });
    }
}
