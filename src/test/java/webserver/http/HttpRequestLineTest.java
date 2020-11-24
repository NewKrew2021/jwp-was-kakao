package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

class HttpRequestLineTest {

    @DisplayName("Request-Line 에서 URI 를 가져올 수 있다")
    @Test
    void parseUri(){
        HttpRequestLine requestLine = new HttpRequestLine("GET /index.html HTTP/1.1");

        assertThat(requestLine.getUri()).isEqualTo(URI.create("/index.html"));
    }

}