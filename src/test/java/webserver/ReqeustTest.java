package webserver;

import dto.RequestValue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ReqeustTest {

    @DisplayName("Request 생성 테스트")
    @Test
    void create() {
        String requestHeader = "GET /index.html HTTP/1.1";
        RequestValue requestValue = parseRequestValue(requestHeader);

        assertThat(Request.of(requestValue)).isEqualToComparingFieldByField(Request.of(requestValue));
    }

    @DisplayName("Request 생성 테스트 : param이 빈값인데 get 호출시 에러")
    @Test
    void getParamMap() {
        String requestHeader = "GET /index.html HTTP/1.1";
        RequestValue requestValue = parseRequestValue(requestHeader);
        Request request = Request.of(requestValue);

        assertThatThrownBy(request::getParamMap)
                .isInstanceOf(IllegalStateException.class);
    }

    private RequestValue parseRequestValue(String header) {
        List<String> requestHeader = Arrays.asList(header);
        return new RequestValue(requestHeader, "");
    }
}
