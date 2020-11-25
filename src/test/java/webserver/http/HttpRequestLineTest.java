package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class HttpRequestLineTest {

    @DisplayName("request line 형식이 올바르지 않으면 exception 이 발생한다")
    @Test
    void invalidRequestLine(){
        assertThatThrownBy(() -> new HttpRequestLine("GET HTTP/1.1"))
                .isInstanceOf(InvalidHttpRequestMessageException.class);
    }


    @DisplayName("Request-Line 에서 URI 를 가져올 수 있다")
    @Test
    void parseUri(){
        HttpRequestLine requestLine = new HttpRequestLine("GET /index.html HTTP/1.1");

        assertThat(requestLine.getUri()).isEqualTo(URI.create("/index.html"));
    }

}