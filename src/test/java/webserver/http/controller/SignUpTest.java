package webserver.http.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.HttpRequestParam;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SignUpTest {

    @DisplayName("userId 파라미터가 없으면 exception 이 발생한다")
    @Test
    void requiredParam1(){
        List<HttpRequestParam> params = HttpRequestParamUtils.params("password=password","name=nio","email=nio@kakao.com");
        assertThatThrownBy( () -> new SignUp(params))
                .isInstanceOf(MissingRequiredParamException.class);
    }

    @DisplayName("password 파라미터가 없으면 exception 이 발생한다")
    @Test
    void requiredParam2(){
        List<HttpRequestParam> params = HttpRequestParamUtils.params("userId=nio.d","name=nio","email=nio@kakao.com");
        assertThatThrownBy( () -> new SignUp(params))
                .isInstanceOf(MissingRequiredParamException.class);
    }

    @DisplayName("name 파라미터가 없으면 exception 이 발생한다")
    @Test
    void requiredParam3(){
        List<HttpRequestParam> params = HttpRequestParamUtils.params("userId=nio.d","password=password","email=nio@kakao.com");
        assertThatThrownBy( () -> new SignUp(params))
                .isInstanceOf(MissingRequiredParamException.class);
    }

    @DisplayName("email 파라미터가 없으면 exception 이 발생한다")
    @Test
    void requiredParams4(){
        List<HttpRequestParam> params = HttpRequestParamUtils.params("userId=nio.d","password=password","name=nio");
        assertThatThrownBy( () -> new SignUp(params))
                .isInstanceOf(MissingRequiredParamException.class);
    }


    @DisplayName("userId 값이 없으면 exception 이 발생한다")
    @Test
    void notEmptyParam1(){
        List<HttpRequestParam> params = HttpRequestParamUtils.params("userId=","password=password","name=nio","email=nio@kakao.com");
        assertThatThrownBy( () -> new SignUp(params))
                .isInstanceOf(NotEmptyParamException.class);
    }

    @DisplayName("password 값이 없으면 exception 이 발생한다")
    @Test
    void notEmptyParam2(){
        List<HttpRequestParam> params = HttpRequestParamUtils.params("userId=nio","password=","name=nio","email=nio@kakao.com");
        assertThatThrownBy( () -> new SignUp(params))
                .isInstanceOf(NotEmptyParamException.class);
    }

    @DisplayName("name 값이 없으면 exception 이 발생한다")
    @Test
    void notEmptyParam3(){
        List<HttpRequestParam> params = HttpRequestParamUtils.params("userId=nio","password=password","name=","email=nio@kakao.com");
        assertThatThrownBy( () -> new SignUp(params))
                .isInstanceOf(NotEmptyParamException.class);
    }

    @DisplayName("email 값이 없으면 exception 이 발생한다")
    @Test
    void notEmptyParam4(){
        List<HttpRequestParam> params = HttpRequestParamUtils.params("userId=nio","password=password","name=nio","email=");
        assertThatThrownBy( () -> new SignUp(params))
                .isInstanceOf(NotEmptyParamException.class);
    }

}