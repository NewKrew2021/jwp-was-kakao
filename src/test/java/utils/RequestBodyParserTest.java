package utils;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestBodyParserTest {
    @Test
    public void getRequestParam() {
        Map<String, String> requestParam = RequestBodyParser.getRequestParams("userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net");
        
        assertThat(requestParam.get("userId")).isEqualTo("javajigi");
        assertThat(requestParam.get("password")).isEqualTo("password");
        assertThat(requestParam.get("name")).isEqualTo("%EB%B0%95%EC%9E%AC%EC%84%B1");
        assertThat(requestParam.get("email")).isEqualTo("javajigi%40slipp.net");
    }
}