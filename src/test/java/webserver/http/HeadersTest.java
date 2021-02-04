package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

public class HeadersTest {
    @DisplayName("split 되지 않은 상태의 헤더를 삽입한다")
    @Test
    void testSaveStringForm() {
        Headers headers = new Headers();
        headers.saveHeader("Content-Type: application/json");

        assertThat(headers.get("Content-Type")).isEqualTo("application/json");
    }

    @DisplayName("key value 형태의 헤더를 삽입한다")
    @Test
    void testSaveKeyValueForm() {
        Headers headers = new Headers();
        headers.saveHeader("Content-Type", "application/json");

        assertThat(headers.get("Content-Type")).isEqualTo("application/json");
    }

    @DisplayName("존재하지 않는 헤더는 null 이 아닌 empty 를 반환한다")
    @Test
    void testGetNonExisting() {
        Headers headers = new Headers();

        assertThat(headers.get("Content-Type")).isEmpty();
    }

    @DisplayName("삽입된 헤더가 하나일 경우")
    @ParameterizedTest
    @CsvSource({"Content-Type,application/json", "Set-Cookie,logined=true; path=/"})
    void testGetProcessedHeaders(String key, String expected) {
        Headers headers = new Headers();
        headers.saveHeader(key, expected);

        assertThat(headers.get(key)).isEqualTo(expected);
    }

    @DisplayName("삽입된 헤더가 여럿일 경우")
    @Test
    void testMultipleHeaders() {
        Headers headers = new Headers();
        headers.saveHeader("Set-Cookie", "logined=true; path=/");
        headers.saveHeader("Set-Cookie", "sessionId=a123; path=/user");

        assertThat(headers.getProcessedHeaders())
                .isEqualTo("set-cookie: logined=true; path=/; sessionId=a123; path=/user");
    }

    @DisplayName("삽입된 헤더 중에 empty 헤더가 있을 때")
    @Test
    void testEmptyHeader() {
        Headers headers = new Headers();
        headers.saveHeader("Set-Cookie", "");
        headers.saveHeader("Set-Cookie", "sessionId=a123; path=/user");

        assertThat(headers.getProcessedHeaders())
                .isEqualTo("set-cookie: sessionId=a123; path=/user");
    }
}