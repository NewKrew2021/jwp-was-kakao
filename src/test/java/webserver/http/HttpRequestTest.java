package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class HttpRequestTest {

    List<HttpHeader> headers = Arrays.asList(
            new HttpHeader("Host: localhost:8080"),
            new HttpHeader("Connection: Keep-Alive"),
            new HttpHeader("User-Agent: Apache-HttpClient/4.5.12 (Java/11.0.8)"),
            new HttpHeader("Accept-Encoding: gzip,deflate")
    );

    @DisplayName("request message 에서 http request line 가져올 수 있다")
    @Test
    void requestLine() {
        HttpRequest httpRequest = new HttpRequest("GET /index.html HTTP/1.1", headers, "");

        assertThat(httpRequest.getMethod()).isEqualTo(HttpMethod.GET);
        assertThat(httpRequest.getPath()).isEqualTo("/index.html");
    }

    @DisplayName("request line 형식이 올바르지 않으면 exception 이 발생한다")
    @Test
    void invalidRequestLine(){
        String invalidRequestLine = "GET HTTP/1.1";

        assertThatThrownBy(() -> new HttpRequest(invalidRequestLine, headers, ""))
                .isInstanceOf(InvalidHttpRequestMessageException.class);
    }

}
