package model;

import exception.utils.NoFileException;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpResponseTest {

    @Test
    void responseTest() {
        HttpResponse response = HttpResponse.of(new DataOutputStream(new ByteArrayOutputStream()));
        response.setCookie("logined=true").redirect("/path");

        Map<String, String> expected = new HashMap<>();
        expected.put("Set-Cookie", "logined=true; Path=/; HttpOnly");
        expected.put("Location", "/path");
        assertThat(response.getHeaders()).isEqualTo(expected);
        assertThat(response.getStartLine()).isEqualTo("HTTP/1.1 302 FOUND");
    }

    @Test
    void responseWithBodyTest() throws NoFileException {
        HttpResponse response = HttpResponse.of(new DataOutputStream(new ByteArrayOutputStream()));
        response.forward("./template", "/testhtml.html");

        Map<String, String> expected = new HashMap<>();
        expected.put("Content-Length", "123");
        expected.put("Content-Type", "text/html;charset=utf-8");
        assertThat(response.getHeaders()).isEqualTo(expected);
        assertThat(response.getStartLine()).isEqualTo("HTTP/1.1 200 OK");
    }
}
