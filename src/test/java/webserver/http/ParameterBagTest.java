package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

class ParameterBagTest {

    @Test
    @DisplayName("파라미터_Url_decode 확인")
    public void parameter_value_check() {

        Map<String, String> map = new HashMap<>();
        map.put("key1", "value1");
        map.put("key2", "%EC%95%88%EB%85%95%ED%95%98%EC%84%B8%EC%9A%94");
        map.put("key3", "안녕하세요");

        ParameterBag bag = new ParameterBag(map);

        assertThat(bag.getParameter("key1")).isEqualTo("value1");
        assertThat(bag.getParameter("key2")).isEqualTo("안녕하세요");
        assertThat(bag.getParameter("key3")).isEqualTo("안녕하세요");

    }

    @Test
    @DisplayName("Parameter Bag has method test")
    public void hasParameter() {

        Map<String, String> map = new HashMap<>();
        map.put("key1", "value1");
        map.put("key2", "안녕하세요");

        ParameterBag bag = new ParameterBag(map);

        assertThat(bag.hasParameter("key1")).isTrue();
        assertThat(bag.hasParameter("key2")).isTrue();
        assertThat(bag.hasParameter("key3")).isFalse();

    }

}