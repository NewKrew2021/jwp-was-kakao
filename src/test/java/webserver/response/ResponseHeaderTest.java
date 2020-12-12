package webserver.response;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import webserver.Cookie;
import webserver.request.HttpRequest;

import static org.assertj.core.api.Assertions.assertThat;

class ResponseHeaderTest {
    ResponseHeader header = ResponseHeader.ok(HttpRequest.empty());

    @Test
    void status200() {
        assertThat(header.toString()).startsWith("HTTP/1.1 200 OK \r\n\r\n");
    }

    @Test
    void status302() {
        header.setStatus(HttpStatus.FOUND);

        assertThat(header.toString()).startsWith(("HTTP/1.1 302 Found \r\n\r\n"));
    }

    @Test
    void cookie() {
        header.setCookie(new Cookie("logined", "true", "/"));

        assertThat(header.toString()).contains("Set-Cookie: logined=true; Path=/\r\n\r\n");
    }

    @Test
    void location() {
        header.setLocation("http://localhost:8080/index.html");

        assertThat(header.toString()).contains("Location: http://localhost:8080/index.html\r\n\r\n");
    }

    @Test
    void contentType() {
        header.setContentType("text/html");

        assertThat(header.toString()).contains("Content-Type: text/html\r\n\r\n");
    }

    @Test
    void contentLength() {
        header.setContentLength(30);

        assertThat(header.toString()).contains("Content-Length: 30\r\n\r\n");
    }
}


