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
        String data = "GET / HTTP/1.1" + IOUtils.CRLF +
                "Host: localhost:8080" + IOUtils.CRLF +
                "Connection: keep-alive" + IOUtils.CRLF +
                "Upgrade-Insecure-Requests: 1" + IOUtils.CRLF +
                "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.96 Safari/537.36" + IOUtils.CRLF +
                "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9" + IOUtils.CRLF +
                "Sec-Fetch-Site: none" + IOUtils.CRLF +
                "Sec-Fetch-Mode: navigate" + IOUtils.CRLF +
                "Sec-Fetch-User: ?1" + IOUtils.CRLF +
                "Sec-Fetch-Dest: document" + IOUtils.CRLF +
                "Accept-Encoding: gzip, deflate, br" + IOUtils.CRLF +
                "Accept-Language: ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7" + IOUtils.CRLF;

        InputStream inputStream = new ByteArrayInputStream(data.getBytes());
        HttpRequest httpRequest = HttpRequest.of(inputStream);

        assertThat(httpRequest).extracting("httpMethod").isEqualTo(HttpMethod.GET);
        assertThat(httpRequest.getHttpUrl().getUrl()).isEqualTo("/");
        assertThat(httpRequest.getHttpHeaders().get("Host")).isEqualTo("localhost:8080");
    }

    @Test
    void createWithBody() {
        String data = "POST / HTTP/1.1" + IOUtils.CRLF +
                "Host: localhost:8080" + IOUtils.CRLF +
                "Connection: keep-alive" + IOUtils.CRLF +
                "Upgrade-Insecure-Requests: 1" + IOUtils.CRLF +
                "Content-Length: 93" + IOUtils.CRLF +
                "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.96 Safari/537.36" + IOUtils.CRLF +
                "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9" + IOUtils.CRLF +
                "Sec-Fetch-Site: none" + IOUtils.CRLF +
                "Sec-Fetch-Mode: navigate" + IOUtils.CRLF +
                "Sec-Fetch-User: ?1" + IOUtils.CRLF +
                "Sec-Fetch-Dest: document" + IOUtils.CRLF +
                "Accept-Encoding: gzip, deflate, br" + IOUtils.CRLF +
                "Accept-Language: ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7" + IOUtils.CRLF + IOUtils.CRLF +
                "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net" + IOUtils.CRLF;

        InputStream inputStream = new ByteArrayInputStream(data.getBytes());
        HttpRequest httpRequest = HttpRequest.of(inputStream);

        assertThat(httpRequest).extracting("httpMethod").isEqualTo(HttpMethod.POST);
        assertThat(httpRequest.getHttpUrl().getUrl()).isEqualTo("/");
        assertThat(httpRequest.getHttpHeaders().get("Host")).isEqualTo("localhost:8080");
        assertThat(httpRequest.getHttpBody().getBody()).isEqualTo("userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net".getBytes());
    }
}
