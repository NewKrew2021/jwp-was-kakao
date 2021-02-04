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

    @DisplayName("같은 메소드인 경우 true")
    @Test
    void hasSameMethod() {
        HttpRequest request = new HttpRequest(RequestMethod.GET, "/index.html");
        DispatchInfo dispatchInfo = DispatchInfo.Template;

        assertThat(request.hasSameMethod(dispatchInfo.getHttpRequest())).isTrue();
    }

    @DisplayName("같은 uri 경우 true")
    @Test
    void hasSameUri() {
        HttpRequest request = new HttpRequest(RequestMethod.GET, "/user/list");
        DispatchInfo dispatchInfo = DispatchInfo.UserList;

        assertThat(request.hasSameUri(dispatchInfo.getHttpRequest())).isTrue();
    }

    @Test
    void startsWith() {
        HttpRequest request = new HttpRequest(RequestMethod.GET, "/css/styles.css");
        StaticRequestPath path = StaticRequestPath.CSS;

        assertThat(request.startsWith(path.getPath())).isTrue();
    }

    @Test
    void endsWith() {
        HttpRequest request = new HttpRequest(RequestMethod.GET, "/index.html");
        TemplateRequestExtension extension = TemplateRequestExtension.HTML;

        assertThat(request.endsWith(extension.getExtension())).isTrue();
    }
}