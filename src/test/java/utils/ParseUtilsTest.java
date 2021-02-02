package utils;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class ParseUtilsTest {

    @Test
    void parseHeaderKey() {
        String header = "Host: localhost:8080";
        String expected = "Host";
        assertThat(ParseUtils.parseHeaderKey(header)).isEqualTo(expected);
    }

    @Test
    void parseHeaderValue() {
        String header = "Host: localhost:8080";
        String expected = "localhost:8080";
        assertThat(ParseUtils.parseHeaderValue(header)).isEqualTo(expected);
    }

    @Test
    void getParametersTest() {
        String parameterPairs = "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";
        Map<String, String> parameters = ParseUtils.getParameters(parameterPairs);
        assertThat(parameters.get("userId")).isEqualTo("javajigi");
        assertThat(parameters.get("password")).isEqualTo("password");
        assertThat(parameters.get("name")).isEqualTo("%EB%B0%95%EC%9E%AC%EC%84%B1");
        assertThat(parameters.get("email")).isEqualTo("javajigi%40slipp.net");
    }

    @Test
    void getParameterPairsTest() {
        String url = "/user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";
        assertThat(ParseUtils.getParameterPairs(url)).isEqualTo("userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net");
    }


    @Test
    void getUrlPathTest(){
        String url = "/user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";
        assertThat(ParseUtils.getUrlPath(url)).isEqualTo("/user/create");
    }
}