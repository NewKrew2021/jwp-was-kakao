package utils;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class URLUtilsTest {
    @Test
    void parseParameter() {
        String url = "/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";
        Map<String, String> params = new HashMap<>();

        params.put("userId", "javajigi");
        params.put("password", "password");
        params.put("name", "%EB%B0%95%EC%9E%AC%EC%84%B1");
        params.put("email", "javajigi%40slipp.net");

        assertThat(URLUtils.parseParameter(url)).isEqualTo(params);
    }
}