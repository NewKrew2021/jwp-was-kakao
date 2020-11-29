package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class ParseURITest {

    @DisplayName("URI 테스트")
    @ParameterizedTest
    @CsvSource(value = {
            "GET /index.html HTTP/1.1 | /index",
            "GET /user/create?userId=javajigi&password=password  HTTP/1.1 | /user/create"
    }, delimiter = '|')
    void getURI(String path, String expectedURI) {
        String URI = ParseURI.getURI(path);
        assertThat(URI).isEqualTo(expectedURI);
    }

    @DisplayName("URI 중 Param 파싱 테스트 : Param 존재 여부 확인")
    @ParameterizedTest
    @CsvSource(value = {
            "GET /index.html HTTP/1.1 | false",
            "GET /user/create?userId=javajigi&password=password  HTTP/1.1 | true"
    }, delimiter = '|')
    void getParams1(String path, boolean existParam) {
        Optional<String> params = ParseURI.getParams(path);
        assertThat(params.isPresent()).isEqualTo(existParam);
    }

    @DisplayName("URI 중 Param 파싱 테스트 : 존재시 정상적으로 파싱하는지 확인")
    @Test
    void getParams2() {
        String path = "GET /user/create?userId=javajigi&password=password  HTTP/1.1";
        Optional<String> params = ParseURI.getParams(path);

        assertThat(params).isNotEmpty();
        assertThat(params.get()).isEqualTo("userId=javajigi&password=password");
    }

    @DisplayName("URI to ViewPath 테스트")
    @ParameterizedTest
    @CsvSource(value = {
            "    /index.html | ./templates/index.html",
            "/css/styles.css | ./static/css/styles.css",
            " /js/scripts.js | ./static/js/scripts.js"
    }, delimiter = '|')
    void getViewPath(String request, String expectedPath) {
        String path = ParseURI.getViewPath(request);
        assertThat(path).isEqualTo(expectedPath);
    }
}
