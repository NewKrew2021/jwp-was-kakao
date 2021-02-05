package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import view.InputView;

import java.io.FileInputStream;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("HTTP 요청 관련 기능")
class HttpRequestTest {
    private static final String TEST_RESOURCE_PATH = "./src/test/resources/";

    @Test
    void request_restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        String resourceUrl = "https://edu.nextstep.camp";
        ResponseEntity<String> response = restTemplate.getForEntity(resourceUrl + "/c/4YUvqn9V", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void request_GET() throws Exception {
        // given
        InputStream in = new FileInputStream(TEST_RESOURCE_PATH + "Http_GET.txt");
        InputView inputView = InputView.from(in);

        // when
        HttpRequest request = inputView.getHttpRequest();

        // then
        assertEquals("GET", request.getMethod().name());
        assertEquals("/user/create", request.getPath());
        assertEquals("keep-alive", request.getHeader("Connection"));
        assertEquals("javajigi", request.getParameter("userId"));
    }

    @Test
    void request_POST() throws Exception {
        // given
        InputStream in = new FileInputStream(TEST_RESOURCE_PATH + "Http_POST.txt");
        InputView inputView = InputView.from(in);

        // when
        HttpRequest request = inputView.getHttpRequest();

        // then
        assertEquals("POST", request.getMethod().name());
        assertEquals("/user/create", request.getPath());
        assertEquals("keep-alive", request.getHeader("Connection"));
        assertEquals("javajigi", request.getParameter("userId"));
    }
}
