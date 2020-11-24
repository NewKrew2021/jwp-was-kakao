package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Value;

import static org.assertj.core.api.Assertions.assertThat;

class HttpRequestParamTest {

    @DisplayName("이름=값 형태 문자열을 생성인자로 주면 객체를 생성할 수 있다")
    @Test
    void create1(){
        HttpRequestParam param = new HttpRequestParam("name=nio");

        assertThat(param).isEqualTo(new HttpRequestParam("name", "nio"));
    }

    @DisplayName("값이 없는 문자열을 생성인자로 이름만 있고 값은 없는 객체가 생성된다")
    @ParameterizedTest
    @ValueSource(strings = {"name", "name="})
    void create2(String query){
        HttpRequestParam param = new HttpRequestParam(query);

        assertThat(param).isEqualTo(new HttpRequestParam("name", null));
    }
}