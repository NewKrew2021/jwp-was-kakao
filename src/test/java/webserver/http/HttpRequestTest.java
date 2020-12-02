package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class HttpRequestTest {

    @DisplayName("request line 이 비어 있으면 HttpRequest 를 생성시 exception 발생한다")
    @Test
    void invalidArgument(){
        assertThatThrownBy( () -> HttpRequest.builder()
                .headers(Arrays.asList(new HttpHeader("Content-type", "text/html")))
                .build()).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("Header 에서 Cookie 값을 가져올 수 있다")
    @Test
    void getCookie(){
        HttpRequest httpRequest = HttpRequest.builder()
                .requestLine(new HttpRequestLine("GET index.html HTTP/1.1"))
                .headers(
                        new HttpHeader("Cookie", "logined=true; name=nio")
                )
                .build();

        assertThat(httpRequest.getCookie("logined")).isEqualTo("true");
        assertThat(httpRequest.getCookie("name")).isEqualTo("nio");
    }

    @DisplayName("존재하지 않는 Cookie 값을 조회하면 null 을 return 한다")
    @Test
    void getNotExistCookie(){
        HttpRequest httpRequest = HttpRequest.builder()
                .requestLine(new HttpRequestLine("GET index.html HTTP/1.1"))
                .headers(
                        new HttpHeader("Cookie", "logined=true; name=nio")
                )
                .build();

        assertThat(httpRequest.getCookie("notExist")).isNull();
    }
}