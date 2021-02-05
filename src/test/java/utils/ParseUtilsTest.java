package utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class ParseUtilsTest {

    @Test
    @DisplayName("&를 기준으로 파싱하고 =를 기준으로 key, value를 나누어 저장한다. 인코딩된 값은 디코딩하여 저장한다.")
    void parseAmpersandTest() throws UnsupportedEncodingException {
        String text = "userId=1234&password=1234&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=email%40email.com";
        Map<String,String> results = ParseUtils.parseParametersByAmpersand(text);
        assertThat(results.get("userId")).isEqualTo("1234");
        assertThat(results).containsEntry("userId", "1234");
        assertThat(results).containsEntry("name", "박재성");
        assertThat(results).containsEntry("password", "1234");
        assertThat(results).containsEntry("email", "email@email.com");
    }

    @ParameterizedTest(name = ":을 기준으로 공백을 trim하고 parsing한다.")
    @ValueSource(strings = {"key1 : value1" , " key1:value1 ", "key1 :value1"})
    void parseColonTest(String text) {
        Map.Entry<String,String> results = ParseUtils.parseParametersByColon(text);
        assertThat(results).isNotNull();
        assertThat(results.getKey()).isEqualTo("key1");
        assertThat(results.getValue()).isEqualTo("value1");
    }
}
