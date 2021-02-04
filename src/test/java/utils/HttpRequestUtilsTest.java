package utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static utils.HttpRequestUtils.*;

class HttpRequestUtilsTest {

    @DisplayName("문자열이 주어지면 path 값을 extractPath 로 추출한다.")
    @Test
    void extractPathTest() {
        //given
        String given = "/user/create?id=1";
        //when , then
        String expected = "/user/create";
        assertThat(extractPath(given)).isEqualTo(expected);
    }

    @DisplayName("문자열이 주어졌을때 QueryString을 추출한다.")
    @Test
    void extractParamsTest() {
        //given
        String given = "/user/create?id=1";
        //when , then
        Optional<String> expected = Optional.of("id=1");
        assertThat(extractParams(given)).isEqualTo(expected);
    }

    @DisplayName("QueryString이 주어졌을 때, Map 형식으로 변환하여 반환한다.")
    @Test
    void requestStringToMapTest() {
        //given
        String given = "id=1&name=echo&password=password";
        //when , then
        Map<String,String> expected = new HashMap<>();
        expected.put("id","1");
        expected.put("name","echo");
        expected.put("password","password");
        assertThat(requestStringToMap(given)).isEqualTo(expected);
    }

}