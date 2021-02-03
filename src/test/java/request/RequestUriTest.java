package request;

import annotation.web.RequestMethod;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestUriTest {

    private RequestUri requestUri;

    @BeforeEach
    public void setup(){
        String line = "GET /user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net HTTP/1.1\n";
        requestUri = RequestUri.from(line);
    }

    @DisplayName("주어진 첫 라인에서 메서드를 추출하여 getRequestMethod 로 이를 반환한다.")
    @Test
    public void 요청_메소드_테스트(){
        assertThat(requestUri.getRequestMethod()).isEqualTo(RequestMethod.GET);
    }

    @DisplayName("주어진 첫 라인에서 uri를 추출하여 getPath 로 이를 반환한다.")
    @Test
    public void 요청_uri_테스트(){
        assertThat(requestUri.getPath()).isEqualTo("/user/create");
    }

    @DisplayName("주어진 첫 라인에서 파라미터를 추출하여 getUriValue 로 키에 해당하는 value 를 반환한다.")
    @Test
    public void 요청_파라미터_테스트(){
        Optional<String> expected = Optional.of("javajigi");
        assertThat(requestUri.getUriValue("userId")).isEqualTo(expected);
    }
}
