package model;

import exception.utils.NoFileException;
import org.junit.jupiter.api.Test;
import utils.FileIoUtils;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpResponseTest {

    @Test
    void responseTest() {
        HttpResponse response = new HttpResponse();
        response.setCookie("logined=true").redirect("/path");

        Map<String, String> expected = new HashMap<>();
        expected.put("Set-Cookie", "logined=true; Path=/; HttpOnly");
        expected.put("Location", "/path");
        assertThat(response.getHeaders()).isEqualTo(expected);
        assertThat(response.getStartLine()).isEqualTo("HTTP/1.1 302 FOUND");
    }

    @Test
    void responseWithBodyTest() throws NoFileException {
        HttpResponse response = new HttpResponse();
        byte[] readFile = FileIoUtils.loadFileFromClasspath("./template/testhtml.html");
        response.forward("./template", "/testhtml.html");

        Map<String, String> expected = new HashMap<>();
        expected.put("Content-Length", "123");
        expected.put("Content-Type", "text/html;charset=utf-8");
        assertThat(response.getHeaders()).isEqualTo(expected);
        assertThat(response.getStartLine()).isEqualTo("HTTP/1.1 200 OK");
        assertThat(response.getBody()).isEqualTo(readFile);
    }
}
