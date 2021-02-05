package utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class ParseUtilsTest {

    @Test
    @DisplayName("헤더가 주어질 떄 헤더 Key를 분리한다")
    void parseHeaderKeyTest() {
        String header = "Host: localhost:8080";
        String expected = "Host";
        assertThat(ParseUtils.parseHeaderKey(header)).isEqualTo(expected);
    }

    @Test
    @DisplayName("헤더가 주어질 떄 헤더 Value를 분리한다")
    void parseHeaderValueTest() {
        String header = "Host: localhost:8080";
        String expected = "localhost:8080";
        assertThat(ParseUtils.parseHeaderValue(header)).isEqualTo(expected);
    }

    @Test
    @DisplayName("&로 연결된 파라미터들이 주어질 떄 이를 분리한다")
    void getParametersTest() {
        String parameterPairs = "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";
        Map<String, String> parameters = ParseUtils.getParameters(parameterPairs);
        assertThat(parameters.get("userId")).isEqualTo("javajigi");
        assertThat(parameters.get("password")).isEqualTo("password");
        assertThat(parameters.get("name")).isEqualTo("%EB%B0%95%EC%9E%AC%EC%84%B1");
        assertThat(parameters.get("email")).isEqualTo("javajigi%40slipp.net");
    }

    @Test
    @DisplayName("파라미터가 포함된 URL 주소가 주어질 때 parameter를 분리한다")
    void getParameterPairsTest() {
        String url = "/user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";
        assertThat(ParseUtils.getParameterPairs(url)).isEqualTo("userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net");
    }


    @Test
    @DisplayName("파라미터가 포함된 URL 주소가 주어질 때 이를 path를 분리한다")
    void getUrlPathTest(){
        String url = "/user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";
        assertThat(ParseUtils.getUrlPath(url)).isEqualTo("/user/create");
    }

    @ParameterizedTest
    @CsvSource({"abc.tff,.tff", "zxczxc.png,.png", "abc.bc.dc,.dc"})
    @DisplayName("파일명이 주어질 떄 확장자를 분리한다")
    void getExtensionTest(String path, String expected) {
        assertThat(ParseUtils.getExtension(path)).isEqualTo(expected);
    }
}