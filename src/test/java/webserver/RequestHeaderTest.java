package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("요청 헤더 테스트")
public class RequestHeaderTest {

    @DisplayName("요청헤더 문자열로 변환")
    @Test
    public void getRequestHeaderAsString() {
        RequestHeader header = RequestHeader.of(Arrays.asList("GET /favicon.ico HTTP/1.1", "Host: localhost:8080"));

        assertThat(header.toString()).isEqualTo("GET /favicon.ico HTTP/1.1\nHost: localhost:8080");
    }

    @DisplayName("URL 경로 반환")
    @ParameterizedTest
    @MethodSource("getFirstLines")
    public void getTemplatePath(String firstLineOfHeader, String expectedPath) {
        RequestHeader header = RequestHeader.of(Arrays.asList(firstLineOfHeader));

        String path = header.getPath();

        assertThat(path).isEqualTo(expectedPath);
    }

    private static Stream<Arguments> getFirstLines() {
        return Stream.of(Arguments.arguments("GET /index.html HTTP/1.1", "./templates/index.html"),
                Arguments.arguments("GET /favicon.ico HTTP/1.1", "./templates/favicon.ico"),
                Arguments.arguments("GET /css/bootstrap.min.css HTTP/1.1", "./static/css/bootstrap.min.css"),
                Arguments.arguments("GET /js/jquery-2.2.0.min.js HTTP/1.1", "./static/js/jquery-2.2.0.min.js"));
    }

    @Test
    public void getParams() {
        RequestHeader header = RequestHeader.of(Collections.singletonList("GET /user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net HTTP/1.1"));

        Map<String, String> params = header.getParams();

        assertThat(params.get("userId")).isEqualTo("javajigi");
        assertThat(params.get("password")).isEqualTo("password");
        assertThat(params.get("name")).isEqualTo("%EB%B0%95%EC%9E%AC%EC%84%B1");
        assertThat(params.get("email")).isEqualTo("javajigi%40slipp.net");
    }

    @ParameterizedTest
    @CsvSource(value = {"GET /user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net HTTP/1.1:GET",
            "POST /user/create HTTP/1.1:POST"
    }, delimiter = ':')
    public void getMethod(String firstLine, String expectedMethod) {
        RequestHeader header = RequestHeader.of(Collections.singletonList(firstLine));

        String method = header.getMethod();

        assertThat(method).isEqualTo(expectedMethod);
    }

    @Test
    public void getContentLength() {
        RequestHeader header = RequestHeader.of(Arrays.asList("POST /user/create HTTP/1.1",
                "Host: localhost:8080",
                "Connection: keep-alive",
                "Content-Length: 59",
                "Content-Type: application/x-www-form-urlencoded",
                "Accept: */*"));

        int contentLength = header.getContentLength();

        assertThat(contentLength).isEqualTo(59);
    }

    @Test
    public void getNullContentLength() {
        RequestHeader header = RequestHeader.of(Arrays.asList("POST /user/create HTTP/1.1",
                "Host: localhost:8080",
                "Connection: keep-alive",
                "Content-Type: application/x-www-form-urlencoded",
                "Accept: */*"));

        assertThat(header.getContentLength()).isNull();
    }

    @Test
    public void getHost() {
        RequestHeader header = RequestHeader.of(Arrays.asList("POST /user/create HTTP/1.1",
                "Host: localhost:8080",
                "Connection: keep-alive",
                "Content-Type: application/x-www-form-urlencoded",
                "Accept: */*"));

        assertThat(header.getHost()).isEqualTo("localhost:8080");
    }
}