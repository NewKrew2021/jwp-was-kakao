package webserver.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;

public class HttpResponseTest {
    private ByteArrayOutputStream out;

    @BeforeEach
    void init() {
        out = new ByteArrayOutputStream();
    }

    @Test
    void test_OK() {
        HttpResponse response = new HttpResponse(out);
        String html = "<html><body><h1>Hello</h1></body></html>";

        Assertions.assertThatCode(() ->
            response.sendOk(ContentType.HTML, html.getBytes())
        ).doesNotThrowAnyException();

        Assertions.assertThat(out.toString())
                .contains(HttpStatus.OK.getMessage())
                .contains("Content-Type:", ContentType.HTML.getMimeType())
                .contains("Content-Length:")
                .contains(html);
    }

    @Test
    void test_FOUND() {
        HttpResponse response = new HttpResponse(out);
        response.sendFound("/index.htm");
        Assertions.assertThat(out.toString())
                .contains(HttpStatus.FOUND.getMessage())
                .contains("Location: /index.htm");
    }

    @Test
    void test_FOUND_with_cookie() {
        HttpResponse response = new HttpResponse(out);
        response.setCookie("testKey", "testVal");
        response.sendFound("/index.htm");
        Assertions.assertThat(out.toString())
                .contains(HttpStatus.FOUND.getMessage())
                .contains("Set-Cookie:", "testKey=testVal")
                .contains("Location: /index.htm");
    }

    @Test
    void test_NOT_FOUND() {
        HttpResponse response = new HttpResponse(out);
        response.sendHeader(HttpStatus.NOT_FOUND);
        Assertions.assertThat(out.toString())
                .contains(HttpStatus.NOT_FOUND.getMessage());
    }
}
