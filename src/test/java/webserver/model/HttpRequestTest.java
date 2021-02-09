package webserver.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class HttpRequestTest {
    String request1 = "GET /index.html HTTP/1.1\n" +
            "Host: localhost:8080\n" +
            "Connection: keep-alive\n" +
            "Accept: */*\n";

    String request2 = "GET /user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net HTTP/1.1\n" +
            "Host: localhost:8080\n" +
            "Connection: keep-alive\n" +
            "Accept: */*\n";

    String request3 = "POST /user/create HTTP/1.1\n" +
            "Host: localhost:8080\n" +
            "Connection: keep-alive\n" +
            "Content-Length: 59\n" +
            "Content-Type: application/x-www-form-urlencoded\n" +
            "Accept: */*\n" +
            "\n" +
            "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net\n";

    @BeforeEach
    public void setUp() {

    }

    @DisplayName("GET method")
    @Test
    void method_GET() throws IOException {
        InputStream is = new ByteArrayInputStream(request1.getBytes());

        HttpRequest httpRequest = new HttpRequest(is);
        Assertions.assertThat(httpRequest.getMethod()).isEqualTo(HttpMethod.GET);
    }

    @DisplayName("POST method")
    @Test
    void method_POST() throws IOException {
        InputStream is = new ByteArrayInputStream(request3.getBytes());

        HttpRequest httpRequest = new HttpRequest(is);
        Assertions.assertThat(httpRequest.getMethod()).isEqualTo(HttpMethod.POST);
    }

    @DisplayName("path")
    @Test
    void path() throws IOException {
        InputStream is = new ByteArrayInputStream(request1.getBytes());

        HttpRequest httpRequest = new HttpRequest(is);
        Assertions.assertThat(httpRequest.getPath()).isEqualTo("/index.html");
    }

    @DisplayName("query parameters")
    @Test
    void queryParameter() throws IOException {
        InputStream is = new ByteArrayInputStream(request2.getBytes());

        HttpRequest httpRequest = new HttpRequest(is);

        Assertions.assertThat(httpRequest.getParameter("userId")).isEqualTo("javajigi");
        Assertions.assertThat(httpRequest.getParameter("password")).isEqualTo("password");
        Assertions.assertThat(httpRequest.getParameter("name")).isEqualTo("박재성");
        Assertions.assertThat(httpRequest.getParameter("email")).isEqualTo("javajigi@slipp.net");
    }
}
