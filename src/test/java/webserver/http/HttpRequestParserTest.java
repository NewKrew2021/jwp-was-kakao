package webserver.http;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpRequestParserTest {

    @Test
    public void parse() throws Exception {
        String sample = "GET /index.html?test=123 HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Accept: */*\n";

        assertThat(new HttpRequestParser().parse(new ByteArrayInputStream(sample.getBytes())))
                .satisfies(req -> {
                    assertThat(req.getMethod()).isEqualTo("GET");
                    assertThat(req.getPath()).isEqualTo("/index.html");
                    assertThat(req.getParameters()).containsEntry("test", "123");
                    assertThat(req.getHeaders()).containsEntry("Host", "localhost:8080")
                            .containsEntry("Connection", "keep-alive")
                            .containsEntry("Accept", "*/*");
                });
    }
}
