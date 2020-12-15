package webserver.response;

import org.junit.jupiter.api.Test;
import view.View;
import webserver.Cookie;
import webserver.request.HttpRequest;

import static org.assertj.core.api.Assertions.assertThat;

class HttpResponseTest {
    HttpResponse response = HttpResponse.ok(HttpRequest.empty());

    @Test
    void setView() {
        response.setView(View.of(null, "./templates/index.html"));

        assertThat(response.getHeader().toString()).startsWith("HTTP/1.1 200 OK");
        assertThat(response.getHeader().toString()).contains("Content-Type: text/html");
    }

    @Test
    void setNotFoundView() {
        response.setView(null);

        assertThat(response.getHeader().toString()).startsWith("HTTP/1.1 404 Not Found");
    }

    @Test
    void redirect() {
        HttpRequest request = HttpRequest.empty();
        request.getHeader().addHeader("Host", "localhost:8080");

        response.setRedirect("/index.html");

        assertThat(response.getHeader().toString()).startsWith("HTTP/1.1 302 Found");
        assertThat(response.getHeader().toString()).contains("Location: /index.html");
    }

    @Test
    void redirectWithCookie() {
        HttpRequest request = HttpRequest.empty();
        request.getHeader().addHeader("Host", "localhost:8080");

        response.setRedirectWithCookie(new Cookie("logined", "true", "/"), "/index.html");

        assertThat(response.getHeader().toString()).startsWith("HTTP/1.1 302 Found");
        assertThat(response.getHeader().toString()).contains("Location: /index.html");
        assertThat(response.getHeader().toString()).contains("Set-Cookie: logined=true; Path=/");
    }

    @Test
    void ok() {
        assertThat(response.getHeader().toString()).startsWith("HTTP/1.1 200 OK");
    }

    @Test
    void error() {
        HttpResponse response = HttpResponse.error();

        assertThat(response.getHeader().toString()).startsWith("HTTP/1.1 500 Internal Server Error");
    }
}