package apps.slipp.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.HttpRequestParam;
import webserver.http.MissingRequiredParamException;
import webserver.http.NotEmptyParamException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LoginTest {

    @DisplayName("password 파라미터가 없으면 exception 이 발생한다")
    @Test
    void requiredParams1(){
        List<HttpRequestParam> params = THttpRequestParams.params("userId=nio.d");
        assertThatThrownBy( () -> new Login(params))
                .isInstanceOf(MissingRequiredParamException.class);
    }

    @DisplayName("userId 파라미터가 없으면 exception 이 발생한다")
    @Test
    void requiredParams2(){
        List<HttpRequestParam> params = THttpRequestParams.params("password=password");
        assertThatThrownBy( () -> new Login(params))
                .isInstanceOf(MissingRequiredParamException.class);
    }

    @DisplayName("userId 값이 없으면 exception 이 발생한다")
    @Test
    void notEmptyParam1(){
        List<HttpRequestParam> params = THttpRequestParams.params("userId=", "password=password");
        assertThatThrownBy( () -> new Login(params))
                .isInstanceOf(NotEmptyParamException.class);
    }

    @DisplayName("password 값이 없으면 exception 이 발생한다")
    @Test
    void notEmptyParam2(){
        List<HttpRequestParam> params = THttpRequestParams.params("userId=nio", "password=");
        assertThatThrownBy( () -> new Login(params))
                .isInstanceOf(NotEmptyParamException.class);
    }

}