package domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.OutputStream;

import static org.assertj.core.api.Assertions.assertThat;


class HttpResponseTest {

    private OutputStream outputStream;
    private HttpResponse response;

    @BeforeEach
    public void setResponse() {
        outputStream = new ByteArrayOutputStream();
        response = new HttpResponse(new DataOutputStream(outputStream));
    }

    @Test
    @DisplayName("html 리스폰스 포워드 테스트")
    public void responseForward() {
        // when
        response.forward("./templates/index.html");

        // then
        String outputString = outputStream.toString();
        assertThat(outputString).contains("HTTP/1.1 200 OK ");
        assertThat(outputString).contains("Content-Type: text/html; charset=utf-8");
    }

    @Test
    @DisplayName("css 리스프노스 포워드 테스트")
    void responseCssForward() {
        // when
        response.forward("./static/css/styles.css");

        // then
        String outputString = outputStream.toString();
        assertThat(outputString).contains("HTTP/1.1 200 OK ");
        assertThat(outputString).contains("Content-Type: text/css; charset=utf-8");
    }

    @Test
    @DisplayName("리스폰스 리다이렉트 테스트")
    public void responseRedirect() {
        // when
        response.sendRedirect("/index.html");

        // then
        String outputString = outputStream.toString();
        assertThat(outputString).contains("HTTP/1.1 302 Found");
        assertThat(outputString).contains("Location: /index.html");
    }

    @Test
    @DisplayName("쿠키 생성여부 테스트")
    public void responseCookies() {
        // when
        response.addHeader("Set-Cookie", "logined=true");
        response.sendRedirect("/index.html");

        // then
        String outputString = outputStream.toString();
        assertThat(outputString).contains("HTTP/1.1 302 Found");
        assertThat(outputString).contains("Set-Cookie: logined=true");
        assertThat(outputString).contains("Location: /index.html");
    }

}