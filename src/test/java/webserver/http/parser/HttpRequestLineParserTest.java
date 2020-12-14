package webserver.http.parser;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.HttpMethod;
import webserver.http.HttpRequestLine;

import static org.assertj.core.api.Assertions.*;

class HttpRequestLineParserTest {

    @Test
    @DisplayName("GET 정상적인 경우")
    void get_normal() {

        HttpRequestLine line = HttpRequestLineParser.parse("GET /index.html HTTP/1.1");
        assertThat(line.getMethod()).isEqualTo(HttpMethod.GET);
    }

    @Test
    @DisplayName("POST 정상적인 경우")
    void post_normal() {

        HttpRequestLine line = HttpRequestLineParser.parse("POST /index.html HTTP/1.1");
        assertThat(line.getMethod()).isEqualTo(HttpMethod.POST);
    }

    @Test
    @DisplayName("잘못된 값")
    void wrong_line() {

        assertThatIllegalArgumentException()
                .isThrownBy(() -> {
                    HttpRequestLine line = HttpRequestLineParser.parse("POST /index.html");
                });
    }

    @Test
    @DisplayName("GET 쿼리 스트링 ")
    void get_with_queryString() {

        HttpRequestLine line = HttpRequestLineParser.parse("GET /index.html?param1=paramValue1 HTTP/1.1");
        assertThat(line.getQueryString().getParameter("param1")).isEqualTo("paramValue1");

    }

}