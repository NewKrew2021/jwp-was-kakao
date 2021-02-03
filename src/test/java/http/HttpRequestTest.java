package http;

import annotation.web.RequestMethod;
import controller.DispatchInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class HttpRequestTest {
    private HttpRequest request;

    @BeforeEach
    void setUp() {
        request = new HttpRequestParser("GET /user/create?userId=id&password=pw&name=jyp&email=jyp@email.com HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Accept: */*").parse();
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
        request = new HttpRequestParser("GET /index.html HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Accept: */*").parse();

        assertThat(request.isTemplateRequest()).isTrue();
    }

    @DisplayName("Static request일 경우 true")
    @Test
    void isStaticRequest() {
        request = new HttpRequestParser("GET /css/style.css HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Accept: text/css,*/*;q=0.1\n" +
                "Connection: keep-alive").parse();

        assertThat(request.isStaticRequest()).isTrue();
    }

    @DisplayName("매치하는 request일 경우 true")
    @Test
    void matchWith() {
        request = new HttpRequestParser("POST /user/create HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Content-Length: 59\n" +
                "Content-Type: application/x-www-form-urlencoded\n" +
                "Accept: */*\n" +
                "\n" +
                "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net").parse();

        assertThat(request.matchWith(DispatchInfo.UserCreate.getHttpRequest())).isTrue();
    }
}