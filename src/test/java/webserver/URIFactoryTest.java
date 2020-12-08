package webserver;

import dto.RequestValue;
import model.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class URIFactoryTest {

    private URIFactory uriFactory;

    @BeforeEach
    public void setup() {
        uriFactory = new URIFactory();
    }

    @DisplayName("URIPath 테스트 : /user/create")
    @Test
    void compose1() {
        String header = "GET /user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net HTTP/1.1";
        Request request = build(header, "");

        Response response = uriFactory.create(request);

        assertThat(response.getHttpStatus()).isEqualTo(HttpStatus.HTTP_FOUND);
    }

    @DisplayName("URIPath 테스트 : /user/login")
    @Test
    void compose2() {
        String header = "POST /user/login HTTP/1.1";
        String body = "userId=javajigi&password=password";
        Request request = build(header, body);

        Response response = uriFactory.create(request);

        assertThat(response.getHttpStatus()).isEqualTo(HttpStatus.HTTP_FOUND);
    }

    @DisplayName("URIPath 테스트 : /user/list -> 로그인한 경우")
    @Test
    void compose3() {
        List<String> headerList = Arrays.asList("GET /user/list HTTP/1.1",
                                                "Cookie: JSESSIONID=DFCD469C; logined=true");
        Request request = build(headerList, "");

        Response response = uriFactory.create(request);

        assertThat(response.getHttpStatus()).isEqualTo(HttpStatus.HTTP_OK);
    }

    @DisplayName("URIPath 테스트 : /user/list -> 로그인하지 않은 경우")
    @Test
    void compose4() {
        List<String> headerList = Arrays.asList("GET /user/list HTTP/1.1",
                                                "Cookie: JSESSIONID=DFCD469C; logined=false");
        Request request = build(headerList, "");

        Response response = uriFactory.create(request);

        assertThat(response.getHttpStatus()).isEqualTo(HttpStatus.HTTP_FOUND);
    }

    @DisplayName("URIPath 테스트 : staticFile인 경우")
    @ParameterizedTest
    @ValueSource(strings = {
            "GET /user/list.html HTTP/1.1",
            "GET bootstrap.css HTTP/1.1"})
    void compose5(String header) {
        Request request = build(header, "");

        Response response = uriFactory.create(request);

        assertThat(response.getHttpStatus()).isEqualTo(HttpStatus.HTTP_OK);
    }

    private Request build(String header, String body){
        List<String> headerList = Arrays.asList(header);
        RequestValue requestValue = new RequestValue(headerList, body);
        return Request.of(requestValue);
    }

    private Request build(List<String> headerList, String body){
        RequestValue requestValue = new RequestValue(headerList, body);
        return Request.of(requestValue);
    }
}
