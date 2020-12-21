package webserver;

import dto.RequestValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ResponseTest {

    private Request request;

    @BeforeEach
    private void setup() {
        List<String> requestHeader = Arrays.asList(
                "POST /user/create HTTP/1.1",
                "Host: localhost:8080",
                "Connection: keep-alive",
                "Content-Length: 59",
                "Content-Type: application/x-www-form-urlencoded",
                "Accept: */*",
                "Cookie: JSESSIONID=DFCD469C0155FA0C17E9BCC6A21A446E; logined=true; grafana_session=fb29c4298834fd1e5d8e40c400266353",
                "",
                "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net"
        );
        String body = "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";

        request = Request.of(new RequestValue(requestHeader, body));
    }

    @DisplayName("Response 생성 테스트")
    @Test
    void create() {
        Response response = Response.of(request);

        assertThat(response).isEqualToComparingFieldByField(Response.of(request));
    }

    @DisplayName("Response 생성 테스트 : 리다이렉트")
    @Test
    void ofDirect() {
        Response response = Response.of(request);
        response.sendRedirect("/index.html");

        assertThat(response.getAddHttpDesc().size()).isEqualTo(1);
        assertThat(response.getAddHttpDesc().get(0)).contains("Location: /index.html");
    }
}
