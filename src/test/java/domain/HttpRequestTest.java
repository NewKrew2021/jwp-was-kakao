package domain;

import annotation.web.RequestMethod;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpRequestTest {
    String request1 = "GET /index.html HTTP/1.1\n" +
            "Host: localhost:8080\n" +
            "Connection: keep-alive\n" +
            "Accept: */*\n";

    String request2 = "GET /user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net HTTP/1.1\n" +
            "Host: localhost:8080\n" +
            "Connection: keep-alive\n" +
            "Accept: */*\n";

    String request3 = "GET /user/create HTTP/1.1\n" +
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

    @DisplayName("method1")
    @Test
    void method1() {
        InputStream is = new ByteArrayInputStream(request1.getBytes());

        HttpRequest httpRequest = new HttpRequest(is);
        assertThat(httpRequest.getMethod()).isEqualTo(RequestMethod.GET);
    }

    @DisplayName("method2")
    @Test
    void method2() {
        InputStream is = new ByteArrayInputStream(request3.getBytes());

        HttpRequest httpRequest = new HttpRequest(is);
        assertThat(httpRequest.getMethod()).isEqualTo(RequestMethod.POST);
    }

    @DisplayName("path")
    @Test
    void path() {
        InputStream is = new ByteArrayInputStream(request1.getBytes());

        HttpRequest httpRequest = new HttpRequest(is);
        assertThat(httpRequest.getPath()).isEqualTo("/index.html");
    }

    @DisplayName("query parameters")
    @Test
    void queryParameter() {
        InputStream is = new ByteArrayInputStream(request2.getBytes());

        HttpRequest httpRequest = new HttpRequest(is);

        assertThat(httpRequest.getParameter("userId")).isEqualTo("javajigi");
        assertThat(httpRequest.getParameter("password")).isEqualTo("password");
        assertThat(httpRequest.getParameter("name")).isEqualTo("%EB%B0%95%EC%9E%AC%EC%84%B1");
        assertThat(httpRequest.getParameter("email")).isEqualTo("javajigi%40slipp.net");
    }
}
