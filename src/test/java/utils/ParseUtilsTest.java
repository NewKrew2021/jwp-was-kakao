package utils;

import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class ParseUtilsTest {
    @Test
    void parseAmpersandTest() throws UnsupportedEncodingException {
        String text = "userId=1234&password=1234&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=email%40email.com";
        Map<String,String> results = ParseUtils.parseParametersByAmpersand(text);
        assertThat(results.get("userId")).isEqualTo("1234");
        assertThat(results.get("password")).isEqualTo("1234");
        assertThat(results.get("name")).isEqualTo("박재성");
        assertThat(results.get("email")).isEqualTo("email@email.com");
    }

    @Test
    void parseColonTest() {
        String text = "key1 : value1";
        String text2 = " key2 :value2 ";

        Map.Entry<String,String> results = ParseUtils.parseParametersByColon(text);
        assertThat(results).isNotNull();
        assertThat(results.getKey()).isEqualTo("key1");
        assertThat(results.getValue()).isEqualTo("value1");

        results = ParseUtils.parseParametersByColon(text2);
        assertThat(results.getKey()).isEqualTo("key2");
        assertThat(results.getValue()).isEqualTo("value2");
    }
}
