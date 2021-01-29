package utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.HttpMethod;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class ParserTest {
    @DisplayName("HTTP 헤더를 입력받으면 url을 반환한다.")
    @Test
    void parseURLFromHeader() {
        // given
        String header = "GET /index.html HTTP/1.1";

        // then
        assertThat(Parser.parseURLFromRequestLine(header)).isEqualTo("/index.html");
    }

    @DisplayName("HTTP 헤더를 입력받으면 HTTP-Method를 반환한다.")
    @Test
    void parseMethodFromHeader() {
        // given
        String header = "POST /user/create HTTP/1.1";

        // then
        assertThat(Parser.parseMethodFromRequestLine(header)).isEqualTo(HttpMethod.POST);
    }

    @DisplayName("query를 입력받으면 사용자 정보를 반환한다.")
    @Test
    void parseUserParams() {
        // given
        String header = "GET /user/create?userId=hello&password=1q2w3e4r!&name=waylon&email=hello@kakaocorp.com HTTP/1.1";
        Map<String, String> expected = new HashMap<>();
        expected.put("userId", "hello");
        expected.put("password", "1q2w3e4r!");
        expected.put("name", "waylon");
        expected.put("email", "hello@kakaocorp.com");

        // then
        Map<String, String> actual = Parser.parseUserParams(header);
        for(String key : actual.keySet()) {
            System.out.println(key + " \t " + actual.get(key));
            assertThat(actual.get(key)).isEqualTo(expected.get(key));
        }
    }
}
