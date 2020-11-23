package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class HttpRequestTest {

    List<String> headers = Arrays.asList(
            "Host: localhost:8080",
            "Connection: Keep-Alive",
            "User-Agent: Apache-HttpClient/4.5.12 (Java/11.0.8)",
            "Accept-Encoding: gzip,deflate"
    );


    @DisplayName("http 요청에서 request line 정보를 가져올 수 있다")
    @Test
    void requestLine() {
        List<String> httpRequestMessage = new ArrayList<>();
        httpRequestMessage.add("GET /index.html HTTP/1.1");
        httpRequestMessage.addAll(headers);

        HttpRequest httpRequest = new HttpRequest(httpRequestMessage);

        assertThat(httpRequest.getRequestLine()).isEqualTo(new HttpRequestLine("GET", "/index.html", "HTTP/1.1"));
    }

    @DisplayName("request line 형식이 올바르지 않으면 exception 이 발생한다")
    @Test
    void invalidRequestLine(){
        String invalidRequestLine = "GET HTTP/1.1";
        List<String> httpRequestMessage = new ArrayList<>();
        httpRequestMessage.add(invalidRequestLine);
        httpRequestMessage.addAll(headers);

        assertThatThrownBy(() -> new HttpRequest(httpRequestMessage))
                .isInstanceOf(InvalidHttpRequestMessageException.class);
    }

}
