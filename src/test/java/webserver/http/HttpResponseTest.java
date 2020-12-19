package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

class HttpResponseTest {

    @Test
    @DisplayName("정상 200 OK 응답인 경우 콘텐츠 타입, 바디, OK 확인")
    public void HttpOkHeader() {

        OutputStream outputStream = getOutputStream();
        HttpResponse response = new HttpResponse(outputStream);

        String contentType = "text/html";
        String hello = "Hello world";

        response.response200Header(hello.length(), contentType);
        response.responseBody(hello.getBytes(StandardCharsets.UTF_8));

        String outputString = outputStream.toString();
        assertThat(outputString).contains(contentType, hello, "200 OK");
    }

    @Test
    @DisplayName("302 리다이렉트 응답확인")
    public void RedirectHeader() {

        OutputStream outputStream = getOutputStream();
        HttpResponse response = new HttpResponse(outputStream);

        String redirectLocation = "http://github.com";

        response.sendRedirect(redirectLocation);

        String outputString = outputStream.toString();
        assertThat(outputString).contains(redirectLocation, "302 Found", "Location");
    }

    @Test
    @DisplayName("404 리다이렉트 응답확인")
    public void NotFoundHeader() {

        OutputStream outputStream = getOutputStream();
        HttpResponse response = new HttpResponse(outputStream);

        response.response404Header();

        String outputString = outputStream.toString();
        assertThat(outputString).contains("404 Not Found");
    }

    @Test
    @DisplayName("쿠키 확인")
    public void checkCookie() {

        OutputStream outputStream = getOutputStream();
        HttpResponse response = new HttpResponse(outputStream);

        response.addHeaderValue("Set-Cookie", "logined=true");
        response.sendRedirect("/index.html");

        String outputString = outputStream.toString();
        assertThat(outputString).contains("index.html", "logined=true", "Set-Cookie");
    }

    private OutputStream getOutputStream() {
        return new ByteArrayOutputStream();
    }

}