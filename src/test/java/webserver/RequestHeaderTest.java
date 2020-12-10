package webserver;

import dto.RequestValue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestHeaderTest {

    @DisplayName("RequestHeader 생성 테스트")
    @Test
    void create() {
        String requestHeader = "GET /index.html HTTP/1.1";
        RequestValue requestValue = parseRequestValue(requestHeader);

        assertThat(RequestHeader.of(requestValue))
                .isEqualToComparingOnlyGivenFields(RequestHeader.of(requestValue));
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
        RequestHeader requestHeader = RequestHeader.of(requestValue);

        assertThat(requestHeader.getURL()).isEqualTo(expectedURL);
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
        RequestHeader requestHeader = RequestHeader.of(requestValue);

        assertThat(requestHeader.getPathGateway()).isEqualTo(expectedPathGateway);
    }

    private RequestValue parseRequestValue(String header) {
        List<String> requestHeader = Arrays.asList(header);
        return new RequestValue(requestHeader, "");
    }
}
