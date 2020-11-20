package utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("RequestUtil 테스트")
public class RequestHeaderParserTest {
    @DisplayName("request 요청 경로 가져오기")
    @ParameterizedTest
    @CsvSource(value = {"GET / HTTP/1.1:/", "GET /favicon.ico HTTP/1.1:favicon.ico"}, delimiter = ':')
    public void getPath(String requestHeaderFirstLine, String expectedPath) {
        String path = RequestHeaderParser.getRequestPath(requestHeaderFirstLine);
        assertThat(path).isEqualTo(expectedPath);
    }
}