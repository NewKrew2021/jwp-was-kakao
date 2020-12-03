package dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestValueTest {

    @DisplayName("Method 파싱 테스트")
    @ParameterizedTest
    @CsvSource(value = {
            "GET /index.html HTTP/1.1 | GET",
            "POST /user/create HTTP/1.1 | POST"
    }, delimiter = '|')
    void getMethod(String header, String expectedMethod) {
        RequestValue requestValue = parseRequestValue(header);

        assertThat(requestValue.getMethod()).isEqualTo(expectedMethod);
    }

    @DisplayName("URL 파싱 테스트")
    @ParameterizedTest
    @CsvSource(value = {
            "GET /index.html HTTP/1.1 | /index.html",
            "POST /user/create HTTP/1.1 | /user/create"
    }, delimiter = '|')
    void getURL(String header, String expectedURL) {
        RequestValue requestValue = parseRequestValue(header);

        assertThat(requestValue.getURL()).isEqualTo(expectedURL);
    }

    @DisplayName("URL Path 파싱 테스트")
    @ParameterizedTest
    @CsvSource(value = {
            "GET /index.html HTTP/1.1 | /index",
            "GET /user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net HTTP/1.1 | /user/create",
            "POST /user/create HTTP/1.1 | /user/create"
    }, delimiter = '|')
    void getURLPath(String header, String expectedURLPath) {
        RequestValue requestValue = parseRequestValue(header);

        assertThat(requestValue.getURLPath()).isEqualTo(expectedURLPath);
    }


    @DisplayName("Get Param 파싱 테스트 : Param 존재 여부 확인")
    @ParameterizedTest
    @CsvSource(value = {
            "GET /index.html HTTP/1.1 | false",
            "GET /user/create?userId=javajigi&password=password  HTTP/1.1 | true"
    }, delimiter = '|')
    void getGetParams1(String header, boolean existParam) {
        RequestValue requestValue = parseRequestValue(header);

        assertThat(requestValue.getParams().isPresent()).isEqualTo(existParam);
    }

    @DisplayName("Get Param 파싱 테스트 : 데이터 존재 시 정상적으로 파싱하는지 확인")
    @Test
    void getGetParams2() {
        String header = "GET /user/create?userId=javajigi&password=password  HTTP/1.1";
        RequestValue requestValue = parseRequestValue(header);
        Optional<String> params = requestValue.getParams();

        assertThat(params).isNotEmpty();
        assertThat(params.get()).isEqualTo("userId=javajigi&password=password");
    }

    @DisplayName("Post Param 파싱 테스트")
    @Test
    void getPostParams() {
        List<String> requestHeader = Arrays.asList(
                "POST /user/create HTTP/1.1",
                "Host: localhost:8080",
                "Connection: keep-alive",
                "Content-Length: 59",
                "Content-Type: application/x-www-form-urlencoded",
                "Accept: */*",
                "",
                "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net"
        );
        String body = "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";

        RequestValue requestValue = new RequestValue(requestHeader, body);
        Optional<String> params = requestValue.getParams();

        assertThat(params).isNotEmpty();
        assertThat(params.get()).isEqualTo(
                "userId=javajigi&password=password&name=박재성&email=javajigi@slipp.net");
    }

    private RequestValue parseRequestValue(String header) {
        List<String> requestHeader = Arrays.asList(header);
        return new RequestValue(requestHeader, "");
    }
}