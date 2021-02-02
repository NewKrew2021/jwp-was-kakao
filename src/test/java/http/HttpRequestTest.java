package http;

import annotation.web.RequestMethod;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class HttpRequestTest {
    private HttpRequestParser parser;
    private HttpRequest request;

    @BeforeEach
    void setUp() {
        parser = new HttpRequestParser();
        parser.parse("GET /user/create?userId=id&password=pw&name=jyp&email=jyp@email.com HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Accept: */*");
        request = new HttpRequest(parser);
    }

    @DisplayName("올바른 파라미터를 반환하는지 테스트")
    @ParameterizedTest
    @CsvSource({"userId,id", "password,pw", "name,jyp", "email,jyp@email.com"})
    void getParam(String key, String value) {
        assertThat(request.getParam(key)).isEqualTo(value);
    }

    @DisplayName("일치하는 request일 경우 true")
    @Test
    void sameRequestLine() {
        assertThat(request.sameRequestLine(new HttpRequest(RequestMethod.GET, "/user/create"))).isTrue();
    }

    @DisplayName("Template request일 경우 true")
    @Test
    void isTemplateRequest() {
        parser.parse("GET /index.html HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Accept: */*");
        request = new HttpRequest(parser);

        assertThat(request.isTemplateRequest()).isTrue();
    }

    @DisplayName("Static request일 경우 true")
    @Test
    void isStaticRequest() {
        parser.parse("GET /css/style.css HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Accept: text/css,*/*;q=0.1\n" +
                "Connection: keep-alive");
        request = new HttpRequest(parser);

        assertThat(request.isStaticRequest()).isTrue();
    }
}