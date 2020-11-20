package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
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
}