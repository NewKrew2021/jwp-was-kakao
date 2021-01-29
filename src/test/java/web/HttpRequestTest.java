package web;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import utils.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpRequestTest {
    @Test
    void create() {
        String data = "GET / HTTP/1.1" + IOUtils.NEW_LINE +
                "Host: localhost:8080" + IOUtils.NEW_LINE +
                "Connection: keep-alive" + IOUtils.NEW_LINE +
                "Upgrade-Insecure-Requests: 1" + IOUtils.NEW_LINE +
                "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.96 Safari/537.36" + IOUtils.NEW_LINE +
                "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9" + IOUtils.NEW_LINE +
                "Sec-Fetch-Site: none" + IOUtils.NEW_LINE +
                "Sec-Fetch-Mode: navigate" + IOUtils.NEW_LINE +
                "Sec-Fetch-User: ?1" + IOUtils.NEW_LINE +
                "Sec-Fetch-Dest: document" + IOUtils.NEW_LINE +
                "Accept-Encoding: gzip, deflate, br" + IOUtils.NEW_LINE +
                "Accept-Language: ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7" + IOUtils.NEW_LINE;

        InputStream inputStream = new ByteArrayInputStream(data.getBytes());
        HttpRequest httpRequest = HttpRequest.of(inputStream);

        assertThat(httpRequest).extracting("httpMethod").isEqualTo(HttpMethod.GET);
        assertThat(httpRequest.getHttpUrl().getUrl()).isEqualTo("/");
        assertThat(httpRequest.getHttpHeaders().get("Host")).isEqualTo("localhost:8080");
    }
}
