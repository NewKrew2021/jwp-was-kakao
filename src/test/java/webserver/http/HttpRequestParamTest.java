package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class HttpRequestParamTest {

    @DisplayName("param 문자열이 Null 또는 공백이면 exception 이 발생한다")
    @ParameterizedTest
    @NullAndEmptySource
    void nullAndEmpty(String paramString){
        assertThatThrownBy(() -> new HttpRequestParam(paramString))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("이름만 있는 param 문자열이면 value 는 공백으로 설정된다")
    @ParameterizedTest
    @ValueSource(strings = {"name", "name="})
    void onlyName(String paramString){
        HttpRequestParam param = new HttpRequestParam(paramString);
        assertThat(param.getValue()).isEqualTo("");
    }

    @DisplayName("url encoding 된 param 은 decoding 해준다")
    @ParameterizedTest
    @CsvSource(value = {"name=%EC%B5%9C%EC%84%B8%ED%99%98:최세환", "email=sehan.choi%40gmail.com:sehan.choi@gmail.com"}, delimiterString = ":")
    void decodedValue(String paramString, String decodedValue){
        HttpRequestParam param = new HttpRequestParam(paramString );

        assertThat(param.getValue()).isEqualTo(decodedValue);
    }

}