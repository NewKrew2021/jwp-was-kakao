package webserver.http;

import org.junit.jupiter.api.Test;
import webserver.http.parser.HttpRequestParser;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpRequestParserTest {

    @Test
    public void parse_normalGet() throws Exception {
        String sample = "GET /index.html?test=123 HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Accept: */*\n";

        assertThat(new HttpRequestParser().parse(new ByteArrayInputStream(sample.getBytes())))
                .satisfies(req -> {
                    assertThat(req.getMethod()).isEqualTo(HttpMethod.GET);
                    assertThat(req.getPath()).isEqualTo("/index.html");
                    assertThat(req.getParameters()).containsEntry("test", "123");
                    assertThat(req.getHeaders()).containsEntry("Host", "localhost:8080")
                            .containsEntry("Connection", "keep-alive")
                            .containsEntry("Accept", "*/*");
                });
    }

    @Test
    public void parse_formGet() throws Exception {
        String sample = "GET /user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Accept: */*\n";

        assertThat(new HttpRequestParser().parse(new ByteArrayInputStream(sample.getBytes())))
                .satisfies(req -> {
                    assertThat(req.getMethod()).isEqualTo(HttpMethod.GET);
                    assertThat(req.getPath()).isEqualTo("/user/create");
                    assertThat(req.getParameters()).containsEntry("userId", "javajigi")
                            .containsEntry("password", "password")
                            .containsEntry("name", "박재성")
                            .containsEntry("email", "javajigi@slipp.net");
                    assertThat(req.getHeaders()).containsEntry("Host", "localhost:8080")
                            .containsEntry("Connection", "keep-alive")
                            .containsEntry("Accept", "*/*");
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

        assertThat(new HttpRequestParser().parse(new ByteArrayInputStream(sample.getBytes())))
                .satisfies(req -> {
                    assertThat(req.getMethod()).isEqualTo(HttpMethod.POST);
                    assertThat(req.getPath()).isEqualTo("/user/create");
                    assertThat(req.getHeaders()).containsEntry("Host", "localhost:8080")
                            .containsEntry("Connection", "keep-alive")
                            .containsEntry("Content-Length", "93")
                            .containsEntry("Content-Type", "application/x-www-form-urlencoded")
                            .containsEntry("Accept", "*/*");
                    Map<String, String> body = req.getBodyInMap();
                    assertThat(body).containsEntry("userId", "javajigi")
                            .containsEntry("password", "password")
                            .containsEntry("name", "박재성")
                            .containsEntry("email", "javajigi@slipp.net");
                });
    }
}
