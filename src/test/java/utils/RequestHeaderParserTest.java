package utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("RequestUtil 테스트")
public class RequestHeaderParserTest {
    @DisplayName("request 요청 경로 가져오기")
    @ParameterizedTest
    @CsvSource(value = {"GET / HTTP/1.1:/", "GET /favicon.ico HTTP/1.1:/favicon.ico",
            "GET /user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net HTTP/1.1:/user/create"}, delimiter = ':')
    public void getPath(String requestHeaderFirstLine, String expectedPath) {
        String path = RequestHeaderParser.getRequestPath(requestHeaderFirstLine);

        assertThat(path).isEqualTo(expectedPath);
    }

    @Test
    public void getRequestParam() {
        Map<String, String> requestParam = RequestHeaderParser.getRequestParams("GET /user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net HTTP/1.1:/user/create");

        assertThat(requestParam.get("userId")).isEqualTo("javajigi");
        assertThat(requestParam.get("password")).isEqualTo("password");
        assertThat(requestParam.get("name")).isEqualTo("%EB%B0%95%EC%9E%AC%EC%84%B1");
        assertThat(requestParam.get("email")).isEqualTo("javajigi%40slipp.net");
    }

    @ParameterizedTest
    @CsvSource(value = {"GET /user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net HTTP/1.1:GET",
            "POST /user/create HTTP/1.1:POST"
    }, delimiter = ':')
    public void getMethod(String firstLine, String expectedMethod) {
        String method = RequestHeaderParser.getMethod(firstLine);

        assertThat(method).isEqualTo(expectedMethod);
    }

    @Test
    public void getContentLength() {
        Integer contentLength = RequestHeaderParser.getContentLength("Content-Length: 59");

        assertThat(contentLength).isEqualTo(59);
    }

    @Test
    public void getHost() {
        String host = RequestHeaderParser.getHost("Host: localhost:8080");

        assertThat(host).isEqualTo("localhost:8080");
    }

    @Test
    public void encode() throws UnsupportedEncodingException {
        String email = "56%40gmail.com";
        assertThat(URLDecoder.decode(email, "UTF-8")).isEqualTo("56@gmail.com");
    }
}