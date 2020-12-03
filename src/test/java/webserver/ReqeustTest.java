package webserver;

import dto.RequestValue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ReqeustTest {

    @DisplayName("Request 생성 테스트")
    @Test
    void create() {
        String requestHeader = "GET /index.html HTTP/1.1";
        RequestValue requestValue = parseRequestValue(requestHeader);

        assertThat(Request.of(requestValue)).isEqualToComparingOnlyGivenFields(Request.of(requestValue));
    }

    @DisplayName("getURL 테스트")
    @ParameterizedTest
    @CsvSource(value = {
            "GET /index.html HTTP/1.1 | /index.html",
            "GET /user/list  HTTP/1.1 | /user/list",
            "GET /user/create?userId=javajigi&password=password  HTTP/1.1 | /user/create?userId=javajigi&password=password"
    }, delimiter = '|')
    void getURL(String header, String expectedURL) {
        RequestValue requestValue = parseRequestValue(header);
        Request request = Request.of(requestValue);

        assertThat(request.getURL()).isEqualTo(expectedURL);
    }

    @DisplayName("getPathGateway 테스트")
    @ParameterizedTest
    @CsvSource(value = {
            "GET /index.html HTTP/1.1 | /index",
            "GET /user/list  HTTP/1.1 | /user/list",
            "GET /user/create?userId=javajigi&password=password  HTTP/1.1 | /user/create"
    }, delimiter = '|')
    void getPathGateway(String header, String expectedPathGateway) {
        RequestValue requestValue = parseRequestValue(header);
        Request request = Request.of(requestValue);

        assertThat(request.getPathGateway()).isEqualTo(expectedPathGateway);
    }

    private RequestValue parseRequestValue(String header) {
        List<String> requestHeader = Arrays.asList(header);
        return new RequestValue(requestHeader, "");
    }
}
